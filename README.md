module-wx-service
=================

微信服务框架


1. 功能描述
1.1 微信消息交互
1.2 支持用户上行消息和事件消息应答
1.3 基于XML配置和基于Java的消息应答
1.4 消息的接收支持：文本/图片/语音/视频/位置/链接/事件
1.5 消息的发送支持：音乐/文本/图文
1.6 支持反射方式加载应答处理对象和Spring方式加载应答处理对象
1.7 微信服务接入验证
1.8 自动提交微信菜单（如果存在）

2. 概念
2.1 消息
　　消息是微信用户与公众帐号接入服务器之间交互时传递的信息，用户发送的消息（上行）通过微信服务器发送给接入服务器，接入服务器返回的消息（下行）通过微信服务器发送给用户。
2.2 应答处理对象
　　微信消息通信框架，在接收到消息后，会分发给某一个Java对象来完成操作逻辑，并返回消息。这个对象即为消息处理对象。

3. 配置方式
3.1 基础配置
3.1.1 环境初始化方法中，实例化 support.wx.service.WxApiSupport 对象
3.1.1.1 普通支持
	需要提供 Token 和 配置文件的路径。
		WxApiSupport wxApiSupport = new WxApiSupport("wxlzmtest20131006", "support/wx/demo/config/wxReply-xmlconfig.xml");
3.1.1.2 自定义菜单支持
	需要额外提供 appid,secret 和 微信菜单路径。
		WxApiSupport wxApiSupport = new WxApiSupport("wxlzmtest20131006", "wxe74f6a848a951d09", 
					"4496a76f342e7f464215c09914b8463b", "configs/wxReply.xml", "support/wx/demo/config/wxMenu.json"); 
3.1.2 将HTTP请求交付给WxApiSupport对象完成请求
	try {
		wxApiSupport.notice(request, response);
	} catch (Exception e) {
		e.printStackTrace();
		PrintWriter out = response.getWriter();
		out.print("server error.");
		out.flush();
		out.close();
	}

3.2 通过反射创建Bean的配置
3.2.1 采用反射方式创建的消息处理类必须实现 support.wx.service.WxMsgAccept 接口，在accept方法中，完成消息受理逻辑
		package support.wx.demo.accept;
		
		import javax.servlet.http.HttpServletRequest;
		
		import org.dom4j.Element;
		
		import support.wx.service.WxMsgAccept;
		import support.wx.vo.recv.WxRecvMsg;
		import support.wx.vo.send.WxSendMsg;
		import support.wx.vo.send.WxSendTextMsg;
		
		public class Default implements WxMsgAccept {
		
			@Override
			public WxSendMsg accept(WxRecvMsg recvMsg, WxSendMsg sendMsg, HttpServletRequest request, Element configNode) {
				sendMsg = new WxSendTextMsg(sendMsg, "this is text message.");
				return sendMsg;
			}
		
		}
		
3.2.2 在配置文件 wxReply.xml 中注册消息处理类
		<wx beanload="reflex" >
			<reply>
				<default accept="support.wx.demo.accept.Default"></default>
			</reply>
		</wx>

3.3 通过Spring创建Bean的配置
3.3.1 在Spring中声明Beanload对象，在applicationContext.xml文件中添加以下代码
		<bean class="support.wx.service.Beanload" ></bean>
3.3.2 创建消息处理类，同3.2.1。
3.3.3 在Spring中声明创建的Bean
		<bean id="wx.accept.default" class="support.wx.demo.accept.Default" ></bean>
3.3.4 在配置文件 wxReply.xml 中注册消息处理类
		<wx beanload="spring" >
			<reply>
				<default accept="wx.accept.default"></default>
			</reply>
		</wx>

3.4 通过XML配置消息应答
	通过XML配置消息应答可以与多种Bean创建方式（reflex/spring）共存，当accept配置不存在时，就会使用XML消息解析
		<wx beanload="spring" >
			<reply>
				<text match=".*"  >
					<answer type="text" ><![CDATA[i'm text message, from xml config]]></answer>
				</text>
			</reply>
		</wx>

4 回复消息类型
4.1 Java代码消息
4.1.1 文本消息
	new WxSendTextMsg(sendMsg, "内容");
4.1.2 图文消息
	WxSendNewsMsg newsMsg = new WxSendNewsMsg(sendMsg)
			                .addItem("标题", "描述", "图片地址", "点击后跳转的链接")
			                .addItem....
			                // 最多可以添加10个
4.1.3 音乐消息
	new WxSendMusicMsg(sendMsg, "标题","描述","低品质音乐地址", "高品质音乐地址 (wifi环境会使用这个地址进行播放)");
4.2 XML消息
4.2.1 文本消息
	<answer type="text" >内容</answer>
4.2.2 图文消息
	<answer type="news" >
		<item>
			<title>百度搜索</title>
			<description>全球最大的中文搜索引擎、致力于让网民更便捷地获取信息，找到所求。百度超过千亿的中文网页数据库，可以瞬间找到相关的搜索结果。</description>
			<imageUrl>http://www.baidu.com/img/bdlogo.gif</imageUrl>
			<linkUrl>http://www.baidu.com/</linkUrl>
		</item>
	</answer>
4.2.3 音乐消息
	<answer type="music" >
		<title>i'm music message title, from xml config</title>
		<description>i'm music message description.</description>
		<musicUrl>http://zhangmenshiting.baidu.com/data2/music/33795619/14945107241200128.mp3?xcode=9bc5d4c8ea6daa22663ac8488439b8fbf3b502c7ddf22793</musicUrl>
		<hqMusicUrl>http://zhangmenshiting.baidu.com/data2/music/33795619/14945107241200128.mp3?xcode=9bc5d4c8ea6daa22663ac8488439b8fbf3b502c7ddf22793</hqMusicUrl>
	</answer>
	
5 消息分发配置
5.1 消息类型
5.1.1 pic
	图片消息
5.1.2 link
	链接消息
5.1.3 geo
	地理位置消息
5.1.4 video
	视频消息
5.1.5 voice
	语音消息
5.1.6 text
	文本消息，需要配置match属性，表示接受消息的匹配的正则表达式。接受所有文本消息，请传入【.*】
5.1.7 event
	事件消息，需要配置key属性
5.1.7.1 subscribe~
	用户关注
5.1.7.2 unsubscribe~
	用户取消关注
5.1.7.3 click~xxx
	菜单点击事件



