package com.momiji.gateway.mapper

import com.momiji.api.gateway.inbound.api.model.ReceivedUser
import com.momiji.gateway.repository.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun map(value: UserEntity): ReceivedUser {
        return ReceivedUser(
            id = value.nativeId,
            username = value.username,
            fullname = value.fullname,
        )
    }

    fun map(value: ReceivedUser, messengerFrontend: String): UserEntity {
        return UserEntity(
            frontend = messengerFrontend,
            nativeId = value.id,
            username = value.username,
            fullname = value.fullname,
        )
    }
}
