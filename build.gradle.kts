plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.dreven"
version = "0.0.1-SNAPSHOT"
description = "quello"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    // 添加mapstruct依赖
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
    // 确保mapstruct和lombok兼容
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    // 数据库
    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("com.baomidou:mybatis-plus-spring-boot3-starter:3.5.14")
    implementation("com.baomidou:dynamic-datasource-spring-boot3-starter:4.3.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}