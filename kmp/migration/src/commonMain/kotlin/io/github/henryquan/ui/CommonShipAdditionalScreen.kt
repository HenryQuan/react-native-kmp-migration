package io.github.henryquan.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.github.henryquan.FilterUseCase
import io.github.henryquan.ShipAdditional
import io.github.henryquan.ShipAdditionalMap
import io.github.henryquan.service.ShipAdditionalService
import kotlinx.coroutines.launch

@Composable
fun CommonShipAdditionalList(shipAdditional: ShipAdditionalMap?) {
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
fun useCommonShipAdditional(): Pair<ShipAdditionalMap?, FilterUseCase?> {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonHomeScreen(
    launchReactNative: (() -> Unit)? = null
) {
    val (shipAdditional, filterUseCase) = useCommonShipAdditional()
    var filterText by remember { mutableStateOf(TextFieldValue("")) }
    var filteredList by remember { mutableStateOf<List<String>>(emptyList()) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Complex Screen") },
        )
    }, content = { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
        ) {
            // Main message
            Text(
                text = "Just consider this as an existing screen",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium
            )

            // Navigation button if available
            launchReactNative?.let {
                Button(
                    onClick = { launchReactNative() },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text("Go to React Native")
                }
            }

            // Text field for filtering
            TextField(value = filterText,
                onValueChange = { newValue ->
                    if (newValue.text.isEmpty()) {
                        filterText = newValue
                        filteredList = emptyList()
                        return@TextField
                    }

                    // don't accept next line, remove \n
                    if (newValue.text.contains("\n")) {
                        filterText = TextFieldValue(newValue.text.replace("\n", ""))
                        return@TextField
                    }

                    filterText = newValue
                    val penInput = newValue.text.toIntOrNull() ?: 0
                    val result = filterUseCase?.filterHEPen(penInput)
                    filteredList = result ?: emptyList()
                },
                placeholder = { Text("Filter by alphaPiercingHE") },
                modifier = Modifier.fillMaxWidth()
            )

            // Filter result message
            Text(
                text = if (filteredList.isNotEmpty()) "Filtered result: ${filteredList.size}" else "No filter result, showing all ships",
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Display the list in a scrollable column
            if (filteredList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(filteredList) { item ->
                        ListItem(modifier = Modifier.padding(2.dp),
                            headlineContent = { Text(item) })
                    }
                }
            } else {
                CommonShipAdditionalList(shipAdditional)
            }
        }
    })
}