package buildlogic

import com.android.build.api.dsl.CommonExtension

fun CommonExtension<*, *, *, *>.androidConfig() {
    compileSdk = Versions.compileSdk

    defaultConfig {
        minSdk = Versions.minSdk
    }
}
