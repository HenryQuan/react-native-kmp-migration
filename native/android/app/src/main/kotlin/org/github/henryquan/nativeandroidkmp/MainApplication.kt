package org.github.henryquan.nativeandroidkmp

import android.app.Application
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.load
import com.facebook.react.defaults.DefaultReactHost.getDefaultReactHost
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.react.soloader.OpenSourceMergedSoMapping
import com.facebook.soloader.SoLoader

internal class MainApplication : Application(), ReactApplication {
    override val reactNativeHost: ReactNativeHost
        get() = object : DefaultReactNativeHost(this) {
            override fun getPackages(): List<ReactPackage> {
                return PackageList(this).packages
            }

            override fun getJSMainModuleName(): String {
                return "index"
            }

            override fun getUseDeveloperSupport(): Boolean {
                return BuildConfig.DEBUG
            }

            override val isNewArchEnabled: Boolean
                get() = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
            override val isHermesEnabled: Boolean
                get() = BuildConfig.IS_HERMES_ENABLED
        }

    override val reactHost: ReactHost
        get() {
            return getDefaultReactHost(applicationContext, reactNativeHost)
        }

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, OpenSourceMergedSoMapping)
        if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
            load()
        }
    }
}
