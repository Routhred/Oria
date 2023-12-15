buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.21")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    kotlin("plugin.serialization") version "1.9.21" apply false
}

