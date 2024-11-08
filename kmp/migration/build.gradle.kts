plugins {
    alias(kmp.plugins.kotlin.multiplatform)
    alias(kmp.plugins.kotlinx.serialization)
    alias(kmp.plugins.kotlin.cocoapods)
}

kotlin {
    // This will be used in React Native
    js {
        moduleName = "kmp-migration"
        binaries.executable()
        generateTypeScriptDefinitions()
        nodejs()
        useEsModules()
    }
    // Android and iOS using the same code
    jvm {
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }

    // Apple
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosArm64()
    macosX64()
    watchosX64()
    cocoapods {
        version = "0.0.1"
        summary = "KMP Migration"
        homepage = "https://github.com/HenryQuan/react-native-kmp-migration"
        name = "KmpMigration"
        framework {
            baseName = "KmpMigration" // the name of the framework
            export(project(":migration"))
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(kmp.ktor.client.core)
            implementation(kmp.ktor.client.content.negotiation)
            implementation(kmp.ktor.client.serialization)
            implementation(kmp.ktor.serialization.kotlinx.json)

            implementation(kmp.kotlinx.serialization.json)
            implementation(kmp.kotlinx.coroutines.core)
        }
        commonTest.dependencies {
            implementation(kmp.kotlin.test)
            implementation(kmp.kotlinx.coroutines.test)
        }
        jsMain.dependencies {
            implementation(kmp.ktor.client.js)
        }
        jvmMain.dependencies {
            implementation(kmp.ktor.client.okhttp)
        }
        appleMain.dependencies {
            implementation(kmp.ktor.client.darwin)
        }

        // JS requires this annotation to be exported
        all {
            languageSettings {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
    }

    tasks {
        // build the cocoapods framework for iOS & macOS
        val buildCocoapods by creating {
            dependsOn(":migration:cocoapodsInstall")
        }
    }
}
