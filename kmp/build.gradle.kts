plugins {
    alias(kmp.plugins.kotlinx.serialization).apply(false)
    alias(kmp.plugins.kotlin.multiplatform).apply(false)
    alias(kmp.plugins.kotlin.cocoapods).apply(false)
}

// NOTE: use allprojects instead of subprojects here to avoid
// Cannot resolve external because no repositories are defined.
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
