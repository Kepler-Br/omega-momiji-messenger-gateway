package com.momiji.gateway.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

data class FrontendConfig(
    val url: String,
)

@ConfigurationProperties("momiji")
data class MomijiConfigurationProperties constructor(
    var frontends: Map<String, FrontendConfig>,
    var bots: List<String>,
)