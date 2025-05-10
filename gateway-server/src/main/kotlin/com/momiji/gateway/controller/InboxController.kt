package com.momiji.gateway.controller

import com.momiji.gateway.api.InboxApi
import com.momiji.gateway.api.dto.InboxMessagesRequest
import com.momiji.gateway.service.InboxService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class InboxController(
    private val inboxService: InboxService
) : InboxApi {
    override fun receiveMessage(inboxMessagesRequest: InboxMessagesRequest): ResponseEntity<Unit> {
        inboxService.receive(inboxMessagesRequest)

        return ResponseEntity.ok().build()
    }
}
