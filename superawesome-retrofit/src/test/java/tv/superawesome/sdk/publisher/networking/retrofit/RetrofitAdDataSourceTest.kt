package tv.superawesome.sdk.publisher.networking.retrofit

import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Properties
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.Test
import tv.superawesome.sdk.publisher.common.models.AdQuery
import tv.superawesome.sdk.publisher.common.models.ConnectionType

@Serializable
data class Data(val a: Int, val b: String = "42")

class RetrofitAdDataSourceTest {
    @ImplicitReflectionSerializer
    @Test
    fun test() {

//        val map = mapOf<String, Any>(
//                "a" to 1,
//                "b" to false,
//                "c" to ConnectionType.wifi
//        )

        val query = AdQuery(false, "sdk", 10, "bundle", "name", 20, ConnectionType.wifi,
                "", "", 1, 1, 1, 1, 1, 1, 1)


        val map2 = Properties.store(query)

        print("map2: ${map2}")
//        val json = jsonAdapter.toJson(map)
//        print(json)


        // Json also has .Default configuration which provides more reasonable settings,
        // but is subject to change in future versions
        val json = Json(JsonConfiguration.Stable)
        // serializing objects
//        val jsonData = json.stringify(Data.serializer(), da)
        // serializing lists
        val jsonList = json.stringify(Data.serializer().list, listOf(Data(42)))
//        println(jsonData) // {"a": 42, "b": "42"}
        println(jsonList) // [{"a": 42, "b": "42"}]

        // parsing data back
        val obj = json.parse(Data.serializer(), """{"a":42}""") // b is optional since it has default value
        println(obj) // Data(a=42, b="42")
    }
}