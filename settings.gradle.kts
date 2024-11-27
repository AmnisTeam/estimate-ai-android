pluginManagement {
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

rootProject.name = "EstimateAI"
include(":app")
include(":core:database")
include(":core:resource")
include(":core:shared-prefs")
include(":core:api")
include(":feature:registration")
include(":feature:login")
include(":feature:password-reset")
include(":feature:tests-list")
include(":feature:test-select")
