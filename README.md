# 微信小程序信道服务器DEMO
## 背景介绍
腾讯云提供了两套信道服务器给开发者，一个是公共免费的ws.qcloud.com，但是这个服务器存在诸多问题，比如不兼容php业务服务器，data字段为JSON对象而非字符串，这可能对开发者造成很多问题；另一个是提供给购买腾讯云小程序解决方案的用户，形如XXXXX.ws.qcloud.la，这个信道服务器相对稳定好用，但是对于很多没有购买解决方案的用户来说就望成莫及。<br>
这是提供了一个微信小程序的信道服务器demo，你可以将它部署到自己的服务器中，不再受腾讯云的限制并可以自行维护。<br>
该项目是由java语言编写，IDE为IDEA Intellij，使用了spring框架下的web，message，websocket等模块，由maven控制项目依赖

## 最低环境
- Apache Tomcat 7.0，从该版本才开始引入了Websocket
- JDK1.7，Websocket 是servlet 3.1的技术，JDK7才提供实现

## 部署方式
- 快速部署：鉴于部分开发者基本不需要对信道服务器进行测试，只需要信道服务，您可以直接到target目录下下载对应的war包部署到Tomcat等服务器上。该war包是JDK8生成的，因而服务器JVM必须1.8+版本<br>
- 自行打包部署：如果你的Tomcat对应JVM为较低版本或者你想进行代码修改，请下载源码，自行打war包再部署。<br>

## 调试模式
- 调试模式是用来测试WS性能和连接性的一个解决方案。这个方案可以暂时摆脱业务服务器的控制，但为了避免测试模式成为别人的攻击手段，您必须进行HTTP AUTH完成登录，登陆成功之后才可以进行接下来的调试
- 这里使用了HTTP AUTH作为登陆校验的方式，因而你必须设置账户密码才能正常开启调试模式，设置方法见【其他设置】。换句话说，如果你想关闭调试模式，只需要删除这个设置。
- 调试WS的时候，只需要在请求的WS连接只需要一个额外参数对：debug=debug，便可以进行测试。</br>
举个例子：ws://localhost:8080/tt/websocket?debug=debug</br>
在调试模式下，你可以自定义tcId和tunnelId，且不会进行有效性检验（有效性检验）。如果您没有设置这两个参数，服务器会把值都设置为"debug"。
本项目的主页已经内嵌一个简易的WS程序，您可以通过此程序，快速进行调试。</br>

## 必要设置
- 设置文件就是腾讯云SDK配置文件，Linux下即/etc/qcloud/sdk.config，选择和SDK配置文件是因为现在信道服务器和业务服务器可以在同一个服务器运行，配置文件采用同一个比较很方便。<br>
如果您将业务服务器独立于业务服务器，那么服务器可能不存在这个文件，请自行创建，设置的键值对为JSON格式，建议UTF-8编码！<br>
在这个文件中添加如下格式的KV键值<br>
`"BigforceTunnelHostLocation" : "www.xxx.com/ssr",`<br>
这个BigforceTunnelHostLocation对应的值是你的信道服务器的地址<br>
举个栗子,你的将这个程序打包为[ssr.war]并将其部署到tomcat下的webapps下面, 你的Tomcat对应域名为https://www.xxx.com, 你的信道服务器地址是https://www.xxx.com/ssr, 因而这里设置的值就是www.xxx.com/ssr<br>
**不需要请求协议前缀！**<br>
**必须是域名链接地址！** 不能为IP地址，这是由HTTPS决定的，这里的设置关系到websocket的URL生成和连接

## 其他设置
- 关于调试模式，您需要在必要设置里面所涉及的文件里面添加如下格式的KV键值<br>
`"BigforceTunnelUser" : "u1:p1;u2:p2",`<br>
即用户和密码之间用":"隔开，用户之间需要用";"隔开。
- 关于Websocket的设置，可以到spring.xml下设置：<br>
比如闲置时间，找到`<property name="maxSessionIdleTimeout" value="60000"/>`，value的值是毫秒<br>
比如信息尺寸，可以将注释掉的`<property name="maxTextMessageBufferSize" value="8192"/>`解除注释并修改<br>
这些设置都是不必须的，你可以根据项目需要进行修改或者无视，眼尖的应该发现，这里其实是关于Spring-websocket的设置，具体问题请自行搜索


## 注意事项
- 本项目所有测试均是以UTF8编码环境下测试，如果遇到**签名错误**问题，建议自查是否为编码问题<br>
- 别忘记在小程序配置里面添加socket合法域名
- 如果您使用的是非Tomcat服务器（如：Nginx），您需要对服务器软件开启**协议升级**功能，这是Websocket协议要求的，具体设置请自行搜索"nginx websocket"
- 根据部分使用者反馈，现在已经兼容java、php两款语言的业务服务器，其他语言版本的业务服务器效果未知。<br>

## 更新说明
- 2017-05-08 主页增加WS测试功能，新增调试模式<br>
- 2017-05-02 HTTP拦截会检验tcId和tunnelId合法性，没有经过业务服务器而随意生成的连接将被禁止访问，避免恶意连接<br>
- 2017-04-19 请求连接时会返回signature，用来兼容php业务服务器

## 最后说明
- 这个demo只是提供了一个信道服务器的模板，正常使用问题不大。可能有些异常处理可能还有问题，欢迎指正<br>
- 为保证和业务服务器通信时签名校验不出现问题，所用的Hash类直接从Wefer sdk抽取，保证一致性<br>
- 感谢Ghost幽灵在php业务服务器兼容测试的支持
