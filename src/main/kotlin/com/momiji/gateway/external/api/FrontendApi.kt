package com.momiji.gateway.external.api

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

data class SendMessageRequest(
    @JsonProperty("text")
    val text: String,
    @JsonProperty("reply_to")
    val replyTo: String?,
    @JsonProperty("chat_id")
    val chatId: String,
)

data class SendMessageResponse(
    @JsonProperty("error_message")
    val errorMessage: String?,
    @JsonProperty("message_id")
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
