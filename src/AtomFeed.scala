package net.al3x.blog

import scala.xml.XML

class AtomFeed(posts: Seq[Post]) {
  lazy val feed =
  <feed xmlns="http://www.w3.org/2005/Atom">
    <title>John Doe</title>
    <subtitle>A fancy subtitle.</subtitle>
    <link href="http://example.com/"/>
    <link href="http://example.com/atom.xml" rel="self"/>
    <updated>{posts(0).updatedDate}</updated>
    <author>
      <name>John Doe</name>
      <uri>http://example.com/about.html</uri>
    </author>
    <id>http://example.com/</id>
    {for (post <- posts) yield
    <entry>
      <title>{post.title}</title>
      <link href={post.url} rel="alternate"/>
      <id>{post.atomId}</id>
      <updated>{post.updatedDate}</updated>
      <content type="html">{post.bodyMinusTitle}</content>
      <author>
        <name>John Doe</name>
        <uri>http://example.com/about.html</uri>
      </author>
    </entry>
    }
  </feed>

  def write = XML.saveFull(Config.atomPath, feed, "UTF-8", true, null)
}
