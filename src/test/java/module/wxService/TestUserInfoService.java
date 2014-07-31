package module.wxService;

import java.util.Map;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import module.wxService.service.apisupport.UserInfoService;

public class TestUserInfoService {
	private static final Log log = LogFactory.getLog(TestUserInfoService.class);
	
	@Test
	public void test() {
		// 关注用户
		Map<String, Object> obj = UserInfoService.pullUserInfo("oRVwHuIA8N8MaHb_12K3HCNmOs9E");
		log.info(obj);
		Assert.assertTrue(UserInfoService.isSubscribe("oRVwHuIA8N8MaHb_12K3HCNmOs9E"));
		// 未关注用户
		Map<String, Object> obj2 = UserInfoService.pullUserInfo("oRVwHuFBzp7nhSf9neYq2C4h61Fo");
		log.info(obj2);
		Assert.assertFalse(UserInfoService.isSubscribe("oRVwHuFBzp7nhSf9neYq2C4h61Fo"));
	}

}
