package com.momiji.gateway.controller

import com.momiji.gateway.api.ReceiveMessageApi
import com.momiji.gateway.api.SendMessageApi
import com.momiji.gateway.api.dto.SendMessageResponse
import com.momiji.gateway.api.dto.SendMessageWithMediaRequest
import com.momiji.gateway.api.dto.SendStickerRequest
import com.momiji.gateway.api.dto.SendTextRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping(
    path = ["outbox"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@RestController
class OutboxController: SendMessageApi {
    override fun outboxSendImageMessage(
        chatId: String,
        frontendName: String,
        sendMessageWithMediaRequest: SendMessageWithMediaRequest?
    ): ResponseEntity<SendMessageResponse> {
        TODO("Not yet implemented")
    }

    override fun outboxSendStickerMessage(
        chatId: String,
        frontendName: String,
        sendStickerRequest: SendStickerRequest?
    ): ResponseEntity<SendMessageResponse> {
        TODO("Not yet implemented")
    }

    override fun outboxSendTextMessage(
        frontendName: String,
        chatId: String,
        sendTextRequest: SendTextRequest?
    ): ResponseEntity<SendMessageResponse> {
        TODO("Not yet implemented")
    }
}