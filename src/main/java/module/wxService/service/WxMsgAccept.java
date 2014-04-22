package module.wxService.service;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Element;

import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;

public interface WxMsgAccept {

	public WxSendMsg accept(WxRecvMsg msg, WxSendMsg sendMsg, HttpServletRequest request, Element configNode);
	
}
