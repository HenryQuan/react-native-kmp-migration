package org.github.henryquan.nativeandroidkmp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.henryquan.FilterUseCase
import io.github.henryquan.ShipAdditional
import io.github.henryquan.ShipAdditionalMap
import io.github.henryquan.service.ShipAdditionalService
import kotlinx.coroutines.launch

// Converted from React Native code

@Composable
fun ShipAdditionalList(shipAdditional: ShipAdditionalMap?) {
    if (shipAdditional == null) {
        Column {
            Text("Loading...")
        }
    } else {
        LazyColumn {
            items(shipAdditional.toList()) { (key, value) ->
                val keyString = "${key}${value.sigma}"
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(keyString)
                    Text(value.toString())
                }
            }
        }
    }
}

@Composable
fun useShipAdditional(): Pair<ShipAdditionalMap?, FilterUseCase?> {
    val shipAdditional = remember { mutableStateOf<Map<String, ShipAdditional>?>(null) }
    val filterUseCase = remember { mutableStateOf<FilterUseCase?>(null) }
    val service = remember { ShipAdditionalService() }
    val scope = rememberCoroutineScope()

    // Side effect to load data once when the composable enters the composition
    LaunchedEffect(Unit) {
        try {
            // Fetch data from the service
            scope.launch {
                val result = service.getShipAdditional()
                println("getShipAdditional is finished")
                shipAdditional.value = result
                filterUseCase.value = FilterUseCase(result)
            }
        } catch (e: Exception) {
            println("Error fetching ship additional data: $e")
        }
    }

    // Return the state as a Pair
    return shipAdditional.value to filterUseCase.value
}
