package module.wxService.vo.recv;

import module.wxService.vo.WxMsg;

public class WxRecvMsg extends WxMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6587291226251609011L;
	private String msgId;
	
	public WxRecvMsg(String toUser,String fromUser,String createDt,String msgType,String msgId) {
		super(toUser, fromUser, createDt, msgType);
		this.msgId= msgId;
	}
	
	public WxRecvMsg(WxRecvMsg msg) {
		this(msg.getToUser(),msg.getFromUser(),msg.getCreateDt(),msg.getMsgType(),msg.getMsgId());
	}
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
}
