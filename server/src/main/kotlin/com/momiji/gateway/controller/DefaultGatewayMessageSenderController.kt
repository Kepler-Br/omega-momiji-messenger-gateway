package com.momiji.gateway.controller

import com.momiji.api.common.model.BasicResponse
import com.momiji.api.common.model.SendMessageResponse
import com.momiji.api.gateway.outbound.GatewayMessageSenderController
import com.momiji.api.gateway.outbound.model.FrontendNamesResponse
import com.momiji.api.gateway.outbound.model.SendTextMessageRequest
import com.momiji.gateway.service.MessageSenderService
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
    private val messageSenderService: MessageSenderService,
) : GatewayMessageSenderController {

    override fun sendText(@RequestBody request: SendTextMessageRequest): SendMessageResponse {
        return messageSenderService.sendText(request = request)
    }

    override fun sendTypingAction(frontend: String, chatId: String): BasicResponse {
        return messageSenderService.sendTypingAction(frontend = frontend, chatId = chatId)
    }

    override fun getFrontendNames(): FrontendNamesResponse {
        return messageSenderService.getFrontendNames()
    }
}
