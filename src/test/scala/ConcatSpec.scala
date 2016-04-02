import org.scalacheck.Prop._
import org.scalacheck.Properties

object ConcatSpec extends Properties("ConcatSpec") {
  val W = shapeless.Witness

  property("Concat[ab, cd]") = secure {
    val c = Concat[W.`"ab"`.T, W.`"cd"`.T]

    val s1: c.Out = "abcd" // Ok

    //val s2: c.Out = "abc"
    /*
    [error] scala-macro/src/test/scala/ConcatSpec.scala:12: type mismatch;
    [error]  found   : String("abc")
    [error]  required: c.Out
    [error]     (which expands to)  String("abcd")
    [error]     val s2: c.Out = "abc"
    [error]                     ^
    */

    s1 ?= "abcd"
  }
}
