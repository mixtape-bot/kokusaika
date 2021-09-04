package kokusaika

import kokusaika.util.KokusaikaResources
import kokusaika.util.readText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class Kokusaika(val resources: KokusaikaResources) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(Kokusaika::class.java)

        /**
         * Default Json parser.
         */
        val defaultJson = Json {
            isLenient = true
            allowStructuredMapKeys = true
            ignoreUnknownKeys = true
        }

        fun getResourceContents(path: String): String? {
            val stream = Kokusaika::class.java.getResourceAsStream(path)
            return stream?.readText()
        }
    }

    /**
     *
     */
    val languages: MutableList<Language> = mutableListOf()

    /**
     *
     */
    fun load() {
        val contents = getResourceContents("${resources.languagesPath}/list.json")
            ?: return log.warn("Unable to load language list.")

        val languages = resources.format.decodeFromString<List<String>>(contents)
        for (language in languages) {
            load(language)
        }
    }

    /**
     *
     */
    fun load(file: String) {
        val contents = getResourceContents("${resources.languagesPath}/$file.json")
            ?: return log.warn("Unable to load the contents of '$file.json'")

        return load(Locale.forLanguageTag(file), contents)
    }

    /**
     *
     */
    fun load(id: Locale, contents: String) {
        val map = contents
            .runCatching { resources.format.decodeFromString<JsonObject>(this) }
            .getOrNull()
            ?: return log.warn("Exception occurred while parsing '${id.displayName}.json'")

        languages.add(Language(this, id, map))
    }

    /**
     *
     */
    operator fun get(locale: Locale): Language? = languages.firstOrNull {
        it.id == locale
    }

    /**
     *
     */
    fun getOrFallback(locale: Locale, fallback: Locale = resources.fallback): Language? {
        return this[locale] ?: this[fallback]
    }

}
