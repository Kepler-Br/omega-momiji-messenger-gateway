package com.momiji.gateway.mapper

import com.momiji.gateway.controller.dto.ReceivedChat
import com.momiji.gateway.repository.entity.ChatEntity
import com.momiji.gateway.repository.entity.enumerator.MessengerFrontend
import org.springframework.stereotype.Component

@Component
class ChatMapper {

    fun map(chat: ChatEntity): ReceivedChat {
        return ReceivedChat(
            id = chat.nativeId,
            title = chat.title,
            type = chat.type,
        )
    }

    fun map(receivedChat: ReceivedChat, messengerFrontend: MessengerFrontend): ChatEntity {
        return ChatEntity(
            nativeId = receivedChat.id,
            title = receivedChat.title,
            messenger = messengerFrontend,
            type = receivedChat.type,
        )
    }
}
