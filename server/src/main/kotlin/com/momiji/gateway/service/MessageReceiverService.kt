package com.momiji.gateway.service

import com.momiji.api.bot.BotReceiveMessageClient
import com.momiji.api.bot.model.NewMessageRequest
import com.momiji.api.gateway.inbound.model.ReceivedMessage
import com.momiji.gateway.repository.TxExecutor
import java.util.concurrent.Executors
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class MessageReceiverService(
    private val txExecutor: TxExecutor,
    private val dataService: DataService,
    private val botReceiveMessageClient: BotReceiveMessageClient,
) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)
    private val threadExecutor = Executors.newFixedThreadPool(2)


    fun receive(message: ReceivedMessage) {
        val messageWasUpdated = txExecutor.new { dataService.saveMessage(message) }

        val request = NewMessageRequest(
            frontend = message.frontend,
            chatId = message.chat.id,
            messageId = message.id,
            isUpdated = messageWasUpdated,
        )

        threadExecutor.submit {
            try {
                botReceiveMessageClient.newMessage(request)
            } catch (ex: RuntimeException) {
                logger.error(
                    "Exception has occurred during sending message in thread executor",
                    ex
                )
            }
        }
    }
}
