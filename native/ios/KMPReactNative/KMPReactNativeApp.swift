//
//  KMPReactNativeApp.swift
//  KMPReactNative
//
//  Created by Yiheng Quan on 7/11/2024.
//

import SwiftUI
import FlutterPluginRegistrant

// https://docs.flutter.dev/add-to-app/ios/add-flutter-screen#create-a-flutterengine
// modified a bit due to lower iOS version support
// @Observable for iOS 17+
class FlutterDependencies : ObservableObject {
    private let engineGroup = FlutterEngineGroup(name: "EngineGroup", project: nil)
    lazy var flutterEngine: FlutterEngine = {
        engineGroup.makeEngine(with: nil)
    }()
    lazy var standaloneEngine: FlutterEngine = {
        engineGroup.makeEngine(withEntrypoint: "flutterhello", libraryURI: nil)
    }()
    init() {
        // Runs the default Dart entrypoint with a default Flutter route.
        flutterEngine.run()
        standaloneEngine.run()
        // Connects plugins with iOS platform code to this app.
        GeneratedPluginRegistrant.register(with: self.flutterEngine);
    }
}

@main
struct KMPReactNativeApp: App {
    @StateObject var flutterDependencies = FlutterDependencies()
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    var body: some Scene {
        WindowGroup {
            AllTabView()
                .environmentObject(flutterDependencies)
        }
    }
}
