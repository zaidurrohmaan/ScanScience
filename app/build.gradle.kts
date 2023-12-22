plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.scanscience"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.scanscience"
        minSdk = 24
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        mlModelBinding = true
    }
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://capstone-406803.et.r.appspot.com\"")
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.10")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.exifinterface:exifinterface:1.3.7")
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.datastore:datastore-preferences:1.1.0-alpha07")
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation("androidx.room:room-paging:2.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0") // InstantTaskExecutorRule
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") //TestDispatcher
    testImplementation("org.mockito:mockito-core:4.4.0")
    testImplementation("org.mockito:mockito-inline:4.4.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}