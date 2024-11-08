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
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosArm64(),
        macosX64(),
        watchosX64(),
    ).forEach {
        it.binaries.framework {
            baseName = "KmpMigration"
        }
    }
    cocoapods {
        version = "0.0.2"
        summary = "KMP Migration"
        homepage = "https://github.com/HenryQuan/react-native-kmp-migration"
        name = "KmpMigration"
        ios.deploymentTarget = "14.0"
        framework {
            baseName = "KmpMigration" // the name of the framework
            isStatic = true // avoid dynamic framework
            // DO NOT export this project, only export dependencies with API visibility
//            export(project(":migration"))
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
