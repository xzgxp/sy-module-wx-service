package module.wxService;

import java.io.File;

import org.junit.Test;

import module.wxService.service.apisupport.MediaTransport;
import module.wxService.service.apisupport.MediaType;

public class TestApi {
	
	@Test
	public void test() throws Exception {
		//
		String appid = "wx5641c2474b7e5ab7";
		String secret = "d0f06e3e128cba96f1f0b7240ac24200";
		String mediaId = "qTvtO2SzbLn_maBcEI7scAMMuiubRTMZwPwx2SfcjN7zEUPlaH3X8IyLc4YcPz-d";
		//
//		File file = MediaTransport.download(
//				"http://mmbiz.qpic.cn/mmbiz/0fEs5XR1q4gibm164Swes1iaR9SqV8wVhURZIJ9BhFWqgpBsLmI4p6FIZHA7jqtaiaLwnf5yNfaxib0DuPpObiaHXicQ/0", 
//				savePath);
////		File file = MediaTransport.download(appid, secret, mediaId, savePath);
//		System.out.println(file);

		File savePath = new File("d:\\");
		File upload = new File("C:\\Users\\Administrator\\Pictures\\布乔天成\\我的微网副本.jpg");
		System.out.println(">> upload : " + upload);
		String mediaid = MediaTransport.upload(appid, secret, upload, MediaType.image);
		System.out.println(">> media : " + mediaid);
		File download = MediaTransport.download(appid, secret, mediaid, savePath);
		System.out.println(">> download : " + download);
	}

}
