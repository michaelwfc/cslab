# C dev env on 
https://code.visualstudio.com/docs/languages/cpp  

## Install a compiler on windows
- MS Visual C++ (Studio)

## vscode 


## windows 下使用make命令编译代码
https://blog.csdn.net/Nicholas_Liu2017/article/details/78323391

在 Windows 操作系统中，没有原生的 "make" 命令，但是您可以安装 GNU 工具链来获得它。  
安装 GNU 工具链的方法有很多，其中一种方法是通过安装 MinGW-w64。这是一个在 Windows 平台上提供了许多 GNU 工具的开源项目。在安装 MinGW-w64 后，您可以使用其提供的 "mingw32-make" 命令来执行类似于 Makefile 的任务。  
以下是使用 MinGW-w64 安装 make 命令的步骤：  
1.前往 MinGW-w64 的官方网站 (mingw-w64.org/doku.php/do…  
2.运行安装程序，并按照提示进行安装。请注意，如果您正在运行 64 位的 Windows 操作系统，则应选择与您的操作系统架构相对应的安装程序。  
3.在安装过程中，请确保选择了 "mingw32-base" 和 "mingw32-make" 以及其他您需要的工具。编译器勾选 C compiler 和 C++ compiler  
4.设置环境变量 ： Path 中增加 MinGW/bin  
5.复制或者重命名  "mingw32-make.exe to make.exe   
6.在命令行中输入 "mingw32-make" or make 来执行 Makefile 文件中的任务。  