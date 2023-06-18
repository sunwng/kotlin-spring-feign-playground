import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// todo: 이후에 버전 정보 gradle.properties 로 추출해보기
plugins {
    id("org.springframework.boot") version "2.5.14" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0" apply false
    kotlin("jvm") version "1.7.10" apply false
    kotlin("kapt") version "1.7.10" apply false
    kotlin("plugin.spring") version "1.7.10" apply false
    kotlin("plugin.jpa") version "1.7.10" apply false
}

allprojects {
    group = "org.example"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    dependencies {
        val implementation by configurations
        val testImplementation by configurations
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
//        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation(kotlin("test"))
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
}

