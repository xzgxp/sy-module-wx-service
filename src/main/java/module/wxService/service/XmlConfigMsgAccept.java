package module.wxService.service;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

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
	

	@Override
	public WxSendMsg accept(WxRecvMsg msg, WxSendMsg sendMsg,
			HttpServletRequest request, Element configNode) {
		Element answer = configNode.element("answer");
		MsgType msgtype = null;
		try {
			msgtype = MsgType.valueOf(answer.attributeValue("type"));
		} catch (Exception e) {
			log.warn("msg type convert faild.", e);
			return null;
		}
		// 判断类型
		switch (msgtype) {
		case text :
			return new WxSendTextMsg(sendMsg, answer.getText());
		case music :
			return new WxSendMusicMsg(
					sendMsg, 
					answer.elementTextTrim("title"), 
					answer.elementTextTrim("description"), 
					answer.elementTextTrim("musicUrl"), 
					answer.elementTextTrim("hqMusicUrl"));
		case news :
			if (answer.element("item") == null) {
				return null;
			}
			WxSendNewsMsg newsMsg = new WxSendNewsMsg(sendMsg);
			Iterator<Element> items = (Iterator<Element>) answer.elementIterator("item");
			while (items.hasNext()) {
				Element item = items.next();
				newsMsg.addItem(
						item.elementTextTrim("title"), 
						item.elementTextTrim("description"), 
						item.elementTextTrim("imageUrl"), 
						item.elementTextTrim("linkUrl"));
			}
			return newsMsg;
		default:
			log.warn("unknown msg type : " + msgtype);
			return null;
		}
	}

}
