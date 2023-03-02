package com.momiji.gateway.repository

import com.momiji.gateway.repository.entity.ChatEntity
import com.momiji.gateway.repository.entity.enumerator.ChatType
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : CrudRepository<ChatEntity, Long> {
    fun findByNativeIdAndType(nativeId: String, type: ChatType): ChatEntity?
}
