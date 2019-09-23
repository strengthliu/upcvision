package com.surpass.vision.tools;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.dom.util.DocumentFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
 

public class SVGTool {

	public static void main(String[] args) {
		String strImg = GetImageStr("C:\\dev\\upcvision\\src\\main\\resources\\static\\tu\\1苯胺精制.html");
		System.out.println(strImg);
//		String strImg = "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4NCjwhLS0gR2VuZXJhdG9yOiBBZG9iZSBJbGx1c3RyYXRvciAxNC4wLjAsIFNWRyBFeHBvcnQgUGx1Zy1JbiAgLS0+DQo8IURPQ1RZUEUgc3ZnIFBVQkxJQyAiLS8vVzNDLy9EVEQgU1ZHIDEuMS8vRU4iICJodHRwOi8vd3d3LnczLm9yZy9HcmFwaGljcy9TVkcvMS4xL0RURC9zdmcxMS5kdGQiIFsNCgk8IUVOVElUWSBuc19mbG93cyAiaHR0cDovL25zLmFkb2JlLmNvbS9GbG93cy8xLjAvIj4NCl0+DQo8c3ZnIHZlcnNpb249IjEuMSINCgkgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgeG1sbnM6YT0iaHR0cDovL25zLmFkb2JlLmNvbS9BZG9iZVNWR1ZpZXdlckV4dGVuc2lvbnMvMy4wLyINCgkgeD0iMHB4IiB5PSIwcHgiIHdpZHRoPSI5MXB4IiBoZWlnaHQ9IjMzcHgiIHZpZXdCb3g9IjAgNi45OTMgOTEgMzMiIGVuYWJsZS1iYWNrZ3JvdW5kPSJuZXcgMCA2Ljk5MyA5MSAzMyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+DQo8ZGVmcz4NCjwvZGVmcz4NCjxnPg0KCTxnPg0KCQk8cGF0aCBmaWxsLXJ1bGU9ImV2ZW5vZGQiIGNsaXAtcnVsZT0iZXZlbm9kZCIgZD0iTTgzLDIzYy0wLjg5MSwwLjQ0My0xLjc4OSwwLjg3OC0yLDJjMS41MDYsMC45NTcsNS4xODMsMC44MSw5LDANCgkJCWMzLjExNiw0LjM1NS0xLjkxNyw3LjY3LTMsMTFjLTMuNDY1LDAuMTMyLTUuODA2LTAuODYxLTUtNWMxLjkzMSw2LjE0NSw2LjIwNywwLjU4Nyw3LTNjLTguODMxLTMuODE1LTcuOTkzLDEwLjY2Mi0xNywxMA0KCQkJYzIuNzcyLTEuODk1LDUuMzI0LTQuMDA5LDctN2MtMC4zLTEuODExLTQuMjA3LDEuMDEyLTUsMmMtMi41MzUtMi42MDIsMy43MzYtNC44ODEsNy01Yy0xLjU3Mi0wLjc2MS0xLjQ5NC0zLjE3My00LTMNCgkJCWMwLjcyNy0wLjk1OSw0LjY5My01Ljg2MSwyLTRjLTMuMTg3LTEuOTgsMi4wMTYtMy44OSw1LTNjLTAuMTA5LDIuNDg4LTEuODc0LDMuMDk2LDAsNWMyLjI2Ny0xLjA2NywzLjYzNC0zLjAzMyw0LTYNCgkJCWMtNS42NDQtMi4xNzQtMTYuOTQ0LDEuMjYzLTEzLDljLTUuNTYzLTAuMTE1LTEuNDYyLTUuMzUzLTItOWMzLjA1NiwwLjcyMywzLjY0NS0xLjAyMiw2LTFjLTQuNzAyLTEuNjE3LTEuMTQ5LTIuNjE5LDEtNQ0KCQkJYy0wLjMxLTEuMzU3LTIuNTA0LTAuODMtNC0xYy0wLjMyNy0zLjQ1MSw1LjIyNC0zLjY1OCw3LTJjLTEuNzU4LDMuNDc0LTAuODczLDMuNzk2LTIsN2MyLjM1OS0wLjc2NCw2LjkxMS0xLjk3MSw5LDANCgkJCWMwLjY5Nyw1LjM2NC0yLjk1NSw2LjM3OC00LDEwQzg0LjI4OSwyNS4wNDQsODMuNDEsMjQuMjU3LDgzLDIzeiIvPg0KCQk8cGF0aCBmaWxsLXJ1bGU9ImV2ZW5vZGQiIGNsaXAtcnVsZT0iZXZlbm9kZCIgZD0iTTUxLDExYzIuNzI5LTAuNzI5LDIuMDEzLDEuOTg3LDQsMmMtMi42MjIsMS43OTctMi42MjIsNy44OTcsMCw3DQoJCQljLTIuNzgxLDIuMzU3LDAuMjksNS4zODctMiw5YzMuOTE0LDAuNTgxLDUuODA3LTAuODYsOC0yYy0wLjAxMy0xLjk4Ny0yLjcyOS0xLjI3MS0yLTRjMS43MTQsMS4yODYsMy45MzMsMi4wNjcsNCw1DQoJCQljLTMuMjAyLDEuNDY0LTUuNTU3LDMuNzc3LTExLDNjMC0yLjMzMywwLTQuNjY3LDAtN2MtMi45NjgsMS4wMzItMS4yOCw2LjcyLTUsN2MtMC4xNS0yLjQ4MywwLjUxNy00LjE1LDItNQ0KCQkJYzAuMTc0LTIuODQxLTYuNTQyLDEuMjA5LTUtM2MzLjU4OSwwLjU4OSw0LjgzOC0xLjE2Miw3LTJjMC0xLjY2NywwLTMuMzMzLDAtNWMtNC4xMjksMi4yMDQtNy41MjEsNS4xNDYtMTAsOQ0KCQkJYy0yLjA4Mi0zLjEyNyw1LjI4Ni01LjM0NywzLTEyYzMuMDA4LTEuMDA4LDEuNzcyLDIuMjI4LDIsNEM0Ny42OTIsMTUuMDI2LDUxLjc3MiwxNS40MzksNTEsMTF6Ii8+DQoJCTxwYXRoIGZpbGwtcnVsZT0iZXZlbm9kZCIgY2xpcC1ydWxlPSJldmVub2RkIiBkPSJNMTgsMjRjLTMuMDQzLTMuOTA2LDUuNjM2LTcuMTY1LDItMTJjNS4wNDctMC4yMjIsMi4yNjEsNS4xOTYsMSw3DQoJCQljMy4xNTYsMC40ODksMy41MzEtMS44MDIsNy0xYzQuMDc2LDQuMDcxLDEuMTksMTQuMDIxLTEsMThjLTIuOTMyLTAuMDY3LTMuNzE0LTIuMjg2LTUtNGMtMi43MTEsMC42MjItMi40ODksNC4xNzgtNiw0DQoJCQljMi4yMDctMy40Niw1LjI3NC02LjA2LDctMTBjMi4yNDgsMi4wMDItMS43MTcsNi4yNDEsMiw4YzIuODM0LTIuODMzLDMuMTMtOC4yMDQsMy0xNEMyMy41MjYsMjAuMTkzLDE5Ljc1NSwyMS4wODgsMTgsMjR6Ii8+DQoJCTxwYXRoIGZpbGwtcnVsZT0iZXZlbm9kZCIgY2xpcC1ydWxlPSJldmVub2RkIiBkPSJNMCwyOGM0LjQwMi0xLjU5OCw3LjE5Mi00LjgwOCwxMS03YzEuMzgtNC43MjktMy4xMzItNi41NzQtMS05DQoJCQljMi4wMDUsMS42NjIsNC42NDgsMi42ODUsNSw2Yy0yLjI1OSwyLjg0Mi00LjI1LDExLjEzNywwLDEyYzQuNjIsMC4yODYsMS4zOC03LjI4Niw2LTdjLTAuODc3LDMuNDU2LTEuNzM5LDYuOTI4LTQsOQ0KCQkJYy0yLjQ4OCwwLjE1NC0zLjA5Ni0xLjU3MS01LTJjLTAuOTU4LDMuMDQxLDIuMTQyLDEwLjE0Mi0yLDEwYy0wLjYzMy0zLjM2NywxLjI4OS05LjI4OC0xLTExYy0wLjg4LDIuNDUzLTAuOSw1Ljc2Ny00LDYNCgkJCWMtMC43NTctMy44MTMsNC43OTYtNi40NjEsMi05Yy0xLjcxLDEuNjIzLTMuNjQsMy4wMjctNywzQzAsMjguNjY3LDAsMjguMzMzLDAsMjh6Ii8+DQoJPC9nPg0KPC9nPg0KPC9zdmc+DQo=";
//		GenerateImage(strImg);
		
//		base64ToImage(strImg, "c:/test5.png");
		
		try {
			convertToPng(strImg, "c:/test6.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String GetImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		//String imgFile = "C:/chenli.svg";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	public static String getImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		//String imgFile = "C:/chenli.svg";// 待处理的图片
	    if (imgFile == null) {
	        return null;
	    }
	    byte[] data = imgFile.getBytes();

		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	 
		// 图片转化成base64字符串
	 
		// base64字符串转化成图片
		public static boolean GenerateImage(String imgStr,String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片
			if (imgStr == null) // 图像数据为空
				return false;
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				// Base64解码
				byte[] b = decoder.decodeBuffer(imgStr);
				for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {// 调整异常数据
						b[i] += 256;
					}
				}
				// 生成jpeg图片
				//String imgFilePath = "C:/test22.svg";// 新生成的图片
				OutputStream out = new FileOutputStream(imgFilePath);
				out.write(b);
				out.flush();
				out.close();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		
		/**
		 * @Descriptionmap 对字节数组字符串进行Base64解码并生成图片
		 * @author temdy
		 * @Date 2015-01-26
		 * @param base64 图片Base64数据
		 * @param path 图片路径
		 * @return
		 */
		public static boolean base64ToImage(String base64, String path) {// 对字节数组字符串进行Base64解码并生成图片
		    if (base64 == null){ // 图像数据为空
		        return false;
		    }
		    BASE64Decoder decoder = new BASE64Decoder();
		    try {
		        // Base64解码
		        byte[] bytes = decoder.decodeBuffer(base64);
		        for (int i = 0; i < bytes.length; ++i) {
		            if (bytes[i] < 0) {// 调整异常数据
		                bytes[i] += 256;
		            }
		        }
		        // 生成jpeg图片
		        OutputStream out = new FileOutputStream(path);
		        out.write(bytes);
		        out.flush();
		        out.close();
		        return true;
		    } catch (Exception e) {
		        return false;
		    }
		}
		
	    /**
	     * 将svg字符串转换为png
	     *
	     * @param svgCode svg代码
	     * @param pngFilePath 保存的路径
	     * @throws TranscoderException svg代码异常
	     * @throws IOException io错误
	     */
	    public static void convertToPng(String svgCode, String pngFilePath) throws IOException,
	            TranscoderException {
	 
	        File file = new File(pngFilePath);
	 
	        FileOutputStream outputStream = null;
	        try {
	            file.createNewFile();
	            outputStream = new FileOutputStream(file);
	            convertToPng(svgCode, outputStream);
	        } finally {
	            if (outputStream != null) {
	                try {
	                    outputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	 
	    /**
	     * 将svgCode转换成png文件，直接输出到流中
	     *
	     * @param svgCode svg代码
	     * @param outputStream 输出流
	     * @throws TranscoderException 异常
	     * @throws IOException io异常
	     */
	    public static void convertToPng(String svgCode, OutputStream outputStream)
	            throws TranscoderException, IOException {
	        try {
	        	// utf-8 解码
//	            byte[] bytes = svgCode.getBytes("utf-8");
	            
	        	// Base64解码
	            BASE64Decoder decoder = new BASE64Decoder();
		        byte[] bytes = decoder.decodeBuffer(svgCode);
		        for (int i = 0; i < bytes.length; ++i) {
		            if (bytes[i] < 0) {// 调整异常数据
		                bytes[i] += 256;
		            }
		        }
	    	        
	    	    // 根据上面byte[]数组 生成 png 图片    
	            PNGTranscoder t = new PNGTranscoder();
	            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));
	            TranscoderOutput output = new TranscoderOutput(outputStream);
	            t.transcode(input, output);
	            outputStream.flush();
	        } finally {
	            if (outputStream != null) {
	                try {
	                    outputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }


}
