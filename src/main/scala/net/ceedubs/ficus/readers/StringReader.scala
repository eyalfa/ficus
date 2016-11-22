package net.ceedubs.ficus.readers

import com.typesafe.config.{Config, ConfigException, ConfigValueType}

trait StringReader {
  implicit val stringValueReader: ExtendedValueReader[String] = new ExtendedValueReader[String] {
    def read(config: Config, path: String): String = config.getString(path)

    override def canRead(config: Config, path: String): Boolean = {
      checkReadable( _.getString )(config, path)
    }
  }
}

object StringReader extends StringReader
