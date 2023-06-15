package com.momiji.gateway.repository.entity

import com.momiji.gateway.repository.entity.enumerator.MediaType
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table(
    name = "messages",
    schema = "gateway",
)
data class MessageEntity(
    @Id
    var id: Long? = null,
    var text: String? = null,
    var s3Bucket: String? = null,
    var s3Key: String? = null,
    var mediaType: MediaType? = null,
    var chatId: Long? = null,
    var userId: Long? = null,
    var createdAt: LocalDateTime? = null,
    var replyToMessageNativeId: String? = null,
    var frontend: String,
    var nativeId: String,
)
