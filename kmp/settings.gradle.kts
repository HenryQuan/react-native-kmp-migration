rootProject.name = "kmp-migration"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("kmp") {
            from(files("kmp.versions.toml"))
        }
    }
}

include(":migration")
