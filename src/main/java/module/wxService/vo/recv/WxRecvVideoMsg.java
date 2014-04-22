package module.wxService.vo.recv;

/**
 * 视频消息
 * @author 石莹
 *
 */
public class WxRecvVideoMsg extends WxRecvMsg {
	
	private String mediaId;
	private String format;
	
	public WxRecvVideoMsg(WxRecvMsg msg, String mediaId, String format) {
		super(msg);
		this.mediaId = mediaId;
		this.format = format;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	
}
