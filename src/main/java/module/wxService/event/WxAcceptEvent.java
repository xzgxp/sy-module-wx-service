package module.wxService.event;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import module.wxService.service.AcceptNode;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;

import org.dom4j.Element;

/**
 * 微信消息受理事件
 * 
 * @author 石莹 @ caituo
 *
 */
public interface WxAcceptEvent {
	
	/**
	 * 接收到消息请求
	 * @param xmlmsg
	 */
	public void onRevcMsg(String xmlmsg, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 解析XML完成
	 * @param root
	 */
	public void onResolveXmlElement(Element root);
	
	/**
	 * 解析消息对象完成
	 * @param revcMsg
	 */
	public void onRevoleRevcObject(WxRecvMsg revcMsg);
	
	/**
	 * 获得消息受理对象
	 * @param cls
	 */
	public void findAcceptClass(AcceptNode accept, Element subElement);
	
	/**
	 * 消息受理对象 完成消息受理
	 * @param msg
	 * @param sendMsg
	 * @param accept
	 * @param subElement
	 */
	public void onComplateAccept(WxRecvMsg msg, WxSendMsg sendMsg, AcceptNode accept);
	
	/**
	 * 完成发送消息的转换Dom
	 * @param root
	 */
	public void onConvertSendMsgToXmlElement(Element root);
	
	/**
	 * 完成发送消息转换为字符串
	 * @param xmlstring
	 */
	public void onComplateSendMsg(String xmlstring);
	
	/**
	 * 消息处理异常
	 * @param e
	 */
	public void exception(Throwable e);
	
	/**
	 * XML 消息回复模板数据
	 * @param data
	 */
	public void xmlMessageRecvTemplateDate(Map<String, Object> data);
	

}
