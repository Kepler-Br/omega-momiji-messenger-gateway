package com.momiji.gateway.controller.dto

import com.momiji.gateway.repository.entity.enumerator.MessengerFrontend

data class ReceivedMessage(
    val author: ReceivedUser,
    val chat: ReceivedChat,
    val origin: MessengerFrontend,
    val messageId: String,
    val text: String?,
)
