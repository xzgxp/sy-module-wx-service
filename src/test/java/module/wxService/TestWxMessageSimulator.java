package module.wxService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import sy.module.core.config.PropertiesLoader;

public class TestWxMessageSimulator {
	private static final String url = "http://localhost/module-wx-service/module/wxService/notice.do";
	
	@Test
	public void test() throws IOException, InterruptedException {
		final int thunderCount = 100;
		final int loopCount = 10000;
        final CountDownLatch countDownLatch = new CountDownLatch(thunderCount); 
		final List<String> xmls = loadXmls();
		for (int i=0; i<thunderCount; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j=0; j<loopCount; j++) {
						try {
							sendMsg(xmls.get(new Random().nextInt(xmls.size())));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					countDownLatch.countDown();
				}
			}).start();
		}
		countDownLatch.await();
	}
	
	
	private String loadFileAsString(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			StringBuffer buf = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buf.append(line);
				buf.append("\n");
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e){}
		}
		return "";
	}
	
	
	private List<String> loadXmls() throws IOException {
		List<String> list = new ArrayList<String>();
		URL url = TestWxMessageSimulator.class.getClassLoader().getResource("module/wxService/demoMsg/");
		File demoPath = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
		for (File file : demoPath.listFiles()) {
			list.add(loadFileAsString(file));
		}
		return list;
	}
	
	
	@SuppressWarnings("deprecation")
	private String sendMsg(String msg) throws HttpException, IOException {
		HttpClient http = new HttpClient();
		// 计算参数
		String timestamp = ""+new Date().getTime();
		String nonce = ""+new Random().nextInt(Integer.MAX_VALUE);
		String token = PropertiesLoader.getInstance().getConfig("module.wxService.token");
		List<String> ss = new ArrayList<String>();
		ss.add(timestamp);
		ss.add(nonce);
		ss.add(token);
		Collections.sort(ss);
		StringBuilder builder = new StringBuilder();
		for(String s : ss) {
			builder.append(s);
		}
		String signature = HashKit.sha1(builder.toString());
		//
		PostMethod post = new PostMethod(url + "?signature="+signature+"&timestamp="+timestamp+"&nonce="+nonce);
		try {
			post.setRequestHeader("Content-Type", "text/html;charset=UTF-8");
			post.setRequestBody(msg);
			http.executeMethod(post);
			return post.getResponseBodyAsString();
		} finally {
			post.releaseConnection();
			((SimpleHttpConnectionManager) http.getHttpConnectionManager()).shutdown();
		}
	}

}
