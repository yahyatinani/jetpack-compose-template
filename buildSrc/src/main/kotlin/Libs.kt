object Libs {
    const val kotlinVersion = "1.4.21"
    const val jvmTarget = "1.8"

    object Gradle {
        const val plugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

        const val build = "com.android.tools.build:gradle:7.0.0-alpha04"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val lifecycle =
            "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-rc01"
    }

    object AndroidMaterial {
        private const val version = "1.2.1"

        const val material = "com.google.android.material:material:$version"
    }

    object Compose {
        const val version = "1.0.0-alpha09"

        const val ui = "androidx.compose.ui:ui:$version"
        const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
        const val material = "androidx.compose.material:material:$version"
    }

    object Kotest {
        private const val version = "4.3.2"

        const val runner = "io.kotest:kotest-runner-junit5:$version"
        const val assertions = "io.kotest:kotest-assertions-core:$version"
        const val property = "io.kotest:kotest-property:$version"
    }
}
