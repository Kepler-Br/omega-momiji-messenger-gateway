package com.momiji.gateway.controller

import com.momiji.gateway.repository.entity.enumerator.MessengerFrontend
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

data class SendTextMessageRequest(
    val frontend: MessengerFrontend,
    val text: String,
    // Should be a native chat id
    val chatId: String,
    // Should be a native message id
    val replyToMessageId: String?,
)

@Controller
@RestController("outbound")
class MessageSenderController {

    @PostMapping("text-messages")
    fun sendText(request: SendTextMessageRequest) {

    }
}
