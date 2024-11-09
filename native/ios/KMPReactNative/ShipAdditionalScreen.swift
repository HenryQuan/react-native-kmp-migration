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
    private var filterUseCase: FilterUseCase? = nil
    
    @Published var filterText: String = ""
    @Published var filteredList: [UniqueString] = []
    
    private let service = ShipAdditionalService()
    
    var filteredString: String {
        filteredList.isEmpty ? "No filter result, showing all ships" : "Filtered result: \(filteredList.count)"
    }

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
    
    func onFilterTextChange() {
        if !filterText.isEmpty {
            if let penInput = Int32(filterText) {
                // NOTE: the order seems to be random here, emm maybe a sorting is needed from the kotlin side
                // REACT Native side is quite stable, could be due to the UniqueString or whatever reason
                filteredList = filterUseCase?.filterHEPen(pen: penInput)
                    .enumerated()
                    .map { index, value in UniqueString(id: index, value: value) } ?? []
                return
            }
        }
        filteredList = []
    }
}

struct UniqueString: Identifiable {
    let id: Int
    let value: String
}


struct ShipAdditionalScreen: View {
    @StateObject var viewModel = ShipAdditionalViewModel()

    var body: some View {
        NavigationView {
            VStack {
                // Main message
                Text("The SwiftUI version")
                    .multilineTextAlignment(.center)
                    .padding()

                // Text field for filtering
                TextField("Filter by alphaPiercingHE", text: $viewModel.filterText)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .onSubmit(viewModel.onFilterTextChange)
                
                // Filter result message
                Text(viewModel.filteredString)
                    .padding()
                
                // Display the list in a scrollable column
                if !viewModel.filteredList.isEmpty {
                    List(viewModel.filteredList) { item in
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
                ProgressView()
            }
        }
    }
}
