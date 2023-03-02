package com.momiji.gateway.service

import com.momiji.gateway.controller.dto.ReceivedMessage
import com.momiji.gateway.controller.dto.ReceivedUser
import com.momiji.gateway.mapper.ChatMapper
import com.momiji.gateway.mapper.MessageMapper
import com.momiji.gateway.mapper.UserMapper
import com.momiji.gateway.repository.ChatRepository
import com.momiji.gateway.repository.MessageRepository
import com.momiji.gateway.repository.UserRepository
import com.momiji.gateway.repository.entity.MessageEntity
import com.momiji.gateway.repository.entity.UserEntity
import com.momiji.gateway.repository.entity.enumerator.MessengerFrontend
import org.springframework.stereotype.Service
import com.momiji.gateway.controller.dto.ReceivedChat as ChatDto
import com.momiji.gateway.repository.entity.ChatEntity as ChatModel

@Service
class MessageReceiverService(
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository,
    private val chatMapper: ChatMapper,
    private val userMapper: UserMapper,
    private val messageMapper: MessageMapper,
) {

    private fun saveOrGetChat(chat: ChatDto, messengerFrontend: MessengerFrontend): ChatModel {
        // TODO: What exception is thrown if I try to save not unique field?
        val savedChat = chatMapper.map(chat, messengerFrontend)

        return chatRepository.save(savedChat)
    }

    private fun saveOrGetUser(user: ReceivedUser, messengerFrontend: MessengerFrontend): UserEntity {
        // TODO: What exception is thrown if I try to save not unique field?
        val savedUser = userMapper.map(user, messengerFrontend)

        return userRepository.save(savedUser)
    }

    private fun saveOrGetMessage(message: ReceivedMessage): MessageEntity {
        val messageEntity = messageMapper.map(message)

        return messageRepository.save(messageEntity)
    }

    fun receive(message: ReceivedMessage) {
        val chat = saveOrGetChat(message.chat, message.origin)
        val user = saveOrGetUser(message.author, message.origin)
        val message = saveOrGetMessage(message)
    }
}
