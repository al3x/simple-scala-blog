package net.al3x.blog

import java.io.File
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import net.sf.jtextile.JTextile
import scala.collection.{immutable, mutable}
import scala.io.Source
import scala.xml.XML

class Post(file: File) extends FileHelpers {
  val parts = file.getPath.split("posts/")(1).split("/")

  val year = parts(0)
  val month = parts(1)
  val day = parts(2)

  val filename = parts(3).split(".textile")(0)
  val relativeUrl = Array(year, month, day, filename).mkString("/") + ".html"
  val url = "http://al3x.net/" + relativeUrl

  lazy val siteMapDate = Array(year, month, day).mkString("-")
  lazy val atomId  = "tag:al3x.net," + siteMapDate + ":" + relativeUrl

  lazy val title = Source.fromFile(file).getLine(1).split("h1. ")(1)

  lazy val body =
    "<div class=\"post\">" + JTextile.textile(readFile(file)).trim + signoffDate + "</div>"

  lazy val bodyMinusTitle = {
    val bodyLines = body.split("\n")
    bodyLines.slice(1, bodyLines.size).mkString.trim
  }

  lazy val templatizedBody = templatizeFile(new File(Config.template),
                                            immutable.Map("XTITLE" -> title, "XBODY" -> body))

  lazy val updatedDate = {
    val rfc3339 = new SimpleDateFormat("yyyy-MM-dd'T'h:m:ss'-05:00'")
    val calendar = Calendar.getInstance
    calendar.set(year.toInt, month.toInt, day.toInt)
    rfc3339.format(calendar.getTime)
  }

  lazy val signoffDate = {
    val simpleDate = new SimpleDateFormat("MMM d, yyyy")
    val calendar = Calendar.getInstance
    calendar.set(year.toInt, month.toInt, day.toInt)
    val dateStr = simpleDate.format(calendar.getTime)

    <p class="signoff">
      &mdash;<a href={relativeUrl}>{dateStr}</a>
    </p>.toString
  }

  lazy val archiveDate = {
    val simpleDate = new SimpleDateFormat("MMM d")
    val calendar = Calendar.getInstance
    calendar.set(year.toInt, month.toInt, day.toInt)
    simpleDate.format(calendar.getTime)
  }

  def createDir(year: String, month: String, day: String) = {
    val outDir = new File(Array(Config.wwwDir, year, month, day).mkString("/"))
    if (!outDir.exists) {
      outDir.mkdirs
    }
  }

  def createFile(year: String, month: String, day: String, filename: String): File = {
    val outFile = new File(Array(Config.wwwDir, year, month, day, filename).mkString("/") + ".html")
    writeFile(outFile, templatizedBody)
    outFile
  }

  def createSymlink(year: String, month: String, filename: String, postFile: File) = {
    val symlinkFile = new File(Array(Config.wwwDir, year, month, filename).mkString("/") + ".html")
    symlinkFileToFile(postFile, symlinkFile)
  }

  def write = {
    createDir(year, month, day)
    val postFile = createFile(year, month, day, filename)
    createSymlink(year, month, filename, postFile)
  }
}
