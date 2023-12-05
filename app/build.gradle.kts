plugins {
    id("com.android.application")
}

android {
    namespace = "com.copy.copy_device_info"
    compileSdk = 32

    defaultConfig {
        applicationId = "com.copy.copy_device_info"
        minSdk = 24
        targetSdk = 32
        versionCode = 120
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.github.gzu-liyujiang:Android_CN_OAID:4.2.8")
}
