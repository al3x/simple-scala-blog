CLASSES = src/classes
CLASSPATH = src/lib/jtextile.jar:src/lib/gnu-regexp-1.1.4.jar

.PHONY: all clean post sync

all: clean run

run: src/classes/net/al3x/blog/Blog.class
	scala -classpath $(CLASSES):$(CLASSPATH) net.al3x.blog.Blog

src/classes/%.class:
	fsc -d src/classes -classpath $(CLASSPATH) `find src/ -name \*.scala -print`

clean:
	rm -rf src/classes/*
	
post:
	scala -classpath $(CLASSES):$(CLASSPATH) net.al3x.blog.Blog -n

sync:
	rsync -avz www/ elsy:/var/www/al3x.net
