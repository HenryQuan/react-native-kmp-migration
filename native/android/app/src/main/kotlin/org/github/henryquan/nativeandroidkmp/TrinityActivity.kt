package org.github.henryquan.nativeandroidkmp

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import org.github.henryquan.nativeandroidkmp.compose.BottomNavigationRoutes
import org.github.henryquan.nativeandroidkmp.compose.BottomNavigationScaffold
import org.github.henryquan.nativeandroidkmp.flutter.DefaultFlutterFragmentActivity
import org.github.henryquan.nativeandroidkmp.flutter.FlutterFragmentContainerActivity
import org.github.henryquan.nativeandroidkmp.react.ExistingReactActivity
import org.github.henryquan.nativeandroidkmp.ui.theme.AndroidKMPTheme

/**
 * Put Jetpack Compose, React Native, and Flutter together in one activity.
 */
class TrinityActivity : FlutterFragmentContainerActivity(), DefaultHardwareBackBtnHandler {

    // better to put under the xml resources
    private val reactFragmentId = 573075914
    private val flutterFragmentId = 573075915

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidKMPTheme {
                val navController = rememberNavController()
                BottomNavigationScaffold(navController, BottomNavigationRoutes.Compose.name)
            }
        }
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }
}
