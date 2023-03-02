package com.momiji.gateway.mapper

import com.momiji.gateway.controller.dto.ReceivedMessage
import com.momiji.gateway.repository.entity.MessageEntity
import org.springframework.stereotype.Component

@Component
class MessageMapper {

    fun map(message: ReceivedMessage): MessageEntity {
        return MessageEntity(
            origin = message.origin,
            nativeId = message.messageId,
            text = message.text,
        )
    }
}
