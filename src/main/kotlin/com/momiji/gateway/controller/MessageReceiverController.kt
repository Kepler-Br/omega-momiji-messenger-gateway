package com.momiji.gateway.controller

import com.momiji.gateway.controller.dto.ReceivedMessage
import com.momiji.gateway.service.MessageReceiverService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@Controller
@RestController("inbound")
class MessageReceiverController(
    private val messageReceiverService: MessageReceiverService,
) {

    @PostMapping("messages")
    fun receiveMessage(@RequestBody message: ReceivedMessage) {
        messageReceiverService.receive(message)
    }
}
