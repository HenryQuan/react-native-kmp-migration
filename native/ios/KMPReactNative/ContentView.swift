//
//  ContentView.swift
//  KMPReactNative
//
//  Created by Yiheng Quan on 7/11/2024.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            VStack {
                Image(systemName: "globe")
                    .imageScale(.large)
                    .foregroundStyle(.tint)
                Text("Hello, world!")
                NavigationLink(destination: {
                    ReactNativeView(moduleName: "existing")
                }, label: {
                    Text("Go to React Native")
                })
                NavigationLink(destination: {
                    ShipAdditionalScreen()
                }, label: {
                    Text("Native Ship Additional")
                })
                NavigationLink(destination: {
                    ComposeHomeScreenView()
                }, label: {
                    Text("Compose View")
                })
            }
            .padding()
            .navigationTitle("SwiftUI Home")
        }
    }
}

#Preview {
    ContentView()
}
