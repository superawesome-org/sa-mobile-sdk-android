package tv.superawesome.sdk.publisher.testutil

import tv.superawesome.sdk.publisher.components.ConnectionProviderType
import tv.superawesome.sdk.publisher.models.ConnectionType

class FakeConnectionProvider : ConnectionProviderType {
    override fun findConnectionType(): ConnectionType = connectionType

    companion object {
        var connectionType: ConnectionType = ConnectionType.Unknown
    }
}
