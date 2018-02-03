[**ActiveMQ**](https://github.com/apache/activemq):<br>
win:下载activemq，解压执行win64/activemq.bat即启动了broker和console<br>
Mac OS X:<br>
下载 apache-activemq-5.11.1-bin.tar<br>
解压 tar -xvf apache-activemq-5.11.1-bin.tar<br>
cd bin/macosx<br>
启动broker及console ./activemq start | stop | restart<br>
访问console localhost:8161 conf/jetty.xml admin/admin登录<br>
查看broker日志 data/activemq.log conf/activemq.xml<br>