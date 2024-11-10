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

// The + shouldn't be used here, it will cause this error while building the project:
// ERROR: migration-jvm.jar: D8: Class descriptor 'Landroid+kmp/migration/generated/resources/ActualResourceCollectorsKt;'
// cannot be represented in dex format, the issue is with android+kmp, update the name to remove the + sign
rootProject.name = "AndroidKMP"
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
// run `python patch-node-modules.py` to fix react native related issues
plugins { id("com.facebook.react.settings") }
// If using .gradle.kts files:
extensions.configure<com.facebook.react.ReactSettingsExtension> {
    // https://reactnative.dev/docs/integration-with-existing-apps?language=kotlin#configuring-gradle
    // have to update the path for it to run
    autolinkLibrariesFromCommand(
        workingDirectory = file("../../existing/"),
        lockFiles = files("../../existing/package-lock.json", "../../existing/package.json")
    )
}

includeBuild("../../existing/node_modules/@react-native/gradle-plugin")

// setup flutter module
// only works with Flutter 3.27 default groovy, a patch is needed to work with lower versions
// use `python patch-flutter-groovy.py`
val flutterPath = "../../flutter_module/.android/include_flutter.groovy"
apply(from = File(flutterPath))
