package com.momiji.gateway.repository.util

import com.momiji.gateway.api.dto.InboxChat
import com.momiji.gateway.api.dto.InboxMessagesRequest
import com.momiji.gateway.api.dto.InboxUser
import com.momiji.gateway.repository.entity.ChatEntity
import com.momiji.gateway.repository.entity.MessageEntity
import com.momiji.gateway.repository.entity.UserEntity
import com.momiji.gateway.repository.entity.enumerator.ChatType


fun InboxMessagesRequest.toMessageEntity(frontend: String): MessageEntity {
    return MessageEntity(
        text = this.text,
        s3Bucket = null,
        s3Key = null,
        mediaType = null,
        userId = null,
        replyToMessageNativeId = this.replyToMessageId,
        frontend = frontend,
        nativeId = this.id,
    )
}

fun InboxUser.toUserEntity(frontend: String): UserEntity {
    val fullName = if (this.lastName != null) {
        "$firstName $lastName"
    } else {
        this.firstName
    }

    return UserEntity(
        username = this.username,
        fullName = fullName,
        frontend = frontend,
        nativeId = this.id,
    )
}

fun InboxChat.toChatEntity(frontend: String): ChatEntity {
    return ChatEntity(
        title = this.title,
        frontend = frontend,
        nativeId = this.id,
        type = this.type.toEntityType()
    )
}

fun InboxChat.Type.toEntityType(): ChatType {
    return when (this) {
        InboxChat.Type.PRIVATE -> ChatType.PRIVATE
        InboxChat.Type.BOT -> ChatType.PRIVATE
        InboxChat.Type.GROUP -> ChatType.GROUP
        InboxChat.Type.SUPERGROUP -> ChatType.GROUP
        InboxChat.Type.CHANNEL -> ChatType.GROUP
    }
}
