package org.github.henryquan.nativeandroidkmp.compose

import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import com.facebook.react.ReactFragment
import io.flutter.embedding.android.FlutterFragment

@Composable
fun ReactNativeComposeView(componentName: String, modifier: Modifier = Modifier) {
    FragmentBridgeView({
        ReactFragment.Builder()
            .setComponentName(componentName)
            .build()
    }, modifier)
}

@Composable
fun FlutterComposeView(cachedEngineId: String, modifier: Modifier = Modifier) {
    FragmentBridgeView({
        FlutterFragment.withCachedEngine(cachedEngineId).build()
    }, modifier)
}

/**
 * Bridge a Fragment View to Compose.
 * https://stackoverflow.com/a/72055125
 * Also referenced with ChatGPT's implementation.
 */
@Composable
fun FragmentBridgeView(
    fragmentBuilder: () -> Fragment, modifier: Modifier = Modifier
) {
    AndroidView(modifier = modifier, factory = { context ->
        FragmentContainerView(context).apply {
            id = View.generateViewId()
            if (context is FragmentActivity) {
                val fragmentManager = context.supportFragmentManager
                val fragment = fragmentBuilder()
                // allow state loss is requited for this to work because it ignores state loss, more async
                fragmentManager.beginTransaction().replace(id, fragment).commitAllowingStateLoss()
                return@apply
            }

            throw IllegalStateException("FragmentBridgeView must be hosted in a FragmentActivity")
        }
    })
}
