package org.github.henryquan.nativeandroidkmp.react

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.facebook.react.ReactFragment

object ReactNativeFragmentCache {
    private val cache = mutableMapOf<String, Fragment>()

    fun getOrCreateFragment(componentName: String, bundle: Bundle? = null): Fragment {
        if (cache.containsKey(componentName)) {
            return cache[componentName]!!
        }

        val fragment = ReactFragment.Builder()
            .setComponentName(componentName)
            .setLaunchOptions(bundle)
            .build()
        cache[componentName] = fragment
        return fragment
    }

    fun getComponentTag(componentName: String): String {
        return "ReactNativeFragment:$componentName"
    }
}
