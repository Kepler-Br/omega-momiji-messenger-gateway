package com.momiji.gateway.config

import com.momiji.gateway.external.api.FrontendApi
import com.momiji.gateway.frontend.FrontendContainer
import feign.Contract
import feign.Feign
import feign.codec.Decoder
import feign.codec.Encoder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

data class FrontendConfig(
    val url: String,
)

@ConfigurationProperties("momiji")
data class ConfiguredFrontends constructor(
    var frontends: Map<String, FrontendConfig>,
)

@Configuration
// TODO: What FeignClientsConfiguration do?
@Import(FeignClientsConfiguration::class)
class FrontendContainerConfiguration {

    @Bean
    fun frontendContainer(
        config: ConfiguredFrontends,
        contract: Contract,
        decoder: Decoder,
        encoder: Encoder,
    ): FrontendContainer {
        val clients = HashMap<String, FrontendApi>()

        for (frontend in config.frontends) {
            clients[frontend.key] = Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .target(FrontendApi::class.java, frontend.value.url)
        }

        return FrontendContainer(clients)
    }
}
