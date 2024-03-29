import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")
}
val apiKeyPropertiesFile = rootProject.file("local.properties")
val apiKeyProperties = Properties()
apiKeyProperties.load(FileInputStream(apiKeyPropertiesFile))


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

        //Api key desde local properties
        buildConfigField("String","MY_API_KEY",apiKeyProperties.getProperty("MY_API_KEY")?:"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures{
        buildConfig = true
        viewBinding = true
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
    sourceSets {
        getByName("main") {
            res {
                srcDirs("src\\main\\res", "src\\main\\res\\genres")
            }
        }
    }
}
val hilt_version = "2.48"
val ksp_version ="1.9.10-1.0.13"
val lifecycle_version ="2.7.0"
val room_version = "2.6.1"
val splash_scree_version ="1.0.1"
val retrofit_version = "2.9.0"
val glide_version = "4.16.0"
val okhttp_bom = "4.11.0"
val shimer_layout_version = "0.5.0"
val nav_version = "2.7.6"
val lottieVersion = "6.3.0"
val datastorePrefsVersion ="1.0.0"
val splashScreenVersion="1.0.1"

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.preference:preference:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.activity:activity-ktx:1.8.2")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

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

    //Okhttp
    implementation(platform("com.squareup.okhttp3:okhttp-bom:$okhttp_bom"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    //Glide
    implementation ("com.github.bumptech.glide:glide:$glide_version")
    ksp ("com.github.bumptech.glide:compiler:$glide_version")

    //Shimmer layout
    implementation ("com.facebook.shimmer:shimmer:$shimer_layout_version")

    //Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ("androidx.navigation:navigation-ui-ktx:$nav_version")

    //Lottie anim
    implementation ("com.airbnb.android:lottie:$lottieVersion")

    //DataStore preferences
    implementation("androidx.datastore:datastore-preferences:$datastorePrefsVersion")

    //Splash screen librería
    implementation("androidx.core:core-splashscreen:$splashScreenVersion")

}