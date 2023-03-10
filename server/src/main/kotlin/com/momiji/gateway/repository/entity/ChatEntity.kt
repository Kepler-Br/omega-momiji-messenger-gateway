package com.momiji.gateway.repository.entity

import com.momiji.gateway.repository.entity.enumerator.ChatType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("chats")
data class ChatEntity(
    @Id
    var id: Long? = null,
    var title: String,
    var frontend: String,
    var nativeId: String,
    var type: ChatType,
)
