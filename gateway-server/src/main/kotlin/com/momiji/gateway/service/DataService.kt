package com.momiji.gateway.service

import com.momiji.gateway.repository.ChatRepository
import com.momiji.gateway.repository.MessageRepository
import com.momiji.gateway.repository.UserRepository
import com.momiji.gateway.repository.entity.ChatEntity
import com.momiji.gateway.repository.entity.MessageEntity
import com.momiji.gateway.repository.entity.UserEntity
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.stereotype.Service

@Service
class DataService(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
) {

    fun createOrUpdateChat(chat: ChatEntity): ChatEntity {
        val savedChat = try {
            chatRepository.save(chat)
        } catch (e: DbActionExecutionException) {
            try {
                chatRepository.getByFrontendAndNativeId(
                    frontend = chat.frontend,
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

    fun createOrUpdateUser(user: UserEntity): UserEntity {
        val savedUser = try {
            userRepository.save(user)
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
        return if (user.fullName != savedUser.fullName
            || user.username != savedUser.username
        ) {
            userRepository.save(
                savedUser.apply {
                    this.fullName = user.fullName
                    this.username = user.username
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
    fun createOrUpdateMessage(message: MessageEntity): Boolean {
        val savedMessage = try {
            messageRepository.save(message)
        } catch (e: DbActionExecutionException) {
            try {
                messageRepository.getByFrontendAndNativeIdAndChatIdAndUserId(
                    frontend = message.frontend,
                    nativeId = message.nativeId,
                    chatId = message.chatId!!,
                    userId = message.userId!!
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

}
