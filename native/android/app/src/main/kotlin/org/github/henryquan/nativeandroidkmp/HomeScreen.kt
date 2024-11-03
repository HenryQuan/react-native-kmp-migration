package org.github.henryquan.nativeandroidkmp

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val (shipAdditional, filterUseCase) = useShipAdditional()
    var filterText by remember { mutableStateOf(TextFieldValue("")) }
    var filteredList by remember { mutableStateOf<List<String>>(emptyList()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Complex Screen") },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                // Main message
                Text(
                    text = "Just consider this as an existing screen",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium
                )

                // Navigation button
                Button(
                    onClick = {
                        // Navigation to the complex screen
//                        navController.navigate("React Native")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Go to complex screen")
                }

                // Text field for filtering
                TextField(
                    value = filterText,
                    onValueChange = { newValue ->
                        filterText = newValue
                        val result = filterUseCase?.filterHEPen(newValue.text.toIntOrNull() ?: 0)
                        filteredList = result ?: emptyList()
                    },
                    placeholder = { Text("Filter by alphaPiercingHE") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Filter result message
                Text(
                    text = if (filteredList.isNotEmpty()) "Filtered result:" else "No filter result, showing all ships",
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Display the list in a scrollable column
                if (filteredList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        items(filteredList) { item ->
                            ListItem(
                                modifier = Modifier.padding(2.dp),
                                headlineContent = { Text(item) }
                            )
                        }
                    }
                } else {
                    ShipAdditionalList(shipAdditional)
                }
            }
        }
    )
}