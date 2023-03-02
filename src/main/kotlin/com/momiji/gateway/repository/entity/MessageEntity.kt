package com.momiji.gateway.repository.entity

import com.momiji.gateway.repository.entity.enumerator.MessengerFrontend

data class MessageEntity(
    val userId: Long? = null,
    val chatId: Long? = null,
    val origin: MessengerFrontend,
    val nativeId: String,
    val text: String?,
)
