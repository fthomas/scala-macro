import org.scalacheck.Prop._
import org.scalacheck.Properties

object ConcatSpec extends Properties("ConcatSpec") {
  val W = shapeless.Witness

  property("Concat[ab, cd]") = secure {
    val c = Concat[W.`"ab"`.T, W.`"cd"`.T]

    val s1: c.Out = "abcd"

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
    [error] scala-macro/src/test/scala/ConcatSpec.scala:31: type mismatch;
    [error]  found   : String("xyz")
    [error]  required: abc.Out
    [error]     (which expands to)  String("abc")
    [error]     val s2: abc.Out = "xyz"
    [error]                       ^
    */

    s1 ?= "abc"
  }

  property("Concat[Concat[a, b], Concat[c, d]]") = secure {
    val ab = Concat[W.`"a"`.T, W.`"b"`.T]
    val cd = Concat[W.`"c"`.T, W.`"d"`.T]
    val abcd = Concat[ab.Out, cd.Out]

    val s1: abcd.Out = "abcd"

    //val s2: abcd.Out = "xyz"
    /*
    [error] scala-macro/src/test/scala/ConcatSpec.scala:51: type mismatch;
    [error]  found   : String("xyz")
    [error]  required: abcd.Out
    [error]     (which expands to)  String("abcd")
    [error]     val s2: abcd.Out = "xyz"
    [error]                        ^
    */

    s1 ?= "abcd"
  }
}
