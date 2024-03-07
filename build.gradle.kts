import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4" apply false
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.spring") version "1.7.20"
}

extra["springCloudVersion"] = "2022.0.3"

allprojects {
    group = "kepler.momiji.gateway"
    version = "0.0.1"
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")


    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }

        dependencies {
//            dependency("com.google.guava:guava:$guavaVersion")
//            dependency("org.springdoc:springdoc-openapi-ui:$springdocOpenapiUiVersion")
//            dependency("org.openapitools:jackson-databind-nullable:$openApiToolsJacksonVersion")
        }
    }
}
