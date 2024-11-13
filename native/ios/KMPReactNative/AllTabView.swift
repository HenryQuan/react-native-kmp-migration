//
//  AllTabView.swift
//  KMPReactNative
//
//  Created by Yiheng Quan on 13/11/2024.
//


import SwiftUI

struct AllTabView: View {
    var body: some View {
        TabView {
            ContentView()
                .tabItem {
                    Label("Native", systemImage: "list.star")
                }

            ReactNativeView(moduleName: "existing")
                .tabItem {
                    Label("React Native", systemImage: "circle")
                }
            
            ComposeHomeScreenView()
                .tabItem {
                    Label("Compose", systemImage: "pencil")
                }
            
            FlutterView()
                .tabItem {
                    Label("Flutter", systemImage: "flag")
                }
            
            AllInOneView()
                .tabItem {
                    Label("All In One", systemImage: "star")
                }
        }
    }
}

struct AllInOneView: View {
    var body: some View {
        VStack {
            VStack {
                Text("Hello From SwiftUI")
                HStack {
                    Image(systemName: "star")
                    Image(systemName: "star")
                    Image(systemName: "star")
                    Image(systemName: "star")
                    Image(systemName: "star")
                }
            }
            
            HelloComposeView()
            ReactNativeView(moduleName: "reactNativeView")
            StandaloneFlutterView()
        }
    }
}
