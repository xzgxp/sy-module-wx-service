package module.wxService;

import java.io.File;
import java.net.URLDecoder;

import org.junit.Test;

import module.wxService.service.apisupport.MediaTransport;
import module.wxService.service.apisupport.MediaType;
import module.wxService.service.apisupport.ServicePushKit;
import module.wxService.service.apisupport.ServicePushKit.NewsItem;

public class TestServicePush {
	
	private String appid = "wx5641c2474b7e5ab7";
	private String secret = "d0f06e3e128cba96f1f0b7240ac24200";
	private String openid = "oRVwHuIA8N8MaHb_12K3HCNmOs9E";
	
	@Test
	public void testText() throws Exception {
		ServicePushKit.pushTextMessage(openid, "中文内容", appid, secret);
	}
	
	@Test
	public void testPicture() throws Exception {
		File file = new File(URLDecoder.decode(
				TestServicePush.class.getClassLoader().getResource("media/dengziqi.jpg"
						).getFile(), "UTF-8"));
		String mediaid = MediaTransport.upload(
				appid, secret, file, MediaType.image);
		ServicePushKit.pushPictureMessage(openid, mediaid, appid, secret);
	}
	
	@Test
	public void testVoice() throws Exception {
		File file = new File(URLDecoder.decode(
				TestServicePush.class.getClassLoader().getResource("media/qgwCpq_1wLcTH6UgJkdk1zWfLEPfP7mV4F_LkV4gcbf0xep6iApkAQb8QSKLAux2.amr"
						).getFile(), "UTF-8"));
		String mediaid = MediaTransport.upload(
				appid, secret, file, MediaType.voice);
		ServicePushKit.pushVoiceMessage(openid, mediaid, appid, secret);
	}
	
	@Test
	public void testVideo() throws Exception {
		File file = new File(URLDecoder.decode(
				TestServicePush.class.getClassLoader().getResource("media/20121003037.mp4"
						).getFile(), "UTF-8"));
		String mediaid = MediaTransport.upload(
				appid, secret, file, MediaType.video);
		ServicePushKit.pushVideoMessage(openid, mediaid, "title", "详细描述信息", appid, secret);
	}
	
	
	@Test
	public void testMusic() throws Exception {
		File file = new File(URLDecoder.decode(
				TestServicePush.class.getClassLoader().getResource("media/dengziqi.jpg"
						).getFile(), "UTF-8"));
		String mediaid = MediaTransport.upload(
				appid, secret, file, MediaType.image);
		ServicePushKit.pushMusicMessage(
				openid, 
				"http://music.meile.com/d05/XL/68/m_xlzj7910ac69_96.mp3", 
				"http://music.meile.com/d05/XL/68/m_xlzj7910ac69_96.mp3", 
				mediaid, "泡沫", "泡沫-邓紫棋", appid, secret);
	}
	
	
	@Test
	public void testNews() throws Exception {
		// 单图文
		ServicePushKit.pushNewsMessage(openid, appid, secret, 
				new NewsItem(
						"t1", 
						"des", 
						"http://www.baidu.com", 
						"http://t1.gstatic.com/images?q=tbn:ANd9GcQ4kG5h2r4ZFHZCapHLKfyfyaJFeWaKwmlHJ9X3cxUsldY5eeokQA"));
		// 多图文
		ServicePushKit.pushNewsMessage(openid, appid, secret, 
				new NewsItem(
						"t1", 
						"des", 
						"http://www.baidu.com", 
						"http://t1.gstatic.com/images?q=tbn:ANd9GcQ4kG5h2r4ZFHZCapHLKfyfyaJFeWaKwmlHJ9X3cxUsldY5eeokQA"),

				new NewsItem(
						"t2", 
						"des", 
						"http://www.baidu.com", 
						"http://t1.gstatic.com/images?q=tbn:ANd9GcQ4kG5h2r4ZFHZCapHLKfyfyaJFeWaKwmlHJ9X3cxUsldY5eeokQA"));
	}

}
