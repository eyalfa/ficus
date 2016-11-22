package net.ceedubs.ficus.readers

import scala.concurrent.duration.FiniteDuration
import com.typesafe.config.Config
import scala.concurrent.duration.{Duration, NANOSECONDS}

trait DurationReaders {

  /**
   * A reader for for a scala.concurrent.duration.FiniteDuration. This reader should be able to read any valid duration
   * format as defined by the <a href="https://github.com/typesafehub/config/blob/master/HOCON.md">HOCON spec</a>.
   * For example, it can read "15 minutes" or "1 day".
   */
  implicit def finiteDurationReader: ExtendedValueReader[FiniteDuration] = new ExtendedValueReader[FiniteDuration] {
    def read(config: Config, path: String): FiniteDuration = {
      val nanos = config.getDuration(path, NANOSECONDS)
      Duration.fromNanos(nanos)
    }
    private def getDuration( config : Config )( path : String ) = config.getDuration( path, NANOSECONDS)
    override def canRead(config: Config, path: String): Boolean = checkReadable( getDuration )( config, path)
  }
}

object DurationReaders extends DurationReaders
