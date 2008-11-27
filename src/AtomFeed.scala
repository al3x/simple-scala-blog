package net.al3x.blog

import scala.xml.XML

class AtomFeed(posts: Seq[Post]) {
  lazy val feed =
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

  def write = XML.saveFull(Config.atomPath, feed, "UTF-8", true, null)
}
