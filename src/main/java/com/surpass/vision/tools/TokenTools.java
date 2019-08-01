package com.surpass.vision.tools;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

public class TokenTools {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String token = TokenTools.genToken();
		System.out.println(token);
		Boolean b = TokenTools.verificationToken(token);
		if (b)
			System.out.println("token正确");
		else
			System.out.println("token错误");
	}

	private static final String[] codeBase = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
			"Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
			"u", "v", "w", "x", "y", "z" };

	private static Random rand = new Random();

	/** XXTEA加密解密的密钥 */
	private static String secKey = "captcha";

	/** token超时门限(天) */
	private static long expire = 7;

	/** 验证码字符数 */
	private static int charCount = 4;

	public static final String genToken(String secKey) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < charCount; i++) {
			int randInt = Math.abs(rand.nextInt());
			sb.append(codeBase[randInt % codeBase.length]);
		}
		long timestamp = System.currentTimeMillis();
		String token = null;
		token = String.format("%s_%d", sb.toString(), timestamp);
		System.out.println("未加密的token:" + token);
		token = XXTEAUtil.encrypt(token, secKey);
		return token;
	}

	public static final String genToken() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < charCount; i++) {
			int randInt = Math.abs(rand.nextInt());
			sb.append(codeBase[randInt % codeBase.length]);
		}
		long timestamp = System.currentTimeMillis();
		String token = null;
		token = String.format("%s_%d", sb.toString(), timestamp);
		System.out.println("未加密的token:" + token);
		token = XXTEAUtil.encrypt(token, secKey);
		return token;
	}

	public static final boolean verificationToken(String token, String secKey) throws RuntimeException {
		String plainText = XXTEAUtil.decrypt(token, secKey);
		System.out.println(plainText);
		if (StringUtils.isBlank(plainText)) {
			throw new IllegalStateException("解密失败,token可能遭到篡改");
		}
		String[] plainTextArr = plainText.split("_");
		if (plainTextArr.length != 2) {
			throw new IllegalStateException("token数据格式错误");
		}
		long timestamp = 0;
		try {
			timestamp = Long.parseLong(plainTextArr[1]);
		} catch (NumberFormatException e) {
			throw new IllegalStateException("时间戳无效");
		}
		if ((System.currentTimeMillis() - timestamp) > TimeUnit.MILLISECONDS.convert(expire + 5, TimeUnit.DAYS)) {
			throw new IllegalStateException("token已过期");
		}
		if(plainText.contentEquals(secKey))
			return true;
		else
			return false;
	}

	public static final boolean verificationToken(String token) throws RuntimeException {
		String plainText = XXTEAUtil.decrypt(token, secKey);
		System.out.println(plainText);
		if (StringUtils.isBlank(plainText)) {
			throw new IllegalStateException("解密失败,token可能遭到篡改");
		}
		String[] plainTextArr = plainText.split("_");
		if (plainTextArr.length != 2) {
			throw new IllegalStateException("token数据格式错误");
		}
		long timestamp = 0;
		try {
			timestamp = Long.parseLong(plainTextArr[1]);
		} catch (NumberFormatException e) {
			throw new IllegalStateException("时间戳无效");
		}
		if ((System.currentTimeMillis() - timestamp) > TimeUnit.MILLISECONDS.convert(expire + 5, TimeUnit.DAYS)) {
			throw new IllegalStateException("token已过期");
		}
		return true;
	}
}
