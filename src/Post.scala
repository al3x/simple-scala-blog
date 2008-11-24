package net.al3x.blog

import java.io.File
import net.sf.jtextile.JTextile
import scala.collection.{immutable, mutable}
import scala.io.Source
import scala.xml.XML

class Post(val title: String, body: String, path: String) extends FileHelpers {
  val templatizedBody = templatizeFile(new File(Config.postTemplate),
                                       immutable.Map("XTITLE" -> title, "XBODY" -> body))

  println(title)

  def createDir(year: String, month: String, day: String) = {
    val outDir = new File(Array(Config.wwwDir, year, month, day).mkString("/"))
    if (!outDir.exists && outDir.mkdirs) {
      println("created directory " + outDir.getPath)
    }
  }

  def createFile(year: String, month: String, day: String, filename: String): File = {
    val outFile = new File(Array(Config.wwwDir, year, month, day, filename).mkString("/") + ".html")
    writeFile(outFile, templatizedBody)
    println("created file " + outFile.getPath)
    outFile
  }

  def createSymlink(year: String, month: String, filename: String, postFile: File) = {
    val symlinkFile = new File(Array(Config.wwwDir, year, month, filename).mkString("/") + ".html")
    symlinkFileToFile(postFile, symlinkFile)
    println("symlinked file " + symlinkFile.getPath)
  }

  def filePost(post: File) = {
    val parts = path.split("posts/")(1).split("/")
    val year = parts(0)
    val month = parts(1)
    val day = parts(2)
    val filename = parts(3).split(".textile")(0)

    createDir(year, month, day)
    val postFile = createFile(year, month, day, filename)
    createSymlink(year, month, filename, postFile)
  }
}

object Post extends FileHelpers {
  def fromTextile(file: File): Post = {
    val title = Source.fromFile(file).getLine(1).split("h1. ")(1)
    val original = readFile(file)
    val textiled = JTextile.textile(original).trim

    new Post(title, textiled, file.getPath)
  }
}
