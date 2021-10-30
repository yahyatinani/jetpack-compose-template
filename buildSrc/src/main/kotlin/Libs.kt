object Libs {
    const val kotlinVersion = "1.5.31"
    const val jvmTarget = "1.8"

    object Compose {
        private const val gr = "androidx.compose"
        const val version = "1.0.4"

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

    object Lifecycle {
        private const val version = "2.4.0"
        private const val gr = "androidx.lifecycle"

        // ViewModel
        const val vm = "$gr:lifecycle-viewmodel-ktx:$version"

        // ViewModel utilities for Compose
        const val viewModelCompose = "$gr:lifecycle-viewmodel-compose:$version"

        // Lifecycles only (without ViewModel or LiveData)
        const val lifecycles = "$gr:lifecycle-runtime-ktx:$version"

        // Saved state module for ViewModel
        const val savedState = "$gr:lifecycle-viewmodel-savedstate:$version"
    }

    object Activity {
        private const val version = "1.4.0"

        // Integration with activities
        const val compose = "androidx.activity:activity-compose:$version"
    }

    object Navigation {
        private const val version = "2.4.0-alpha10"

        const val compose = "androidx.navigation:navigation-compose:$version"
    }

    object Androidx {
        // Appcompat is needed for themes.xml resource
        const val appcompat = "androidx.appcompat:appcompat:1.4.0-beta01"
        const val constraintLayoutCompose =
            "androidx.constraintlayout:constraintlayout-compose:1.0.0-rc01"
    }

    object Accompanist {
        private const val version = "0.20.0"
        const val navAnimation =
            "com.google.accompanist:accompanist-navigation-animation:$version"
    }

    object LayoutInspector {
        const val uiTooling = Compose.uiTooling
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    }

    object Kotest {
        private const val version = "5.0.0.M3"

        const val runner = "io.kotest:kotest-runner-junit5:$version"
        const val assertions = "io.kotest:kotest-assertions-core:$version"
        const val property = "io.kotest:kotest-property:$version"
    }

    object Y {
        private const val group = "com.github.whyrising.y"
        private const val version = "0.0.7"

        const val core = "$group:y-core:$version"
        const val collections = "$group:y-collections:$version"
        const val concurrency = "$group:y-concurrency:$version"
    }

    object Recompose {
        private const val v = "0.0.5"
        const val recompose = "com.github.whyrising.recompose:recompose:$v"
    }

    object Coroutines {
        private const val group = "org.jetbrains.kotlinx"
        private const val version = "1.5.2"

        const val core = "$group:kotlinx-coroutines-core:$version"
        const val android = "$group:kotlinx-coroutines-android:$version"
        const val coroutinesTest = "$group:kotlinx-coroutines-test:$version"
    }

    object Mockk {
        private const val version = "1.12.0"

        const val core = "io.mockk:mockk:$version"
        const val jvm = "io.mockk:mockk-agent-jvm:$version"
    }
}
