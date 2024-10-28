plugins {
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
}

// NOTE: use allprojects instead of subprojects here to avoid
// Cannot resolve external because no repositories are defined.
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
