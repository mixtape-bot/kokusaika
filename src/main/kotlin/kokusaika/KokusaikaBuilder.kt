package kokusaika

import kokusaika.util.KokusaikaOptions
import kokusaika.util.KokusaikaResources
import kokusaika.util.OnMissing
import kotlinx.serialization.json.Json
import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class KokusaikaBuilder {

  /**
   * The string format to use.
   */
  var format: Json? = null

  /**
   * The resources path to use.
   */
  var languagesPath: String = "/assets/folders"

  /**
   * The fallback locale
   */
  var fallback: Locale = Locale.US

  /**
   * The options to use.
   */
  var options: KokusaikaOptions = KokusaikaOptions()

  /**
   * Configures an instance of [KokusaikaOptions]
   */
  @OptIn(ExperimentalContracts::class)
  fun options(builder: Options.() -> Unit) {
    contract {
      callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
    }

    options = Options()
      .apply(builder)
      .create()
  }

  /**
   * Builds an instance of [Kokusaika] with the configured options
   */
  fun build(): Kokusaika {
    val resources = KokusaikaResources(
      format = format ?: Kokusaika.defaultJson,
      options = options,
      languagesPath = languagesPath,
      fallback = fallback
    )

    return Kokusaika(resources)
  }

  class Options {
    /**
     * The action taken when a missing key is encountered.
     */
    var onMissing: OnMissing = OnMissing.ERROR

    /**
     * Creates an instance of [KokusaikaOptions] with the configured values
     */
    fun create(): KokusaikaOptions = KokusaikaOptions(
      onMissing = onMissing
    )
  }
}
