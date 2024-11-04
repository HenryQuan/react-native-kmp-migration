import com.facebook.react.utils.windowsAwareCommandLine

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

    // react native plugin
    includeBuild("../../existing/node_modules/@react-native/gradle-plugin")
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

    // react native is not happy with this mode, because it is adding something
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// use the existing react native module
plugins { id("com.facebook.react.settings") }
// If using .gradle.kts files:
extensions.configure<com.facebook.react.ReactSettingsExtension> {
    // https://reactnative.dev/docs/integration-with-existing-apps?language=kotlin#configuring-gradle
    // This command doesn't work at all, need to see what to do or wait for Facebook to fix it
    autolinkLibrariesFromCommand(
        workingDirectory = file("../../existing"),
        lockFiles = files("../../existing/package-lock.json", "../../existing/package.json")
    )
}

includeBuild("../../existing/node_modules/@react-native/gradle-plugin")
