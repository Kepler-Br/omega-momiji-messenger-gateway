rootProject.name = "omega-momiji-messenger-gateway"

include(":server")
include(":client")

pluginManagement {
    val springFrameworkBootPluginVersion = "2.7.10"
    val springDependencyManagementPluginVersion = "1.1.0"
    val kotlinJvmVersionPluginVersion = "1.7.10"
    val kotlinSpringPluginVersion = "1.7.10"
    val openapiGeneratorPluginVersion = "7.3.0"

    plugins {
        id("org.springframework.boot") version springFrameworkBootPluginVersion
        id("io.spring.dependency-management") version springDependencyManagementPluginVersion
        id("org.openapi.generator") version openapiGeneratorPluginVersion
        kotlin("jvm") version kotlinJvmVersionPluginVersion
        kotlin("plugin.spring") version kotlinSpringPluginVersion
    }
}
