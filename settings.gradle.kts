rootProject.name = "gateway"

include("gateway-api")
include("gateway-server")

pluginManagement {
//    val springFrameworkBootPluginVersion = "3.2.3"
//    val springDependencyManagementPluginVersion = "1.1.4"
//    val kotlinJvmVersionPluginVersion = "1.9.22"
//    val kotlinSpringPluginVersion = "1.9.22"
    repositories {
        val nexusUsername: String by settings
        val nexusPassword: String by settings
        val nexusPublic = "https://nexus-ci.delta.sbrf.ru/repository/public/"
        val nexusLibDev = "https://nexus-ci.delta.sbrf.ru/repository/maven-lib-dev"
        val nexusLibRelease: String = "https://nexus-ci.delta.sbrf.ru/repository/maven-lib-release"

        maven {
            url = uri(nexusPublic)
            credentials {
                username = nexusUsername
                password = nexusPassword
            }
        }
        maven {
            url = uri(nexusLibDev)
            credentials {
                username = nexusUsername
                password = nexusPassword
            }
        }
        maven {
            url = uri(nexusLibRelease)
            credentials {
                username = nexusUsername
                password = nexusPassword
            }
        }
        mavenLocal()
    }
    val openapiGeneratorPluginVersion = "7.3.0"

    plugins {
//        id("org.springframework.boot") version springFrameworkBootPluginVersion
//        id("io.spring.dependency-management") version springDependencyManagementPluginVersion
        id("org.openapi.generator") version openapiGeneratorPluginVersion
//        kotlin("jvm") version kotlinJvmVersionPluginVersion
//        kotlin("plugin.spring") version kotlinSpringPluginVersion
    }
}
