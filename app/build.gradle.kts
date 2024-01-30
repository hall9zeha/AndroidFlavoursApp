plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.barryzea.androidflavours"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.barryzea.androidflavours"
        minSdk = 24
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
    //En gradle kts se usa de esta forma
    //para organizar las variantes dentro de una categoría usamos las dimensiones
    //podemos agregar las que necesitemos
    flavorDimensions.add("version")
    productFlavors{
        create("free"){
            dimension = "version"
            applicationId = "com.barryzea.androidflavours.free"
            //Podemos  establecer el nombre de la app para cada tipo de producto directamente en el archivo AndroidManifest.xml
            manifestPlaceholders["appLabel"] = "TMDBFree"
            /* y en el manifest lo siguiente
            <application>
              android:label="${appLabel}"
              ...
              >
                <activity
                android:name=".MainActivity"
                android:label="${appLabel}"
                ...

            * */

        }
        create("paid"){
            dimension = "version"
            applicationId = "com.barryzea.androidflavours.paid"
            manifestPlaceholders["appLabel"] = "TMDBPro"
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
val hilt_version = "2.48"
val ksp_version ="1.9.10-1.0.13"
val lifecycle_version ="2.7.0"
val room_version = "2.6.1"
val splash_scree_version ="1.0.1"
val retrofit_version = "2.9.0"
val glide_version = "4.16.0"

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Dagger hilt
    implementation("com.google.dagger:hilt-android:${hilt_version}")
    ksp("com.google.dagger:hilt-android-compiler:${hilt_version}")

    //ViewModel libraries
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${lifecycle_version}")

    //Splash screen librería
    implementation("androidx.core:core-splashscreen:${splash_scree_version}")
    //Room
    implementation ("androidx.room:room-runtime:${room_version}")
    implementation ("androidx.room:room-ktx:${room_version}")
    ksp ("androidx.room:room-compiler:${room_version}")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit_version")

    //Glide
    implementation ("com.github.bumptech.glide:glide:$glide_version")
    ksp ("com.github.bumptech.glide:compiler:$glide_version")

}