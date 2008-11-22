#!/bin/bash
JARS="classes"

for d in lib/*.jar; do
    JARS="$d:$JARS"
done

exec scala -cp "$JARS" "$@"
