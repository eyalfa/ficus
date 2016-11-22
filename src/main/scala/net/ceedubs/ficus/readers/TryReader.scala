package net.ceedubs.ficus.readers

import scala.util.Try
import com.typesafe.config.Config

trait TryReader {
  implicit def tryValueReader[A](implicit valueReader: ValueReader[A]): ExtendedValueReader[Try[A]] = new ExtendedValueReader[Try[A]] {
    def read(config: Config, path: String): Try[A] = Try(valueReader.read(config, path))

    override def canRead(config: Config, path: String): Boolean = true
  }
}

object TryReader extends TryReader
