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

  property("Concat[a, Concat[b, c]]") = secure {
    val bc = Concat[W.`"b"`.T, W.`"c"`.T]
    val abc = Concat[W.`"a"`.T, bc.Out]

    val s1: abc.Out = "abc"

    //val s2: abc.Out = "xyz"
    /*
    [error] /home/frank/data/code/scala-macro/src/test/scala/ConcatSpec.scala:31: type mismatch;
    [error]  found   : String("xyz")
    [error]  required: abc.Out
    [error]     (which expands to)  String("abc")
    [error]     val s2: abc.Out = "xyz"
    [error]                       ^
    */

    s1 ?= "abc"
  }
}
