plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.liquibase:liquibase-core")
    implementation("org.postgresql:postgresql")

    implementation(project(":gateway-api"))


//    implementation("org.springdoc:springdoc-openapi-ui")
//    implementation("org.openapitools:jackson-databind-nullable")
//    implementation("javax.servlet:javax.servlet-api:2.5")
//    implementation("javax.annotation:javax.annotation-api:1.3.2")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks {
//    openApiGenerate {
//        generatorName.set("kotlin-spring")
//        inputSpec.set("${layout.projectDirectory}/src/main/resources/static/openapi/gateway.yaml")
////        inputSpec.set("${layout.projectDirectory}/src/main/resources/static/gateway/openapi/new.yaml")
//        outputDir.set("${layout.buildDirectory.get()}/generated/source/openapi")
//
//        apiPackage.set("com.momiji.gateway.api")
//        modelPackage.set("com.momiji.gateway.api.dto")
//        configOptions.set(
//            mapOf(
//                "interfaceOnly" to "true",
//                "useTags" to "true",
//                "skipDefaultInterface" to "true",
//                "useOptional" to "true",
//                "enumPropertyNaming" to "UPPERCASE",
//                "useSpringBoot3" to "true",
////                "library" to "feign"
//            )
//        )
//    }

//    compileKotlin {
//        dependsOn(openApiGenerate)
//    }
}

sourceSets {
    main {
        kotlin {

        }
        java {
            srcDir("${layout.buildDirectory.get()}/generated/source/openapi/src/main")
        }
    }
}
