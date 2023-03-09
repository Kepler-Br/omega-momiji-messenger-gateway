package com.momiji.gateway.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.momiji.gateway.repository.entity.enumerator.ActionType

data class ActionInfo(
    @JsonProperty("action_type")
    val actionType: ActionType,
    @JsonProperty("related_user")
    val relatedUser: ReceivedUser,
)
