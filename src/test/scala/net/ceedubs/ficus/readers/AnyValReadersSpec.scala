package net.ceedubs.ficus.readers

import com.typesafe.config._
import net.ceedubs.ficus.Spec

class AnyValReadersSpec extends Spec with AnyValReaders { def is = s2"""
  The Boolean value reader should
    read a boolean $readBoolean
    canRead true ${canReadValidValues[Boolean](ConfigValueFactory.fromAnyRef(true) )}
    canRead false ${canReadValidValues[Boolean](ConfigValueFactory.fromAnyRef(false))}

  The Int value reader should
    read an int $readInt
    read a double as an int $readDoubleAsInt

  The Long value reader should
    read a long $readLong
    read an int as a long $readIntAsLong

  The Double value reader should
    read a double $readDouble
    read an int as a double $readIntAsDouble
  """

  def canReadValidValues[A : CanRead ]( cfgValue : ConfigValue ) = prop{ (depth1 : Int) =>
    val depth = depth1 % 111
    def path( tail : String*) : String = (List.fill(depth)("x") ++ tail).mkString(".")
    val cfg = cfgValue.atPath(path("a"))
    CanRead[A].canRead( cfg, path("a") ) must beTrue
    CanRead[A].canRead( cfg, path("b") ) must beFalse
    (!cfgValue.isInstanceOf[ConfigObject] || !cfgValue.asInstanceOf[ConfigObject].toConfig.hasPath(path("a", "a"))) ==> {
      CanRead[A].canRead( cfg, path("a","a") ) must beFalse
    }
  }

  def readBoolean = prop { b: Boolean =>
    val cfg = ConfigFactory.parseString(s"myValue = $b")
    booleanValueReader.read(cfg, "myValue") must beEqualTo(b)
  }

  def readInt = prop { i: Int =>
    val cfg = ConfigFactory.parseString(s"myValue = $i")
    intValueReader.read(cfg, "myValue") must beEqualTo(i)
  }
  
  def readDoubleAsInt = prop { d: Double =>
    (d >= Int.MinValue && d <= Int.MaxValue) ==> {
      val cfg = ConfigFactory.parseString(s"myValue = $d")
      intValueReader.read(cfg, "myValue") must beEqualTo(d.toInt)
    }
  }

  def readLong = prop { l: Long =>
    val cfg = ConfigFactory.parseString(s"myValue = $l")
    longValueReader.read(cfg, "myValue") must beEqualTo(l)
  }
  
  def readIntAsLong = prop { i: Int =>
    val cfg = ConfigFactory.parseString(s"myValue = $i")
    longValueReader.read(cfg, "myValue") must beEqualTo(i.toLong)
  }

  def readDouble = prop { d: Double =>
    val cfg = ConfigFactory.parseString(s"myValue = $d")
    doubleValueReader.read(cfg, "myValue") must beEqualTo(d)
  }

 def readIntAsDouble = prop { i: Int =>
    val cfg = ConfigFactory.parseString(s"myValue = $i")
    doubleValueReader.read(cfg, "myValue") must beEqualTo(i.toDouble)
  }
}
