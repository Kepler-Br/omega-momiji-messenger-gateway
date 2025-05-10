package com.momiji.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@ConfigurationPropertiesScan
class Application

// TODO: add https://stackoverflow.com/questions/19819837/java-executor-with-throttling-throughput-control to all calls to frontends
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
