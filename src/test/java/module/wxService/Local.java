package module.wxService;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Element;

import module.wxService.service.WxMsgAccept;
import module.wxService.vo.recv.WxRecvEventMsg;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;

/**
 * 取消关注，自动解除绑定
 * 
 * @author 石莹 @ caituo
 *
 */
public class Local implements WxMsgAccept {

	@Override
	public WxSendMsg accept(WxRecvMsg msg, WxSendMsg sendMsg,
			HttpServletRequest request, Element configNode) {
		System.out.println(((WxRecvEventMsg) msg).getLatitude());
		System.out.println(((WxRecvEventMsg) msg).getLongitude());
		return null;
	}

}
