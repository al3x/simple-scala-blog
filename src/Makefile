CLASSES = classes:classes/net/al3x/blog
CLASSPATH = lib/jtextile.jar:lib/gnu-regexp-1.1.4.jar
SOURCES = Blog.scala Config.scala FileHelpers.scala Post.scala XMLHelpers.scala

blog: classes/net/al3x/blog/Blog.class
	scala -classpath $(CLASSES):$(CLASSPATH) net.al3x.blog.Blog

.PHONY: clean
clean:
	rm -rf classes/*
	
classes/%.class: $(SOURCES)
	fsc -d classes -classpath $(CLASSPATH) `find . -name \*.scala -print`