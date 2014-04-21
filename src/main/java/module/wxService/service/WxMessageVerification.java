package module.wxService.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 微信消息验证
 * 
 * @author 石莹 @ caituo
 *
 */
public class WxMessageVerification {
	
	public static boolean access(String token,String signature,String timestamp,String nonce) {
		if (token == null || signature == null || timestamp == null || nonce == null) {
			return false;
		}
		
		List<String> ss = new ArrayList<String>();
		ss.add(timestamp);
		ss.add(nonce);
		ss.add(token);
		
		Collections.sort(ss);
		
		StringBuilder builder = new StringBuilder();
		for(String s : ss) {
			builder.append(s);
		}
		return signature.equalsIgnoreCase(sha1(builder.toString()));
	}
	
	
	public static String md5(String value) {
		try {
			return hash(MessageDigest.getInstance("md5"), value);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String sha1(String value) {
		try {
			return hash(MessageDigest.getInstance("SHA1"), value);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private static String hash(MessageDigest digest,String src) {
		return toHexString(digest.digest(src.getBytes()));
	}
	
	private static String toHexString(byte[] bytes) {
		char[] values = new char[bytes.length * 2];
		int i=0;
		for(byte b : bytes) {
			values[i++] = LETTERS[((b & 0xF0) >>> 4)];
			values[i++] = LETTERS[b & 0xF];
		}
		return String.valueOf(values);
	}
	
	private static final char[] LETTERS = "0123456789ABCDEF".toCharArray();

}
