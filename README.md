# onboarder
Website for easing employee onboardnig in projects/workplace

* Coding conventions found at *onboarder/coding conventions/* as an .adoc file: it can be updated by using https://asciidoclive.com/edit/scratch/1. Import the file and add your suggestions. :)
* Import the file *onboarder/coding conventions/EasyOnboarderCodingStyle.xml* in your IDE for formatting the code.

Use Case doc:
* https://github.com/easyonboard/onboarder/blob/master/documentation/User%20Case%20doc.docx

Installation requirements:
* Oracle 11g Express Edition
  * download link: http://www.oracle.com/technetwork/database/database-technologies/express-edition/downloads/index.html
* SQL Developer
  * http://www.oracle.com/technetwork/developer-tools/sql-developer/downloads/index.html
* Gradle
  * version: 4.3.1
  * link:
     * download:  https://gradle.org/releases/
     * how to install: https://gradle.org/install/
* Java 8
  * http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

* Node
  * download link: https://nodejs.org/en/




 Runnig Server Application:

* From Intellij:
     * select Edit Configurations from Run area
     * add --spring.datasource.url=jdbc:oracle:thin:@<machine_ip_address>:1521:xe to program arguments (<machine_ip_address> to be replaced with the IPv4 Address of the computer running the program; "ipconfig" command will show it)

* From Jar
  * open Terminal / Command Prompt
  * go to project_location\server\app\build\libs (with "cd" command)
  * run " -java -jar onborder-server.jar --spring.datasource.url=jdbc:oracle:thin:@<machine_ip_address>:xe" (<machine_ip_address> to be replaced with the IPv4 Address of the computer running the program; "ipconfig" command will show it)

* With Docker (requires installation of Docker)
  * open Terminal / Command Prompt
  * go to project_location\server\app (with "cd" command)
  * run "docker build -t server . --build-arg db_ip=<machine_ip_address> --build-arg db_port=1521" command (<machine_ip_address> to be replaced with the IPv4 Address of the computer running the program; "ipconfig" command will show it)
  * run " docker run -p 8090:8090 server"



Running Client Application

 * With Node (requires installation of node.js)
   * open node.js terminal (with "cd" command)
   * go to project_location\client\angular-front
   * run "npm install" command
   * run "ng serve" command

 * With Docker (requires installation of Docker)
   * go to project_location\angular-front (with "cd" command)
   * run "docker build -t front . --build-arg http_proxy=<http_proxy>" command (replace <http_proxy> with actual proxy)
   * run "docker run  -p  4200:4200 front" command


*NOTE* The application will connect to a Oracle 11g database.
