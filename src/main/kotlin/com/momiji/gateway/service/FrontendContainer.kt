package com.momiji.gateway.service

import com.momiji.gateway.external.api.FrontendApi
import com.momiji.gateway.external.api.SendMessageRequest
import feign.Feign
import feign.codec.Decoder
import feign.codec.Encoder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.openfeign.support.SpringMvcContract
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


data class FrontendConfig(
    val url: String,
)

@ConfigurationProperties("momiji")
data class ConfiguredFrontends constructor(
    var frontends: Map<String, FrontendConfig>,
)

class FrontendContainer(
    private val clients: HashMap<String, FrontendApi>,
)

@Configuration
class FrontendContainerConfiguration {

    @Bean
    fun test(config: ConfiguredFrontends): FrontendContainer {
        val clients = HashMap<String, FrontendApi>()

        for (frontend in config.frontends) {
            clients[frontend.key] = Feign.builder()
//                .client(client)
                .encoder(Encoder.Default())
                .decoder(Decoder.Default())
                .contract(SpringMvcContract())
                .target(FrontendApi::class.java, frontend.value.url)
        }
         clients["telegram"]!!.sendTextMessage(
            SendMessageRequest(
                text="",
                chatId = "123",
                replyTo = null,
            )
        )
        return FrontendContainer(clients)
    }
}
