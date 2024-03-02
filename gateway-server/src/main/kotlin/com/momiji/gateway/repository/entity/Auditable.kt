package com.momiji.gateway.repository.entity

import java.time.LocalDateTime

interface Auditable {
    var createdAt: LocalDateTime?
    var updatedAt: LocalDateTime?
}
