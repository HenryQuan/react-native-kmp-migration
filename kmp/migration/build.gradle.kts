plugins {
    alias(kmp.plugins.kotlin.multiplatform)
    alias(kmp.plugins.kotlinx.serialization)
    // Apple, iOS, macOS, watchOS
    alias(kmp.plugins.kotlin.cocoapods)

    // compose
    alias(kmp.plugins.compose.multiplatform)
    alias(kmp.plugins.compose.compiler)
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
        // NOTE: can still be included if we move compose dependencies to individual targets instead of commonMain
        // watchosX64(), cannot be enabled due to the compose multiplatform
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

            // compose related
            // compose namespace should be included in the plugin probably, we don't have to control the version manually anymore
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(kmp.androidx.lifecycle.viewmodel)
            implementation(kmp.androidx.lifecycle.runtime.compose)
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
