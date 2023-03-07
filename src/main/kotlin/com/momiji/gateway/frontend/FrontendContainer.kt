package com.momiji.gateway.frontend

import com.momiji.gateway.external.api.FrontendApi
import com.momiji.gateway.external.api.SendMessageRequest
import com.momiji.gateway.external.api.SendMessageResponse


class FrontendNotFoundException(frontendName: String) :
    RuntimeException("Frontend \"$frontendName\" not found")

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
