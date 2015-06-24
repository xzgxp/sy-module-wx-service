package module.wxService.vo.send;

import org.dom4j.Document;

public class WxSendPicMsg extends WxSendMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7515410294939658873L;
	private String mediaId;
	
	public WxSendPicMsg(WxSendMsg msg,String mediaId) {
		super(msg);
		setMsgType("image");
		this.mediaId = mediaId;
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		doc.getRootElement().addElement("Image").addElement("MediaId").setText(getMediaId());
		return doc;
	}
}
