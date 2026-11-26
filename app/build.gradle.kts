plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.example.thenewsapp"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.thenewsapp"
        minSdk = 33
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    implementation(libs.koin.android)

    // Architectural Components
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    // Coroutine Lifecycle Scopes
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    // Navigation Components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    // Glide
    implementation(libs.glide)
    ksp(libs.compiler)
}
