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
        val datetimeVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":microservice-question:api-log-v1"))
                implementation(project(":microservice-question:common"))
                implementation(project(":core-common"))
                implementation(project(":core-mappers-log"))
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

    }
}
