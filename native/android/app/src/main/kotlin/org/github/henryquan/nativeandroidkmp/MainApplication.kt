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
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.FlutterEngineGroup
import io.flutter.embedding.engine.dart.DartExecutor

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

        setupFlutterEngineGroup()
    }

    private lateinit var engineGroup: FlutterEngineGroup
    companion object {
        val flutterEngineCacheId = "kmp_rn_flutter_engine_default"
        val standadloneViewEngineCacheId = "kmp_rn_flutter_engine_standalone_view"
    }

    private fun setupFlutterEngineGroup() {
        engineGroup = FlutterEngineGroup(this)
        val defaultEngine = engineGroup.createAndRunDefaultEngine(this)
        val viewEngine = engineGroup.createAndRunEngine(
            this,
            DartExecutor.DartEntrypoint(
                getFlutterBundlePath(),
                "flutterhello"
            )
        )

        FlutterEngineCache.getInstance().apply {
            put(flutterEngineCacheId, defaultEngine)
            put(standadloneViewEngineCacheId, viewEngine)
        }
    }

    private fun getFlutterBundlePath(): String {
        val flutterLoader = FlutterInjector.instance().flutterLoader()
        if (!flutterLoader.initialized()) {
            throw AssertionError(
                "DartEntrypoints can only be created once a FlutterEngine is created."
            )
        }
        return flutterLoader.findAppBundlePath()
    }
}
