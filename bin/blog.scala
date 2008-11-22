import java.io.{BufferedReader, ByteArrayOutputStream, File, FileInputStream, FileReader, FileWriter}
import net.sf.jtextile.JTextile
import scala.collection.mutable.ListBuffer
import scala.io.Source

val postDir = (new File("../posts"))
var posts = new ListBuffer[File]()

def findPosts(dir: File) {
  for (file <- dir.listFiles) {
    if (file.isFile && file.getName.endsWith(".textile")) {
      posts += file
    } else if (file.isDirectory) {
      findPosts(file)
    }
  }
}

def readFile(file: File): String = {
  val src = Source.fromFile(file)
  src.getLines.mkString
}

def readFileToString(f: File) = {
  val bos = new ByteArrayOutputStream
  val ba = new Array[Byte](2048)
  val is = new FileInputStream(f)

  def read {
    is.read(ba) match {
      case n if n < 0 =>
        case 0 => read
      case n => bos.write(ba, 0, n); read
    }
  }

  read
  bos.toString("UTF-8")
}

findPosts(postDir)

for (post <- posts) {
  val body = readFileToString(post)
  val textiled = JTextile.textile(body)
  println(textiled)
}
