package module.wxService;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Element;

import module.wxService.service.WxMsgAccept;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;

/**
 * 取消关注，自动解除绑定
 * 
 * @author 石莹 @ caituo
 *
 */
public class UnSubscribe implements WxMsgAccept {

	@Override
	public WxSendMsg accept(WxRecvMsg msg, WxSendMsg sendMsg,
			HttpServletRequest request, Element configNode) {
		// TODO Auto-generated method stub
		return null;
	}

}
