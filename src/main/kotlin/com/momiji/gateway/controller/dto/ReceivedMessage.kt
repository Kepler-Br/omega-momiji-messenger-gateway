package com.momiji.gateway.controller.dto

data class ReceivedMessage(
    val id: String,
    val author: ReceivedUser,
    val chat: ReceivedChat,
    val frontend: String,
    val text: String?,
)
