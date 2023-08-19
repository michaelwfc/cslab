# Reference

[JAVA 架构师笔记](https://zq99299.github.io/note-architect/ztc/01/02.html)
[前后端分离架构：Web 实现前后端分离，前后端解耦](https://segmentfault.com/a/1190000039765982)

# Key conceptions

- 前后端分离 (解耦) 的核心思想是：前端 Html 页面通过 Ajax 调用后端的 RestFul API 并使用 Json 数据进行交互。
- 通过 Tomcat+Ngnix(也可以中间有个 Node.js)，有效地进行解耦。并且前后端分离会为以后的大型分布式架构、弹性计算架构、微服务架构、多端化服务（多种客户端，例如：浏览器，车载终端，安卓，IOS 等等）打下坚实的基础。
  注：【在互联网架构中，web 服务器：一般指像 nginx，apache 这类的服务器，他们一般只能解析静态资源。应用服务器：一般指像 tomcat，jetty，resin 这类的服务器可以解析动态资源也可以解析静态资源，但解析静态资源的能力没有 web 服务器好。】
  一般只有 Web 服务器才能被外网访问，应用服务器只能内网访问。

# JavaWeb 项目

- 前端:
  html5，css3，ajax, jquery，angularjs，bootstrap，reactjs，vuejs，webpack，less/sass，gulp，nodejs，Google V8 引擎，javascript 多线程，模块化，面向切面编程，设计模式，浏览器兼容性，性能优化等等。
  前端追求的是：页面表现，速度流畅，兼容性，用户体验等等。
- 后端:
  java 基础，设计模式，jvm 原理，spring+springmvc 原理及源码，linux，mysql 事务隔离与锁机制，oracle/mongodb，http/tcp，多线程，分布式架构（dubbo，dubbox，spring cloud），弹性计算架构，微服务架构（springboot+zookeeper+docker+jenkins），java 性能优化，以及相关的项目管理等等。
  后端追求的是：三高（高并发，高可用，高性能），安全，存储，业务等等。

# how

- 大量并发浏览器请求
- html 页面负责调用服务端接口产生数据（通过 ajax 等等，后台返回 json 格式数据，json 数据格式因为简洁高效而取代 xml,填充 html，展现动态效果，在页面上进行解析并操作 DOM）
- web/前端服务器集群 (nginx):除了接口以外的其他所有 http 请求全部转移到前端 nginx 上, nginx 放的是 css，js，图片等等一系列静态资源,负责控制页面引用 & 跳转 & 路由，前端页面异步调用后端的接口,;支持页面热部署，不用重启服务器，前端升级更无缝。nginx 中部署证书，外网使用 https 访问，并且只开放 443 和 80 端口，其他端口一律关闭（防止黑客端口扫描），内网使用 http，性能和安全都有保障。
- 应用服务器集群 (tomcat): 想象成一个数据提供者，加快整体响应速度
- 文件 / 数据库 / 缓存 / 消息队列服务器集群
-
