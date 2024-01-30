// Top-level build file where you can add configuration options common to all sub-projects/modules.


plugins {

val hilt_version = "2.48"
val ksp_version ="1.9.10-1.0.13"

    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.dagger.hilt.android") version hilt_version apply false
    id("com.google.devtools.ksp") version ksp_version apply false

}

