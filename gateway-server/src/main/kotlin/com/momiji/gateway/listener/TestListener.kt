package com.momiji.gateway.listener

import com.momiji.gateway.dto.NewMessage
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TestListener {

    @KafkaListener(topics = ["frontends.messages.v1"])
    fun test(value: NewMessage) {

    }
}