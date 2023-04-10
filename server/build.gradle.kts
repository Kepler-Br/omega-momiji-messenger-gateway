plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.postgresql:postgresql")
    implementation("software.amazon.awssdk:apache-client")
    implementation("software.amazon.awssdk:s3")
    implementation("org.zalando:logbook-spring-boot-starter")
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.momiji.api:bot-client-list-starter")
    implementation("com.momiji.api:frontend-client-list-starter")
    implementation("com.momiji.api:omega-momiji-api")
}
