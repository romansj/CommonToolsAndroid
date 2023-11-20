plugins {
    id("com.android.library")
    id("maven-publish")
    id("com.palantir.git-version") version "3.0.0"
}

extra["PUBLISH_GROUP_ID"] = "io.github.romansj.tools"

android {
    namespace = "com.cherrydev.tools"
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
}

tasks.withType(Test::class).configureEach {
    useJUnitPlatform()
}

dependencies {

}

//fun getVersionName(): String {
//    val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
//    val details = versionDetails()
//    // return details.lastTag
//    return "1.0.0"
//}
//
//fun getArtificatId(): String {
//    return "tools" // todo Setting this only tells it where to look. How to change what is generated?
//}

//publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            groupId = "io.github.romansj"
//            artifactId = getArtificatId()
//            version = getVersionName()
//            artifact("$buildDir/outputs/aar/${getArtificatId()}-release.aar")
//        }
//    }
//
//    repositories {
//        maven {
//            name = "GitHubPackages"
//            url = URI.create("https://maven.pkg.github.com/romansj/CommonToolsAndroid")
//            credentials {
//                username = System.getenv("GITHUB_ACTOR")
//                password = System.getenv("GITHUB_TOKEN")
//            }
//        }
//    }
//}
//
//
//tasks.register("publishToGithub"){
//
//}