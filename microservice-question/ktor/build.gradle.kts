import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val serializationVersion: String by project
val logbackVersion: String by project

fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

plugins {
    application
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("io.ktor.plugin")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

application {
    mainClass.set("io.ktor.server.cio.EngineMain")
}

jib {
    container.mainClass = "io.ktor.server.cio.EngineMain"
}

ktor {
    docker {
        localImageName.set(project.name + "-ktor")
        imageTag.set(project.version.toString())
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
    }
}

kotlin {
    jvm {
        withJava()
    }
    linuxX64 {}
    macosX64 {}
    macosArm64 {}

    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries {
            executable {
                entryPoint = "ru.klekchyan.quizEngine.main"
            }
        }
    }

    sourceSets {

        val commonMain by getting {
            dependencies {
                // kotlin
                implementation(kotlin("stdlib-common"))

                // ktor
                implementation(ktor("content-negotiation"))
                implementation(ktor("core"))
                implementation(ktor("cio"))
                implementation(ktor("auth")) // "io.ktor:ktor-server-auth:$ktorVersion"
                implementation(ktor("auto-head-response")) // "io.ktor:ktor-server-auto-head-response:$ktorVersion"
                implementation(ktor("caching-headers")) // "io.ktor:ktor-server-caching-headers:$ktorVersion"
                implementation(ktor("cors")) // "io.ktor:ktor-server-cors:$ktorVersion"
                implementation(ktor("websockets")) // "io.ktor:ktor-server-websockets:$ktorVersion"
                implementation(ktor("config-yaml")) // "io.ktor:ktor-server-config-yaml:$ktorVersion"

                // serialization
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

                implementation(project(":core-common"))
                implementation(project(":core-mappers"))
                implementation(project(":microservice-question:api-v1"))
                implementation(project(":microservice-question:mappers-v1"))
                implementation(project(":microservice-question:stubs"))
                implementation(project(":microservice-question:common"))
                implementation(project(":microservice-question:biz"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation(ktor("test-host"))
                implementation(ktor("content-negotiation", prefix = "client-"))
                implementation(ktor("websockets", prefix = "client-"))
            }
        }

        val jvmMain by getting {
            dependencies {
                // ktor
                implementation(ktor("call-logging"))

                implementation("ch.qos.logback:logback-classic:$logbackVersion")
            }
        }
    }
}