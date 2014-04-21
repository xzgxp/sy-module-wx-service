package module.wxService.model;

import java.io.Serializable;

import module.wxService.service.WxMessageConverter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ΢��������Ϣ
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
	 * ������Ϣ��Ŀ��ID
	 */
	private String ToUserName;
	
	
	/**
	 * ��Ϣ����ԴID
	 */
	private String FromUserName;
	
	
	/**
	 * ��Ϣ����ʱ�� �����ͣ�
	 */
	private int CreateTime;
	
	
	/**
	 * ��ϢID
	 * ������¼���Ϣ�����ֶ�ΪFromUserName + CreateTime 
	 * ����Ƿ��¼���Ϣ�����ֶ�ֵ����΢�ŷ�����
	 */
	private String msgId;
	
	
	/**
	 * ��Ϣ����
	 */
	private WxMessageType MsgType;
	
	
	
	
	/**
	 * �ı���Ϣ����
	 */
	private String text_Content;
	
	
	/**
	 * ͼƬURL��ַ
	 */
	private String image_PicUrl;
	
	
	/**
	 * ͼƬMediaId
	 */
	private String image_MediaId;
	
	
	
	/**
	 * ����MediaId
	 */
	private String voice_MediaId;
	
	
	/**
	 * ������ʽ����amr��speex��
	 */
	private String voice_Format;
	
	
	/**
	 * �����ӿ�ʶ����
	 */
	private String voice_Recognition;
	
	
	
	/**
	 * ��Ƶ��MediaId
	 */
	private String video_MediaId;
	
	
	/**
	 * ͼƬ����ͼMediaId
	 */
	private String video_ThumbMediaId;
	
	
	
	/**
	 * ����λ��ά��
	 */
	private String location_X;
	

	/**
	 * ����λ�þ���
	 */
	private String location_Y;
	
	
	/**
	 * ��ͼ���Ŵ�С
	 */
	private String location_scale;
	
	
	/**
	 * ����λ�ñ�ǩ
	 */
	private String location_label;
	
	
	
	/**
	 * ���ӱ���
	 */
	private String link_title;
	
	
	/**
	 * ��������
	 */
	private String link_description;
	
	
	/**
	 * ����URL��ַ
	 */
	private String link_url;
	
	
	
	
	
	
	/**
	 * �¼�����
	 */
	private WxMessagePushEvent event;
	
	
	/**
	 * ��δ��ע�û���ɨ���������ά��ʱ����ά�������е�key
	 */
	private String subscribe_qrscene_key;
	
	
	/**
	 * ��δ��ע�û�����ά��ticket
	 */
	private String subscribe_qrscene_ticket;
	
	
	
	
	/**
	 * ����ע�û���ɨ���������ά��ʱ����ά�������е�key
	 */
	private String scan_qrscene_key;
	
	
	/**
	 * ����ע�û�����ά��ticket
	 */
	private String scan_qrscene_ticket;
	
	
	
	
	/**
	 * ����λ��γ��
	 */
	private String location_latitude;
	
	
	/**
	 * ����λ�þ���
	 */
	private String location_longitude;
	
	
	/**
	 * ��λ��ȷ��
	 */
	private String location_precision;
	
	
	
	
	
	/**
	 * ����˵�Key
	 */
	private String click_key;
	
	
	
	
	
	/**
	 * ����˵�URL��ַ
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





