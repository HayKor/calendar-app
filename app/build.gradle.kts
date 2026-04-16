import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

val localProps =
    Properties().apply {
        val file = rootProject.file("local.properties")
        if (file.exists()) file.inputStream().use { load(it) }
    }

fun getProp(
    key: String,
    envKey: String,
    default: String = "",
): String = System.getenv(envKey) ?: localProps.getProperty(key) ?: default

val devApiUrl = getProp("dev.api.url", "DEV_API_URL")
val devGoogleWebClientId = getProp("dev.google_web_client_id", "GOOGLE_WEB_CLIENT_ID")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.haykor.calendar"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.haykor.calendar"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            buildConfigField("String", "API_URL", "\"$devApiUrl\"")
            buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", "\"$devGoogleWebClientId\"")
            buildConfigField("boolean", "IS_DEBUG", "true")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

detekt {
    toolVersion = libs.versions.detekt.get()
    source.setFrom(files("src/main/java", "src/test/java", "src/androidTest/java"))
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    autoCorrect = false
    buildUponDefaultConfig = true
}

dependencies {
    // Android & core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.activity.compose)

    // Credentials
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.playservices)
    implementation(libs.google.identity.googleid)

    // UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Navigation
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)

    // DI
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)

    // Room
    implementation(libs.androidx.room3.runtime)
    ksp(libs.androidx.room3.compiler)

    implementation(libs.androidx.datastore.preferences)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Detekt linter
    detektPlugins(libs.detekt.rules.compose)
}
