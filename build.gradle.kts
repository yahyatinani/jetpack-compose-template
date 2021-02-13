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

plugins {
    id(Plugins.Ktlint.id) version Plugins.Ktlint.version
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = Plugins.Ktlint.id)

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}
