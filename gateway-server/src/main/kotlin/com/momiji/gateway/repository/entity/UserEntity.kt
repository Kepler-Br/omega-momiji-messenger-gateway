package com.momiji.gateway.repository.entity

import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table(name = "users")
data class UserEntity(
    @Id
    var id: Long? = null,
    override var createdAt: LocalDateTime? = null,
    override var updatedAt: LocalDateTime? = null,
    var username: String,
    var fullName: String,
    var frontend: String,
    var nativeId: String,
): Auditable
