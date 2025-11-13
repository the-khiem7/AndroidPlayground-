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

rootProject.name = "AndroidPlayGround"
include(
    ":app",
    ":lab01",
    ":lab02",
    ":lab03",
    ":lab04",
    ":lab05",
    ":lab06",
    ":lab07",
    ":lab08",
    ":lab09",
    ":lab10",
    ":lab11",
    ":lab12",
    ":lab13",
    ":lab14"
)
