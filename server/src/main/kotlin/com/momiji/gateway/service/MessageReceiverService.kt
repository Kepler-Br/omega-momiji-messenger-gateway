package com.momiji.gateway.service

import com.momiji.api.bot.BotReceiveMessageController
import com.momiji.api.bot.model.NewMessageRequest
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
import com.momiji.gateway.repository.entity.UserEntity
import io.micrometer.observation.Observation
import io.micrometer.observation.ObservationRegistry
import java.time.LocalDateTime
import java.util.concurrent.Executors
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.stereotype.Service
import com.momiji.gateway.repository.entity.ChatEntity as ChatModel


@Service
class MessageReceiverService(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
    private val chatMapper: ChatMapper,
    private val userMapper: UserMapper,
    private val messageMapper: MessageMapper,
    private val txExecutor: TxExecutor,
    private val botReceiveMessageController: BotReceiveMessageController,
    private val observationRegistry: ObservationRegistry,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val threadExecutor = Executors.newFixedThreadPool(2)


    private fun saveOrGetChat(chat: ReceivedChat, messengerFrontend: String): ChatModel {
        val mappedChat =
            chatMapper.map(chat, messengerFrontend).apply { createdAt = LocalDateTime.now() }

        val savedChat = try {
            txExecutor.new {
                chatRepository.save(mappedChat)
            }
        } catch (e: DbActionExecutionException) {
            try {
                chatRepository.getByFrontendAndNativeId(
                    frontend = messengerFrontend,
                    nativeId = chat.id
                )
            } catch (e1: RuntimeException) {
                // get failed. Throw original exception
                throw e
            }
        }

        // Update DB if info changed
        return if (mappedChat.title != savedChat.title) {
            chatRepository.save(
                savedChat.apply { this.title = mappedChat.title }
            )
        } else {
            savedChat
        }
    }

    private fun saveOrGetUser(user: ReceivedUser, messengerFrontend: String): UserEntity {
        val mappedUser =
            userMapper.map(user, messengerFrontend).apply { createdAt = LocalDateTime.now() }

        val savedUser = try {
            txExecutor.new {
                userRepository.save(mappedUser)
            }
        } catch (e: DbActionExecutionException) {
            try {
                userRepository.getByFrontendAndNativeId(
                    frontend = messengerFrontend,
                    nativeId = user.id
                )
            } catch (e1: RuntimeException) {
                // get failed. Throw original exception
                throw e
            }
        }

        // Update DB if info changed
        return if (mappedUser.fullname != savedUser.fullname
            || mappedUser.username != savedUser.username
        ) {
            userRepository.save(
                savedUser.apply {
                    this.fullname = mappedUser.fullname
                    this.username = mappedUser.username
                }
            )
        } else {
            savedUser
        }
    }

    /**
     *
     * @return true if message was updated, false otherwise
     */
    private fun updateOrSaveNewMessage(
        message: ReceivedMessage,
        chatId: Long,
        userId: Long
    ): Boolean {
        val mappedMessage = messageMapper.map(message).apply {
            this.chatId = chatId
            this.userId = userId
            this.createdAt = LocalDateTime.now()
        }

        val savedMessage = try {
            txExecutor.new {
                messageRepository.save(mappedMessage)
            }
        } catch (e: DbActionExecutionException) {
            try {
                messageRepository.getByFrontendAndNativeId(
                    frontend = message.frontend,
                    nativeId = message.id
                )
            } catch (e1: RuntimeException) {
                // get failed. Throw original exception
                throw e
            }
        }

        // Update DB if info changed
        return if (savedMessage.text != mappedMessage.text) {
            messageRepository.save(
                savedMessage.apply {
                    this.text = mappedMessage.text
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
    private fun saveMessage(message: ReceivedMessage): Boolean {
        val chat = saveOrGetChat(message.chat, message.frontend)
        val user = saveOrGetUser(message.author, message.frontend)

        return updateOrSaveNewMessage(message = message, chatId = chat.id!!, userId = user.id!!)
    }

    fun receive(message: ReceivedMessage) {
        val messageWasUpdated = txExecutor.new { saveMessage(message) }

        val request = NewMessageRequest(
            frontend = message.frontend,
            chatId = message.chat.id,
            messageId = message.id,
            isUpdated = messageWasUpdated,
        )

        threadExecutor.submit {
            // TODO: TraceID and SpanID are not passed with feign client calls
            val observation = Observation.createNotStarted("newMessage", observationRegistry)

            observation.observe {
                try {
                    botReceiveMessageController.newMessage(request)
                } catch (ex: RuntimeException) {
                    logger.error(
                        "Exception has occurred during sending message in thread executor",
                        ex
                    )
                }
            }

        }
    }
}
