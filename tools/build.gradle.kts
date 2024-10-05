plugins {
    id("com.android.library")
    id("maven-publish")
    id("com.palantir.git-version") version "3.0.0"
}

apply(from = "$rootDir/gradle/android-library.gradle") // library release variant
apply(from = "$rootDir/gradle/android.gradle")// signing for release comes after defining release variant

extra["PUBLISH_GROUP_ID"] = "io.github.romansj.tools"
extra["PUBLISH_VERSION"] = "1.0.1"
extra["PUBLISH_ARTIFACT_ID"] = name

android {
    namespace = extra.get("PUBLISH_GROUP_ID").toString()
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    subprojects.forEach { project ->
        println("subprojects " + project.name)
    }
}

apply(from = "${rootProject.projectDir}/gradle/publish-module.gradle")


tasks.withType(Test::class).configureEach {
    useJUnitPlatform()
}

dependencies {

}