package com.momiji.gateway.external.api

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

data class SendMessageRequest(
    val text: String,
    val replyTo: String?,
    val chatId: String,
)

data class SendMessageResponse(
    val errorMessage: String?,
    val messageId: String?,
)

interface FrontendApi {

    @RequestMapping(
        "/messages",
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun sendTextMessage(body: SendMessageRequest): SendMessageResponse
}
