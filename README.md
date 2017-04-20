# 微信小程序信道服务器DEMO
## 介绍
这是一个微信小程序的信道服务器demo,你可以将它部署到自己的服务器中，不再受腾讯云的限制并可以自行维护。

## 重要说明
本程序是本人研究小程序业务服务器Java SDK制作的，所以只能保证和Java业务服务器正常通信。<br>
根据部分使用者反馈现在已经初步兼容php业务服务器。其他语言版本的业务服务器效果未知。

## 框架
这个demo是由java语言编写，使用了spring框架下的web，message，websocket等模块，由maven控制项目依赖

## 推荐环境
推荐在Linux, Apache Tomcat 7.0+, JDK1.8+上运行

## 部署方式
鉴于部分开发者基本不需要对信道服务器进行测试，只需要信道服务，您可以直接到target目录下下载对应的war包部署到Tomcat等服务器上,但是服务器JVM必须JDK1.8+版本。<br>
如果你的Tomcat对应JVM为较低版本，可下载源码进行修改，自行打包<br>
如果您使用的是Nginx服务器，您需要对服务器进行设置开启协议升级，详情请自行搜索"nginx websocket"

## 必要设置
本项目必须进行设置才能使用！！！<br>
设置文件就是腾讯云SDK配置文件，即/etc/qcloud/sdk.config，如果不存在这个文件请自行创建，设置的键值对为JSON格式，建议UTF-8编码！<br>
在这个文件中添加如下格式的KV键值<br>
`"BigforceTunnelHostLocation" : "www.xxx.com/ssr",`<br>
这个BigforceTunnelHostLocation对应的值是你的信道服务器的地址<br>
举个栗子,你的将这个程序打包为[ssr.war]并将其部署到tomcat下的webapps下面, 你的Tomcat对应域名为https://www.xxx.com, 你的信道服务器地址是https://www.xxx.com/ssr, 因而这里设置的值就是www.xxx.com/ssr<br>
**不需要请求协议前缀！**<br>
**必须是域名链接地址！**，这是由HTTPS决定的，这里的设置关系到websocket的URL生成和连接，如果使用内网，微信小程序无法访问到你的信道服务器<br>

## 其他设置
如果你想设置websocket的IDLE time，可以到spring.xml下设置<br>
找到`<property name="maxSessionIdleTimeout" value="60000"/>`<br>
value的值是毫秒，这里默认设置了10分钟<br>
关于信息尺寸限制，可以将注释掉的`<property name="maxTextMessageBufferSize" value="8192"/>`解除注释并修改<br>
这些设置都是不必须的，你可以根据项目需要进行修改或者无视，眼尖的应该发现，这里其实是关于Spring-websocket的设置，不懂的请自行谷歌

## 注意事项
本项目所有测试均是以UTF8编码环境下测试，如果遇到**签名错误**问题，建议自查是否为编码问题<br>
**别忘记在小程序配置里面添加socket合法域名🙂**

## 最后说明
这个demo只是提供了一个信道服务器的模板，正常使用问题不大。可能有些异常处理可能还有问题，欢迎指正<br>
websocket部分借鉴了网络上的几个配置案例，部分代码可能略有雷同<br>
为保证和业务服务器通信时签名校验问题，所用的Hash类直接从Wefer sdk抽取<br>
感谢Ghost幽灵在php业务服务器兼容测试的支持
