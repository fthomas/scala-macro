import scala.concurrent.duration.Duration
import scala.reflect.macros.blackbox

object compileTime {

  def apply(code: String): Duration = macro impl

  def impl(c: blackbox.Context)(code: c.Expr[String]): c.Expr[Duration] = {
    import c.universe._

    val Literal(Constant(codeStr: String)) = code.tree

    val t1 = System.nanoTime()
    c.typecheck(c.parse(codeStr))
    val t2 = System.nanoTime()
    val td = t2 - t1

    c.Expr(q"_root_.scala.concurrent.duration.Duration($td, _root_.scala.concurrent.duration.NANOSECONDS)")
  }

}

/*
Usage:

scala> compileTime("val x = 1")
res4: scala.concurrent.duration.Duration = 862353 nanoseconds

scala> compileTime("val x = for (i <- 1 to 10) yield i * 2")
res5: scala.concurrent.duration.Duration = 92155703 nanoseconds
*/
