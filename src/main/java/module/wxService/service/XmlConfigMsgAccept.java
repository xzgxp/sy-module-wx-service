package module.wxService.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

import sy.module.core.mvc.ModuleCoreFilter;
import sy.module.core.util.string.StringTemplateKit;
import module.wxService.event.WxAcceptEventManager;
import module.wxService.security.WxOpenIdKit;
import module.wxService.vo.recv.WxRecvMsg;
import module.wxService.vo.send.WxSendMsg;
import module.wxService.vo.send.WxSendMusicMsg;
import module.wxService.vo.send.WxSendNewsMsg;
import module.wxService.vo.send.WxSendTextMsg;

/**
 * 基于XML配置的消息受理器
 * 
 * @author 石莹 @ caituo
 *
 */
public class XmlConfigMsgAccept implements WxMsgAccept {
	private static final Log log = LogFactory.getLog(XmlConfigMsgAccept.class);
	
	/**
	 * 准备数据
	 * @param msg
	 * @param sendMsg
	 * @param request
	 * @param configNode
	 * @return template date
	 */
	private Map<String, Object> loadData(WxRecvMsg msg, WxSendMsg sendMsg,
			HttpServletRequest request, Element configNode) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("wx_openid", msg.getFromUser());
		data.put("wx_openid_sign", WxOpenIdKit.getOpenidSign(msg.getFromUser()));
		data.put("url_basepath", ModuleCoreFilter.basePath);
		WxAcceptEventManager.getWxAcceptEventInstance().xmlMessageRecvTemplateDate(data);
		return data;
	}

	@Override 
	public WxSendMsg accept(WxRecvMsg msg, WxSendMsg sendMsg,
			HttpServletRequest request, Element configNode) {
		try {
			Element answer = configNode.element("answer");
			MsgType msgtype = null;
			try {
				msgtype = MsgType.valueOf(answer.attributeValue("type"));
			} catch (Exception e) {
				log.warn("msg type convert faild.", e);
				return null;
			}
			// Freemarker模板
			StringTemplateKit stringTemplateKit = new StringTemplateKit();
			Map<String, Object> data = loadData(msg, sendMsg, request, configNode);
			// 判断类型
			switch (msgtype) {
			case text :
				return new WxSendTextMsg(sendMsg, stringTemplateKit.mergerString(answer.getText(), data));
			case music :
				return new WxSendMusicMsg(
						sendMsg, 
						stringTemplateKit.mergerString(answer.elementText("title"), data), 
						stringTemplateKit.mergerString(answer.elementText("description"), data), 
						stringTemplateKit.mergerString(answer.elementText("musicUrl"), data), 
						stringTemplateKit.mergerString(answer.elementText("hqMusicUrl"), data));
			case news :
				if (answer.element("item") == null) {
					return null;
				}
				WxSendNewsMsg newsMsg = new WxSendNewsMsg(sendMsg);
				@SuppressWarnings("unchecked")
				Iterator<Element> items = (Iterator<Element>) answer.elementIterator("item");
				while (items.hasNext()) {
					Element item = items.next();
					newsMsg.addItem(
							stringTemplateKit.mergerString(item.elementText("title"), data), 
							stringTemplateKit.mergerString(item.elementText("description"), data), 
							stringTemplateKit.mergerString(item.elementText("imageUrl"), data), 
							stringTemplateKit.mergerString(item.elementText("linkUrl"), data));
				}
				return newsMsg;
			default:
				log.warn("unknown msg type : " + msgtype);
				return null;
			}
		} catch (Exception e) {
			log.warn("xml config load faild.", e);
			return null;
		}
	}

}
