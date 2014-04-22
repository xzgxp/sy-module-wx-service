package module.wxService.demo.accept;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Element;

import module.wxService.service.WxMsgAccept;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;
import module.wxService.vo.send.WxSendTextMsg;

public class Pic implements WxMsgAccept {

	@Override
	public WxSendMsg accept(WxRecvMsg recvMsg, WxSendMsg sendMsg, HttpServletRequest request, Element configNode) {
		sendMsg = new WxSendTextMsg(sendMsg, "hello, pic");
		return sendMsg;
	}

}
