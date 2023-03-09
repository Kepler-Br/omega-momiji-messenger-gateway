package com.momiji.gateway.mapper

import com.momiji.gateway.inbound.api.model.ReceivedMessage
import com.momiji.gateway.repository.entity.MessageEntity
import org.springframework.stereotype.Component

@Component
class MessageMapper {

    fun map(message: ReceivedMessage): MessageEntity {
        return MessageEntity(
            frontend = message.frontend,
            nativeId = message.id,
            text = message.text,
        )
    }
}
