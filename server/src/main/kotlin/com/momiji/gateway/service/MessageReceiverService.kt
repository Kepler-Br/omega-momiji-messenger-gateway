package com.momiji.gateway.service

import com.momiji.gateway.inbound.api.model.ReceivedChat
import com.momiji.gateway.inbound.api.model.ReceivedMessage
import com.momiji.gateway.inbound.api.model.ReceivedUser
import com.momiji.gateway.mapper.ChatMapper
import com.momiji.gateway.mapper.MessageMapper
import com.momiji.gateway.mapper.UserMapper
import com.momiji.gateway.repository.ChatRepository
import com.momiji.gateway.repository.MessageRepository
import com.momiji.gateway.repository.TxExecutor
import com.momiji.gateway.repository.UserRepository
import com.momiji.gateway.repository.entity.MessageEntity
import com.momiji.gateway.repository.entity.UserEntity
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
) {

    private fun saveOrGetChat(chat: ReceivedChat, messengerFrontend: String): ChatModel {
        val mappedChat = chatMapper.map(chat, messengerFrontend)

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
        val mappedUser = userMapper.map(user, messengerFrontend)

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

    private fun saveOrGetMessage(
        message: ReceivedMessage,
        chatId: Long,
        userId: Long
    ): MessageEntity {
        val mappedMessage = messageMapper.map(message).apply {
            this.chatId = chatId
            this.userId = userId
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
        } else {
            savedMessage
        }
    }

    @Transactional
    fun receive(message: ReceivedMessage) {
        val chat = saveOrGetChat(message.chat, message.frontend)
        val user = saveOrGetUser(message.author, message.frontend)
        saveOrGetMessage(message = message, chatId = chat.id!!, userId = user.id!!)
    }
}
