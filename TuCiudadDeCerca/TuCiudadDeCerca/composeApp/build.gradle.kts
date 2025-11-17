import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)

}


kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

    }

    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            //KTOR
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            //DATETIME
            implementation(libs.kotlinx.datetime)

            //io
            implementation(libs.kotlinx.io)

            //serializacion
            implementation(libs.kotlinx.serialization.json)


            //room
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            //dataStore
            implementation(libs.data.store)
            implementation(libs.data.store.preferences)

            implementation(libs.kamel.image.default)




        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)

        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            //implementation(compose.desktop.ui.window)
        }
    }


}

val jvmMainJar by tasks.registering(Jar::class) {
    group = "build"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "org.dferna14.project.MainKt"
    }

    from(kotlin.targets["jvm"].compilations["main"].output.allOutputs)

    dependsOn(configurations["jvmRuntimeClasspath"])
    from({
        configurations["jvmRuntimeClasspath"].map { if (it.isDirectory) it else zipTree(it) }
    })
}


//configuracion de android
android {
    namespace = "org.dferna14.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.dferna14.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    room {
        schemaDirectory("${projectDir}/schemas")
    }

    signingConfigs {
        create("release") {
            storeFile = file("keystore/my-release-key.jks")
            storePassword = "psswproconsi"
            keyAlias = "proconsi_key"
            keyPassword = "psswproconsi"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")

            applicationVariants.all {
                outputs.all {
                    val outputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
                    outputImpl.outputFileName = "TuCiudadDeCerca.apk"
                }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


}



dependencies {

    //dependencias ksp con room
    "kspAndroid"(libs.androidx.room.compiler)
    "kspJvm"(libs.androidx.room.compiler)

    debugImplementation(compose.uiTooling)
    //navigation-compose
    implementation(libs.navigation.compose)


}

compose.desktop {
    application {
        mainClass = "org.dferna14.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "TuCiudadDeCerca"
            packageVersion = "1.0.0"
        }
    }
}








