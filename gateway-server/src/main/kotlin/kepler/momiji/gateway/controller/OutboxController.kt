package kepler.momiji.gateway.controller

import com.momiji.gateway.api.GatewayOutboxApi
import com.momiji.gateway.api.dto.SendMessageResponse
import com.momiji.gateway.api.dto.SendMessageWithMediaRequest
import com.momiji.gateway.api.dto.SendStickerRequest
import com.momiji.gateway.api.dto.SendTextMessageRequest
import io.micrometer.core.annotation.Timed
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(
    path = ["outbox"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@RestController
class OutboxController : GatewayOutboxApi {

    @Timed(
        value = "gateway_controller_processing_time",
        description = "The time it takes to process request and return response",
        extraTags = [
            "controller", "outbox",
            "method", "send_image_message"],
        histogram = true
    )
    override fun outboxSendImageMessage(
        chatId: String,
        frontendName: String,
        sendMessageWithMediaRequest: SendMessageWithMediaRequest?
    ): ResponseEntity<SendMessageResponse> {
        TODO("Not yet implemented")
    }

    @Timed(
        value = "gateway_controller_processing_time",
        description = "The time it takes to process request and return response",
        extraTags = [
            "controller", "outbox",
            "method", "send_sticker_message"],
        histogram = true
    )
    override fun outboxSendStickerMessage(
        chatId: String,
        frontendName: String,
        sendStickerRequest: SendStickerRequest?
    ): ResponseEntity<SendMessageResponse> {
        TODO("Not yet implemented")
    }

    @Timed(
        value = "gateway_controller_processing_time",
        description = "The time it takes to process request and return response",
        extraTags = [
            "controller", "outbox",
            "method", "send_text_message"],
        histogram = true
    )
    override fun outboxSendTextMessage(
        frontendName: String,
        chatId: String,
        sendTextMessageRequest: SendTextMessageRequest?
    ): ResponseEntity<SendMessageResponse> {
        TODO("Not yet implemented")
    }
}
