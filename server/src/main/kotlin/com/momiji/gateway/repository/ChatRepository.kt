package com.momiji.gateway.repository

import com.momiji.gateway.repository.entity.ChatEntity
import org.springframework.data.repository.CrudRepository

interface ChatRepository : CrudRepository<ChatEntity, Long> {
    fun getByFrontendAndNativeId(frontend: String, nativeId: String): ChatEntity
}
