package com.momiji.gateway.inbound.api

import com.momiji.gateway.inbound.api.model.ReceivedMessage


interface MessageReceiverController {

    fun receiveMessage(message: ReceivedMessage)
}
