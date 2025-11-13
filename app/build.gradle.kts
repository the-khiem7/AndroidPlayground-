plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.androidplayground"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.androidplayground"
        minSdk = 24
        targetSdk = 36
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("com.caverock:androidsvg:1.4")
    implementation(libs.recyclerview)
    implementation(project(":lab01"))
    implementation(project(":lab02"))
    implementation(project(":lab03"))
    implementation(project(":lab04"))
    implementation(project(":lab05"))
    implementation(project(":lab06"))
    implementation(project(":lab07"))
    implementation(project(":lab08"))
    implementation(project(":lab09"))
    implementation(project(":lab10"))
    implementation(project(":lab11"))
    implementation(project(":lab12"))
    implementation(project(":lab13"))
    implementation(project(":lab14"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
