package kepler.momiji.gateway.controller

import com.momiji.gateway.api.GatewayInboxApi
import com.momiji.gateway.api.dto.InboxNewMessage
import io.micrometer.core.annotation.Timed
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(
    path = ["inbox"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@RestController
class GatewayInboxController : GatewayInboxApi {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Timed(
        value = "gateway_controller_processing_time",
        description = "The time it takes to process request and return response",
        extraTags = [
            "controller", "inbox",
            "method", "receive_message"],
        histogram = true
    )
    override fun inboxReceiveMessage(
        frontendName: String,
        chatId: String,
        inboxNewMessage: InboxNewMessage
    ): ResponseEntity<Unit> {
        logger.debug(
            "Inbox receive message: FrontendName: {}; chatId: {}; inboxNewMessage: {}",
            frontendName,
            chatId,
            inboxNewMessage
        )
        TODO("Not yet implemented")
    }

}
