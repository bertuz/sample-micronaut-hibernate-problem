plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.6.7"
    id("io.micronaut.test-resources") version "3.6.7"
    id("com.diffplug.spotless") version "6.12.0"
}

version = "0.1"
group = "example.micronaut"
val kotlinVersion=project.properties.get("kotlinVersion")


repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut.data:micronaut-data-processor")
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.data:micronaut-data-hibernate-reactive")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    // mysql
//    implementation("io.vertx:vertx-mysql-client")

    // postgres
    implementation("com.ongres.scram:client:2.1")
    implementation("io.micronaut.sql:micronaut-vertx-pg-client")
    implementation("io.vertx:vertx-pg-client")

    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    testResourcesService("mysql:mysql-connector-java")
    compileOnly("org.graalvm.nativeimage:svm")

    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

// mysql
//    implementation("io.micronaut.sql:micronaut-jdbc-hikari") // not sure it's needed
//    runtimeOnly("mysql:mysql-connector-java")

    //postgres
    runtimeOnly("org.postgresql:postgresql")
}


application {
    mainClass.set("example.micronaut.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

spotless {
    kotlin {
        // version, setUseExperimental, userData and editorConfigOverride are all optional
        ktlint("0.45.2")
            .setUseExperimental(true)
            .userData(mapOf("android" to "false"))
            .editorConfigOverride(mapOf("indent_size" to 2))
    }
}

tasks.check { dependsOn(tasks.spotlessCheck) }

graalvmNative.toolchainDetection.set(false)

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("example.micronaut.*")
    }
    testResources {
//        additionalModules.add("hibernate-reactive-mysql")
        additionalModules.add("hibernate-reactive-postgresql")
    }
}

tasks.named<io.micronaut.gradle.docker.MicronautDockerfile>("dockerfile") {
    baseImage.set("eclipse-temurin:17.0.5_8-jre")
}
