package com.momiji.gateway.inbound.api

import com.momiji.gateway.inbound.api.model.ReceivedMessage
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping(
    path = ["inbound"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
interface GatewayMessageReceiverController {

    @PutMapping("messages")
    fun receiveMessage(message: ReceivedMessage)
}
