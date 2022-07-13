//
//  JSONController.swift
//  
//
//  Created by Tom O'Rourke on 11/07/2022.
//

import Vapor

struct JSONController {

    func getJson(_ fileName: String) -> (Request) throws -> Response {
        return { request in

            let data = JSONLoader().loadJson(withFileName: fileName)
            let body = Response.Body(data: data)
            return Response(status: HTTPStatus.ok, body: body)
        }
    }
}
