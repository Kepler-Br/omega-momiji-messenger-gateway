import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm") apply false
    kotlin("plugin.spring") apply false
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
            val awspringCloudVersion: String by project

            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
            mavenBom("org.zalando:logbook-bom:$logbookVersion")
//            mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:$awspringCloudVersion")
            mavenBom(SpringBootPlugin.BOM_COORDINATES)
        }

        dependencies {
            val apiVersion: String by project
            val awssdkVersion: String by project
            val guavaVersion: String by project
            val springdocOpenapiUiVersion = "1.7.0"
            val openApiToolsJacksonVersion = "0.1.0"

            dependency("com.google.guava:guava:$guavaVersion")
            dependency("org.springdoc:springdoc-openapi-ui:$springdocOpenapiUiVersion")
            dependency("org.openapitools:jackson-databind-nullable:$openApiToolsJacksonVersion")

//            dependencySet("com.momiji.api:$apiVersion") {
//                entry("omega-momiji-api")
//                entry("frontend-client-list-starter")
//                entry("bot-client-list-starter")
//            }
            dependencySet("software.amazon.awssdk:$awssdkVersion") {
                entry("apache-client")
                entry("s3")
            }
        }
    }

}
