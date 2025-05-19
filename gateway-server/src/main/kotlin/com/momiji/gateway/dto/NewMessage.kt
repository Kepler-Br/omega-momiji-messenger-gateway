package com.momiji.gateway.dto

data class NewMessage(
    val user: User,
    val chat: Chat,
    val forwardFrom: User,
    val frontend: String,
    val text: String?,
    val mentioned: Boolean,
    val replyToMessageId: String?,
    val mediaType: MediaType,
    val s3Bucket: String?,
    val s3Object: String?,
)
