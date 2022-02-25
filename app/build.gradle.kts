plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

android {

    compileSdk = 31

    defaultConfig {
        applicationId = "dev.syorito_hatsuki.onlyone"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    viewBinding {
        isEnabled = true
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

    implementation ("io.ktor:ktor-client-core:1.6.7")
    implementation ("io.ktor:ktor-client-android:1.6.7")
    implementation ("io.ktor:ktor-client-serialization:1.6.7")

    implementation ("io.insert-koin:koin-android:3.1.5")
    implementation ("io.insert-koin:koin-ktor:3.1.5")

    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation ("io.coil-kt:coil:1.4.0")

    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.4.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation ("androidx.fragment:fragment-ktx:1.4.1")
    
    implementation ("com.google.android.material:material:1.5.0")

    testImplementation ("junit:junit:4.13.2")

    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
}