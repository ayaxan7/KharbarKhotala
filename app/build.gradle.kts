import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}
// Load from local.properties
val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties().apply {
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { load(it) }
    }
}
val newsApiKey = localProperties.getProperty("NEWS_API_KEY") ?: "\"MISSING_API_KEY\""

android {
    namespace = "com.ayaan.kharbarkhotala"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ayaan.kharbarkhotala"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "NEWS_API_KEY", "\"$newsApiKey\"")
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
        compose = true
        buildConfig=true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.get()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.core.splashscreen)

    // Compose Navigation + Hilt
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    ksp(libs.google.hilt.compiler)


    // Google Maps
//    implementation(libs.play.services.maps)
//    implementation(libs.play.services.location)
//    implementation(libs.maps.compose)
    implementation (libs.androidx.datastore.preferences)
//    // DataStore
//    implementation(libs.androidx.datastore)
//    implementation(libs.androidx.datastore.preferences)
//    implementation(libs.androidx.datastore.preferences.rxjava2)
//    implementation(libs.androidx.datastore.preferences.rxjava3)
//    implementation(libs.androidx.datastore.preferences.core)
//    implementation(libs.androidx.datastore.rxjava2)
//    implementation(libs.androidx.datastore.rxjava3)
//    implementation(libs.androidx.datastore.core)
//    implementation(libs.datastore.core)
//    implementation(libs.datastore.preferences)
    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt Testing
    androidTestImplementation(libs.dagger.hilt.android.testing)
    kspAndroidTest(libs.google.hilt.compiler)
    testImplementation(libs.dagger.hilt.android.testing)
    kspTest(libs.google.hilt.compiler)

    //Paging
    implementation(libs.androidx.paging)
    implementation(libs.androidx.paging.compose)

    //RoomDB
    implementation(libs.androidx.room.runtime)
//    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.room.ktx)
}
