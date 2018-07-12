#!/bin/bash
set -ue

mkdir -p bin
find . -name "*.java" | xargs javac -d bin
java -classpath bin Homework3
