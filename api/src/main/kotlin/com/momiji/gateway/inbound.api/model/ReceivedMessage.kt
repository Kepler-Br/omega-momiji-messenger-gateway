package com.momiji.gateway.inbound.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.momiji.gateway.inbound.api.model.enumerator.MessageType


data class ReceivedMessage(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("author")
    val author: ReceivedUser,
    @JsonProperty("chat")
    val chat: ReceivedChat,
    @JsonProperty("frontend")
    val frontend: String,
    @JsonProperty("text")
    val text: String?,
    @JsonProperty("type")
    val type: MessageType,
    @JsonProperty("action_info")
    val actionInfo: ActionInfo?,
)
