package com.momiji.gateway.controller

import com.momiji.gateway.controller.dto.ReceivedMessage
import com.momiji.gateway.service.MessageReceiverService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(
    path = ["inbound"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class MessageReceiverController(
    private val messageReceiverService: MessageReceiverService,
) {

    @PostMapping("messages")
    fun receiveMessage(@RequestBody message: ReceivedMessage) {
        messageReceiverService.receive(message)
    }
}
