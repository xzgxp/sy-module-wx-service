package module.wxService.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sy.module.core.util.XmlHelper;

/**
 * 微信消息类型
 * 
 * @author shiying
 *
 */
public enum WxMessageType {
	
	event("事件消息", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage, Document document, Element node_xml) {
			XmlHelper.appendCDataNode(document, node_xml, "Event", wxMessage.getEvent().toString());
			wxMessage.getEvent().getWxMessageTypeOpera().writeMoreXmlElements(wxMessage, document, node_xml);
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			wxMessage.setEvent(WxMessagePushEvent.valueOf(XmlHelper.getElementByName(node_xml, "Event").getTextContent()));
			wxMessage.getEvent().getWxMessageTypeOpera().readMoreProperties(wxMessage, document, node_xml);
			wxMessage.setMsgId(wxMessage.getFromUserName() + wxMessage.getCreateTime());
		}
	}),		// 事件消息
	
	text("文本消息", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage, Document document, Element node_xml) {
			XmlHelper.appendCDataNode(document, node_xml, "Content", ""+wxMessage.getText_Content());
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			wxMessage.setMsgId(XmlHelper.getElementByName(node_xml, "MsgId").getTextContent());
			wxMessage.setText_Content(XmlHelper.getElementByName(node_xml, "Content").getTextContent());
		}
	}),		// 文本消息
	
	image("图片消息", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage, Document document, Element node_xml) {
			XmlHelper.appendCDataNode(document, node_xml, "MsgId", wxMessage.getMsgId());
			XmlHelper.appendCDataNode(document, node_xml, "MediaId", wxMessage.getImage_MediaId());
			XmlHelper.appendCDataNode(document, node_xml, "PicUrl", wxMessage.getImage_PicUrl());
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			wxMessage.setMsgId(XmlHelper.getElementByName(node_xml, "MsgId").getTextContent());
			wxMessage.setImage_MediaId(XmlHelper.getElementByName(node_xml, "MediaId").getTextContent());
			wxMessage.setImage_PicUrl(XmlHelper.getElementByName(node_xml, "PicUrl").getTextContent());
		}
	}),	// 图片消息
	
	voice("语音消息", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage, Document document, Element node_xml) {
			XmlHelper.appendCDataNode(document, node_xml, "MsgId", wxMessage.getMsgId());
			XmlHelper.appendCDataNode(document, node_xml, "MediaId", wxMessage.getVoice_MediaId());
			XmlHelper.appendCDataNode(document, node_xml, "Format", wxMessage.getVoice_Format());
			if (wxMessage.getVoice_Recognition() != null) {
				XmlHelper.appendCDataNode(document, node_xml, "Recognition", wxMessage.getVoice_Recognition());
			}
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			wxMessage.setVoice_MediaId(XmlHelper.getElementByName(node_xml, "MediaId").getTextContent());
			wxMessage.setVoice_Format(XmlHelper.getElementByName(node_xml, "Format").getTextContent());
			wxMessage.setMsgId(XmlHelper.getElementByName(node_xml, "MsgId").getTextContent());
			try {
				wxMessage.setVoice_Recognition(XmlHelper.getElementByName(node_xml, "Recognition").getTextContent());
			} catch (Exception e){}
		}
	}),	// 语音消息
	
	video("视频消息", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage, Document document, Element node_xml) {
			XmlHelper.appendCDataNode(document, node_xml, "MsgId", wxMessage.getMsgId());
			XmlHelper.appendCDataNode(document, node_xml, "MediaId", wxMessage.getVideo_MediaId());
			XmlHelper.appendCDataNode(document, node_xml, "ThumbMediaId", wxMessage.getVideo_ThumbMediaId());
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			wxMessage.setVideo_MediaId(XmlHelper.getElementByName(node_xml, "MediaId").getTextContent());
			wxMessage.setVideo_ThumbMediaId(XmlHelper.getElementByName(node_xml, "ThumbMediaId").getTextContent());
			wxMessage.setMsgId(XmlHelper.getElementByName(node_xml, "MsgId").getTextContent());
		}
	}),	// 视频消息
	
	location("地理位置消息", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage, Document document, Element node_xml) {
			XmlHelper.appendCDataNode(document, node_xml, "MsgId", wxMessage.getMsgId());
			XmlHelper.appendCDataNode(document, node_xml, "Location_X", wxMessage.getLocation_X());
			XmlHelper.appendCDataNode(document, node_xml, "Location_Y", wxMessage.getLocation_Y());
			XmlHelper.appendCDataNode(document, node_xml, "Scale", wxMessage.getLocation_scale());
			XmlHelper.appendCDataNode(document, node_xml, "Label", wxMessage.getLocation_label());
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			wxMessage.setLocation_X(XmlHelper.getElementByName(node_xml, "Location_X").getTextContent());
			wxMessage.setLocation_Y(XmlHelper.getElementByName(node_xml, "Location_Y").getTextContent());
			wxMessage.setLocation_scale(XmlHelper.getElementByName(node_xml, "Scale").getTextContent());
			wxMessage.setLocation_label(XmlHelper.getElementByName(node_xml, "Label").getTextContent());
			wxMessage.setMsgId(XmlHelper.getElementByName(node_xml, "MsgId").getTextContent());
		}
	}),	// 地理位置消息
	
	link("链接消息", new WxMessageTypeOpera() {
		@Override
		public void writeMoreXmlElements(WxMessage wxMessage, Document document, Element node_xml) {
			XmlHelper.appendCDataNode(document, node_xml, "MsgId", wxMessage.getMsgId());
			XmlHelper.appendCDataNode(document, node_xml, "Description", wxMessage.getLink_description());
			XmlHelper.appendCDataNode(document, node_xml, "Title", wxMessage.getLink_title());
			XmlHelper.appendCDataNode(document, node_xml, "Url", wxMessage.getLink_url());
		}
		@Override
		public void readMoreProperties(WxMessage wxMessage, Document document,
				Element node_xml) {
			wxMessage.setLink_description(XmlHelper.getElementByName(node_xml, "Description").getTextContent());
			wxMessage.setLink_title(XmlHelper.getElementByName(node_xml, "Title").getTextContent());
			wxMessage.setLink_url(XmlHelper.getElementByName(node_xml, "Url").getTextContent());
			wxMessage.setMsgId(XmlHelper.getElementByName(node_xml, "MsgId").getTextContent());
		}
	});	// 链接消息
	
	
	WxMessageType(String display, WxMessageTypeOpera wxMessageTypeOpera) {
		this.display = display;
		this.wxMessageTypeOpera = wxMessageTypeOpera;
	}
	
	/**
	 * 类型操作
	 */
	private WxMessageTypeOpera wxMessageTypeOpera;
	
	/**
	 * 显示名称
	 */
	private String display;

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



