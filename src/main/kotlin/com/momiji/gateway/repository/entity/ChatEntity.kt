package com.momiji.gateway.repository.entity

import com.momiji.gateway.repository.entity.enumerator.ChatType
import com.momiji.gateway.repository.entity.enumerator.MessengerFrontend

data class ChatEntity(
    var id: Long? = null,
    var nativeId: String,
    var title: String,
    var type: ChatType,
    var messenger: MessengerFrontend,
)
