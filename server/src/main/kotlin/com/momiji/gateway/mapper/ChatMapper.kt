package com.momiji.gateway.mapper

import com.momiji.api.gateway.inbound.model.ReceivedChat
import com.momiji.gateway.repository.entity.ChatEntity
import com.momiji.gateway.repository.entity.enumerator.ChatType
import org.springframework.stereotype.Component
import com.momiji.api.gateway.inbound.model.enumerator.ChatType as ApiChatType
import com.momiji.gateway.repository.entity.enumerator.ChatType as EntityChatType

@Component
class ChatMapper {

    private fun mapChatType(value: EntityChatType): ApiChatType {
        return when (value) {
            EntityChatType.PRIVATE -> ApiChatType.PRIVATE
            EntityChatType.GROUP -> ApiChatType.GROUP
            ChatType.UNKNOWN -> throw UnknownMappingEnum("Unable to map enum value: $value")
        }
    }

    private fun mapChatType(value: ApiChatType): EntityChatType {
        return when (value) {
            ApiChatType.PRIVATE -> EntityChatType.PRIVATE
            ApiChatType.GROUP -> EntityChatType.GROUP
        }
    }

    fun map(chat: ChatEntity): ReceivedChat {
        return ReceivedChat(
            id = chat.nativeId,
            title = chat.title,
            type = mapChatType(chat.type),
        )
    }

    fun map(receivedChat: ReceivedChat, messengerFrontend: String): ChatEntity {
        return ChatEntity(
            nativeId = receivedChat.id,
            title = receivedChat.title,
            frontend = messengerFrontend,
            type = mapChatType(receivedChat.type),
        )
    }
}
