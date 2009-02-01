package net.al3x.blog

import java.io.File
import scala.collection.{immutable, mutable}
import scala.collection.jcl

class Archive(posts: Seq[Post]) extends FileHelpers {
  def archiveByYearMap: jcl.TreeMap[String, mutable.ListBuffer[Post]] = {
    var yearMap = new jcl.TreeMap[String, mutable.ListBuffer[Post]]

    for (post <- posts.reverse) {
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
  <h1>Archive</h1>
  {for (key <- archiveByYearMap.keys.toList.reverse) yield
    <h2>{key}</h2>
    <ul id="archive-by-year" class="yearlist">
    {for (post <- archiveByYearMap(key)) yield
      <li>{post.archiveDate} &mdash; <a href={post.relativeUrl}>{post.title}</a></li>
    }
    </ul>
  }
  </div>

  def write = {
    val templatizedBody = templatizeFile(new File(Config.template),
                                         immutable.Map("XTITLE" -> "Archive",
                                                       "XBODY"  -> yearsDiv.toString))
    writeFile(new File(Config.archivePath), templatizedBody)
  }
}