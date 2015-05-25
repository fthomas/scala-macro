import org.scalacheck.Prop._
import org.scalacheck.Properties

class MacroSpec extends Properties("MacroSpec") {
  property("") = secure {
    val w = shapeless.Witness(42)
    Macro.foo[w.T]
    true
  }
}
