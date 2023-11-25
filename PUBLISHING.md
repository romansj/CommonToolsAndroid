# Publishing
If Gradle is installed, then `gradle command` will suffice, otherwise `./gradlew`

Increment version according to [Semantic Versioning](https://www.semver.org) (should automate this)  
Increment those modules you plan to release:
```groovy
PUBLISH_VERSION = '0.0.1'
```

## Publish to Maven Central
Run publish task from command line to pick up environment variables:
```groovy
gradle publish
```


The following environment variables need to be defined
```
OSSRH_USERNAME
OSSRH_PASSWORD
SONATYPE_STAGING_PROFILE_ID
SIGNING_KEY_ID
SIGNING_PASSWORD
SIGNING_KEY
```

## Publish to local repository (Maven Local)
Can run with IntelliJ because no environment variables are needed.

```groovy
gradle lib:publishToMavenLocal
```

### Import in consumer project
Edit repository list to include `mavenLocal`, e.g. in settings.gradle:
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal() // <-- Add this line
    }
}
```