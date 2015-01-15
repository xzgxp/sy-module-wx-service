package module.wxService;

import java.util.Date;
import java.util.UUID;

import module.wxService.service.WxJsSdkKit;
import module.wxService.service.apisupport.JsApiTicketService;

import org.junit.Test;

public class TestWxJsSdkKit {
	
	@Test
	public void test() throws Exception {
		String appid = "wx5641c2474b7e5ab7";
		String secret = "d0f06e3e128cba96f1f0b7240ac24200";
		
		String noncestr = UUID.randomUUID().toString();
		String jsapi_ticket = JsApiTicketService.requestJsApiTicket(appid, secret);
		String timestamp = ""+new Date().getTime()/1000;
		String url = "http://www.baidu.com";
		System.out.println("noncestr="+noncestr);
		System.out.println("jsapi_ticket="+jsapi_ticket);
		System.out.println("timestamp="+timestamp);
		System.out.println("url="+url);
		System.out.println("sign="+WxJsSdkKit.generationSign(noncestr, jsapi_ticket, timestamp, url));
	}

}
