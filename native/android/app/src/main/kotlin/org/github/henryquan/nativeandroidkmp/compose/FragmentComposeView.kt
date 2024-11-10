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
fun ReactNativeComposeView(componentName: String) {
    val reactNativeFragment = ReactFragment.Builder()
                   .setComponentName(componentName)
                   // pass props to React Native if needed
//                   .setLaunchOptions(Bundle().apply { putString("message", "my value") })
                   .build()

    FragmentBridgeView(reactNativeFragment)
}

@Composable
fun FlutterComposeView(cachedEngineId: String) {
    val flutterFragment: FlutterFragment = FlutterFragment.withCachedEngine(cachedEngineId).build()
    FragmentBridgeView(flutterFragment)
}

/**
 * Bridge a Fragment View to Compose.
 * https://stackoverflow.com/a/72055125
 * Also referenced with ChatGPT's implementation.
 */
@Composable
fun FragmentBridgeView(
    fragment: Fragment, modifier: Modifier = Modifier
) {
    AndroidView(modifier = modifier.fillMaxSize(), factory = { context ->
        FragmentContainerView(context).apply {
            id = View.generateViewId()
            if (context is FragmentActivity) {
                val fragmentManager = context.supportFragmentManager
                // allow state loss is requited for this to work because it ignores state loss, more async
                fragmentManager.beginTransaction().replace(id, fragment).commitAllowingStateLoss()
                return@apply
            }

            throw IllegalStateException("FragmentBridgeView must be hosted in a FragmentActivity")
        }
    })
}
