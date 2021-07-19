plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.uiTooling)
    implementation(Libs.Compose.foundation)
    implementation(Libs.Compose.material)
    implementation(Libs.Compose.iconsCore)
    implementation(Libs.Compose.iconsExt)

    implementation(Libs.Androidx.appcompat)
    implementation(Libs.Androidx.activityCompose)
    implementation(Libs.Androidx.viewModelCompose)
    implementation(Libs.Androidx.navigationCompose)
    implementation(Libs.Androidx.constraintLayoutCompose)

    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")

    implementation(Libs.Y.core)
    implementation(Libs.Y.collections)

    implementation(Libs.Coroutines.core)
    implementation(Libs.Coroutines.android)
    implementation(Libs.Coroutines.vm)

    debugImplementation(Libs.LayoutInspector.uiTooling)
    debugImplementation(Libs.LayoutInspector.reflect)

    testImplementation(Libs.Kotest.runner)
    testImplementation(Libs.Kotest.assertions)
    testImplementation(Libs.Kotest.property)

    testImplementation(Libs.Coroutines.coroutinesTest)
    testImplementation(Libs.Compose.uiTestJUnit)
}

android {
    compileSdk = 30
    buildToolsVersion = "31.0.0"

    defaultConfig {
        // TODO: Change the applicationId
        applicationId = "com.why.template.compose"
        minSdk = 22
        targetSdk = 30
        versionCode = 1
        versionName = Ci.publishVersion
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Libs.jvmTarget
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.Compose.version
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
