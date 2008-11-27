package net.al3x.blog

import java.io.File
import scala.collection.{immutable, mutable}
import net.sf.jtextile.JTextile

class About(filePath: String) extends FileHelpers {
  val file = new File(filePath)
  val aboutHTML = JTextile.textile(readFile(file)).trim

  lazy val about = templatizeFile(new File(Config.template),
                                  immutable.Map("XTITLE" -> "About",
                                                "XBODY"  -> aboutHTML))

  def write = writeFile(new File(Config.aboutPath), about)
}
