plugins {
    id("com.android.application") version ("7.1.1")
    id("org.jetbrains.kotlin.android") version ("1.6.21")
}

android {
    compileSdk = 33
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "martin.zhang.mytextview"
        minSdk = 21
        targetSdkPreview = "33"
        versionName = "1.0.0"
        versionCode = 1
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            this.isMinifyEnabled = false
            this.isDebuggable = true
            this.isTestCoverageEnabled = true
        }

        getByName("release") {
            this.isMinifyEnabled = true
            this.isShrinkResources = true
            this.isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    applicationVariants.all {
        val variant = this
        variant.outputs.all {
            val output = this
            val outputImpl = output as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            val outputFile = output.outputFile
            if (outputFile?.name?.endsWith(".apk") == true) {
                val fileName = "ShinningTextDemo_${buildType.name}_${defaultConfig.versionName}.apk"
                outputImpl.outputFileName = fileName
            }
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
