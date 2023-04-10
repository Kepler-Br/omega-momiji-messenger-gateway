import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    id("org.springframework.boot") version "2.7.10"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.10" apply false
    kotlin("plugin.spring") version "1.7.10" apply false
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    dependencyManagement {
        imports {
            val springCloudVersion: String by project
            val logbookVersion: String by project

            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
            mavenBom("org.zalando:logbook-bom:$logbookVersion")
            mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:2.4.0")
            mavenBom(SpringBootPlugin.BOM_COORDINATES)
        }

        dependencies {
            val apiVersion: String by project
            val awssdkVersion: String by project


            dependency("com.google.guava:guava:31.1-jre")

            dependencySet("com.momiji.api:$apiVersion") {
                entry("omega-momiji-api")
                entry("frontend-client-list-starter")
                entry("bot-client-list-starter")
            }
            dependencySet("software.amazon.awssdk:$awssdkVersion") {
                entry("apache-client")
                entry("s3")
            }
        }
    }

    repositories {
        mavenCentral()
        mavenLocal()
    }
}
