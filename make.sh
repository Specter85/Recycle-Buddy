#!/bin/sh

javac -sourcepath src src/recycleBuddy/*.java
jar cvfm RecycleBuddy.jar manifest.txt -C bin recycleBuddy

