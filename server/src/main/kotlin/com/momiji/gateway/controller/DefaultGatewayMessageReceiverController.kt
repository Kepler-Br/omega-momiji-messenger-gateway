package com.momiji.gateway.controller

import com.momiji.gateway.inbound.api.GatewayMessageReceiverController
import com.momiji.gateway.inbound.api.model.ReceivedMessage
import com.momiji.gateway.service.MessageReceiverService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class DefaultGatewayMessageReceiverController(
    private val messageReceiverService: MessageReceiverService,
) : GatewayMessageReceiverController {

    override fun receiveMessage(@RequestBody message: ReceivedMessage) {
        messageReceiverService.receive(message)
    }
}
