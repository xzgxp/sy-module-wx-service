package module.wxService.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sy.module.core.util.XmlHelper;

/**
 * 推送事件消息类型
 * 
 * @author shiying
 *
 */
public enum WxMessagePushEvent {
	
	
	subscribe("订阅事件", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage,
				Document document, Element node_xml) {
			// TODO
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			try {
				wxMessage.setSubscribe_qrscene_key(XmlHelper.getElementByName(node_xml, "EventKey").getTextContent());
			} catch (Exception e){}
			try {
				wxMessage.setSubscribe_qrscene_ticket(XmlHelper.getElementByName(node_xml, "Ticket").getTextContent());
			} catch (Exception e){}
		}
	}),		// 订阅事件
	
	unsubscribe("退订事件", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage,
				Document document, Element node_xml) {
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
		}
	}),	// 退订事件
	
	SCAN("二维码扫描事件", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage,
				Document document, Element node_xml) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			try {
				wxMessage.setSubscribe_qrscene_key(XmlHelper.getElementByName(node_xml, "EventKey").getTextContent());
			} catch (Exception e){}
			try {
				wxMessage.setSubscribe_qrscene_ticket(XmlHelper.getElementByName(node_xml, "Ticket").getTextContent());
			} catch (Exception e){}
		}
	}),			// 二维码扫描事件
	
	LOCATION("地理位置上报事件", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage,
				Document document, Element node_xml) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			wxMessage.setLocation_latitude(XmlHelper.getElementByName(node_xml, "Latitude").getTextContent());
			wxMessage.setLocation_longitude(XmlHelper.getElementByName(node_xml, "Longitude").getTextContent());
			wxMessage.setLocation_precision(XmlHelper.getElementByName(node_xml, "Precision").getTextContent());
		}
	}),		// 地理位置上报事件
	
	CLICK("菜单点击事件", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage,
				Document document, Element node_xml) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			wxMessage.setClick_key(XmlHelper.getElementByName(node_xml, "EventKey").getTextContent());
		}
	}),			// 菜单点击事件
	
	VIEW("菜单URL地址浏览事件", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage,
				Document document, Element node_xml) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			wxMessage.setView_url(XmlHelper.getElementByName(node_xml, "EventKey").getTextContent());
		}
	});			// 菜单URL地址浏览事件
	
	/**
	 * 类型操作
	 */
	private WxMessageTypeOpera wxMessageTypeOpera;
	
	/**
	 * 显示名称
	 */
	private String display;
	
	WxMessagePushEvent(
			String display, 
			WxMessageTypeOpera wxMessageTypeOpera) {
		this.display = display;
		this.wxMessageTypeOpera = wxMessageTypeOpera;
	}

	public WxMessageTypeOpera getWxMessageTypeOpera() {
		return wxMessageTypeOpera;
	}

	public void setWxMessageTypeOpera(WxMessageTypeOpera wxMessageTypeOpera) {
		this.wxMessageTypeOpera = wxMessageTypeOpera;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

}
