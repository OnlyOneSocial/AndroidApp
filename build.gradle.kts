buildscript {
    repositories {
        google()
    }
    dependencies {
        val nav_version = "2.4.1"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application")  version "7.1.1" apply false
    id ("com.android.library") version "7.1.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.6.10" apply false
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}

