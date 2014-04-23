package module.wxService;

import module.wxService.security.WxOpenIdKit;

import org.junit.Assert;
import org.junit.Test;

public class TestUrlCodeKit {
	
	@Test
	public void test() throws InterruptedException {
		String openid = "testopenid";
		String sign = WxOpenIdKit.getOpenidSign(openid);
		// 验证通过
		Assert.assertTrue(WxOpenIdKit.checkOpenIdSign(openid, sign));
		// 参考src/test/resources/configs/config.properties，两秒后过期
		Thread.sleep(2000);
		Assert.assertFalse(WxOpenIdKit.checkOpenIdSign(openid, sign));
	}

}
