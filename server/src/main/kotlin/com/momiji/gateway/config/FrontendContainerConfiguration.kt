package com.momiji.gateway.config

import com.momiji.api.bot.api.ReceiveMessageEventsController
import com.momiji.api.bot.api.model.NewMessageRequest
import com.momiji.api.frontend.api.FrontendController
import com.momiji.gateway.config.properties.MomijiConfigurationProperties
import com.momiji.gateway.frontend.FrontendContainer
import feign.Contract
import feign.Feign
import feign.codec.Decoder
import feign.codec.Encoder
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

class BotClientsContainer(
    private val bots: List<ReceiveMessageEventsController>
) : ReceiveMessageEventsController {
    override fun newMessage(request: NewMessageRequest) {
        for (bot in bots) {
            // TODO: catch exceptions and log them
            bot.newMessage(request)
        }
    }
}

@Configuration
// TODO: What FeignClientsConfiguration do?
@Import(FeignClientsConfiguration::class)
class FrontendContainerConfiguration {

    @Bean
    fun frontendContainer(
        config: MomijiConfigurationProperties,
        contract: Contract,
        decoder: Decoder,
        encoder: Encoder,
    ): FrontendContainer {
        val clients = HashMap<String, FrontendController>()

        for (frontend in config.frontends) {
            clients[frontend.key] =
                Feign.builder()
                    .encoder(encoder)
                    .decoder(decoder)
                    .contract(contract)
                    .target(FrontendController::class.java, frontend.value.url)
        }

        return FrontendContainer(clients)
    }

    @Bean
    fun botClientContainer(
        config: MomijiConfigurationProperties,
        contract: Contract,
        decoder: Decoder,
        encoder: Encoder,
    ): ReceiveMessageEventsController {
        val clients = config.bots.map {
            Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .target(ReceiveMessageEventsController::class.java, it)
        }

        return BotClientsContainer(clients)
    }
}
