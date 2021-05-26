# Kokusai-ka

国際化 (Kokusai-ka "internationalization") is a simple kotlin internationalization library that makes use of [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) and Java's Locale class.

## 🚧 Installation

[comment]: <> (You must install a kotlinx.serialization format. 国際化 was originally made for JSON, although it may be able to support others in the future.)

###### Groovy	

```groovy
repositories {
  maven { url "https://dimensional.jfrog.io/artifactory/maven" }
}

dependencies {
  implementation "gg.mixtape:kokusaika:1.0.0"
}
```

###### Kotlin

```kotlin
repositories {
  maven {
    name = "dimensional.fun"
    url = uri("https://dimensional.jfrog.io/artifactory/maven")
  }
}

dependencies {
  implementation("gg.mixtape:kokusaika:1.0.0")
}
```

## 🚀 Usage

```kotlin
import kokusaika.Kokusaika
import kotlinx.serialization.json.Json
import java.util.*

fun main() {
  val kokusaika = Kokusaika {
    format = Json { }
    languagesPath = "/assets/languages" // path within the resources folder
    fallback = Locale.US
  }
  
  val myString = kokusaika[Locale.US]?.read<String>("hello")
  println(myString) // -> world
}
```

```json
{
  "hello": "world"
}
```

###### Folder Structure

```
/resources
  /assets
    /languages
      list.json
      en-US.json
```

---

Copyright &copy; mixtape bot 2019 - 2021 all rights reserved
