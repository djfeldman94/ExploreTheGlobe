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

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.compilations.getByName("main") {
            cinterops.create("sqlite3") {
                defFile(project.file("src/iosMain/c_interop/sqlite3.def"))
                compilerOpts("-lsqlite3")
            }
        }
    }



    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
            implementation(libs.sqldelight.coroutines)

        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.ktor.client.mock)
            implementation(libs.turbine)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.sqldelight.android)

        }

        jvmMain.dependencies {
            implementation(libs.sqldelight.jvm)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.ios)
        }

        androidUnitTest.dependencies {
            implementation(libs.sqldelight.jvm)

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