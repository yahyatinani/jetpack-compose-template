buildscript {
    val kotlinVersion = "1.4.21"
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.android.tools.build:gradle:7.0.0-alpha04")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}
