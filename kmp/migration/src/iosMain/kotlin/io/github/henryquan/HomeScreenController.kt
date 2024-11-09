package io.github.henryquan

import androidx.compose.ui.window.ComposeUIViewController
import io.github.henryquan.ui.CommonHomeScreen

fun HomeViewController(
    launchReactNative: (() -> Unit)? = null
) = ComposeUIViewController { CommonHomeScreen(launchReactNative) }
