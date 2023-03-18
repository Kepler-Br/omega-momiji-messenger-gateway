package com.momiji.gateway.mapper

import com.momiji.api.gateway.inbound.model.ReceivedMessage
import com.momiji.api.gateway.inbound.model.enumerator.MediaType
import com.momiji.gateway.repository.entity.enumerator.MediaType as EntityMediaType
import com.momiji.gateway.repository.entity.MessageEntity
import org.springframework.stereotype.Component

@Component
class MessageMapper {

    private fun map(value: MediaType?): EntityMediaType? {
        return when (value) {
            MediaType.STICKER -> EntityMediaType.STICKER
            MediaType.AUDIO -> EntityMediaType.AUDIO
            MediaType.VOICE -> EntityMediaType.VOICE
            MediaType.PHOTO -> EntityMediaType.PHOTO
            MediaType.VIDEO -> EntityMediaType.VIDEO
            MediaType.GIF -> EntityMediaType.GIF
            MediaType.VIDEO_NOTE -> EntityMediaType.VIDEO_NOTE
            null -> null
        }
    }

    fun map(value: ReceivedMessage): MessageEntity {
        return MessageEntity(
            frontend = value.frontend,
            nativeId = value.id,
            text = value.text,
            mediaType = map(value.mediaType),
        )
    }
}
