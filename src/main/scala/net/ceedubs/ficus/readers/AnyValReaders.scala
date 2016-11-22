package net.ceedubs.ficus.readers

import com.typesafe.config.Config

trait AnyValReaders {
  implicit val booleanValueReader: ExtendedValueReader[Boolean] = new ExtendedValueReader[Boolean] {
    def read(config: Config, path: String): Boolean = config.getBoolean(path)

    override def canRead(config: Config, path: String): Boolean = {
      checkReadable(_.getBoolean)(config, path)
    }
  }

  implicit val intValueReader: ExtendedValueReader[Int] = new ExtendedValueReader[Int] {
    def read(config: Config, path: String): Int = config.getInt(path)

    override def canRead(config: Config, path: String): Boolean = {
      checkReadable(_.getInt)(config, path)
    }
  }

  implicit val longValueReader: ExtendedValueReader[Long] = new ExtendedValueReader[Long] {
    def read(config: Config, path: String): Long = config.getLong(path)

    override def canRead(config: Config, path: String): Boolean = {
      checkReadable(_.getLong)(config, path)
    }
  }

  implicit val doubleValueReader: ExtendedValueReader[Double] = new ExtendedValueReader[Double] {
    def read(config: Config, path: String): Double = config.getDouble(path)

    override def canRead(config: Config, path: String): Boolean = {
      checkReadable(_.getDouble)(config, path)
    }
  }
  
}

object AnyValReaders extends AnyValReaders
