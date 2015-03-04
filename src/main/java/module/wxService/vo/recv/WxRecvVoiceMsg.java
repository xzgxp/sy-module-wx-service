package module.wxService.vo.recv;

/**
 * 语音消息
 * @author 石莹
 *
 */
public class WxRecvVoiceMsg extends WxRecvMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4823303193006072004L;
	private String mediaId;
	private String format;
	private String recognition;
	
	public WxRecvVoiceMsg(WxRecvMsg msg, String mediaId, String format, String recognition) {
		super(msg);
		this.mediaId = mediaId;
		this.format = format;
		this.recognition = recognition;
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
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	
	
}
