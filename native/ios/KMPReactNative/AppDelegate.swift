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
    
    override init() {
        super.init()
        UIApplication.shared.delegate = self
    }
    
    override func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        self.automaticallyLoadReactNativeWindow = false
        super.application(application, didFinishLaunchingWithOptions: launchOptions)
        // MUST be called after calling SUPER
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
