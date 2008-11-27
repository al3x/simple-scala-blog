package net.al3x.blog

import scala.xml.XML

class Sitemap(posts: Seq[Post]) {
  lazy val lastPostDate = posts(posts.size - 1).siteMapDate

  lazy val sitemap =
  <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
    <url>
      <loc>http://al3x.net/</loc>
      <lastmod>{lastPostDate}</lastmod>
      <changefreq>daily</changefreq>
      <priority>1.0</priority>
    </url>
    <url>
      <loc>http://al3x.net/about.html</loc>
      <lastmod>{lastPostDate}</lastmod>
      <changefreq>monthly</changefreq>
      <priority>0.8</priority>
    </url>
    <url>
      <loc>http://al3x.net/archive.html</loc>
      <lastmod>{lastPostDate}</lastmod>
      <changefreq>daily</changefreq>
      <priority>0.7</priority>
    </url>
    {for (post <- posts) yield
    <url>
      <loc>{post.url}</loc>
      <lastmod>{post.siteMapDate}</lastmod>
      <changefreq>monthly</changefreq>
      <priority>0.5</priority>
     </url>
     }
  </urlset>

  def write = XML.saveFull(Config.sitemapPath, sitemap, "UTF-8", true, null)
}
