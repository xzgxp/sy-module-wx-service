package module.wxService.vo.send;

import org.dom4j.Document;

public class WxSendTextMsg extends WxSendMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7515410294939658873L;
	private String content;
	
	public WxSendTextMsg(WxSendMsg msg,String content) {
		super(msg);
		setMsgType("text");
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		createElement(doc.getRootElement(), "Content", getContent());
		return doc;
	}
}
