import buildlogic.Namespace

plugins {
    id("ads.sdk.android.library")
}

apply(from = "../publish.gradle")

extra.apply {
    set("libraryDescription", "SuperAwesome Full SDK")
    set("libraryName", "SuperAwesome")
}

android {
    namespace = "${Namespace.base}.sdk"
}

dependencies {
    api(project(":superawesome-base"))
}
