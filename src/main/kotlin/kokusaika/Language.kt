package kokusaika

import kokusaika.util.OnMissing
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import java.util.*

data class Language(val kokusaika: Kokusaika, val id: Locale, val data: JsonElement) {

  /**
   *
   */
  inline operator fun <reified T> get(path: String): T {
    return read(path)!!
  }

  /**
   *
   */
  inline fun <reified T> read(path: String): T? {
    return read(path.split('.'))
  }

  /**
   *
   */
  inline fun <reified T> readNonNull(path: String): T {
    return read(path)!!
  }

  /**
   *
   */
  fun format(path: String, vararg args: Any?): String {
    return readNonNull<String>(path).format(id, *args)
  }

  /**
   *
   */
  @PublishedApi
  internal inline fun <reified T> read(parts: List<String>, start: JsonObject = data.jsonObject): T? {
    var obj = start
    var idx = 0
    while (idx != parts.size - 1) {
      val element = obj[parts[idx]]
      if (element is JsonObject) {
        obj = element
        idx++
      } else {
        return missingKey(parts.joinToString("."))
      }
    }

    val element = obj[parts[idx]]
      ?: return missingKey(parts.joinToString("."))

    return kokusaika.resources.format.decodeFromJsonElement(element)
  }

  /**
   *
   */
  @PublishedApi
  internal fun missingKey(path: String): Nothing? {
    return when (kokusaika.resources.options.onMissing) {
      OnMissing.ERROR -> throw error("Missing language key '$path'")
      OnMissing.RETURN_NULL -> null
    }
  }

}
