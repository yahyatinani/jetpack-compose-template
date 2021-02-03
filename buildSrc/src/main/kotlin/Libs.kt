object Libs {
    const val kotlinVersion = "1.4.21"
    const val jvmTarget = "11"

    object Gradle {
        const val plugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

        const val build = "com.android.tools.build:gradle:7.0.0-alpha05"
    }

    object AndroidX {
        private const val lifeCycleVersion = "2.3.0-rc01"

        const val coreKtx = "androidx.core:core-ktx:1.5.0-beta01"
        const val appcompat = "androidx.appcompat:appcompat:1.3.0-beta01"

        const val lifecycle =
            "androidx.lifecycle:lifecycle-runtime-ktx:$lifeCycleVersion"
        const val vmLifecycle =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeCycleVersion"
    }

    object AndroidMaterial {
        private const val version = "1.3.0-rc01"

        const val material = "com.google.android.material:material:$version"
    }

    object Compose {
        private const val gr = "androidx.compose"
        const val version = "1.0.0-alpha10"

        const val ui = "$gr.ui:ui:$version"

        // Tooling support (Previews, etc.)
        const val uiTooling = "$gr.ui:ui-tooling:$version"

        // Foundation (Border, Background, Box, Image, shapes, animations, etc.)
        const val foundation = "$gr.foundation:foundation:$version"

        // Material design
        const val material = "$gr.material:material:$version"

        // Material design icons
        const val iconsCore = "$gr.material:material-icons-core:$version"
        const val iconsExt = "$gr.material:material-icons-extended:$version"

        // UI Testing
        const val uiTestJUnit = "$gr.ui:ui-test-junit4:$version"
    }

    object Kotest {
        private const val version = "4.3.2"

        const val runner = "io.kotest:kotest-runner-junit5:$version"
        const val assertions = "io.kotest:kotest-assertions-core:$version"
        const val property = "io.kotest:kotest-property:$version"
    }

    object Y {
        private const val group = "com.github.whyrising.y"
        private const val version = "0.0.2.1"

        const val core = "$group:y-core:$version"
        const val collections = "$group:y-collections:$version"
    }

    object Kotlinx {
        private const val group = "org.jetbrains.kotlinx"
        private const val version = "1.4.2"

        const val coroutines = "$group:kotlinx-coroutines-android:$version"

        const val coroutinesTest = "$group:kotlinx-coroutines-test:$version"
    }
}
