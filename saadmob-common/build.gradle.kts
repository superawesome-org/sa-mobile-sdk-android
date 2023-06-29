plugins {
    id("ads.sdk.android.library")
}

apply(from = "../publish.gradle")
apply(from = "../jacoco.gradle")

extra.apply {
    set("libraryDescription", "SuperAwesome Mobile SDK for Android with AdMob extension")
    set("libraryName", "SuperAwesome AdMob")
}

dependencies {
    implementation(project(":superawesome-common"))
    implementation(libs.google.play.services.ads)
}
