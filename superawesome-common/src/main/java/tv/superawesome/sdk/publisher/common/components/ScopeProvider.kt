package tv.superawesome.sdk.publisher.common.components

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProviderType {
    var main: CoroutineDispatcher
    var io: CoroutineDispatcher
}

class DispatcherProvider : DispatcherProviderType {
    override var main: CoroutineDispatcher = Dispatchers.Main
    override var io: CoroutineDispatcher = Dispatchers.IO
}