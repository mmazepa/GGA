#!/bin/bash

classfiles=(*.class)
if [[ -f ${classfiles[0]} ]]; then
  rm *.class
fi

javac *.java
java App $1
