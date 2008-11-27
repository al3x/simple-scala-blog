package net.al3x.blog

import java.io.File
import scala.collection.{immutable, mutable}
import scala.collection.jcl
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

  def archiveByYearMap(posts: Seq[Post]): jcl.TreeMap[String, mutable.ListBuffer[Post]] = {
    var yearMap = new jcl.TreeMap[String, mutable.ListBuffer[Post]]

    for (post <- posts) {
      var yearList = {
        if (yearMap.contains(post.year)) {
           yearMap(post.year)
        } else {
          new mutable.ListBuffer[Post]()
        }
      }
      yearList += post
      yearMap += (post.year -> yearList)
    }

    yearMap
  }

  def archivePageBody(posts: Seq[Post]) = {
    val yearMap = archiveByYearMap(posts)
    val yearsDiv =
    <div id="years-div">
    {for (key <- yearMap.keys) yield
      <h1>{key}</h1>
      <ul id="archive-by-year">
      {for (post <- yearMap(key)) yield
        <li><a href={post.url}>{post.title}</a></li>
      }
      </ul>
    }
    </div>

    yearsDiv.toString
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
