CLASSES = src/classes
CLASSPATH = src/lib/jtextile.jar:src/lib/gnu-regexp-1.1.4.jar

blog: src/classes/net/al3x/blog/Blog.class
	scala -classpath $(CLASSES):$(CLASSPATH) net.al3x.blog.Blog

.PHONY: clean
clean:
	rm -rf src/classes/*
	
src/classes/%.class:
	fsc -d src/classes -classpath $(CLASSPATH) `find src/ -name \*.scala -print`