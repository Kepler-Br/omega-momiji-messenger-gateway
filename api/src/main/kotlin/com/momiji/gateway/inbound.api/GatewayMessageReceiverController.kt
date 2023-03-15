package com.momiji.gateway.inbound.api

import com.momiji.gateway.inbound.api.model.ReceivedMessage
import org.springframework.web.bind.annotation.PutMapping


interface GatewayMessageReceiverController {

    @PutMapping("messages")
    fun receiveMessage(message: ReceivedMessage)
}
