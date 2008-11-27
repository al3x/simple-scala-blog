package net.al3x.blog

import java.io.File
import scala.collection.{immutable, mutable}
import scala.collection.jcl

class Archive(posts: Seq[Post]) extends FileHelpers {
  def archiveByYearMap: jcl.TreeMap[String, mutable.ListBuffer[Post]] = {
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

  lazy val yearsDiv =
  <div id="years-div">
  {for (key <- archiveByYearMap.keys) yield
    <h1>{key}</h1>
    <ul id="archive-by-year">
    {for (post <- archiveByYearMap(key)) yield
      <li><a href={post.url}>{post.title}</a></li>
    }
    </ul>
  }
  </div>

  def write = {
    val templatizedBody = templatizeFile(new File(Config.template),
                                         immutable.Map("XTITLE" -> "Archived Online Writings",
                                                       "XBODY"  -> yearsDiv.toString))
    writeFile(new File(Config.archivePath), templatizedBody)
  }
}