import shapeless.Witness

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

object Macro {
  def foo[N](implicit aux: Witness.Aux[N]): Unit = macro fooImpl[N]

  def fooImpl[N: c.WeakTypeTag](c: Context)(aux: c.Expr[Witness.Aux[N]]): c.Expr[Unit] = {
    import c.universe._

    val typechecked = aux.tree
    println("Typechecked tree:")
    println(show(typechecked))

    val untypechecked = c.untypecheck(typechecked)
    println("Untypechecked tree:")
    println(show(untypechecked))

    val retypechecked = c.typecheck(untypechecked)
    println("Retypechecked tree:")
    println(show(retypechecked))

    def eval = c.eval(c.Expr(untypechecked))
    scala.util.Try(eval).getOrElse(eval)

    c.Expr[Unit](q"()")
  }
}
