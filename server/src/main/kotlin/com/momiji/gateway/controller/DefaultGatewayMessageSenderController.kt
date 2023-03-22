package com.momiji.gateway.controller

import com.momiji.api.common.model.BasicResponse
import com.momiji.api.common.model.ResponseStatus
import com.momiji.api.common.model.SendMessageRequest
import com.momiji.api.common.model.SendMessageResponse
import com.momiji.api.frontend.FrontendContainer
import com.momiji.api.gateway.outbound.GatewayMessageSenderController
import com.momiji.api.gateway.outbound.model.FrontendNamesResponse
import com.momiji.api.gateway.outbound.model.SendTextMessageRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun sendText(@RequestBody request: SendTextMessageRequest): SendMessageResponse {
        logger.debug("Sending text message: $request")

        return frontendContainer.sendTextMessage(
            body = SendMessageRequest(
                text = request.text,
                replyTo = request.replyToMessageId,
                chatId = request.chatId,
            ),
            frontend = request.frontend,
        )
    }

    override fun sendTypingAction(frontend: String, chatId: String): BasicResponse {
        logger.debug("Sending typing action to frontend \"$frontend\", chat id \"$chatId\"")

        frontendContainer.sendTypingAction(chatId = chatId, frontend = frontend)

        return BasicResponse(
            status = ResponseStatus.OK,
        )
    }

    override fun getFrontendNames(): FrontendNamesResponse {
        logger.debug("Get frontend names")

        return FrontendNamesResponse(
            frontendContainer.getFrontendNames()
        )
    }
}
