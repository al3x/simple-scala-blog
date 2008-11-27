package net.al3x.blog

import java.io.File
import scala.collection.{immutable, mutable}

class Index(posts: Seq[Post]) extends FileHelpers {
  def indexBody = {
    var out = ""
    for (post <- posts) {
      out += post.body
    }
    out
  }

  lazy val index = templatizeFile(new File(Config.template),
                                  immutable.Map("XTITLE" -> "Home",
                                                "XBODY"  -> indexBody))

  def write = writeFile(new File(Config.indexPath), index)
}
