package com.momiji.gateway.common.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SendMessageRequest(
    @JsonProperty("text")
    val text: String,
    @JsonProperty("reply_to")
    val replyTo: String?,
    @JsonProperty("chat_id")
    val chatId: String,
)
