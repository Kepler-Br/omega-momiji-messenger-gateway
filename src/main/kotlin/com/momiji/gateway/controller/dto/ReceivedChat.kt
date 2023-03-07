package com.momiji.gateway.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.momiji.gateway.repository.entity.enumerator.ChatType

data class ReceivedChat(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("type")
    var type: ChatType,
)
