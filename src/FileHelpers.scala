package net.al3x.blog

import java.io.{File, FileReader, FileWriter}
import java.lang.Runtime
import scala.collection.{immutable, mutable}
import scala.io.Source

trait FileHelpers {
  def findPosts(postDir: File): mutable.ListBuffer[Post] = {
    var foundPosts = new mutable.ListBuffer[Post]()

    def recursiveFind(dir: File) {
      for (file <- dir.listFiles) {
        if (file.isFile && file.getName.endsWith(".textile") && (file.getName != "about.textile")) {
          foundPosts += new Post(file)
        } else if (file.isDirectory) {
          recursiveFind(file)
        }
      }
    }

    recursiveFind(postDir)
    foundPosts
  }

  def readFile(file: File): String = {
    val src = Source.fromFile(file)
    src.getLines.mkString
  }

  def symlinkFileToFile(a: File, b: File) = {
    if (!b.exists) {
      val symCmd = "ln -s " + a.getPath + " " + b.getPath
      Runtime.getRuntime.exec(symCmd)
    }
  }

  def templatizeFile(file: File, varMap: immutable.Map[String, String]): String = {
    var out = readFile(file)
    for (key <- varMap.keys) {
      out = out.replace(key, varMap(key))
    }
    out
  }

  def writeFile(file: File, contents: String) = {
    def writeIt = {
      file.createNewFile
      val writer = new FileWriter(file)
      writer.write(contents)
      writer.flush
      writer.close
    }

    if (file.exists) {
      file.delete
    }

    writeIt
  }

  def copyFileToPath(file: File, path: String) = {
    val body = readFile(file)
    val newFileName = Array(path, file.getName).mkString("/")
    val newFile = new File(newFileName)
    writeFile(newFile, body)
  }

  def copyAllFiles(from: String, to: String) = {
    val dir = new File(from)

    if (!dir.isDirectory) {
      throw new RuntimeException(dir + " is not a directory, cannot copy from it.")
    }

    for (file <- dir.listFiles) {
      if (file.isFile) {
        copyFileToPath(file, to)
      }
    }
  }

}
