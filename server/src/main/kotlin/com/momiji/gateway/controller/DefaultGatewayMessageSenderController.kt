package com.momiji.gateway.controller

import com.momiji.gateway.common.api.model.SendMessageRequest
import com.momiji.gateway.common.api.model.SendMessageResponse
import com.momiji.gateway.frontend.FrontendContainer
import com.momiji.gateway.outbound.api.GatewayMessageSenderController
import com.momiji.gateway.outbound.api.model.FrontendNamesResponse
import com.momiji.gateway.outbound.api.model.SendTextMessageRequest
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
