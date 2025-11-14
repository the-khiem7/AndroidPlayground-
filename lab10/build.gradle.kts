plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.lab10"
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
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Retrofit (Đã import thành công với phiên bản 2.11.0)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson) // Giữ phiên bản 2.11.0
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Thêm Scalars Converter nếu bạn cần nhận phản hồi dưới dạng String hoặc kiểu dữ liệu nguyên thủy
    implementation(libs.retrofit.converter.scalars)

    // Glide
    implementation(libs.glide)
    implementation(libs.glide.okhttp)
    annotationProcessor(libs.glide.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
