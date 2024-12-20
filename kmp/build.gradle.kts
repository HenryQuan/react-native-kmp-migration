plugins {
    alias(kmp.plugins.kotlinx.serialization).apply(false)
    alias(kmp.plugins.kotlin.multiplatform).apply(false)
    alias(kmp.plugins.kotlin.cocoapods).apply(false)

    // compose
    alias(kmp.plugins.compose.multiplatform).apply(false)
    alias(kmp.plugins.compose.compiler).apply(false)
}

// NOTE: use allprojects instead of subprojects here to avoid
// Cannot resolve external because no repositories are defined.
// NOTE: moved to settings.gradle.kts
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}
