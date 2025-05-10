plugins {
    id("org.openapi.generator")
}

dependencies {
    // TODO: version
    api("jakarta.servlet:jakarta.servlet-api:6.0.0")
    api("jakarta.validation:jakarta.validation-api:3.0.2")
    api("org.springframework:spring-web:6.2.6")
    api("org.springframework:spring-context:6.2.6")
    api("io.swagger.core.v3:swagger-annotations-jakarta:2.2.30")
    api("io.swagger.core.v3:swagger-models-jakarta:2.2.30")
    api("com.fasterxml.jackson.core:jackson-annotations:2.18.3")
}

tasks {
    openApiGenerate {
        generatorName.set("kotlin-spring")
        inputSpec.set("${layout.projectDirectory}/src/main/resources/static/gateway/openapi/gateway.yaml")
        outputDir.set("${layout.buildDirectory.get()}/generated/source/openapi")

        apiPackage.set("com.momiji.gateway.api")
        modelPackage.set("com.momiji.gateway.api.dto")
        configOptions.set(
            mapOf(
                "interfaceOnly" to "true",
                "useTags" to "true",
                "skipDefaultInterface" to "true",
                "useOptional" to "true",
                "enumPropertyNaming" to "UPPERCASE",
                "useSpringBoot3" to "true",
            )
        )
    }

    compileKotlin {
        dependsOn(openApiGenerate)
    }
}

sourceSets {
    main {
        java {
            srcDir("${layout.buildDirectory.get()}/generated/source/openapi/src/main")
        }
    }
}
