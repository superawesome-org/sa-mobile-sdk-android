import buildlogic.Namespace

plugins {
    id("ads.sdk.android.library")
}

apply(from = "../publish.gradle")

extra.apply {
    set("libraryDescription", "SuperAwesome Mobile SDK for Android with AdMob extension")
    set("libraryName", "SuperAwesome AdMob")
}

android {
    namespace = "${Namespace.plugins.publisher}.admob"
}

dependencies {
    implementation(project(":superawesome-base"))
    implementation(libs.google.play.services.ads)
}
