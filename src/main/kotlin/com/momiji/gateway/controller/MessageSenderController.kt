package com.momiji.gateway.controller

import com.fasterxml.jackson.annotation.JsonProperty
import com.momiji.gateway.controller.dto.SendTextMessageRequest
import com.momiji.gateway.external.api.SendMessageRequest
import com.momiji.gateway.external.api.SendMessageResponse
import com.momiji.gateway.frontend.FrontendContainer
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class FrontendNamesResponse(
    @JsonProperty("names")
    val names: List<String>,
)

@RestController("outbound")
@RequestMapping(
    path = ["outbound"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class MessageSenderController(
    private val frontendContainer: FrontendContainer
) {

    @PostMapping("text-messages")
    fun sendText(@RequestBody request: SendTextMessageRequest): SendMessageResponse {
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
    fun getFrontendNames(): FrontendNamesResponse {
        return FrontendNamesResponse(
            frontendContainer.getFrontendNames()
        )
    }
}
