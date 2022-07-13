import Fluent
import Vapor

private let jsonController = JSONController()

func routes(_ app: Application) throws {

    // MARK: Get requests -

    app.get("ad", "82090", use: jsonController.getJson("mock_interstitial_response"))
    app.get("ad", "87892", use: jsonController.getJson("mock_interstitial_response"))
}
