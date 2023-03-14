plugins {
    id("org.springframework.boot")
}

dependencies {
    val botApiVersion: String by project

    implementation(project(":${rootProject.name}-api"))
    implementation("com.momiji.bot:omega-momiji-bot-api:$botApiVersion")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.postgresql:postgresql")
    implementation(kotlin("stdlib-jdk8"))
}
