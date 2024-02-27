package com.momiji.gateway.repository.entity

import com.momiji.gateway.repository.entity.enumerator.ChatType
import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(
    name = "chats",
    schema = "gateway",
)
data class ChatEntity(
    @Id
    var id: Long? = null,
    override var createdAt: LocalDateTime? = null,
    override var updatedAt: LocalDateTime? = null,
    var title: String,
    var frontend: String,
    var nativeId: String,
    var type: ChatType,
): Auditable
