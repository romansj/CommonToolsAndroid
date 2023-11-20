# CommonToolsAndroid
Project to host reusable Android utils and basic implementations.

Modules:
- app (util and implementation usage demo)
- tools (library module)
  - behavior
  - common
  - debounce
  - dialogs
  - file
  - keyboard
  - navigation
  - network
  - recyclerview
  - sharing
  - time
- benchmarkmacro
- benchmarkmicro

## Installation
Import only the modules you need:

Kotlin DSL
```kotlin
implementation("io.github.romansj.tools:keyboard:0.0.1")
```

Groovy DSL
```groovy
implementation 'io.github.romansj.tools:keyboard:0.0.1'
```


## Publishing
If Gradle is installed, then `gradle command` will suffice, otherwise `./gradlew`

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