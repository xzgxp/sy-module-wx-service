package module.wxService.vo.recv;


public class WxRecvTextMsg extends WxRecvMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6836881499282054929L;
	private String content;
	
	public WxRecvTextMsg(WxRecvMsg msg,String content) {
		super(msg);
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
