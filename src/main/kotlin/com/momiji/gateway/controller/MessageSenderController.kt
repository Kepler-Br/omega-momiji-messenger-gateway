package com.momiji.gateway.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

data class SendTextMessageRequest(
    val frontend: String,
    val text: String,
    // Should be a native chat id
    val chatId: String,
    // Should be a native message id
    val replyToMessageId: String?,
)

@RestController("outbound")
class MessageSenderController {

    @PostMapping("text-messages")
    fun sendText(request: SendTextMessageRequest) {
        // TODO
    }
}
