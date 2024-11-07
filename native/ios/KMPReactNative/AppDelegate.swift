//
//  AppDelegate.swift
//  KMPReactNative
//
//  Created by Yiheng Quan on 7/11/2024.
//

import UIKit
import React
import React_RCTAppDelegate

class AppDelegate: RCTAppDelegate {
    
    override func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        rootViewFactory = RCTRootViewFactory(
            configuration: .init(bundleURLBlock: {
                self.bundleURL()
            }, newArchEnabled: true, turboModuleEnabled: true, bridgelessEnabled: true)
        )
        ReactNativeViewFactory.shared.provide(viewFactory: rootViewFactory)
        return true
    }
  
    override func sourceURL(for bridge: RCTBridge) -> URL? {
        bundleURL()
    }

    override func bundleURL() -> URL? {
#if DEBUG
        RCTBundleURLProvider.sharedSettings().jsBundleURL(forBundleRoot: "index")
#else
        Bundle.main.url(forResource: "main", withExtension: "jsbundle")
#endif
    }
}
