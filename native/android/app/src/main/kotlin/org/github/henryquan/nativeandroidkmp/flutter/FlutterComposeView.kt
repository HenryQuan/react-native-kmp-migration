package org.github.henryquan.nativeandroidkmp.flutter

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import io.flutter.embedding.android.FlutterFragment
import org.github.henryquan.nativeandroidkmp.databinding.FlutterFragmentBridgeBinding

@Composable
fun FlutterComposeView() {

    AndroidViewBinding(FlutterFragmentBridgeBinding::inflate) {
        // the activity is needed to get the fragment


        val myFragment = flutterFragmentContainerView.getFragment<FlutterFragment>()
        // ...
    }
}
