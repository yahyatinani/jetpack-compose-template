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
        private const val version = "1.3.0-beta01"

        const val material = "com.google.android.material:material:$version"
    }

    object Compose {
        private const val prefix = "androidx.compose"
        const val version = "1.0.0-alpha10"

        const val ui = "$prefix.ui:ui:$version"

        // Tooling support (Previews, etc.)
        const val uiTooling = "$prefix.ui:ui-tooling:$version"

        // Foundation (Border, Background, Box, Image, shapes, animations, etc.)
        const val foundation = "$prefix.foundation:foundation:$version"

        // Material design
        const val material = "$prefix.material:material:$version"

        // Material design icons
        const val iconsCore = "$prefix.material:material-icons-core:$version"
        const val iconsExt = "$prefix.material:material-icons-extended:$version"
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
}
