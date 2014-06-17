package module.wxService;

import org.junit.Assert;
import org.junit.Test;

import module.wxService.service.apisupport.AccessTokenService;

public class TestAccessTokenService {
	
	@Test
	public void testAccessTokenService() {
		String appid = "wx5641c2474b7e5ab7";
		String secret = "d0f06e3e128cba96f1f0b7240ac24200";
		
		// 因为缓存机制，多次读取应该一致
		String token = AccessTokenService.requestWxAccessToken(appid, secret);
		Assert.assertEquals(
				token, AccessTokenService.requestWxAccessToken(appid, secret));
		
		// 令缓存过期
		AccessTokenService.setTimeout(appid);
		
		String token2 = AccessTokenService.requestWxAccessToken(appid, secret);
		
		Assert.assertNotSame(token, token2);
		
		Assert.assertEquals(token2, AccessTokenService.requestWxAccessToken(appid, secret));
	}

}
