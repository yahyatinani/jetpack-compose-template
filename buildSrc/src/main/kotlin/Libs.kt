object Libs {
    const val kotlinVersion = "1.5.10"
    const val jvmTarget = "1.8"

    object Compose {
        private const val gr = "androidx.compose"
        const val version = "1.0.0"

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

    object Androidx {
        // Integration with activities
        const val activityCompose =
            "androidx.activity:activity-compose:1.3.0-beta02"

        // Integration with ViewModels
        const val viewModelCompose =
            "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"

        // Appcompat is needed for themes.xml resource
        const val appcompat = "androidx.appcompat:appcompat:1.3.0-rc01"

        const val navigationCompose =
            "androidx.navigation:navigation-compose:2.4.0-alpha03"

        const val constraintLayoutCompose =
            "androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha08"
    }

    object LayoutInspector {
        const val uiTooling = Compose.uiTooling
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    }

    object Kotest {
        private const val version = "4.6.1"

        const val runner = "io.kotest:kotest-runner-junit5:$version"
        const val assertions = "io.kotest:kotest-assertions-core:$version"
        const val property = "io.kotest:kotest-property:$version"
    }

    object Y {
        private const val group = "com.github.whyrising.y"
        private const val version = "0.0.3"

        const val core = "$group:y-core:$version"
        const val collections = "$group:y-collections:$version"
    }

    object Recompose {
        private const val v = "0.0.2"
        const val recompose = "com.github.whyrising.recompose:recompose:$v"
    }

    object Coroutines {
        private const val group = "org.jetbrains.kotlinx"
        private const val version = "1.5.1"

        const val core = "$group:kotlinx-coroutines-core:$version"
        const val android = "$group:kotlinx-coroutines-android:$version"
        const val coroutinesTest = "$group:kotlinx-coroutines-test:$version"

        const val vm = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
    }
}
