package com.momiji.gateway.frontend

import com.momiji.gateway.controller.SendTextMessageRequest

data class SentMessage(
    val nativeId: String,
)

interface Frontend {

    fun sendText(request: SendTextMessageRequest): SentMessage
}
