package com.momiji.gateway.service

import com.momiji.api.gateway.inbound.model.ReceivedChat
import com.momiji.api.gateway.inbound.model.ReceivedMessage
import com.momiji.api.gateway.inbound.model.ReceivedUser
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
import java.time.LocalDateTime
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.stereotype.Service

@Service
class DataService(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
    private val chatMapper: ChatMapper,
    private val userMapper: UserMapper,
    private val messageMapper: MessageMapper,
    private val txExecutor: TxExecutor,
) {


    fun createOrUpdateChat(chat: ReceivedChat, frontendName: String): ChatEntity {
        val mappedChat =
            chatMapper.map(chat, frontendName).apply { createdAt = LocalDateTime.now() }

        return createOrUpdateChat(mappedChat, frontendName)
    }

    fun createOrUpdateChat(chat: ChatEntity, frontendName: String): ChatEntity {
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

    fun createOrUpdateUser(user: ReceivedUser, frontendName: String): UserEntity {
        val mappedUser =
            userMapper.map(user, frontendName).apply { createdAt = LocalDateTime.now() }

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
    fun updateOrSaveNewMessage(
        message: ReceivedMessage,
        chatId: Long,
        userId: Long
    ): Boolean {
        val mappedMessage = messageMapper.map(message).apply {
            this.chatId = chatId
            this.userId = userId
            this.createdAt = LocalDateTime.now()
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


    /**
     *
     * @return true if message was updated, false otherwise
     */
    fun saveMessage(message: ReceivedMessage): Boolean {
        val chat = createOrUpdateChat(message.chat, message.frontend)
        val user = createOrUpdateUser(message.author, message.frontend)

        return updateOrSaveNewMessage(message = message, chatId = chat.id!!, userId = user.id!!)
    }
}
