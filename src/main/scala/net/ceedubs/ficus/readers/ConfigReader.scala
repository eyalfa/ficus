package net.ceedubs.ficus.readers

import com.typesafe.config.Config
import net.ceedubs.ficus.{SimpleFicusConfig, FicusConfig}

trait ConfigReader {
  implicit val configValueReader: ExtendedValueReader[Config] = new ExtendedValueReader[Config] {
    def read(config: Config, path: String): Config = config.getConfig(path)

    override def canRead(config: Config, path: String): Boolean = checkReadable( _.getConfig )(config, path)
  }

  implicit val ficusConfigValueReader: ValueReader[FicusConfig] = configValueReader.map(SimpleFicusConfig)
}

object ConfigReader extends ConfigReader
