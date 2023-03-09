package com.momiji.gateway.outbound.api.model

import com.fasterxml.jackson.annotation.JsonProperty

data class FrontendNamesResponse(
    @JsonProperty("names")
    val names: List<String>,
)
