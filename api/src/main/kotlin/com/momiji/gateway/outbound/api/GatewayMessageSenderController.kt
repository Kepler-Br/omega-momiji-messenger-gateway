package com.momiji.gateway.outbound.api

import com.momiji.gateway.common.api.model.SendMessageResponse
import com.momiji.gateway.outbound.api.model.FrontendNamesResponse
import com.momiji.gateway.outbound.api.model.SendTextMessageRequest
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping(
    path = ["outbound"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
interface GatewayMessageSenderController {

    @PostMapping("text-messages")
    fun sendText(request: SendTextMessageRequest): SendMessageResponse

    @GetMapping("frontends")
    fun getFrontendNames(): FrontendNamesResponse
}
