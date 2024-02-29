plugins {
    id("org.openapi.generator")
}

dependencies {
    implementation("org.springdoc:springdoc-openapi-ui")
    implementation("org.openapitools:jackson-databind-nullable")
    implementation("jakarta.servlet:jakarta.servlet-api")
    implementation("jakarta.annotation:jakarta.annotation-api")
}

tasks {
    openApiGenerate {
        generatorName.set("kotlin-spring")
        inputSpec.set("${layout.projectDirectory}/src/main/resources/static/gateway/openapi/new-gateway.yaml")
        outputDir.set("${layout.buildDirectory.get()}/generated/source/openapi")

        apiPackage.set("com.momiji.gateway.api")
        modelPackage.set("com.momiji.gateway.api.dto")
        configOptions.set(
            mapOf(
                "interfaceOnly" to "true",
                "useTags" to "true",
                "skipDefaultInterface" to "true",
                "useOptional" to "true",
                "enumPropertyNaming" to "UPPERCASE"
            )
        )
    }

    compileKotlin {
        dependsOn(openApiGenerate)
    }
}
