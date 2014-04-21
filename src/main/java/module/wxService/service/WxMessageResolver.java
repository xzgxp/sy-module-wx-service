package module.wxService.service;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import module.wxService.model.WxMessage;
import module.wxService.model.WxMessagePushEvent;
import module.wxService.model.WxMessageType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sy.module.core.util.XmlHelper;


/**
 * 微信消息解析器
 * 
 * @author shiying
 *
 */
public class WxMessageResolver {
	private static final Log log = LogFactory.getLog(WxMessageResolver.class);
	
	private static WxMessageResolver wxMessageResolver = null;
	
	private WxMessageResolver() {}
	
	/**
	 * 工厂方法
	 * @return 实例
	 */
	public static WxMessageResolver getInstance() {
		if (wxMessageResolver == null) {
			wxMessageResolver = new WxMessageResolver();
		}
		return wxMessageResolver;
	}
	
	
	/**
	 * 解析XML流
	 * @param xmlstream
	 * @return 解析消息
	 */
	public WxMessage resolveMessage(InputStream xmlstream) throws IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(xmlstream);
			Element root = doc.getDocumentElement();
			if (root == null) return new WxMessage();
			
			System.out.println(XmlHelper.convertDocumentToXml(root));
			//
			WxMessage wxMessage = new WxMessage();
			wxMessage.setToUserName(XmlHelper.getElementByName(root, "ToUserName").getTextContent());
			wxMessage.setFromUserName(XmlHelper.getElementByName(root, "FromUserName").getTextContent());
			wxMessage.setCreateTime(Integer.parseInt(XmlHelper.getElementByName(root, "CreateTime").getTextContent()));
			wxMessage.setMsgType(WxMessageType.valueOf(XmlHelper.getElementByName(root, "MsgType").getTextContent()));
			// 添加类型特有属性
			wxMessage.getMsgType().getWxMessageTypeOpera().readMoreProperties(wxMessage, doc, root);
			//
			return wxMessage;
			
		} catch (Exception e) {
			throw new IOException("resolve message error.", e);
		}
	}
	

}
