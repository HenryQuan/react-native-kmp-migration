plugins {
    alias(kmp.plugins.kotlin.multiplatform)
    alias(kmp.plugins.kotlinx.serialization)
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
    iosX64()
    iosArm64()
    iosSimulatorArm64()

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
        iosMain.dependencies {
            implementation(kmp.ktor.client.darwin)
        }

        // JS requires this annotation to be exported
        all {
            languageSettings {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
    }
}
