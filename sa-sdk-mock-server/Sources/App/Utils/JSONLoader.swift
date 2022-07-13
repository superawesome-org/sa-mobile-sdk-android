//
//  JSONLoader.swift
//  
//
//  Created by Tom O'Rourke on 11/07/2022.
//

import Vapor

class JSONLoader {

    func loadJson(withFileName fileName: String) -> Data {
        let directory = DirectoryConfiguration.detect()
        let mockResponsesDir = "Sources/App/MockResponses"
        let url = URL(fileURLWithPath: directory.workingDirectory)
            .appendingPathComponent(mockResponsesDir, isDirectory: true)
            .appendingPathComponent("\(fileName).json", isDirectory: false)
        let terminal = Terminal()
        do {
            let data = try Data(contentsOf: url)
            return data
        } catch {
            terminal.print("FAILED to load json named: \(fileName).json path: \(url)")
            guard fileName != "error_request" else { return Data() }
            return loadJson(withFileName: "error_request")
        }
    }
}
