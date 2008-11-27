package net.al3x.blog

import scala.collection.{immutable, mutable}
import java.io.File

class Index(posts: Seq[Post]) extends FileHelpers {
  def indexBody = {
    var out = ""
    for (post <- posts) {
      out += post.body
    }
    out
  }

  lazy val index = templatizeFile(new File(Config.template),
                                  immutable.Map("XTITLE" -> "Online Writings",
                                                "XBODY"  -> indexBody))

  def write = writeFile(new File(Config.indexPath), index)
}
