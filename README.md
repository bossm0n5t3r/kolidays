# Kolidays

[![Release](https://jitpack.io/v/bossm0n5t3r/kolidays.svg)](https://jitpack.io/#bossm0n5t3r/kolidays)

코틀린(`Ko`tlin)으로 만든 한국(`Ko`rea) 공휴일(Ho`lidays`) 라이브러리입니다.

그래서 `Ko`(tlin or rea) + (Ho)`lidays` = `Kolidays`

## How to use

### Gradle

```kotlin
repositories {
    maven{
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("com.github.bossm0n5t3r:kolidays:master-SNAPSHOT")
}

// or

dependencies {
    implementation("com.github.bossm0n5t3r:kolidays:Tag") // ex) 23.05.20
}
```

### Local

```shell
$ ./gradlew clean :batch:buildJar
$ java -jar ./batch/build/libs/batch.jar
$ ./gradlew :core:clean :core:build :core:publishToMavenLocal
```
