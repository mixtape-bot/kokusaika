package kokusaika.util

import kokusaika.Kokusaika
import kokusaika.KokusaikaBuilder
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

enum class OnMissing { ERROR, RETURN_NULL }

data class KokusaikaResources @OptIn(ExperimentalSerializationApi::class) constructor(
  val format: Json,
  val options: KokusaikaOptions,
  val fallback: Locale,
  val languagesPath: String
)

data class KokusaikaOptions(val onMissing: OnMissing = OnMissing.ERROR)

/**
 * Convenience method for creating an instance of [Kokusaika]
 */
@OptIn(ExperimentalContracts::class)
fun Kokusaika(builder: KokusaikaBuilder.() -> Unit): Kokusaika {
  contract {
    callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
  }

  return KokusaikaBuilder()
    .apply(builder)
    .build()
}
