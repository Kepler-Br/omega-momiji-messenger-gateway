package com.momiji.gateway.service

import com.google.common.hash.Hashing
import com.momiji.api.gateway.inbound.model.ReceivedChat
import com.momiji.api.gateway.inbound.model.ReceivedMessage
import com.momiji.api.gateway.inbound.model.ReceivedUser
import com.momiji.api.gateway.inbound.model.enumerator.MediaType
import com.momiji.gateway.mapper.ChatMapper
import com.momiji.gateway.mapper.MessageMapper
import com.momiji.gateway.mapper.UserMapper
import com.momiji.gateway.repository.ChatRepository
import com.momiji.gateway.repository.MessageRepository
import com.momiji.gateway.repository.TxExecutor
import com.momiji.gateway.repository.UserRepository
import com.momiji.gateway.repository.entity.ChatEntity
import com.momiji.gateway.repository.entity.MessageEntity
import com.momiji.gateway.repository.entity.UserEntity
import com.momiji.gateway.repository.entity.enumerator.ChatType
import java.util.Base64
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

@Service
class DataService(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
    private val chatMapper: ChatMapper,
    private val userMapper: UserMapper,
    private val messageMapper: MessageMapper,
    private val txExecutor: TxExecutor,
    private val s3Client: S3Client,
) {


    private fun createOrUpdateChat(chat: ReceivedChat, frontendName: String): ChatEntity {
        val mappedChat =
            chatMapper.map(chat, frontendName)

        return createOrUpdateChat(mappedChat, frontendName)
    }

    private fun createOrUpdateChat(chat: ChatEntity, frontendName: String): ChatEntity {
        val savedChat = try {
            txExecutor.new {
                chatRepository.save(chat)
            }
        } catch (e: DbActionExecutionException) {
            try {
                chatRepository.getByFrontendAndNativeId(
                    frontend = frontendName,
                    nativeId = chat.nativeId
                )
            } catch (e1: Exception) {
                // get failed. Throw original exception
                throw e
            }
        }

        // Update DB if info changed
        return if (chat.title != savedChat.title) {
            chatRepository.save(
                savedChat.apply { this.title = chat.title }
            )
        } else {
            savedChat
        }
    }

    private fun createOrUpdateUser(user: ReceivedUser, frontendName: String): UserEntity {
        val mappedUser =
            userMapper.map(user, frontendName)

        return createOrUpdateUser(mappedUser)
    }

    fun createOrUpdateUser(user: UserEntity): UserEntity {
        val savedUser = try {
            txExecutor.new {
                userRepository.save(user)
            }
        } catch (e: DbActionExecutionException) {
            try {
                userRepository.getByFrontendAndNativeId(
                    frontend = user.frontend,
                    nativeId = user.nativeId
                )
            } catch (e1: Exception) {
                // get failed. Throw original exception
                throw e
            }
        }

        // Update DB if info changed
        return if (user.fullname != savedUser.fullname
            || user.username != savedUser.username
        ) {
            userRepository.save(
                savedUser.apply {
                    this.fullname = user.fullname
                    this.username = user.username
                }
            )
        } else {
            savedUser
        }
    }

    fun createDefaultOrGetChat(nativeId: String, frontend: String): ChatEntity {
        return try {
            txExecutor.new {
                chatRepository.save(
                    ChatEntity(
                        title = "Unknown",
                        nativeId = nativeId,
                        frontend = frontend,
                        type = ChatType.UNKNOWN
                    )
                )
            }
        } catch (e: DbActionExecutionException) {
            try {
                chatRepository.getByFrontendAndNativeId(
                    frontend = frontend,
                    nativeId = nativeId
                )
            } catch (e1: Exception) {
                // get failed. Throw original exception
                throw e
            }
        }
    }

    /**
     *
     * @return true if message was updated, false otherwise
     */
    private fun updateOrSaveNewMessage(
        message: ReceivedMessage,
        chatId: Long,
        userId: Long,
        s3Bucket: String?,
        s3Key: String?,
    ): Boolean {
        val mappedMessage = messageMapper.map(message).apply {
            this.chatId = chatId
            this.userId = userId
            this.s3Bucket = s3Bucket
            this.s3Key = s3Key
        }

        return updateOrSaveNewMessage(message = mappedMessage)
    }

    /**
     *
     * @return true if message was updated, false otherwise
     */
    fun updateOrSaveNewMessage(
        message: MessageEntity,
    ): Boolean {
        val savedMessage = try {
            txExecutor.new {
                messageRepository.save(message)
            }
        } catch (e: DbActionExecutionException) {
            try {
                messageRepository.getByFrontendAndNativeId(
                    frontend = message.frontend,
                    nativeId = message.nativeId
                )
            } catch (e1: Exception) {
                // get failed. Throw original exception
                throw e
            }
        }

        // Update DB if info changed
        return if (savedMessage.text != message.text) {
            messageRepository.save(
                savedMessage.apply {
                    this.text = message.text
                }
            )

            true
        } else {
            false
        }
    }

    private fun bucketNameFromMediaType(mediaType: MediaType): String {
        return when (mediaType) {
            MediaType.AUDIO,
            MediaType.VOICE -> "audio"

            MediaType.STICKER,
            MediaType.PHOTO -> "image"

            MediaType.VIDEO,
            MediaType.GIF,
            MediaType.VIDEO_NOTE -> "video"
        }
    }

    private fun saveMedia(message: ReceivedMessage): Pair<String, String>? {
        if (message.mediaBytes == null || message.mediaType == null) {
            return null
        }

        val decoded = Base64.getDecoder().decode(message.mediaBytes)

        val hashHex = Hashing.sha256()
            .hashBytes(decoded)
            .toString()

        val bucket = bucketNameFromMediaType(message.mediaType!!)

        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(bucket)
                .key(hashHex)
                .build(),
            RequestBody.fromBytes(decoded)
        )

        return Pair(bucket, hashHex)
    }

    /**
     *
     * @return true if message was updated, false otherwise
     */
    fun saveMessage(message: ReceivedMessage): Boolean {
        val chat = createOrUpdateChat(message.chat, message.frontend)
        val user = createOrUpdateUser(message.author, message.frontend)
        val mediaLink = saveMedia(message)

        return updateOrSaveNewMessage(
            message = message,
            chatId = chat.id!!,
            userId = user.id!!,
            s3Bucket = mediaLink?.first,
            s3Key = mediaLink?.second,
        )
    }
}
