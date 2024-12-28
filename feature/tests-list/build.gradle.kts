plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
//    alias(libs.plugins.jetbrains.kotlin.ksp)
}

android {
    namespace = "com.evg.tests_list"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":core:resource"))
    implementation(project(":core:api"))

    // Koin
    implementation(libs.di.koin)
    implementation(libs.di.koin.compose)

    // MVI Orbit
    implementation(libs.mvi.orbit.core)
    implementation(libs.mvi.orbit.viewmodel)
    implementation(libs.mvi.orbit.compose)

    // Navigation
    implementation(libs.androidx.ui.navigation)

    // Paging
    implementation(libs.jetpack.paging.runtime)
    implementation(libs.jetpack.paging.common)
    implementation(libs.jetpack.paging.compose)

    // Swipe Refresh
    implementation(libs.swiperefresh.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}