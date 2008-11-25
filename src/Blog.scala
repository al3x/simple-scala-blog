package net.al3x.blog

import java.io.File
import scala.collection.{immutable, mutable}
import scala.xml.XML

object Blog extends XMLHelpers {

  def findPosts(postDir: File): mutable.ListBuffer[Post] = {
    var foundPosts = new mutable.ListBuffer[Post]()

    def recursiveFind(dir: File) {
      for (file <- dir.listFiles) {
        if (file.isFile && file.getName.endsWith(".textile")) {
          foundPosts += new Post(file)
        } else if (file.isDirectory) {
          recursiveFind(file)
        }
      }
    }

    recursiveFind(postDir)
    foundPosts
  }

  def main(args: Array[String]) {
    val posts = findPosts(new File(Config.postDir))
    val lastTenPosts = posts.reverse.slice(0, 10)

    if (Config.force) {
      posts.foreach(post => post.write)
    } else {
      lastTenPosts.foreach(post => post.write)
    }

    XML.saveFull(Config.atomPath, toAtom(lastTenPosts), "UTF-8", true, null)
    XML.saveFull(Config.sitemapPath, toSitemap(posts), "UTF-8", true, null)
  }

}
