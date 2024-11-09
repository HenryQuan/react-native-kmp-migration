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
        HomeScreenControllerKt.HomeViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        //
    }
}
