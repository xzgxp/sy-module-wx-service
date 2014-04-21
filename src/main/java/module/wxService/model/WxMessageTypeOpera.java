package module.wxService.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 微信消息类型私有功能
 * @author shiying
 *
 */
public interface WxMessageTypeOpera {
	
	/**
	 * 添加更多xml属性
	 * @param document
	 * @param node_xml
	 */
	public void writeMoreXmlElements(WxMessage wxMessage, Document document, Element node_xml);
	
	/**
	 * 读取更多属性
	 * @param wxMessage
	 * @param document
	 * @param node_xml
	 */
	public void readMoreProperties(WxMessage wxMessage, Document document, Element node_xml);

}
