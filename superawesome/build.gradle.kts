plugins {
    id("ads.sdk.android.library")
}

apply(from = "../publish.gradle")
apply(from = "../jacoco.gradle")

extra.apply {
    set("libraryDescription", "SuperAwesome Full SDK")
    set("libraryName", "SuperAwesome")
}

dependencies {
    api(project(":superawesome-base"))
}
