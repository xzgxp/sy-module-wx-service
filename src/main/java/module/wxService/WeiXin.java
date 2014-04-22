package module.wxService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import module.wxService.parser.WxMsgKit;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;

public final class WeiXin {
	private static final Log log = LogFactory.getLog(WeiXin.class);
	
	public static boolean access(String token,String signature,String timestamp,String nonce) {
		List<String> ss = new ArrayList<String>();
		ss.add(timestamp);
		ss.add(nonce);
		ss.add(token);
		
		Collections.sort(ss);
		
		StringBuilder builder = new StringBuilder();
		for(String s : ss) {
			builder.append(s);
		}
		return signature.equalsIgnoreCase(HashKit.sha1(builder.toString()));
	}
	
	public static WxRecvMsg recv(InputStream in) throws IOException, DocumentException {
		return WxMsgKit.parse(in);
	}
	
	public static void send(WxSendMsg msg,OutputStream out) throws IOException, DocumentException {
		Document doc = WxMsgKit.parse(msg);
		Document dom = DocumentHelper.parseText(doc.asXML());
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
		log.debug("send message >> \n" + xmlContent);
		//
		if(null != doc) {
			out.write(doc.asXML().toString().getBytes("UTF-8"));
		} else {
			log.warn("发送消息时,解析出dom为空 msg :"+msg);
		}
	}
	
	public static WxSendMsg builderSendByRecv(WxRecvMsg msg) {
		WxRecvMsg m = new WxRecvMsg(msg);
		String from = m.getFromUser();
		m.setFromUser(m.getToUser());
		m.setToUser(from);
		m.setCreateDt((System.currentTimeMillis() / 1000)+"");
		return new WxSendMsg(m);
	}
}
