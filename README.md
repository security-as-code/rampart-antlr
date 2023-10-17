# Antlr limited to Java Parser
This repository is for Ramparts's fork of the ANTLR framework with every language but Java stripped out.
The other change is to namespace shift, as every Java file was moved to package: org.rampart.antlr

## Prerequisites

You need JDK 1.6.x and MVN 3.1.1

## Building

To build (and locally validate) the project you can run (inside the project directory):

```
env JAVA_HOME=<path-to-jdk6> <path-to-mvn>/bin/mvn clean package
```

This would clean the package, test and build test artifacts. You may also install artifacts into your local repository by running

```
env JAVA_HOME=<path-to-jdk6> <path-to-mvn>/bin/mvn clean install
```
