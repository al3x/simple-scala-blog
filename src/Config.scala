package net.al3x.blog

object Config {
  val force = true

  val pwd = "/Users/al3x/src/blog"

  val postDir = pwd + "/posts"
  val wwwDir  = pwd + "/www"

  val aboutTemplate   = pwd + "/templates/about.html"
  val archiveTemplate = pwd + "/templates/archive.html"
  val indexTemplate   = pwd + "/templates/index.html"
  val postTemplate    = pwd + "/templates/post.html"

  val aboutPath   = wwwDir + "/about.html"
  val archivePath = wwwDir + "/archive.html"
  val indexPath   = wwwDir + "/index.html"
  val atomPath    = wwwDir + "/index.atom"
  val sitemapPath = wwwDir + "/sitemap.xml"
}
