package module.wxService;

import org.junit.Assert;
import org.junit.Test;

import module.wxService.service.apisupport.JsApiTicketService;

public class TestJsApiTicketService {
	
	@Test
	public void testJsApiTicketService() {
		String appid = "wx5641c2474b7e5ab7";
		String secret = "d0f06e3e128cba96f1f0b7240ac24200";
		
		// 因为缓存机制，多次读取应该一致
		String ticket = JsApiTicketService.requestJsApiTicket(appid, secret);
		Assert.assertEquals(
				ticket, JsApiTicketService.requestJsApiTicket(appid, secret));
		
		// 令缓存过期
		JsApiTicketService.setTimeout(appid);
		
		String ticket2 = JsApiTicketService.requestJsApiTicket(appid, secret);
		
		Assert.assertNotSame(ticket, ticket2);
		
		Assert.assertEquals(ticket2, JsApiTicketService.requestJsApiTicket(appid, secret));
	}

}
