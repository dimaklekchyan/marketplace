plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm { }
    linuxX64 { }
    macosX64 { }
    macosArm64 { }

    sourceSets {
        val coroutinesVersion: String by project
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))

                // transport models
                implementation(project(":core-common"))
                implementation(project(":lib-logging-common"))
                implementation(project(":microservice-question:common"))
                implementation(project(":microservice-question:api-v1"))
                implementation(project(":microservice-question:mappers-v1"))

                // logging
                implementation(project(":microservice-question:api-log-v1"))
                implementation(project(":microservice-question:mappers-log-v1"))

                // Stubs
                implementation(project(":microservice-question:stubs"))

                // Biz
                implementation(project(":microservice-question:biz"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val linuxX64Test by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

