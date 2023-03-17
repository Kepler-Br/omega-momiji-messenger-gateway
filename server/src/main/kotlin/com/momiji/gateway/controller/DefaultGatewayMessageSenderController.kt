package com.momiji.gateway.controller

import com.momiji.api.common.model.SendMessageRequest
import com.momiji.api.common.model.SendMessageResponse
import com.momiji.api.frontend.FrontendContainer
import com.momiji.api.gateway.outbound.GatewayMessageSenderController
import com.momiji.api.gateway.outbound.model.FrontendNamesResponse
import com.momiji.api.gateway.outbound.model.SendTextMessageRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(
    path = ["outbound"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@RestController("outbound")
class DefaultGatewayMessageSenderController(
    private val frontendContainer: FrontendContainer
) : GatewayMessageSenderController {

    override fun sendText(@RequestBody request: SendTextMessageRequest): SendMessageResponse {
        return frontendContainer.sendTextMessage(
            body = SendMessageRequest(
                text = request.text,
                replyTo = request.replyToMessageId,
                chatId = request.chatId,
            ),
            frontend = request.frontend,
        )
    }

    override fun getFrontendNames(): FrontendNamesResponse {
        return FrontendNamesResponse(
            frontendContainer.getFrontendNames()
        )
    }
}
