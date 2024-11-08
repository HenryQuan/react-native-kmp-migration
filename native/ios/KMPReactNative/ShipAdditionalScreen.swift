//
//  ShipAdditionalScreen.swift
//  KMPReactNative
//
//  Created by Yiheng Quan on 8/11/2024.
//

import KmpMigration
import SwiftUI

typealias ShipAdditionalMap = [String: ShipAdditional]

class ShipAdditionalViewModel: ObservableObject {
    @Published var shipAdditional: ShipAdditionalMap? = nil
    @Published var filterUseCase: FilterUseCase? = nil
    
    private let service = ShipAdditionalService()

    func fetchData() {
        guard shipAdditional == nil else { return }
        Task {
            do {
                let result = try await service.getShipAdditional()
                print("getShipAdditional is finished")
                DispatchQueue.main.async {
                    self.shipAdditional = result
                    self.filterUseCase = FilterUseCase(additionalMap: result)
                }
            } catch {
                print("Error fetching ship additional data: \(error)")
            }
        }
    }
}

struct UniqueString: Identifiable {
    let id = UUID()
    let value: String
}


struct ShipAdditionalScreen: View {
    @State private var filterText = ""
    @State private var filteredList: [UniqueString] = []
    @ObservedObject var viewModel = ShipAdditionalViewModel()

    var body: some View {
        NavigationView {
            VStack {
                // Main message
                Text("The SwiftUI version")
                    .multilineTextAlignment(.center)
                    .padding()

                // Text field for filtering
                TextField("Filter by alphaPiercingHE", text: $filterText)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .onSubmit {
                        if !filterText.isEmpty {
                            if let penInput = Int32(filterText) {
                                filteredList = viewModel.filterUseCase?.filterHEPen(pen: penInput)
                                    .map({UniqueString(value: $0)}) ?? []
                                return
                            }
                        }
                        filteredList = []
                    }
                
                // Filter result message
                Text(filteredList.isEmpty ? "No filter result, showing all ships" : "Filtered result: \(filteredList.count)")
                    .padding()
                
                // Display the list in a scrollable column
                if !filteredList.isEmpty {
                    List(filteredList) { item in
                        VStack(alignment: .leading) {
                            Text(item.value)
                                .font(.headline)
                        }
                    }
                } else {
                    ShipAdditionalListView(shipAdditional: viewModel.shipAdditional)
                }
            }.onAppear {
                viewModel.fetchData()
            }
        }
    }
}


struct ShipAdditionalListView: View {
    let shipAdditional: ShipAdditionalMap?
    
    var body: some View {
        VStack {
            if let shipAdditional = shipAdditional {
                List {
                    ForEach(Array(shipAdditional.enumerated()), id: \.offset) { (index, entry) in
                        let (key, value) = entry
                        let keyString = "\(key)\(value.sigma?.stringValue ?? "")"
                        VStack(alignment: .leading) {
                            Text(keyString)
                                .font(.headline)
                            Text(value.description)
                        }
                    }
                }
            } else {
                Text("Loading...")
                    .font(.subheadline)
                    .padding()
            }
        }
    }
}
