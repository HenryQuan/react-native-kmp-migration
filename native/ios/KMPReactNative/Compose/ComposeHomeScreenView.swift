//
//  ComposeView.swift
//  KMPReactNative
//
//  Created by Yiheng Quan on 9/11/2024.
//

import SwiftUI
import KmpMigration


struct ComposeHomeScreenView : UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        HomeScreenControllerKt.HomeViewController(
            launchReactNative: {
                print("Can you do this easily in React Native or Flutter?")

                // just grab the root and show a modal
                let rootViewController = UIApplication.shared.windows.first?.rootViewController
                rootViewController?.present(buildReactNativeController(), animated: true, completion: nil)
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        //
    }

    private func buildReactNativeController() -> UIViewController {
        let reactNativeController = UIViewController()
        let reactView = ReactNativeViewFactory.shared.produce(with: "existing")
        if reactView == nil {
            assertionFailure("This React Native view is not registered correctly.")
        }
        reactNativeController.view = reactView
        return reactNativeController
    }
}

struct HelloComposeView : UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        HomeScreenControllerKt.HelloComposeViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
