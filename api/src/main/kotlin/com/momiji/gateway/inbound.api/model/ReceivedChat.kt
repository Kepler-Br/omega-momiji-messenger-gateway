package com.momiji.gateway.inbound.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.momiji.gateway.inbound.api.model.enumerator.ChatType

data class ReceivedChat(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("type")
    var type: ChatType,
)
