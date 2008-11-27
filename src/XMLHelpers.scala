package net.al3x.blog

import scala.xml.XML

trait XMLHelpers {
  def toAtom(posts: Seq[Post]) = {
    <feed xmlns="http://www.w3.org/2005/Atom">
      <title>Alex Payne</title>
      <subtitle>The online writings of Alex Payne.</subtitle>
      <link href="http://al3x.net/"/>
      <link href="http://al3x.net/index.atom" rel="self"/>
      <updated>{posts(0).updatedDate}</updated>
      <author>
        <name>Alex Payne</name>
        <uri>http://al3x.net/about.html</uri>
      </author>
      <id>http://al3x.net/</id>
      {for (post <- posts) yield
      <entry>
        <title>{post.title}</title>
        <link href={post.url} rel="alternate"/>
        <id>{post.atomId}</id>
        <updated>{post.updatedDate}</updated>
        <content type="html">{post.bodyMinusTitle}</content>
        <author>
          <name>Alex Payne</name>
          <uri>http://al3x.net/about.html</uri>
        </author>
      </entry>
      }
    </feed>
  }

  def toSitemap(posts: Seq[Post]) = {
    <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
      <url>
        <loc>http://al3x.net/</loc>
        <lastmod>2005-01-01</lastmod>
        <changefreq>daily</changefreq>
        <priority>1.0</priority>
      </url>
      <url>
        <loc>http://al3x.net/about.html</loc>
        <lastmod>2005-01-01</lastmod>
        <changefreq>monthly</changefreq>
        <priority>0.8</priority>
      </url>
      <url>
        <loc>http://al3x.net/archive.html</loc>
        <lastmod>2005-01-01</lastmod>
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
  }
}