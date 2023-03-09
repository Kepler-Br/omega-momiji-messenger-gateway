package com.momiji.gateway.outbound.api

import com.momiji.gateway.common.api.model.SendMessageResponse
import com.momiji.gateway.outbound.api.model.FrontendNamesResponse
import com.momiji.gateway.outbound.api.model.SendTextMessageRequest


interface MessageSenderController {

    fun sendText(request: SendTextMessageRequest): SendMessageResponse

    fun getFrontendNames(): FrontendNamesResponse
}
