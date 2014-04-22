package module.wxService.simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import module.wxService.model.WxMessage;
import module.wxService.service.WxMessageResolver;

public class TestWxMessageResolver {
	
	public void testSubscribe() throws IOException {
		WxMessage xm = WxMessageResolver.getInstance().resolveMessage(
				TestWxMessageResolver.class.getClassLoader().getResourceAsStream("message/scan.message"));
		System.out.println(new ObjectMapper().writeValueAsString(xm));
	}

	@Test
	public void test() throws FileNotFoundException, IOException {
		File path = new File(URLDecoder.decode(TestWxMessageResolver.class.getClassLoader().getResource("message/scan.message").getFile(), "UTF-8")).getParentFile();
		for (File f : path.listFiles()) {
			WxMessage xm = WxMessageResolver.getInstance().resolveMessage(
					new FileInputStream(f));
			System.out.println(new ObjectMapper().writeValueAsString(xm));
			System.out.println(xm.toString());
			System.out.println("===========================================================");
		}
	}

}
