
rootProject.name = "quizEngine"

pluginManagement {
    pluginManagement {
        val kotlinVersion: String by settings
        val kotestVersion: String by settings
        val openapiVersion: String by settings
        val ktorVersion: String by settings

        plugins {
            kotlin("jvm") version kotlinVersion
            kotlin("multiplatform") version kotlinVersion
            kotlin("plugin.serialization") version kotlinVersion apply false

            id("io.kotest.multiplatform") version kotestVersion apply false
            id("org.openapi.generator") version openapiVersion apply false

            id("io.ktor.plugin") version ktorVersion apply false
        }
    }
}

include("acceptance")
include("core-api")
include("core-common")
include("core-mappers")

include("microservice-question")
include("microservice-question:api-v1")
include("microservice-question:mappers-v1")
include("microservice-question:common")
include("microservice-question:biz")
include("microservice-question:stubs")
include("microservice-question:ktor")

include("microservice-game")
include("microservice-game:api-v1")
include("microservice-game:mappers-v1")
include("microservice-game:common")
include("microservice-game:biz")
include("microservice-game:stubs")
include("microservice-game:ktor")

include("microservice-round")
include("microservice-round:api-v1")
include("microservice-round:mappers-v1")
include("microservice-round:common")
include("microservice-round:biz")
include("microservice-round:stubs")
include("microservice-round:ktor")