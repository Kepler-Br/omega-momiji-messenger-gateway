import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    id("io.spring.dependency-management")
    id("org.openapi.generator") apply false
    id("org.springframework.boot") apply false
    kotlin("jvm")
    kotlin("plugin.spring") apply false
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
        withJavadocJar()
//        withSourcesJar()
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }
    }

    dependencyManagement {
        imports {
//            val springCloudVersion: String by project
//            val logbookVersion: String by project
//            val awspringCloudVersion: String by project
//
//            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
//            mavenBom("org.zalando:logbook-bom:$logbookVersion")
////            mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:$awspringCloudVersion")
            mavenBom(SpringBootPlugin.BOM_COORDINATES)
        }

        dependencies {
            dependency("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8")
//            dependency("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.8")

//            val apiVersion: String by project
//            val awssdkVersion: String by project
//            val guavaVersion: String by project
//            val springdocOpenapiUiVersion = "1.7.0"
//            val openApiToolsJacksonVersion = "0.1.0"
//
//            dependency("com.google.guava:guava:$guavaVersion")
//            dependency("org.springdoc:springdoc-openapi-ui:$springdocOpenapiUiVersion")
//            dependency("org.openapitools:jackson-databind-nullable:$openApiToolsJacksonVersion")
//
////            dependencySet("com.momiji.api:$apiVersion") {
////                entry("omega-momiji-api")
////                entry("frontend-client-list-starter")
////                entry("bot-client-list-starter")
////            }
//            dependencySet("software.amazon.awssdk:$awssdkVersion") {
//                entry("apache-client")
//                entry("s3")
//            }
        }
    }

}
