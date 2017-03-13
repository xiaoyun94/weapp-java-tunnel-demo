# weapp-java-tunnel-demo
This is a demo of tunnel server for Weapp. It is ABLE to replace the original tunnel server provided by Tencent. So you can deploy this demo on your own server without limit of Tencent cloud and do server maintenance by your self.<br>
这是一个微信小程序的信道服务器demo,你可以将它部署到自己的服务器中，不再受腾讯云的限制并可以自行维护。

##框架
这个demo是由java语言编写，使用了spring框架下的web，message，websocket等模块，由maven控制项目依赖

##推荐环境
推荐在Linux, Apache Tomcat 7.0+, JDK1.7+上运行

##必要设置
本项目必须进行设置才能使用！在TunnelConfiguration.java下面修改host设置<br>
这个值是你的信道服务器的地址<br>
举个栗子,你的将这个程序打包为[ssr.war]并将其部署到tomcat下的webapps下面<br>
你的信道服务器地址是https://www.xxx.com/ssr<br>
host的值就是www.xxx.com/ssr<br>
不需要请求协议前缀！<br>
这里必须是外网连接地址！这里的设置关系到websocket的URL生成，如果使用内网，微信小程序不可能访问到你的信道服务器

##其他设置
如果你想设置websocket的IDLE time，可以到spring.xml下设置<br>
找到`<property name="maxSessionIdleTimeout" value="60000"/>`<br>
value的值是毫秒，这里默认设置了10分钟<br>
关于信息尺寸限制，可以将注释掉的`<property name="maxTextMessageBufferSize" value="8192"/>`解除注释并修改

##注意事项
本项目所有测试均是以UTF8编码环境下测试，如果遇到**签名错误**问题，建议自查是否为编码问题<br>

##最后说明
这个demo只是提供了一个信道服务器的模板，正常使用问题不大。可能有些异常处理可能还有问题，欢迎指正<br>
websocket部分借鉴了网络上的几个配置案例，部分代码可能略有雷同<br>
为保证和业务服务器通信时签名校验问题，所用的Hash类直接从Wefer sdk抽取