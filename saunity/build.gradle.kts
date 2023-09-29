import buildlogic.Namespace

plugins {
    id("ads.sdk.android.library")
}

apply(from = "../publish.gradle")

extra.apply {
    set("libraryDescription", "SuperAwesome Mobile SDK for Android with Unity extension")
    set("libraryName", "SuperAwesome Unity")
}

android {
    namespace = "${Namespace.plugins.publisher}.unity"
}

dependencies {
    implementation(project(":superawesome-base"))
    implementation(libs.androidx.lifecycle.runtime.ktx)
}
