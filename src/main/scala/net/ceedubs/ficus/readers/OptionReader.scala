package net.ceedubs.ficus.readers

import com.typesafe.config.Config

trait OptionReader {
  implicit def optionValueReader[A](implicit valueReader: ValueReader[A]): ValueReader[Option[A]] = new ValueReader[Option[A]] {
    def read(config: Config, path: String): Option[A] = {
      if (config.hasPath(path)) {
        Some(valueReader.read(config, path))
      } else {
        None
      }
    }
    implicit def optionCanRead[A]( implicit canReadA : CanRead[A]) = new CanRead[A]{
      override def canRead(config: Config, path: String): Boolean = {
        //in case of missing path we return Nonw, so it's supported,
        //otherwise we attempt to read an A out of it, so we delegate to its CanRead instance
        !config.hasPath( path ) || canReadA.canRead(config, path)
      }
    }
  }

}

object OptionReader extends OptionReader
