plugins {
    id("org.openapi.generator")
}

dependencies {
//    implementation("org.springdoc:springdoc-openapi-ui")
//    implementation("org.openapitools:jackson-databind-nullable")
//    implementation("jakarta.servlet:jakarta.servlet-api")
//    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("jakarta.validation:jakarta.validation-api")
    implementation("jakarta.annotation:jakarta.annotation-api")

}

tasks {
    openApiGenerate {
        generatorName.set("kotlin-spring")
        inputSpec.set("${layout.projectDirectory}/src/main/resources/static/gateway/openapi/merged.yaml")
        outputDir.set("${layout.buildDirectory.get()}/generated/source/openapi")

        apiPackage.set("kepler.momiji.gateway.api")
        modelPackage.set("kepler.momiji.gateway.api.dto")
        templateDir.set("${layout.projectDirectory}/openapi/templates/kotlin-spring")
        globalProperties.set(mapOf(
            "apis,model" to "",
        ))
        configOptions.set(
            mapOf(
                "interfaceOnly" to "true",
                "useTags" to "true",
                "skipDefaultInterface" to "true",
//                "useOptional" to "true",
                "enumPropertyNaming" to "UPPERCASE",
                "library" to "spring-boot",
                "annotationLibrary" to "none",
                "documentationProvider" to "none",
            )
        )
    }

    compileKotlin {
        dependsOn(openApiGenerate)
    }
}


sourceSets {
    main {
//        kotlin {
//
//        }
        java {
            srcDir("${layout.buildDirectory.get()}/generated/source/openapi/src/main/kotlin")
        }
    }
}
