package module.wxService.model;

import java.io.Serializable;

import module.wxService.service.WxMessageConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 微信请求消息
 * 
 * @author shiying
 *
 */
public class WxMessage implements Serializable {
	private static final Log log = LogFactory.getLog(WxMessage.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -5091711393883270383L;
	
	
	/**
	 * 接受消息的目标ID
	 */
	private String ToUserName;
	
	
	/**
	 * 消息的来源ID
	 */
	private String FromUserName;
	
	
	/**
	 * 消息创建时间 （整型）
	 */
	private int CreateTime;
	
	
	/**
	 * 消息ID
	 * 如果是事件消息，该字段为FromUserName + CreateTime 
	 * 如果是非事件消息，该字段值来自微信服务器
	 */
	private String msgId;
	
	
	/**
	 * 消息类型
	 */
	private WxMessageType MsgType;
	
	
	
	
	/**
	 * 文本消息内容
	 */
	private String text_Content;
	
	
	/**
	 * 图片URL地址
	 */
	private String image_PicUrl;
	
	
	/**
	 * 图片MediaId
	 */
	private String image_MediaId;
	
	
	
	/**
	 * 语音MediaId
	 */
	private String voice_MediaId;
	
	
	/**
	 * 语音格式，如amr，speex等
	 */
	private String voice_Format;
	
	
	/**
	 * 语音接口识别结果
	 */
	private String voice_Recognition;
	
	
	
	/**
	 * 视频的MediaId
	 */
	private String video_MediaId;
	
	
	/**
	 * 图片缩略图MediaId
	 */
	private String video_ThumbMediaId;
	
	
	
	/**
	 * 地理位置维度
	 */
	private String location_X;
	

	/**
	 * 地理位置经度
	 */
	private String location_Y;
	
	
	/**
	 * 地图缩放大小
	 */
	private String location_scale;
	
	
	/**
	 * 地理位置标签
	 */
	private String location_label;
	
	
	
	/**
	 * 链接标题
	 */
	private String link_title;
	
	
	/**
	 * 链接详情
	 */
	private String link_description;
	
	
	/**
	 * 链接URL地址
	 */
	private String link_url;
	
	
	
	
	
	
	/**
	 * 事件类型
	 */
	private WxMessagePushEvent event;
	
	
	/**
	 * （未关注用户）扫描带参数二维码时，二维码所带有的key
	 */
	private String subscribe_qrscene_key;
	
	
	/**
	 * （未关注用户）二维码ticket
	 */
	private String subscribe_qrscene_ticket;
	
	
	
	
	/**
	 * （关注用户）扫描带参数二维码时，二维码所带有的key
	 */
	private String scan_qrscene_key;
	
	
	/**
	 * （关注用户）二维码ticket
	 */
	private String scan_qrscene_ticket;
	
	
	
	
	/**
	 * 地理位置纬度
	 */
	private String location_latitude;
	
	
	/**
	 * 地理位置经度
	 */
	private String location_longitude;
	
	
	/**
	 * 定位精确度
	 */
	private String location_precision;
	
	
	
	
	
	/**
	 * 点击菜单Key
	 */
	private String click_key;
	
	
	
	
	
	/**
	 * 点击菜单URL地址
	 */
	private String view_url;





	public String getToUserName() {
		return ToUserName;
	}





	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}





	public String getFromUserName() {
		return FromUserName;
	}





	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}





	public int getCreateTime() {
		return CreateTime;
	}





	public void setCreateTime(int createTime) {
		CreateTime = createTime;
	}





	public String getMsgId() {
		return msgId;
	}





	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}





	public WxMessageType getMsgType() {
		return MsgType;
	}





	public void setMsgType(WxMessageType msgType) {
		MsgType = msgType;
	}





	public String getText_Content() {
		return text_Content;
	}





	public void setText_Content(String text_Content) {
		this.text_Content = text_Content;
	}





	public String getImage_PicUrl() {
		return image_PicUrl;
	}





	public void setImage_PicUrl(String image_PicUrl) {
		this.image_PicUrl = image_PicUrl;
	}





	public String getImage_MediaId() {
		return image_MediaId;
	}





	public void setImage_MediaId(String image_MediaId) {
		this.image_MediaId = image_MediaId;
	}





	public String getVoice_MediaId() {
		return voice_MediaId;
	}





	public void setVoice_MediaId(String voice_MediaId) {
		this.voice_MediaId = voice_MediaId;
	}





	public String getVoice_Format() {
		return voice_Format;
	}





	public void setVoice_Format(String voice_Format) {
		this.voice_Format = voice_Format;
	}





	public String getVoice_Recognition() {
		return voice_Recognition;
	}





	public void setVoice_Recognition(String voice_Recognition) {
		this.voice_Recognition = voice_Recognition;
	}





	public String getVideo_MediaId() {
		return video_MediaId;
	}





	public void setVideo_MediaId(String video_MediaId) {
		this.video_MediaId = video_MediaId;
	}





	public String getVideo_ThumbMediaId() {
		return video_ThumbMediaId;
	}





	public void setVideo_ThumbMediaId(String video_ThumbMediaId) {
		this.video_ThumbMediaId = video_ThumbMediaId;
	}





	public String getLocation_X() {
		return location_X;
	}





	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}





	public String getLocation_Y() {
		return location_Y;
	}





	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}





	public String getLocation_scale() {
		return location_scale;
	}





	public void setLocation_scale(String location_scale) {
		this.location_scale = location_scale;
	}





	public String getLocation_label() {
		return location_label;
	}





	public void setLocation_label(String location_label) {
		this.location_label = location_label;
	}





	public String getLink_title() {
		return link_title;
	}





	public void setLink_title(String link_title) {
		this.link_title = link_title;
	}





	public String getLink_description() {
		return link_description;
	}





	public void setLink_description(String link_description) {
		this.link_description = link_description;
	}





	public String getLink_url() {
		return link_url;
	}





	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}





	public WxMessagePushEvent getEvent() {
		return event;
	}





	public void setEvent(WxMessagePushEvent event) {
		this.event = event;
	}





	public String getSubscribe_qrscene_key() {
		return subscribe_qrscene_key;
	}





	public void setSubscribe_qrscene_key(String subscribe_qrscene_key) {
		this.subscribe_qrscene_key = subscribe_qrscene_key;
	}





	public String getSubscribe_qrscene_ticket() {
		return subscribe_qrscene_ticket;
	}





	public void setSubscribe_qrscene_ticket(String subscribe_qrscene_ticket) {
		this.subscribe_qrscene_ticket = subscribe_qrscene_ticket;
	}





	public String getScan_qrscene_key() {
		return scan_qrscene_key;
	}





	public void setScan_qrscene_key(String scan_qrscene_key) {
		this.scan_qrscene_key = scan_qrscene_key;
	}





	public String getScan_qrscene_ticket() {
		return scan_qrscene_ticket;
	}





	public void setScan_qrscene_ticket(String scan_qrscene_ticket) {
		this.scan_qrscene_ticket = scan_qrscene_ticket;
	}





	public String getLocation_latitude() {
		return location_latitude;
	}





	public void setLocation_latitude(String location_latitude) {
		this.location_latitude = location_latitude;
	}





	public String getLocation_longitude() {
		return location_longitude;
	}





	public void setLocation_longitude(String location_longitude) {
		this.location_longitude = location_longitude;
	}





	public String getLocation_precision() {
		return location_precision;
	}





	public void setLocation_precision(String location_precision) {
		this.location_precision = location_precision;
	}





	public String getClick_key() {
		return click_key;
	}





	public void setClick_key(String click_key) {
		this.click_key = click_key;
	}





	public String getView_url() {
		return view_url;
	}





	public void setView_url(String view_url) {
		this.view_url = view_url;
	}





	@Override
	public String toString() {
		try {
			return WxMessageConverter.convertMessageToXml(this);
		} catch (Exception e) {
			log.warn("convert wx message exception.", e);
			return "convert exception! ["+e.getMessage()+"]";
		}
	}
	
	
	
	
	
	
	
	

}





