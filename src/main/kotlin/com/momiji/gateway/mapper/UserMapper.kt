package com.momiji.gateway.mapper

import com.momiji.gateway.controller.dto.ReceivedUser
import com.momiji.gateway.repository.entity.UserEntity
import com.momiji.gateway.repository.entity.enumerator.MessengerFrontend
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

    fun map(value: ReceivedUser, messengerFrontend: MessengerFrontend): UserEntity {
        return UserEntity(
            messenger = messengerFrontend,
            nativeId = value.id,
            username = value.username,
            fullname = value.fullname,
        )
    }
}
