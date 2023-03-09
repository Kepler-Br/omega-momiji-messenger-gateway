package com.momiji.gateway.controller

import com.momiji.gateway.common.api.model.SendMessageRequest
import com.momiji.gateway.common.api.model.SendMessageResponse
import com.momiji.gateway.frontend.FrontendContainer
import com.momiji.gateway.outbound.api.MessageSenderController
import com.momiji.gateway.outbound.api.model.FrontendNamesResponse
import com.momiji.gateway.outbound.api.model.SendTextMessageRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("outbound")
@RequestMapping(
    path = ["outbound"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class DefaultMessageSenderController(
    private val frontendContainer: FrontendContainer
) : MessageSenderController {

    @PostMapping("text-messages")
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

    @GetMapping("frontends")
    override fun getFrontendNames(): FrontendNamesResponse {
        return FrontendNamesResponse(
            frontendContainer.getFrontendNames()
        )
    }
}