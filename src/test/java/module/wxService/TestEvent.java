package module.wxService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;

import module.wxService.event.WxAcceptEvent;
import module.wxService.service.AcceptNode;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;

public class TestEvent implements WxAcceptEvent {

	@Override
	public void onRevcMsg(String xmlmsg, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResolveXmlElement(Element root) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRevoleRevcObject(WxRecvMsg revcMsg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findAcceptClass(AcceptNode accept, Element subElement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComplateAccept(WxRecvMsg msg, WxSendMsg sendMsg,
			AcceptNode accept) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConvertSendMsgToXmlElement(Element root) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComplateSendMsg(String xmlstring) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exception(Throwable e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void xmlMessageRecvTemplateDate(Map<String, Object> data) {
		// TODO Auto-generated method stub
		
	}

	
}
