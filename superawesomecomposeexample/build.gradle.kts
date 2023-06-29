plugins {
    id("ads.sdk.android.app")
    id("kotlin-parcelize")
}

android {
    namespace = "com.superawesome.composeexample"

    defaultConfig {
        applicationId = "com.superawesome.composeexample"
        minSdk = 21

        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures.compose = true
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packagingOptions.resources {
        excludes.add("/META-INF/{AL2.0,LGPL2.1}")
    }
}

dependencies {
    implementation(project(":superawesome-common"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose)
    testImplementation(libs.junit4)
    androidTestImplementation (libs.androidx.test.ext.junit)
    androidTestImplementation (libs.androidx.test.espresso.core)
    androidTestImplementation(libs.bundles.compose.ui.test)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.manifest)
}
