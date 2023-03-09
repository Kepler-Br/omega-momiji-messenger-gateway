package com.momiji.gateway.frontend.api

import com.momiji.gateway.common.api.model.SendMessageRequest
import com.momiji.gateway.common.api.model.SendMessageResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


interface FrontendApi {

    @RequestMapping(
        "/messages",
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun sendTextMessage(body: SendMessageRequest): SendMessageResponse
}
