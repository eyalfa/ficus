package net.ceedubs.ficus.readers

import com.typesafe.config.{Config, ConfigException, ConfigValue}

/**
  * Created by eyalf on 11/22/2016.
  */
trait CanRead[A] {
  def canRead(config: Config, path: String) : Boolean

  protected def checkReadable[T]( f : Config => String => T )(config: Config, path: String) : Boolean = {
    try{
      f(config)(path)
      true
    } catch{
      case _ : ConfigException.WrongType | _ : ConfigException.Missing | _ : ConfigException.BadValue => false
    }
  }
}

trait ExtendedValueReader[A] extends ValueReader[A] with CanRead[A]

object CanRead{
  def apply[A : CanRead ] = implicitly[CanRead[A]]
}