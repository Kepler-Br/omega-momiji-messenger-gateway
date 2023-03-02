package com.momiji.gateway.controller.dto

import com.momiji.gateway.repository.entity.enumerator.ChatType

data class ReceivedChat(
    val id: String,
    val title: String,
    var type: ChatType,
)
