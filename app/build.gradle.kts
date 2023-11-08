plugins {
    id("com.android.application")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.appocalypse.naturenav"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.appocalypse.naturenav"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "debug"
            keyPassword = "kfh2958@atyhAVP"
            storeFile = file("../keystore/debug.jks")
            storePassword = "kfh2958@atyhAVP"
        }
        create("release") {
            keyAlias = "release"
            keyPassword = "sjd32MLY#!ALP?;56Rf"
            storeFile = file("../keystore/release.jks")
            storePassword = "sjd32MLY#!ALP?;56Rf"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("org.osmdroid:osmdroid-android:6.1.17")
    implementation("com.google.android.gms:play-services-oss-licenses:17.0.1")
    implementation("com.github.AppIntro:AppIntro:6.3.1")
    implementation("com.github.MKergall:osmbonuspack:6.9.0")

    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
}
