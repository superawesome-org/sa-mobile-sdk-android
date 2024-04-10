plugins {
    id("ads.sdk.android.app")
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.superawesome.ironsource.example"

    defaultConfig {
        versionName = "9.3.3"
        applicationId = "com.superawesome.ironsource.example"
        minSdk = 21
        targetSdk = 33
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures.compose = true
    buildFeatures.buildConfig = true

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    buildTypes {
        release {
            buildConfigField("String", "IS_MEDIATOR_VERSION", "\"${libs.versions.ironsource.mediator.get()}\"")
            buildConfigField("String", "IS_ADAPTER_VERSION", "\"${libs.versions.ironsource.adapters.superawesome.get()}\"")
        }
        debug {
            buildConfigField("String", "IS_MEDIATOR_VERSION", "\"${libs.versions.ironsource.mediator.get()}\"")
            buildConfigField("String", "IS_ADAPTER_VERSION", "\"${libs.versions.ironsource.adapters.superawesome.get()}\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material3)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.junit4)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.manifest)

    // IronSource adapter and SuperAwesome SDK
    implementation(libs.ironsource.mediation)
    implementation(libs.ironsource.adapters.superawesome)
    implementation(project(":superawesome"))

    // AdMob
    implementation(libs.google.play.services.ads.identifier)
}
