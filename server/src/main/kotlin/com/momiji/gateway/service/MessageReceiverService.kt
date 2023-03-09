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
        return try {
            return txExecutor.new {
                chatRepository.save(
                    chatMapper.map(chat, messengerFrontend)
                )
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
    }

    private fun saveOrGetUser(user: ReceivedUser, messengerFrontend: String): UserEntity {
        return try {
            return txExecutor.new {
                userRepository.save(
                    userMapper.map(user, messengerFrontend)
                )
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
    }

    private fun saveOrGetMessage(
        message: ReceivedMessage,
        chatId: Long,
        userId: Long
    ): MessageEntity {
        return try {
            return txExecutor.new {
                messageRepository.save(
                    messageMapper.map(message).apply {
                        this.chatId = chatId
                        this.userId = userId
                    }
                )
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
    }

    fun receive(message: ReceivedMessage) {
        val chat = saveOrGetChat(message.chat, message.frontend)
        val user = saveOrGetUser(message.author, message.frontend)
        saveOrGetMessage(message = message, chatId = chat.id!!, userId = user.id!!)
    }
}
