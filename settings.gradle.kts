pluginManagement {
    includeBuild("build-logic")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "harudiary"
include(":app")
include(":domain")
include(":core:designsystem")
include(":core:resource")
include(":core:navigation")
include(":core:utils")
include(":core:model")
include(":feature:main")
include(":feature:home")
include(":feature:my")
include(":data")
include(":local")
