package module.wxService.service.apisupport;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import sy.module.core.util.string.StringTemplateKit;

/**
 * 客服接口支持
 * 
 * @author 石莹 @ caituo
 *
 */
public class ServicePushKit {
	
	/**
	 * 图文消息推送
	 * @param openid
	 * @param appid
	 * @param secret
	 * @param items
	 * @throws Exception
	 */
	public static void pushNewsMessage(
			String openid, String appid, String secret, 
			NewsItem ... items
	) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("openid", openid);
		data.put("items", items);
		String body = treatmentTemplate("customer_service_push_news.ftl", data);
		postMessage(body, appid, secret);
	}
	
	
	/**
	 * 视频消息推送
	 * @param openid
	 * @param musicurl
	 * @param hqmusicurl
	 * @param thumb_media_id
	 * @param title
	 * @param description
	 * @param appid
	 * @param secret
	 * @throws Exception
	 */
	public static void pushMusicMessage(
			String openid, 
			String musicurl, String hqmusicurl, String thumb_media_id,
			String title, String description,
			String appid, String secret
	) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("openid", openid);
		data.put("title", title);
		data.put("description", description);
		data.put("musicurl", musicurl);
		data.put("hqmusicurl", hqmusicurl);
		data.put("thumb_media_id", thumb_media_id);
		String body = treatmentTemplate("customer_service_push_music.ftl", data);
		postMessage(body, appid, secret);
	}
	
	
	/**
	 * 视频消息推送
	 * @param openid
	 * @param mediaid
	 * @param title
	 * @param description
	 * @param appid
	 * @param secret
	 * @throws Exception
	 */
	public static void pushVideoMessage(
			String openid, 
			String mediaid, String title, String description,
			String appid, String secret
	) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("openid", openid);
		data.put("mediaid", mediaid);
		data.put("title", title);
		data.put("description", description);
		String body = treatmentTemplate("customer_service_push_video.ftl", data);
		postMessage(body, appid, secret);
	}
	
	
	/**
	 * 语音消息推送
	 * @param openid
	 * @param mediaid
	 * @param appid
	 * @param secret
	 * @throws Exception
	 */
	public static void pushVoiceMessage(
			String openid, String mediaid, String appid, String secret
	) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("openid", openid);
		data.put("mediaid", mediaid);
		String body = treatmentTemplate("customer_service_push_voice.ftl", data);
		postMessage(body, appid, secret);
	}
	
	
	/**
	 * 图片消息推送
	 * @param openid
	 * @param mediaid
	 * @param appid
	 * @param secret
	 * @throws Exception
	 */
	public static void pushPictureMessage(
			String openid, String mediaid, String appid, String secret
	) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("openid", openid);
		data.put("mediaid", mediaid);
		String body = treatmentTemplate("customer_service_push_pic.ftl", data);
		postMessage(body, appid, secret);
	}
	
	
	/**
	 * 文本消息推送
	 * @param openid
	 * @param content
	 * @param appid
	 * @param secret
	 * @throws Exception
	 */
	public static void pushTextMessage(
			String openid, String content, String appid, String secret
	) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("openid", openid);
		data.put("content", content);
		String body = treatmentTemplate("customer_service_push_text.ftl", data);
		postMessage(body, appid, secret);
	}
	
	/**
	 * 提交消息
	 * @param xml
	 * @param appid
	 * @param secret
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private static void postMessage(String xml, String appid, String secret) throws Exception {
		//
		String accessToken = AccessTokenService.requestWxAccessToken(appid, secret);
		if (accessToken == null || accessToken.equals("")) {
			throw new Exception("request access token faild.");
		}
		//
		HttpClient http = new HttpClient();
		PostMethod post = new PostMethod("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken);
		try {
			post.addRequestHeader("Content-Type", "text/html;charset=UTF-8");
			post.setRequestBody(xml);
			http.executeMethod(post);
			if (post.getStatusCode() == 200) {
				String result = new String(post.getResponseBody(), "UTF-8");
				// 分析结果
				Pattern pattern = Pattern.compile("\\\"errcode\\\":\\s*([^,]*),");
				Matcher matcher = pattern.matcher(result);
				if (matcher.find()) {
					if (matcher.groupCount() >=1 && matcher.group(1).equals("0")) {
						return;
					}
				}
				throw new Exception("error weixin push status : " + result);
			} else {
				throw new Exception("faild http status code : " + post.getStatusCode());
			}
		} finally {
			post.releaseConnection();
			((SimpleHttpConnectionManager) http.getHttpConnectionManager()).shutdown();
		}
	}
	
	/**
	 * 处理模板
	 * @param templateName
	 * @param data
	 * @return rs
	 * @throws Exception
	 */
	private static String treatmentTemplate(
			String templateName, Map<String, Object> data
	) throws Exception {
		InputStream is = ServicePushKit.class.getClassLoader().getResourceAsStream(
				"module/wxService/service/apisupport/"+templateName);
		if (is == null) {
			throw new Exception("template name load faild : " + templateName);
		}
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(is));
			String content = "";
			String line = null;
			while ((line = br.readLine()) != null) {
				content += line;
				content += "\n";
			}
			return new StringTemplateKit().mergerString(content, data);
		} finally {
			is.close();
		}
	}



	public static class NewsItem {
		private String title;
		private String description;
		private String url;
		private String picurl;
		public NewsItem(String title, String description, String url,
				String picurl) {
			super();
			this.title = title;
			this.description = description;
			this.url = url;
			this.picurl = picurl;
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
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getPicurl() {
			return picurl;
		}
		public void setPicurl(String picurl) {
			this.picurl = picurl;
		}
	}
}
