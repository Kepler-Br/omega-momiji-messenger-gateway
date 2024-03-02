package com.momiji.gateway.controller

import com.momiji.gateway.api.ReceiveMessageApi
import com.momiji.gateway.api.dto.CommonResponse
import com.momiji.gateway.api.dto.PutMessageRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(
    path = ["inbox"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
@RestController
class InboxController : ReceiveMessageApi {

    @PutMapping("/frontends/{frontend_name}/chats/{chat_id}/messages")
    override fun inboxReceiveMessage(
        @PathVariable("frontend_name")
        frontendName: String,
        @PathVariable("chat_id")
        chatId: String,
        @RequestBody
        putMessageRequest: PutMessageRequest
    ): ResponseEntity<CommonResponse> {
        TODO("Not yet implemented")
    }
}