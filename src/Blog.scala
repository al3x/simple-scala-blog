package net.al3x.blog

import java.io.File
import scala.collection.{immutable, mutable}
import scala.collection.jcl
import scala.xml.XML

object Blog extends FileHelpers {
  def main(args: Array[String]) {
    val posts = findPosts(new File(Config.postDir))
    val lastTenPosts = posts.reverse.slice(0, 10)

    if (args.isDefinedAt(0)) {
      args(0) match {
        case "-f" => posts.foreach(post => post.write)
        case "-n" => Post.newPost(args(1))
        case _ => println("Unknown argument."); System.exit(-1)
      }
    } else {
      lastTenPosts.foreach(post => post.write)
    }

    // copy static files
    copyAllFiles(Config.staticDir, Config.wwwDir)

    // generate dynamic files
    new About(Config.aboutPost).write
    new Archive(posts).write
    new Sitemap(posts).write
    new AtomFeed(lastTenPosts).write
    new Index(lastTenPosts).write
  }
}
