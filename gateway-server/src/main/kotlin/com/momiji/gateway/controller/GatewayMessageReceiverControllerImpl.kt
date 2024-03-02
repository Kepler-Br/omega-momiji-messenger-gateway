package com.momiji.gateway.controller

import com.momiji.api.common.model.ResponseStatus
import com.momiji.api.common.model.SimpleResponse
import com.momiji.api.gateway.inbound.GatewayMessageReceiverClient
import com.momiji.api.gateway.inbound.model.ReceivedMessage
import com.momiji.gateway.service.MessageReceiverService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(
    path = ["inbound"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@RestController
class GatewayMessageReceiverControllerImpl(
    private val messageReceiverService: MessageReceiverService,
) : GatewayMessageReceiverClient {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun receiveMessage(@RequestBody message: ReceivedMessage): SimpleResponse {
        logger.debug("Incoming message: {}", message)

        messageReceiverService.receive(message)

        return SimpleResponse(
            status = ResponseStatus.OK,
        )
    }
}
