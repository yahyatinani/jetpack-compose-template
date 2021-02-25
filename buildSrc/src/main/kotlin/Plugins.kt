object Plugins {
    object Ktlint {
        const val version = "9.4.1"
        const val id = "org.jlleitschuh.gradle.ktlint"
    }

    object Kotlin {
        const val gradle =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Libs.kotlinVersion}"
    }

    object Android {
        private const val version = "7.0.0-alpha08"

        const val gradle = "com.android.tools.build:gradle:$version"
    }
}