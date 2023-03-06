package com.momiji.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.momiji.gateway.service")
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
