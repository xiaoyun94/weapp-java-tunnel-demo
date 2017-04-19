# 微信小程序信道服务器DEMO
## 介绍
This is a demo of tunnel server for Weapp. It is ABLE to replace the original tunnel server provided by Tencent. So you can deploy this demo on your own server without limit of Tencent cloud and do server maintenance by your self.<br>
这是一个微信小程序的信道服务器demo,你可以将它部署到自己的服务器中，不再受腾讯云的限制并可以自行维护。

## 重要说明
本程序是本人研究小程序业务服务器Java SDK制作的，所以只能保证和Java业务服务器正常通信。<br>
根据部分使用者反馈现在已经初步兼容php业务服务器。其他语言版本的业务服务器效果未知。

## 框架
这个demo是由java语言编写，使用了spring框架下的web，message，websocket等模块，由maven控制项目依赖

## 推荐环境
推荐在Linux, Apache Tomcat 7.0+, JDK1.7+上运行

## 必要设置
在2017-4-20日之后的版本中，将设置文件和程序进行隔离。开发者可以直接到target目录下下载对应的war包部署到Tomcat等服务器上<br>
本项目必须进行设置才能使用！<br>
设置文件就是腾讯云SDK配置文件，即/etc/qcloud/sdk.config，如果不存在这个文件请自行创建，文本为JSON格式，建议UTF-8编码！<br>
在这个文件中添加如下格式的KV键值<br>
`"BigforceTunnelHostLocation" : "www.xxx.com/ssr",`
这个BigforceTunnelHostLocation对应的值是你的信道服务器的地址<br>
举个栗子,你的将这个程序打包为[ssr.war]并将其部署到tomcat下的webapps下面<br>
你的Tomcat对应域名为https://www.xxx.com<br>
你的信道服务器地址是https://www.xxx.com/ssr<br>
因而这里设置的值就是www.xxx.com/ssr<br>
不需要请求协议前缀！<br>
这里必须是外网连接地址！这里的设置关系到websocket的URL生成和链接，如果使用内网，微信小程序无法访问到你的信道服务器
**另外，别忘记在小程序配置里面添加socket合法域名**

## 其他设置
如果你想设置websocket的IDLE time，可以到spring.xml下设置<br>
找到`<property name="maxSessionIdleTimeout" value="60000"/>`<br>
value的值是毫秒，这里默认设置了10分钟<br>
关于信息尺寸限制，可以将注释掉的`<property name="maxTextMessageBufferSize" value="8192"/>`解除注释并修改<br>
关于Spring-websocket的设置，请自行谷歌

## 注意事项
本项目所有测试均是以UTF8编码环境下测试，如果遇到**签名错误**问题，建议自查是否为编码问题<br>

## 最后说明
这个demo只是提供了一个信道服务器的模板，正常使用问题不大。可能有些异常处理可能还有问题，欢迎指正<br>
websocket部分借鉴了网络上的几个配置案例，部分代码可能略有雷同<br>
为保证和业务服务器通信时签名校验问题，所用的Hash类直接从Wefer sdk抽取<br>
感谢Ghost幽灵在php业务服务器兼容测试的支持
