# Publishing
If Gradle is installed, then `gradle command` will suffice, otherwise `./gradlew`

Increment version according to [Semantic Versioning](https://www.semver.org) (should automate this)  
Increment those modules you plan to release:
```groovy
PUBLISH_VERSION = '0.0.1'
```

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

## Code formatting
Spotless is used to format code in Google Java Format.
```groovy
gradle spotlessJavaApply
```