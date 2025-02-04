plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.serializable)
    alias(libs.plugins.compose.compiler)
    //alias(libs.plugins.jetbrains.kotlin.ksp)
}

android {
    namespace = "com.evg.estimateai"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.evg.estimateai"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:resource"))
    implementation(project(":core:shared-prefs"))
    implementation(project(":core:api"))
    implementation(project(":core:database"))
    implementation(project(":feature:registration"))
    implementation(project(":feature:login"))
    implementation(project(":feature:password-reset"))
    implementation(project(":feature:tests-list"))
    implementation(project(":feature:test-select"))
    implementation(project(":feature:test-essay"))
    implementation(project(":feature:statistics"))

    // Koin
    implementation(libs.di.koin)
    implementation(libs.di.koin.compose)

    // Navigation
    implementation(libs.androidx.ui.navigation)

    // Serialization
    implementation(libs.kotlinx.serialization)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material)
    debugImplementation(libs.androidx.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}