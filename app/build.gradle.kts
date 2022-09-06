plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "dev.yjyoon.neurosky"
    compileSdk = 33

    defaultConfig {
        applicationId = "dev.yjyoon.neurosky"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)

    implementation(libs.bundles.compose)

    implementation(libs.google.accompanist.systemuicontroller)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
