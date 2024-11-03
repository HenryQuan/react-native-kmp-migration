package org.github.henryquan.nativeandroidkmp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import io.github.henryquan.FilterUseCase
import io.github.henryquan.service.ShipAdditionalService
import kotlinx.coroutines.launch

@Composable
fun ShipAdditionalView() {

    val scope = rememberCoroutineScope()
    val service = ShipAdditionalService()


    LaunchedEffect(Unit) {
        scope.launch {
            val additional = service.getShipAdditional()
            val useCase = FilterUseCase(additional)
        }

    }

}