plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

repositories {
    google()
    mavenCentral()
}

android {
    namespace = "net.wiedekopf.gacompanion.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "net.wiedekopf.gacompanion"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.android.androidx.core.ktx)
    implementation(libs.android.androidx.lifecycle.runtime.ktx)
    implementation(libs.android.androidx.activity.compose)
    implementation(platform(libs.android.androidx.compose.bom))
    implementation(libs.android.androidx.ui)
    implementation(libs.android.androidx.ui.graphics)
    implementation(libs.android.androidx.ui.tooling.preview)
    implementation(libs.android.androidx.material3)
    implementation(libs.android.ui.text.google.fonts)
    testImplementation(libs.common.junit)
    androidTestImplementation(libs.android.androidx.junit)
    androidTestImplementation(libs.android.androidx.espresso.core)
    androidTestImplementation(platform(libs.android.androidx.compose.bom))
    androidTestImplementation(libs.android.androidx.ui.test.junit4)
    debugImplementation(libs.android.androidx.ui.tooling)
    debugImplementation(libs.android.androidx.ui.test.manifest)

    implementation(libs.android.ktor.client.core)
    implementation(libs.android.ktor.client.android)
    implementation(libs.android.androidx.navigation)
    implementation(libs.android.androidx.datastore)
}
