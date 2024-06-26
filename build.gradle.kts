plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.androidHilt) apply false
}

buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
    }
}
