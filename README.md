module-wx-service
=================
module-wx-service（微信服务框架）是JavaEE微信公众帐号的基础服务框架。

# 功能介绍
- 微信消息交互
- 微信菜单提交

# 获得最新版本
## Jar文件下载
访问<https://github.com/wonder-sy0618/repo/tree/master/sy-module/module-wx-service>获得最新版本
需要手动添加依赖
```
+--- sy-module:module-core:1.3a2
|    +--- commons-dbcp:commons-dbcp:1.4
|    |    \--- commons-pool:commons-pool:1.5.4
|    +--- commons-logging:commons-logging:1.1.3
|    +--- log4j:log4j:1.2.17
|    +--- org.freemarker:freemarker:2.3.20
|    +--- org.apache.tomcat:servlet-api:6.0.13
|    +--- org.codehaus.jackson:jackson-core-asl:1.9.13
|    +--- org.codehaus.jackson:jackson-core-lgpl:1.9.13
|    +--- org.codehaus.jackson:jackson-mapper-asl:1.9.13
|    |    \--- org.codehaus.jackson:jackson-core-asl:1.9.13
|    \--- org.codehaus.jackson:jackson-mapper-lgpl:1.9.13
|         \--- org.codehaus.jackson:jackson-core-lgpl:1.9.13
+--- commons-httpclient:commons-httpclient:3.1
|    +--- commons-logging:commons-logging:1.0.4 -> 1.1.3
|    \--- commons-codec:commons-codec:1.2
+--- dom4j:dom4j:1.6.1
|    \--- xml-apis:xml-apis:1.0.b2
+--- jaxen:jaxen:1.1.6
+--- jdom:jdom:1.1
|    \--- org.jdom:jdom:1.1
\--- org.springframework:spring-beans:3.2.0.RELEASE
     \--- org.springframework:spring-core:3.2.0.RELEASE
          \--- commons-logging:commons-logging:1.1.1 -> 1.1.3
```

## Gradle
将私有版本库（<https://raw.githubusercontent.com/wonder-sy0618/repo/master/>）添加到repositories中，声明依赖
``` groovy
repositories {
    maven { url "https://raw.githubusercontent.com/wonder-sy0618/repo/master/" }
    mavenCentral()
}
dependencies {
    compile 'sy-module:module-wx-service:x.x'
}
```
``` shell
gradle cleanEclipse eclipse
```

## 如何使用
### 1. 配置模块核心服务
**该步骤是配置模块核心服务，如果您的项目中已经使用了其它依赖于module-core的组件，可以跳过该步骤**

在web.xml文件中配置核心拦截器
``` xml
<filter>
    <filter-name>ModuleCoreFilter</filter-name>
	<filter-class>sy.module.core.mvc.ModuleCoreFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>ModuleCoreFilter</filter-name>
	<url-pattern>*</url-pattern>
</filter-mapping>
```

[**可选**] 配置日志配置文件，在classpath://log4j.properties
``` properties
log4j.rootLogger=debug, console
log4j.logger.sy.module.core.scanjars=warn
log4j.logger.sy.module.core.moduleconfig=warn
log4j.logger.sy.module.core.release=warn

log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%d{MM-dd HH:mm:ss,SSS}] %c %m %n
```

[**可选**] 配置数据库，在classpath://configs/jdbc.properties
``` properties
driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
url=jdbc\:sqlserver\://192.168.0.220\:1433;DatabaseName\=mobileNumService
username=appdevelop
password=appdevelop

initialSize=10
maxActive=500
maxIdle=20
minIdle=5
logAbandoned=false
removeAbandoned=true
removeAbandonedTimeout=180
maxWait=6000
connectionProperties=
defaultAutoCommit=true
defaultReadOnly=
defaultTransactionIsolation=READ_UNCOMMITTED
validationQuery=select getdate()
testOnBorrow=true
testOnReturn=true
testWhileIdle=true
```

[**可选**] 添加模块参数配置文件classpath://configs/config.properties
``` properties
# weixin access parameters
module.wxService.token=demo-token
module.wxService.configPath=configs/wxReply.xml
module.wxService.appid=
module.wxService.secret=
module.wxService.wxMenuJsonPath=

# weixin signature check pattern, the method used to control the execution of module.wxService.security.UrlCodeKit.checkOpenIdSign(openid, sign), default is : normal
#   none : Not checked
#   normal : Check the signature is correct
#   strict : Check whether the signature timeout
# module.wxService.security.checkModel=strict
# weixin signature private key, default is : default_private_key
# module.wxService.security.privateKey=testPrivateKey
# weixin strict model, time check set, field Constant reference java.util.Calendar, default field[12, MINUTE], amount[30], denote 30 minute
# module.wxService.security.strict.time.field=13
# module.wxService.security.strict.time.amount=2

```

### 2. 接入配置
- 修改模块参数配置文件，添加token
``` properties
module.wxService.token=demo-token
```
设置的值务必与微信后台设置一致

- 创建消息应答配置文件，并将文件路径设置到configPath。文件基本结构如下，详细的编写请参考后文
``` xml
<?xml version="1.0"   ?>
<wx
    xmlns="https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/"	
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
		https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/ 
		https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/master/src/main/resources/module/wxService/schema/wx-reply.xsd">
		
	<reply >
		
	</reply>
</wx>
```
添加到配置
``` properties
module.wxService.configPath=configs/wxReply.xml
```

- 如果需要菜单提交，或者高级接口的支持，需要添加appid信息，该信息来自于微信后台
``` properties
module.wxService.appid=wx5641c2474b7e5ab7
module.wxService.secret=d0f06e3e128cba96f1f0b7240ac24200
```

- 定义菜单结构
添加菜单定义文件wxMenus.json，并把路径声明到配置文件
``` json
 {
     "button":[
     {    
          "type":"click",
          "name":"今日歌曲",
          "key":"V1001_TODAY_MUSIC"
      },
      {
           "type":"click",
           "name":"歌手简介",
           "key":"V1001_TODAY_SINGER"
      },
      {
           "name":"菜单",
           "sub_button":[
           {	
               "type":"view",
               "name":"搜索",
               "url":"http://www.soso.com/"
            },
            {
               "type":"view",
               "name":"视频",
               "url":"http://v.qq.com/"
            },
            {
               "type":"click",
               "name":"赞一下我们",
               "key":"V1001_GOOD"
            }]
       }]
 }
```
上面的例子来自于微信公众平台开发者文档（<http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口>）
添加菜单到配置
``` properties
module.wxService.wxMenuJsonPath=wxMenus.json
```


### 3. 应答配置
#### 应答处理器

在configs/wxReply.xml配置wx/apply节点，该节点负责消息的接收分发。下面以关注事件消息为例，介绍如何配置。定义class文件

``` java
package module.wxService.demo;

import javax.servlet.http.HttpServletRequest;
import org.dom4j.Element;
import module.wxService.service.WxMsgAccept;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;

public class Subscribe implements WxMsgAccept {

    @Override
	public WxSendMsg accept(WxRecvMsg msg, WxSendMsg sendMsg,
			HttpServletRequest request, Element configNode) {
    	return new WxSendTextMsg(sendMsg, "welcome");
	}

}
```

添加配置到wx/apply节点
``` xml
<?xml version="1.0"   ?>
<wx
    xmlns="https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/"    
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
		https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/ 
		https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/master/src/main/resources/module/wxService/schema/wx-reply.xsd">
		
	<reply >
    
        <event key="subscribe~"  accept="module.wxService.demo.Subscribe"  ></event>
        
	</reply>
</wx>
```

支持的分发消息类型

- 事件消息
``` xml
<event key="subscribe~"  accept="xxx"  ></event>
```
```
key参数结构：事件名称~事件值
事件名称：subscribe，unsubscribe，LOCATION，SCAN，CLICK，VIEW
事件值：参考微信公众平台开发者文档（<http://mp.weixin.qq.com/wiki/index.php>）
事件值可以留空，但是中间的波浪线～不允许省略
```

- 文本消息
``` xml
<text match=".*" accept="xxx" ></text>
```
```
match 表示匹配文本消息的正则表达式
该参数不允许省略，如果希望匹配所有消息，请配置为.*
```

- 默认回复（当找不到当前类型的消息处理器的时候，调用默认回复）
``` xml
<default accept="xxx" ></default>
```

- 图片消息
``` xml
<img accept="xxx" ></img>
```

- 链接消息
``` xml
<link accept="xxx" ></link>
```

- 地理位置消息
``` xml
<geo accept="xxx" ></geo>
```

- 语音消息
``` xml
<video accept="xxx" ></video>
```

- 视频消息
``` xml
<voice accept="xxx" ></voice>
```


#### XML消息配置

XML消息配置提供了一种更便捷的方式配置应答，可以配置于任何分发消息类型中。**当消息应答器中accept属性不存在时，会尝试加载answer子标签，读取XML消息配置**。

- 文本消息回复
``` xml
<text match=".*"  >
	<answer type="text" >i'm text message, from xml config</answer>
</text>
```

- 语音消息回复
``` xml
<text match="音乐"  >
	<answer type="music" >
		<title>泡沫</title>
		<description>G.E.M.邓紫棋</description>
		<musicUrl>http://zhangmenshiting.baidu.com/data2/music/33795619/14945107241200128.mp3?xcode=9bc5d4c8ea6daa22663ac8488439b8fbf3b502c7ddf22793</musicUrl>
		<hqMusicUrl>http://zhangmenshiting.baidu.com/data2/music/33795619/14945107241200128.mp3?xcode=9bc5d4c8ea6daa22663ac8488439b8fbf3b502c7ddf22793</hqMusicUrl>
	</answer>
</text>
```

- 图文消息回复
``` xml
<text match="图文"  >
	<answer type="news" >
		<item>
			<title>百度搜索</title>
			<description>全球最大的中文搜索引擎、致力于让网民更便捷地获取信息，找到所求。百度超过千亿的中文网页数据库，可以瞬间找到相关的搜索结果。</description>
			<imageUrl>http://www.baidu.com/img/bdlogo.gif</imageUrl>
			<linkUrl>http://www.baidu.com/</linkUrl>
		</item>
		<item>
			<title>百度知道</title>
			<description>百度知道是由全球最大的中文搜索引擎百度自主研发、基于搜索的互动式知识问答分享平台。</description>
			<imageUrl>http://www.baidu.com/img/bdlogo.gif</imageUrl>
			<linkUrl>http://zhidao.baidu.com/</linkUrl>
		</item>
	</answer>
</text>
```

- XML消息配置支持表达式
``` xml
<text match=".*"  >
    <answer type="text" >this open id is : ${wx_openid}</answer>
</text>
```
```
默认支持的表达式数据
    ${wx_openid }
    ${wx_openid_sign }
    ${url_basepath }
需要添加自定义属性，请参考后文事件机制，覆盖xmlMessageRecvTemplateDate事件处理方法
表达式引擎使用Freemarker，支持更高级的语法
请参考Freemarker官方文档（<http://freemarker.org/docs/index.html>）

```

### 4. Spring支持
默认情况下消息分发器会通过java反射机制创建消息受理对象，可以通过配置从Spring容器中获得消息受理对象。

- 在Spring配置文件中添加Beanload监听
``` xml
<bean class="support.wx.service.Beanload" ></bean>
```

- 在configs/wxReply.xml配置，为wx节点增加beanload属性，值为spring
``` xml
<?xml version="1.0"   ?>
<wx beanload="spring"
    xmlns="https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/"    
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
		https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/ 
		https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/master/src/main/resources/module/wxService/schema/wx-reply.xsd">
		
	<reply >
    
        <event key="subscribe~"  accept="module.wxService.demo.Subscribe"  ></event>
        
	</reply>
</wx>
```

- 修改accept为Spring中Bean的名称
``` xml
<text match=".*" accept="spring_bean_name" ></text>
```


### 5. 事件机制

允许通过配置添加事件监听器，以获得更好的扩展性

- 创建事件监听对象
``` 
// 实现module.wxService.event.WxAcceptEvent接口
// 或继承module.wxService.event.WxAcceptEventAdapter类
// 并覆盖父类方法
```
``` java
package module.wxService.demo;

import java.util.Map;

public class WxAcceptEventDemo extends WxAcceptEventAdapter {
    @Override
    public void xmlMessageRecvTemplateDate(Map<String, Object> data) {
		// TODO Auto-generated method stub
	}
}
```
- 配置到configs/wxReply.xml文件中reply节点
``` xml
<?xml version="1.0"   ?>
<wx
    xmlns="https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/"	
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
		https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/ 
		https://raw.githubusercontent.com/wonder-sy0618/module-wx-service/master/src/main/resources/module/wxService/schema/wx-reply.xsd">
		
	<reply event="module.wxService.TestEvent" >
		
	</reply>
</wx>
```
- 支持的事件类型
```
请参考module.wxService.event.WxAcceptEvent接口
```


## 关于
```
作者：石莹
邮箱：wonder_sy0618@foxmail.com
如果您使用中发现了bug，请提交到 github issues
如果您有更好的意见，欢迎与我联系。
```




