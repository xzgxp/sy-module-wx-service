package module.wxService.service;

import java.util.Date;
import java.util.Random;

import module.wxService.model.WxMessage;
import module.wxService.model.WxMessageType;

/**
 * 微信消息构建器
 * 
 * @author shiying
 *
 */
public class WxMessageBuilder {
	
	private String toUserName;
	private String fromUserName;
	
	/**
	 * 初始化一个消息构建器
	 * @param ToUserName
	 * @param FromUserName
	 */
	public WxMessageBuilder(String ToUserName, String FromUserName) {
		this.toUserName = ToUserName;
		this.fromUserName = FromUserName;
	}
	
	/**
	 * 创建基础消息
	 * @return
	 */
	private WxMessage buildBaseMessage(WxMessageType msgType) {
		WxMessage wxMessage = new WxMessage();
		wxMessage.setToUserName(toUserName);
		wxMessage.setFromUserName(fromUserName);
		wxMessage.setCreateTime((int) new Date().getTime());
		wxMessage.setMsgType(msgType);
		return wxMessage;
	}
	
	/**
	 * 构建一个文本消息
	 * @return
	 */
	public WxMessage buildTextMessage(String text_Content) {
		WxMessage wxMessage = buildBaseMessage(WxMessageType.text);
		wxMessage.setText_Content(text_Content);
		wxMessage.setMsgId("simulator_" + new Date().getTime() + "_" + new Random().nextInt(1000));
		return wxMessage;
	}
	
	
	

}
