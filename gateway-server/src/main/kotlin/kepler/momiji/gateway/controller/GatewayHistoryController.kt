package kepler.momiji.gateway.controller

import com.momiji.gateway.api.GatewayHistoryApi
import com.momiji.gateway.api.dto.GetMessageListResponse
import com.momiji.gateway.api.dto.HistoryMessage
import io.micrometer.core.annotation.Timed
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(
    path = ["history"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@RestController
class GatewayHistoryController : GatewayHistoryApi {
    @Timed(
        value = "gateway_controller_processing_time",
        description = "The time it takes to process request and return response",
        extraTags = [
            "controller", "history",
            "method", "get_concrete_message"],
        histogram = true
    )
    override fun historyGetConcreteMessage(
        frontendName: String,
        chatNativeId: String,
        messageNativeId: String
    ): ResponseEntity<HistoryMessage> {
        TODO("Not yet implemented")
    }

    @Timed(
        value = "gateway_controller_processing_time",
        description = "The time it takes to process request and return response",
        extraTags = [
            "controller", "history",
            "method", "get_list_of_messages"],
        histogram = true
    )
    override fun historyGetListOfMessages(
        frontendName: String,
        chatId: String,
        count: Int,
        id: String?
    ): ResponseEntity<GetMessageListResponse> {
        TODO("Not yet implemented")
    }

}
