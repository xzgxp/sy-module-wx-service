package module.wxService.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.wxService.service.AcceptNode;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;

import org.dom4j.Element;

/**
 * 事件管理器
 * 
 * @author 石莹 @ caituo
 *
 */
public class WxAcceptEventManager implements WxAcceptEvent {
	
	private static final List<WxAcceptEvent> events = new ArrayList<WxAcceptEvent>();
	
	private WxAcceptEventManager() {}
	
	/**
	 * 获得事件处理对象
	 * @return
	 */
	public static WxAcceptEvent getWxAcceptEventInstance() {
		return new WxAcceptEventManager();
	}
	
	/**
	 * 将一个事件添加到事件管理器
	 * @param evnet
	 */
	public static void addWxAcceptEvent(WxAcceptEvent evnet) {
		// 避免重复添加
		for (WxAcceptEvent e : events) {
			if (e.getClass().equals(evnet.getClass())) {
				return;
			}
		}
		events.add(evnet);
	}

	@Override
	public void onRevcMsg(String xmlmsg, HttpServletRequest request,
			HttpServletResponse response) {
		for (WxAcceptEvent e : events) {
			e.onRevcMsg(xmlmsg, request, response);
		}
	}

	@Override
	public void onResolveXmlElement(Element root) {
		for (WxAcceptEvent e : events) {
			e.onResolveXmlElement(root);
		}
	}

	@Override
	public void onRevoleRevcObject(WxRecvMsg revcMsg) {
		for (WxAcceptEvent e : events) {
			e.onRevoleRevcObject(revcMsg);
		}
	}

	@Override
	public void findAcceptClass(AcceptNode accept, Element subElement) {
		for (WxAcceptEvent e : events) {
			e.findAcceptClass(accept, subElement);
		}
	}

	@Override
	public void onComplateAccept(WxRecvMsg msg, WxSendMsg sendMsg,
			AcceptNode accept) {
		for (WxAcceptEvent e : events) {
			e.onComplateAccept(msg, sendMsg, accept);
		}
	}

	@Override
	public void onConvertSendMsgToXmlElement(Element root) {
		for (WxAcceptEvent e : events) {
			e.onConvertSendMsgToXmlElement(root);
		}
	}

	@Override
	public void onComplateSendMsg(String xmlstring) {
		for (WxAcceptEvent e : events) {
			e.onComplateSendMsg(xmlstring);
		}
	}

	@Override
	public void exception(Throwable ex) {
		for (WxAcceptEvent e : events) {
			e.exception(ex);
		}
	}

	@Override
	public void xmlMessageRecvTemplateDate(Map<String, Object> data) {
		for (WxAcceptEvent e : events) {
			e.xmlMessageRecvTemplateDate(data);
		}
	}

}
