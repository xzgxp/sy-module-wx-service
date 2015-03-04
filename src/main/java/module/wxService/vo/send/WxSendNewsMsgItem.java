package module.wxService.vo.send;

import java.io.Serializable;


public class WxSendNewsMsgItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6727971658240681026L;
	private String title;
	private String description;
	private String picUrl;
	private String url;
	public WxSendNewsMsgItem(String title, String description, String picUrl, String url) {
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
