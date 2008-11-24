CLASSES=classes:classes/net/al3x/blog
CLASSPATH=lib/jtextile.jar:lib/gnu-regexp-1.1.4.jar

blog: classes/net/al3x/blog/Blog.class
	scala -classpath $(CLASSES):$(CLASSPATH) net.al3x.blog.Blog

clean:
	rm -rf classes/*
	
classes/%.class: Blog.scala Config.scala FileHelpers.scala Post.scala
	fsc -d classes -classpath $(CLASSPATH) `find . -name \*.scala -print`