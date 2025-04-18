// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false

    // include kmp plugins here also
    alias(kmp.plugins.kotlinx.serialization).apply(false)
    alias(kmp.plugins.kotlin.multiplatform).apply(false)
    // make sure all plugins are applied here also, not only in the kmp project
    alias(kmp.plugins.kotlin.cocoapods).apply(false)
    // react native plugin
    alias(libs.plugins.facebook.react).apply(false)
}
