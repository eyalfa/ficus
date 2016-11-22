package net.ceedubs.ficus.readers

import com.typesafe.config.Config

trait SymbolReader {
  implicit val symbolValueReader: ExtendedValueReader[Symbol] = new ExtendedValueReader[Symbol] {
    def read(config: Config, path: String): Symbol = Symbol(config.getString(path))

    override def canRead(config: Config, path: String): Boolean = {
      StringReader.stringValueReader.canRead( config, path)
    }
  }
}

object SymbolReader extends SymbolReader
