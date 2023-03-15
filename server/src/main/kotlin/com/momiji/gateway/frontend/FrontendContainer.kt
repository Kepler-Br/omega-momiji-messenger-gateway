package com.momiji.gateway.frontend

import com.momiji.api.common.api.model.SendMessageRequest
import com.momiji.api.common.api.model.SendMessageResponse
import com.momiji.api.frontend.api.FrontendController
import com.momiji.gateway.frontend.exception.FrontendNotFoundException


class FrontendContainer(
    private val clients: HashMap<String, FrontendController>,
) {
    private fun getFrontend(frontend: String): FrontendController {
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
