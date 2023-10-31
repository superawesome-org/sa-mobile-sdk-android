import buildlogic.Namespace

plugins {
    id("ads.sdk.android.app")
    id("kotlinx-serialization")
}

android {
    namespace = "${Namespace.base}.demoapp"

    defaultConfig {
        applicationId = "tv.superawesome.demoapp"
        versionCode = 1
        versionName = "9.1.2"

        testInstrumentationRunner = "tv.superawesome.demoapp.runner.UITestRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
        multiDexEnabled = true

        lint.abortOnError = false
        packaging {
            resources.excludes.add("META-INF/*")
        }

        buildFeatures.viewBinding = true
    }

    flavorDimensions += "sdk"
    productFlavors {
        create("common") {
            dimension = "sdk"
            applicationIdSuffix = ".common"
            versionNameSuffix = "-common"
        }
        create("base") {
            dimension = "sdk"
        }
    }
}

dependencies {
    "commonImplementation"(project(":saadmob-common"))
    "baseImplementation"(project(":saadmob"))
    "commonImplementation"(project(":superawesome-common"))
    "baseImplementation"(project(":superawesome-base"))

    implementation(libs.google.play.services.ads)
    implementation(libs.material)
    implementation(libs.androidx.multidex)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity)

    // Testing
    testImplementation(libs.junit4)

    androidTestImplementation(libs.androidx.test.ext.junit.ktx)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.core.ktx)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.espresso.contrib)
    androidTestImplementation(libs.androidx.test.espresso.intents)
    androidTestImplementation(libs.androidx.test.uiautomator)

    // Mock Server
    androidTestImplementation(libs.wiremock) {
        exclude(group = "org.apache.httpcomponents", module = "httpclient")
    }

    androidTestImplementation(libs.httpcomponents.android)
}
