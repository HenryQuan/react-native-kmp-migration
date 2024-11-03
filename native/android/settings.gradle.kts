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

rootProject.name = "Android+KMP"
include(":app")

// use the kmp module, require some setup here
include(":migration")
project(":migration").projectDir = file("../../kmp/migration")
dependencyResolutionManagement {
    versionCatalogs {
        create("kmp") {
            from(files("../../kmp/kmp.versions.toml"))
        }
    }

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
