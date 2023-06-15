package com.momiji.gateway.controller

import com.momiji.api.common.model.ChatAdminsResponse
import com.momiji.api.common.model.SendMessageResponse
import com.momiji.api.common.model.SimpleResponse
import com.momiji.api.gateway.outbound.GatewayMessageSenderClient
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
) : GatewayMessageSenderClient {

    override fun sendText(@RequestBody request: SendTextMessageRequest): SendMessageResponse {
        return messageSenderService.sendText(request = request)
    }

    override fun sendTypingAction(frontend: String, chatId: String): SimpleResponse {
        return messageSenderService.sendTypingAction(frontend = frontend, chatId = chatId)
    }

    override fun getChatAdmins(chatId: String, frontend: String): ChatAdminsResponse {
        return messageSenderService.getChatAdmins(chatId = chatId, frontend = frontend)
    }

    override fun getFrontendNames(): FrontendNamesResponse {
        return messageSenderService.getFrontendNames()
    }
}
