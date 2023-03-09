package com.momiji.gateway.frontend

import com.momiji.gateway.common.api.model.SendMessageRequest
import com.momiji.gateway.common.api.model.SendMessageResponse
import com.momiji.gateway.frontend.api.FrontendApi
import com.momiji.gateway.frontend.exception.FrontendNotFoundException


class FrontendContainer(
    private val clients: HashMap<String, FrontendApi>,
) {
    private fun getFrontend(frontend: String): FrontendApi {
        if (frontend !in clients) {
            throw FrontendNotFoundException(frontend)
        }

        return clients[frontend]!!
    }

    fun sendTextMessage(body: SendMessageRequest, frontend: String): SendMessageResponse {
        return getFrontend(frontend).sendTextMessage(body = body)
    }

    fun getFrontendNames(): List<String> {
        return clients.keys.toList()
    }
}
