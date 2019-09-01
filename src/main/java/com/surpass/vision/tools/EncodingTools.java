package com.surpass.vision.tools;

import java.nio.charset.Charset;

public class EncodingTools {
	public static String getEncoding(String str) {
		String encode;

		encode = "UTF-16";
		try {
			if (str.equals(new String(str.getBytes(), encode))) {
				return encode;
			}
		} catch (Exception ex) {
		}

		encode = "ASCII";
		try {
			if (str.equals(new String(str.getBytes(), encode))) {
				return "字符串<< " + str + " >>中仅由数字和英文字母组成，无法识别其编码格式";
			}
		} catch (Exception ex) {
		}

		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(), encode))) {
				return encode;
			}
		} catch (Exception ex) {
		}

		encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(), encode))) {
				return encode;
			}
		} catch (Exception ex) {
		}

		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(), encode))) {
				return encode;
			}
		} catch (Exception ex) {
		}
		return "未识别编码格式";
	}

	static final String[] encodeStrs = {"UTF-8","GB2312","ISO-8859-1","ASCII","UTF-16","UNICODE",
			"ISO-8859-1","GBK","GB18030",
			"Cyrillic"};
	public static void printEncodingTry(String s) {
		s = s.trim();
		for(int i=0;i<encodeStrs.length;i++) {
			String encode1 = encodeStrs[i];
			for(int j=0;j<encodeStrs.length;j++) {
				String encode2 = encodeStrs[j];
				byte[] b;
				try {
					String checkEncode = getEncoding(s);
					b = s.getBytes(encode1);
//					b = desc.getBytes();
//					s = new String(b, Charset.defaultCharset());
					String deviceNote1 = new String(b, encode2);
					deviceNote1 = deviceNote1.trim();
					 System.out.println(s+" : 检查encode="+checkEncode+" , getBytes("+encode1+") ="+s+" , getBytes("+encode2+") ="+deviceNote1);
					// b = deviceNote.getBytes(Charset.defaultCharset());
//					deviceNote = new String(b, Charset.defaultCharset());//解码:用什么字符集编码就用什么字符集解码
//					deviceNote = new String(b, "utf-8");//解码:用什么字符集编码就用什么字符集解码
//					System.out.println(deviceNote);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 编码
				
			}
		}
		

	}
	public static void main(String[] args) {
		// 获取系统默认编码
		System.out.println("系统默认编码：" + System.getProperty("file.encoding")); // 查询结果GBK
		// 系统默认字符编码
		System.out.println("系统默认字符编码：" + Charset.defaultCharset()); // 查询结果GBK
		// 操作系统用户使用的语言
		System.out.println("系统默认语言：" + System.getProperty("user.language")); // 查询结果zh

		System.out.println();

		String s1 = "hi, nice to meet you!";
		String s2 = "hi, 我来了！";

		System.out.println(getEncoding(s1));
		System.out.println(getEncoding(s2));
	}
}
