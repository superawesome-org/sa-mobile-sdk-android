package tv.superawesome.demoapp

interface HasEnvironment {
    companion object {
        var environment: SDKEnvironment = SDKEnvironment.Production
    }
}
