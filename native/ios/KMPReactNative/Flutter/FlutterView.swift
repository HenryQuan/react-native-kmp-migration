//
//  FlutterView.swift
//  KMPReactNative
//
//  Created by Yiheng Quan on 13/11/2024.
//

import SwiftUI
import Flutter

struct FlutterView: UIViewControllerRepresentable {
  // Flutter dependencies are passed in through the view environment.
    @EnvironmentObject var flutterDependencies: FlutterDependencies
  
    func makeUIViewController(context: Context) -> some UIViewController {
        return FlutterViewController(
          engine: flutterDependencies.flutterEngine,
          nibName: nil,
          bundle: nil)
    }
  
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
    
    }
}

struct StandaloneFlutterView: UIViewControllerRepresentable {
    @EnvironmentObject var flutterDependencies: FlutterDependencies
    
    func makeUIViewController(context: Context) -> some UIViewController {
        return FlutterViewController(
            engine: flutterDependencies.standaloneEngine,
            nibName: nil,
            bundle: nil)
    }
    
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
    
    }

}
