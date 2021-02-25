buildscript {
    repositories {
        gradlePluginPortal()
        google()

        mavenCentral()
    }
    dependencies {
        classpath(Plugins.Android.gradle)
        classpath(Plugins.Kotlin.gradle)
    }
}

plugins {
    id(Plugins.Ktlint.id) version Plugins.Ktlint.version
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://kotlin.bintray.com/kotlinx")
        }
    }
}

subprojects {
    apply(plugin = Plugins.Ktlint.id)

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}
