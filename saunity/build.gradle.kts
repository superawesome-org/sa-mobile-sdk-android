plugins {
    id("ads.sdk.android.library")
}

apply(from = "../publish.gradle")
apply(from = "../jacoco.gradle")

extra.apply {
    set("libraryDescription", "SuperAwesome Mobile SDK for Android with Unity extension")
    set("libraryName", "SuperAwesome Unity")
}

dependencies {
    implementation(project(":superawesome-base"))
}
