import buildlogic.Namespace

plugins {
    id("ads.sdk.android.library")
}

android {
    namespace = "${Namespace.sdk.publisher}.common"

    kotlin {
        explicitApi()
    }
}
