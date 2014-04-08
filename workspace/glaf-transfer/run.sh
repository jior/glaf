#!/bin/sh

if [ -x "$JAVA_HOME/bin/java" ]; then
    JAVA="$JAVA_HOME/bin/java"
else
    JAVA=`which java`
fi

if [ ! -x "$JAVA" ]; then
    echo "Could not find any executable java binary. Please install java in your PATH or set JAVA_HOME"
    exit 1
fi

exec ./ant/bin/ant run -Djvm.arg="-Xmx512m -XX:PermSize=128m -XX:MaxPermSize=256m"
