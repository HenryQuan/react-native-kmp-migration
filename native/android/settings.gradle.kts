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

rootProject.name = "Android+KMP"
include(":app")

// use the kmp module
include(":kmp")
project(":kmp").projectDir = file("../../kmp")
dependencyResolutionManagement {
    versionCatalogs {
        create("kmp") {
            from(files("../../kmp/kmp.versions.toml"))
        }
    }
}