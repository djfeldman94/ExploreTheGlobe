import org.gradle.kotlin.dsl.support.kotlinCompilerOptions

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
    }

    compilerOptions {
        jvmToolchain(17)
    }



    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation("io.ktor:ktor-client-core:2.3.7")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
                implementation(libs.koin.core)
                implementation(libs.sqldelight.coroutines)

            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
                implementation("io.ktor:ktor-client-mock:2.3.7")
                implementation("app.cash.turbine:turbine:1.0.0")
                implementation("io.mockk:mockk:1.13.8")
            }
        }
        
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:2.3.7")
                implementation(libs.sqldelight.android)
            }
        }
    }
}

android {
    namespace = "com.etg.core"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.etg.db")
            version = 1
        }
    }
}