package com.momiji.gateway.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ReceivedUser(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("username")
    val username: String,
    @JsonProperty("fullname")
    val fullname: String,
)
