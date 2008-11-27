package net.al3x.blog

object Config {
  val pwd = "/Users/al3x/src/blog"

  val postDir   = pwd + "/posts"
  val wwwDir    = pwd + "/www"
  val staticDir = pwd + "/static"

  val template = pwd + "/templates/template.html"

  val aboutPost = postDir + "/about.textile"

  val aboutPath   = wwwDir + "/about.html"
  val archivePath = wwwDir + "/archive.html"
  val indexPath   = wwwDir + "/index.html"
  val atomPath    = wwwDir + "/index.atom"
  val sitemapPath = wwwDir + "/sitemap.xml"
}
