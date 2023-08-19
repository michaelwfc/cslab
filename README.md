# cslab

This repository is for cs learning and implementation

# jave devolop in vscode

## install

- jdk
- Extention Pack for Java
- maven + pom.xml

## maven project strcture

- src
  - main
    - java
    - resources
  - test
    - java
    - resources
- target
- pom.xml

## java command

- java compile command
  javac src/**/**/\*.java -d ./bin

- run Hello class
  java -classpath ./bin src.com.tutorial.Hello

## mvn command

- mvn clean package
  use pom.xml to package a .jar
- java -jar target/hello.jar
