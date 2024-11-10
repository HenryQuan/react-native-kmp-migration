package org.github.henryquan.nativeandroidkmp.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.github.henryquan.nativeandroidkmp.MainApplication

@Composable
fun TrinityView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box {
            HelloComposeView()
        }
        Box{
            FlutterComposeView(MainApplication.standadloneViewEngineCacheId, modifier = Modifier.height(200.dp))
        }
        Box {
            ReactNativeComposeView("reactNativeView")
        }
    }
}