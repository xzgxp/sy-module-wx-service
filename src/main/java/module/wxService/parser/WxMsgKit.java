package module.wxService.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import module.wxService.event.WxAcceptEventManager;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;

public final class WxMsgKit {
	private static final Log log = LogFactory.getLog(WxMsgKit.class);
	
	private static final Map<String, WxRecvMsgParser> recvParserMap = new HashMap<String, WxRecvMsgParser>();
	
	static {
		// 文本消息解析程序
		recvParserMap.put("text", new WxRecvTextMsgParser());
		// 链接消息解析程序
		recvParserMap.put("link", new WxRecvLinkMsgParser());
		// 地址消息解析程序
		recvParserMap.put("location", new WxRecvGeoMsgParser());
		// 图片消息解析程序
		recvParserMap.put("image", new WxRecvPicMsgParser());
		// 事件消息解析程序
		recvParserMap.put("event", new WxRecvEventMsgParser());
		// 语音消息解析程序
		recvParserMap.put("voice", new WxRecvVoiceMsgParser());
		// 视频消息解析程序
		recvParserMap.put("video", new WxRecvVideoMsgParser());
	}
	
	public static WxRecvMsg parse(InputStream in) throws IOException, DocumentException {
        Document dom = new SAXReader().read(in);  
        //
		String xmlContent = "";
		StringWriter writer = new StringWriter();
		try {
	        OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			new XMLWriter(writer, format).write(dom);
			writer.flush();
			xmlContent = writer.toString();
		} finally {
			writer.close();
		}
		log.debug("recv message >> \n" + xmlContent);
		//
		WxAcceptEventManager.getWxAcceptEventInstance().onResolveXmlElement(dom.getRootElement());
		Element msgType = dom.getRootElement().element("MsgType");
		if(null != msgType) {
			String txt = msgType.getText().toLowerCase();
			WxRecvMsgParser parser = recvParserMap.get(txt);
			if(null != parser) {
				return parser.parser(dom);
			} else {
				log.warn("unknown message : " + txt);
			}
		}
		return null;
	}
	
	public static Document parse(WxSendMsg msg) throws IOException {
		return msg.toDocument();
	}
}
