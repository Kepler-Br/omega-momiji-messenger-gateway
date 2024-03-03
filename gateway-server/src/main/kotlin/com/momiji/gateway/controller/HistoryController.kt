package com.momiji.gateway.controller

import com.momiji.gateway.api.HistoryApi
import com.momiji.gateway.api.dto.GetMessageListResponse
import com.momiji.gateway.api.dto.MessageWithFileLink
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(
    path = ["history"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@RestController
class HistoryController: HistoryApi {
    override fun historyGetConcreteMessage(
        frontendName: String,
        chatId: String,
        messageId: String
    ): ResponseEntity<MessageWithFileLink> {
        TODO("Not yet implemented")
    }

    override fun historyGetListOfMessages(
        frontendName: String,
        chatId: String,
        count: Int,
        id: String?
    ): ResponseEntity<GetMessageListResponse> {
        TODO("Not yet implemented")
    }
}