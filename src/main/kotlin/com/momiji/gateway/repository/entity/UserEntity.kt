package com.momiji.gateway.repository.entity

import com.momiji.gateway.repository.entity.enumerator.MessengerFrontend

data class UserEntity(
    var id: Long? = null,
    var messenger: MessengerFrontend,
    var nativeId: String,
    var username: String,
    var fullname: String,
)
