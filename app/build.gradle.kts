plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.final_simple_shimmer_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.final_simple_shimmer_app"
        minSdk = 33
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // make sure to install the OpenCSV jar file
    implementation("com.opencsv:opencsv:5.5.2")
    //
    //make sure to install the GraphView jar file
    implementation("com.jjoe64:graphview:4.2.2")
}