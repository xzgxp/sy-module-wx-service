package module.wxService;

import module.wxService.security.UrlCodeKit;

import org.junit.Assert;
import org.junit.Test;

public class TestUrlCodeKit {
	
	@Test
	public void test() throws InterruptedException {
		String openid = "testopenid";
		String sign = UrlCodeKit.getOpenidSign(openid);
		// 验证通过
		Assert.assertTrue(UrlCodeKit.checkOpenIdSign(openid, sign));
		// 参考src/test/resources/configs/config.properties，两秒后过期
		Thread.sleep(2000);
		Assert.assertFalse(UrlCodeKit.checkOpenIdSign(openid, sign));
	}

}
