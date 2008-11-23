import java.io.{File, FileReader, FileWriter}
import java.lang.Runtime
import net.sf.jtextile.JTextile
import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.xml.XML

val postDir = "../posts"
val postTemplate = new File("../templates/post.html")
var postFiles = new ListBuffer[File]()
val wwwDir = "../www"

def findPosts(dir: File) {
  for (file <- dir.listFiles) {
    if (file.isFile && file.getName.endsWith(".textile")) {
      postFiles += file
    } else if (file.isDirectory) {
      findPosts(file)
    }
  }
}

def readTitle(file: File): String = {
  val src = Source.fromFile(file)
  src.getLine(1).split("h1. ")(1)
}

def readFile(file: File): String = {
  val src = Source.fromFile(file)
  src.getLines.mkString
}

def writeFile(file: File, contents: String) = {
  file.createNewFile
  val writer = new FileWriter(file)
  writer.write(contents)
  writer.flush
  writer.close
}

def templatizePost(post: File): String = {
  val title = readTitle(post)
  val body = readFile(post)
  val textiled = JTextile.textile(body).trim
  val temp = readFile(postTemplate)
  var out = temp.replace("XTITLE", title)
  out.replace("XBODY", textiled)
}

def filePost(post: File) = {
  val body = templatizePost(post)

  val parts = post.getPath.split("posts/")(1).split("/")
  val year = parts(0)
  val month = parts(1)
  val day = parts(2)
  val filename = parts(3).split(".textile")(0)

  val outDir = new File(Array(wwwDir, year, month, day).mkString("/"))
  if (!outDir.exists && outDir.mkdirs) {
    println("created directory " + outDir.getPath)
  }

  val outFile = new File(Array(wwwDir, year, month, day, filename).mkString("/") + ".html")
  //if (!outFile.exists) {
    outFile.delete
    writeFile(outFile, body)
    println("created file " + outFile.getPath)
  //}

  val symlinkFile = new File(Array(wwwDir, year, month, filename).mkString("/") + ".html")
  if (!symlinkFile.exists) {
    val symCmd = "ln -s " + outFile.getPath + " " + symlinkFile.getPath
    Runtime.getRuntime.exec(symCmd)
    println("symlinked file " + symlinkFile.getPath)
  }
}

findPosts(new File(postDir))
postFiles.map(filePost)
