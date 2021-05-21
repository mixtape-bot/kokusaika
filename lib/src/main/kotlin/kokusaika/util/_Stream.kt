package kokusaika.util

import java.io.InputStream

fun InputStream.readText(): String {
  return reader().readText()
}
