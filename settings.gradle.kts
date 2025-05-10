rootProject.name = "omega-momiji-messenger-gateway"

include(":gateway-server")
include(":gateway-api")

pluginManagement {
    plugins {
        id("org.openapi.generator") version "7.12.0"
        kotlin("jvm") version "1.9.25"
        kotlin("plugin.spring") version "1.9.25"
        id("org.springframework.boot") version "3.4.5"
        id("io.spring.dependency-management") version "1.1.7"
    }
}
