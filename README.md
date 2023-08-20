# cslab

This repository is for cs learning and implementation

# java devolop in vscode

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

## build jar with mvn command

- setting pom.xml to package a .jar

  set groupId + artifactId + packaging + version
  set build : plugin: maven-shade-plugin + mainClass
  set dependencies:

- mvn clean package
- java -jar target/hello.jar

## build war with mvn command

- 编写 Servlet
- 打包为 war 文件 setting pom.xml and mvn clean package : web-servlet.war
- 复制到 Tomcat 的 webapps 目录下: put .war in tomcat/webapps
- 启动 Tomcat : startup.bat
- open :http://localhost:8080/web-servlet/hello
