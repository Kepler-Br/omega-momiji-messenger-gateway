package com.momiji.gateway.controller

import com.momiji.api.gateway.inbound.api.GatewayMessageReceiverController
import com.momiji.api.gateway.inbound.api.model.ReceivedMessage
import com.momiji.gateway.service.MessageReceiverService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(
    path = ["inbound"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@RestController
class DefaultGatewayMessageReceiverController(
    private val messageReceiverService: MessageReceiverService,
) : GatewayMessageReceiverController {

    override fun receiveMessage(@RequestBody message: ReceivedMessage) {
        messageReceiverService.receive(message)
    }
}
