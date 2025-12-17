plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    // Google Plugins
    alias(libs.plugins.googleService)
}

android {
    namespace = "com.pedro_lopez.pds_iii_"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.pedro_lopez.pds_iii_"
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Firebase / Google dependencies
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.credentials)
    implementation(libs.firebase.credentials.service)
    implementation(libs.firebase.identity.googleid)

    implementation(libs.circle.images)
    implementation(libs.glide)
}