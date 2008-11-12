import scala.collection.mutable.ListBuffer
import java.io.{File, FileWriter}
import java.lang.Runtime

val archive = xml.XML.loadFile(args(0))
val mess = archive \ "entry"
var entries = new ListBuffer[xml.NodeSeq]()

def findEntries(entry: xml.NodeSeq) = {
  (entry \\ "category").foreach(category => (category \\ "@term").text match {
    case "http://schemas.google.com/blogger/2008/kind#post" => entries + entry
    case _ => null
  })
}

def fileEntries(entry: xml.NodeSeq) = {
  (entry \\ "link").foreach(link => (link \\ "@rel").text match {
    case "alternate" => fileEntry((link \\ "@href").text,
                                  (entry \\ "title").text,
                                  (entry \\ "content").text,
                                  (entry \\ "published").text)
    case _ => null
  })
}

def fileEntry(oldUrl: String, title: String, body: String, pubDate: String) = {
  // create the dirs
  val fileParts = oldUrl.split('/')
  val year = fileParts(3)
  val month = fileParts(4)
  val day = pubDate.split('T')(0).split('-')(2)
  val fileName = fileParts(5)
  val originalFilePath = "posts/" + year + "/" + month + "/" + fileName
  val dirToFile = "posts/" + year + "/" + month + "/" + day + "/"
  if (new File(dirToFile).mkdirs) {
    println("made dir " + dirToFile)
  }

  // create the file
  val filePath = dirToFile + fileName
  var file = new File(filePath)
  if (file.createNewFile) {
    println("created " + filePath)
  }

  // write the post
  val post = "<h1>" + title + "</h1>\n" + body
  val writer = new FileWriter(file)
  writer.write(post)
  writer.flush
  writer.close
  println("wrote post to " + filePath)

  // symlink old Blogger paths
  val symlinkPath = day + "/" + fileName
  Runtime.getRuntime.exec("ln -s " + symlinkPath + " " + originalFilePath)
}

mess.map(findEntries)
entries.map(fileEntries)

println("Entries: " + entries.size)

