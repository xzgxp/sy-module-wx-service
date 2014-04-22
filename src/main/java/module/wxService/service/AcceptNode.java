package module.wxService.service;

import org.dom4j.Element;

/**
 * 受理文件描述
 * 
 * @author 石莹 @ caituo
 *
 */
public class AcceptNode {
	
	private WxMsgAccept reflectInter;
	
	private Element subElement;

	public WxMsgAccept getReflectInter() {
		return reflectInter;
	}

	public void setReflectInter(WxMsgAccept reflectInter) {
		this.reflectInter = reflectInter;
	}

	public Element getSubElement() {
		return subElement;
	}

	public void setSubElement(Element subElement) {
		this.subElement = subElement;
	}
	
	

}
