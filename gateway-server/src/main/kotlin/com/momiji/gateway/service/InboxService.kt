package com.momiji.gateway.service

import com.momiji.gateway.api.dto.InboxMessagesRequest
import com.momiji.gateway.repository.util.toChatEntity
import com.momiji.gateway.repository.util.toMessageEntity
import com.momiji.gateway.repository.util.toUserEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import io.swagger.v3.oas.models.OpenAPI
import com.fasterxml.jackson.annotation.JsonProperty

@Service
class InboxService(
    private val dataService: DataService,
) {

    @Transactional
    fun receive(inboxMessagesRequest: InboxMessagesRequest) {
        val user = dataService.createOrUpdateUser(
            inboxMessagesRequest.fromUser.toUserEntity(inboxMessagesRequest.frontend)
        )
        val chat = dataService.createOrUpdateChat(
            inboxMessagesRequest.chat.toChatEntity(inboxMessagesRequest.frontend)
        )

        val messageEntity = inboxMessagesRequest.toMessageEntity(inboxMessagesRequest.frontend).apply {
            this.userId = user.id
            this.chatId = chat.id
        }

        dataService.createOrUpdateMessage(messageEntity)
    }
}

