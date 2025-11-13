plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.prm392_lab_11_api"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    // AndroidX core libs
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // RecyclerView + CardView + SwipeRefreshLayout + CoordinatorLayout
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    implementation(libs.swiperefreshlayout)
    implementation(libs.coordinatorlayout)

    // Retrofit + Gson + OkHttp logging
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.okhttp)

    // Unit Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
