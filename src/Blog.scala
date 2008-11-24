package net.al3x.blog

import java.io.File
import scala.collection.{immutable, mutable}

object Blog {
  var postFiles = new mutable.ListBuffer[File]()
  var allPosts = new mutable.ListBuffer[Post]()

  def findPostFiles(dir: File) {
    for (file <- dir.listFiles) {
      if (file.isFile && file.getName.endsWith(".textile")) {
        postFiles += file
      } else if (file.isDirectory) {
        findPostFiles(file)
      }
    }
  }

  def main(args: Array[String]) {
    findPostFiles(new File(Config.postDir))

    //for (post <- postFiles) {
    //  allPosts += Post.fromTextile(file)
    //}
    //allPosts.foreach(post => post.filePost)

    val lastTen = postFiles.reverse.slice(0, 10)
    lastTen.foreach(post => println(post.getPath))
  }
}
