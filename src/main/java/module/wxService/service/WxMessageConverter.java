package module.wxService.service;

import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import module.wxService.model.WxMessage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sy.module.core.util.XmlHelper;

/**
 * 消息格式转换工具
 * 
 * @author shiying
 *
 */
public class WxMessageConverter {
	
	
	/**
	 * 将一个微信消息转换为字符串
	 * @param wxMessage
	 * @return
	 * @throws ParserConfigurationException
	 * @throws UnsupportedEncodingException
	 * @throws TransformerException
	 */
	public static String convertMessageToXml(WxMessage wxMessage) 
			throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		// xml根节点
		Element node_xml = document.createElement("xml");
		document.appendChild(node_xml);
		// base info
		XmlHelper.appendCDataNode(document, node_xml, "ToUserName", wxMessage.getToUserName());
		XmlHelper.appendCDataNode(document, node_xml, "FromUserName", wxMessage.getFromUserName());
		XmlHelper.appendCDataNode(document, node_xml, "CreateTime", ""+wxMessage.getCreateTime());
		XmlHelper.appendCDataNode(document, node_xml, "MsgType", ""+wxMessage.getMsgType().toString());
		//
		wxMessage.getMsgType().getWxMessageTypeOpera().writeMoreXmlElements(wxMessage, document, node_xml);
		//
		return XmlHelper.convertDocumentToXml(document);
	}

}
