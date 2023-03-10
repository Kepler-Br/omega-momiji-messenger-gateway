package com.momiji.gateway.repository.entity

import com.momiji.gateway.repository.entity.enumerator.MediaType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table("messages")
data class MessageEntity(
    @Id
    var id: Long? = null,
    var text: String? = null,
    var mediaLink: String? = null,
    var mediaType: MediaType? = null,
    var chatId: Long? = null,
    var userId: Long? = null,
    var frontend: String,
    var nativeId: String,
)
