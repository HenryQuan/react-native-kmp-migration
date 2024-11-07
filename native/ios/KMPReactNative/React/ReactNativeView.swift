//
//  ReactNativeView.swift
//  KMPReactNative
//
//  Created by Yiheng Quan on 7/11/2024.
//

import UIKit
import SwiftUI
import React_RCTAppDelegate

class ReactNativeViewFactory {
    
    static var shared = ReactNativeViewFactory()
    private init() {}
    
    /// With SwiftUI, the UIApplication delegate isn't the one we expect, have to pass it in manually
    private var viewFactory: RCTRootViewFactory?
    func provide(viewFactory: RCTRootViewFactory) {
        self.viewFactory = viewFactory
    }
    
    func produce(with moduleName: String) -> UIView? {
        return viewFactory?.view(withModuleName: moduleName, initialProperties: [:])
    }
}

struct ReactNativeView : UIViewRepresentable {
    typealias UIViewType = UIView
    
    let moduleName: String
    
    func makeUIView(context: Context) -> UIViewType {
        let reactView = ReactNativeViewFactory.shared.produce(with: moduleName)
        if reactView == nil {
            assertionFailure("This React Native view is not registered correctly.")
        }
        
        return reactView ?? UIView()
    }
    
    func updateUIView(_ uiView: UIView, context: Context) {
        //
    }
}
