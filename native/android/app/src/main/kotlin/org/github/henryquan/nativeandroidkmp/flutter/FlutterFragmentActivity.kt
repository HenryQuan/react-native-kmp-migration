package org.github.henryquan.nativeandroidkmp.flutter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import io.flutter.embedding.android.FlutterFragment
import org.github.henryquan.nativeandroidkmp.MainApplication
import org.github.henryquan.nativeandroidkmp.R

class DefaultFlutterFragmentActivity : CustomFlutterFragmentActivity(MainApplication.flutterEngineCacheId)

// copied from https://docs.flutter.dev/add-to-app/android/add-flutter-fragment#add-a-flutterfragment-to-an-activity-with-a-new-flutterengine
// must be AppCompatActivity which is also a FragmentActivity, required by React Native
abstract class FlutterFragmentContainerActivity : AppCompatActivity() {
    var flutterFragment: FlutterFragment? = null

    override fun onPostResume() {
        super.onPostResume()
        flutterFragment?.onPostResume()
    }

    override fun onNewIntent(intent: Intent) {
        flutterFragment?.onNewIntent(intent)
        super.onNewIntent(intent)
    }

    override fun onBackPressed() {
        flutterFragment?.onBackPressed()
        super.onBackPressed()
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
//    ) {
//        flutterFragment?.onRequestPermissionsResult(
//            requestCode, permissions, grantResults
//        )
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }


    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        flutterFragment?.onActivityResult(
            requestCode, resultCode, data
        )
    }

    override fun onUserLeaveHint() {
        flutterFragment?.onUserLeaveHint()
        super.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        flutterFragment?.onTrimMemory(level)
    }
}

abstract class CustomFlutterFragmentActivity(private val cachedEngineId: String) :
    FlutterFragmentContainerActivity() {
    companion object {
        // Define a tag String to represent the FlutterFragment within this
        // Activity's FragmentManager. This value can be whatever you'd like.
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment_placeholder"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate a layout that has a container for your FlutterFragment. For
        // this example, assume that a FrameLayout exists with an ID of
        // R.id.flutter_fragment_layout.
        setContentView(R.layout.flutter_fragment_layout)

        // Get a reference to the Activity's FragmentManager to add a new
        // FlutterFragment, or find an existing one.
        val fragmentManager = supportFragmentManager

        // Attempt to find an existing FlutterFragment, in case this is not the
        // first time that onCreate() was run.
        flutterFragment =
            fragmentManager.findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?

        // Create and attach a FlutterFragment if one does not exist.
        if (flutterFragment == null) {
            val newFlutterFragment: FlutterFragment =
                FlutterFragment.withCachedEngine(cachedEngineId).build()
            flutterFragment = newFlutterFragment
            fragmentManager.beginTransaction().add(
                R.id.flutter_fragment_container, newFlutterFragment, TAG_FLUTTER_FRAGMENT
            ).commit()
        }
    }
}