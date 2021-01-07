buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.Gradle.plugin)
        classpath(Libs.Gradle.build)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}
