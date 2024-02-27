package com.momiji.gateway.repository

import com.momiji.gateway.repository.entity.MessageEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface MessageRepository : CrudRepository<MessageEntity, Long> {
    fun getByFrontendAndNativeIdAndChatIdAndUserId(
        frontend: String,
        nativeId: String,
        chatId: Long,
        userId: Long
    ): MessageEntity
}
