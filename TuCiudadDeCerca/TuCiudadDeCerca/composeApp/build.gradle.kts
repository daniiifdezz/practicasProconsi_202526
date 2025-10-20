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

            //DATETIME
            implementation(libs.kotlinx.datetime)

            //io
            implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.8.0")

            //serializacion
            implementation(libs.kotlinx.serialization.json)


            //room
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            //dataStore
            implementation("androidx.datastore:datastore:1.1.7")
            implementation("androidx.datastore:datastore-preferences:1.1.7")






        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)

        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
    dependencies {
        "kspCommonMainMetadata"(libs.androidx.room.compiler)
    }

}

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
    room{
        schemaDirectory("${projectDir}/schemas")
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}



dependencies {
    debugImplementation(compose.uiTooling)

    //navigation-compose
    implementation("androidx.navigation:navigation-compose:2.8.0")



}

compose.desktop {
    application {
        mainClass = "org.dferna14.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.dferna14.project"
            packageVersion = "1.0.0"
        }
    }
}








