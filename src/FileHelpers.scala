package net.al3x.blog

import java.io.{File, FileReader, FileWriter}
import java.lang.Runtime
import scala.collection.{immutable, mutable}
import scala.io.Source

trait FileHelpers {
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
      out.replace(key, varMap(key))
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
      Config.force match {
        case true => file.delete; writeIt
        case false => writeIt
      }
    }
  }

}
