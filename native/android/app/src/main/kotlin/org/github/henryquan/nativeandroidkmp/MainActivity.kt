package org.github.henryquan.nativeandroidkmp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.henryquan.ui.CommonHomeScreen
import org.github.henryquan.nativeandroidkmp.flutter.DefaultFlutterFragmentActivity
import org.github.henryquan.nativeandroidkmp.react.ExistingReactActivity
import org.github.henryquan.nativeandroidkmp.ui.theme.AndroidKMPTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidKMPTheme {
//                HomeScreen()
                // the shared screen from the multiplatform module
                CommonHomeScreen(
                    launchReactNative = {
                        useLaunchReactNative()
                    },
                    launchFlutter = {
                        useLaunchFlutter()
                    }
                )
            }
        }
    }

    private fun useLaunchReactNative() {
        val intent = Intent(this, ExistingReactActivity::class.java)
        startActivity(intent)
    }

    private fun useLaunchFlutter() {
        val intent = Intent(this, DefaultFlutterFragmentActivity::class.java)
        startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroidKMPApp() {
    AndroidKMPTheme {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = { Text("Android KMP") })
        }) { innerPadding ->
            Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.then(Modifier.padding(16.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidKMPTheme {
        Greeting("Android")
    }
}