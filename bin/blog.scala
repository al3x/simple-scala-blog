import java.io.{BufferedReader, File, FileReader, FileWriter}
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

findPosts(postDir)

for (post <- posts) {
  println(JTextile.textile(readFile(post)))
}
