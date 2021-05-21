# Kokusai-ka

国際化 (Kokusai-ka "internationalization") is a simple kotlin internationalization library that makes use of [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization and Java's Locale class.

## 🚧 Installation

You must install a kotlinx.serialization format, 国際化 was originally made for JSON. Although it has been modified to support any format.

###### Groovy	

```groovy
repositories {
  maven { url "https://jitpack.io" }
}

dependencies {
  implementation "com.github.mixtape-bot:kokusaika:1.0.0"
}
```

###### Kotlin

```kotlin
repositories {
  maven("https://jitpack.io")
}

dependencies {
  implementation("com.github.mixtape-bot:kokusaika:1.0.0")
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
