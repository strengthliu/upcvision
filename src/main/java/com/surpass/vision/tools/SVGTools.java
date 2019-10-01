package com.surpass.vision.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class SVGTools {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String svntext = readHtml("C:\\dev\\upcvision\\SVG\\钢铁\\gangtie.svg");
		System.out.println(svntext);
		try {
//			svntext = "<svg xmlns:xlink=\"http://www.w3.org/1999/xlink\" height=\"400\" width=\"600\" "
//					+ "xmlns=\"http://www.w3.org/2000/svg\" style=\"font-family:'lucida grande', "
//					+ "'lucida sans unicode', arial, helvetica, sans-serif;font-size:12px;\" version=\"1.1\">"
//					+ "<desc>Created with Highcharts 4.1.7</desc><defs><clipPath id=\"highcharts-5\">"
//					+ "<rect height=\"216\" width=\"514\" y=\"0\" x=\"0\">"
//					+ "</rect></clipPath></defs>"
//					+ "<rect class=\" highcharts-background\" fill=\"#FFFFFF\" strokeWidth=\"0\" height=\"400\" width=\"600\" y=\"0\" x=\"0\"></rect>"
//					+ "<rect transform=\"translate(1, 1)\" stroke-width=\"5\" stroke-opacity=\"0.049999999999999996\" stroke=\"black\"  fill=\"none\" height=\"216\" width=\"514\" y=\"70\" x=\"76\"></rect>"
//					+ "<rect transform=\"translate(1, 1)\" stroke-width=\"3\" stroke-opacity=\"0.09999999999999999\" stroke=\"black\"  fill=\"none\" height=\"216\" width=\"514\" y=\"70\" x=\"76\">"
//					+ "</rect>"
//					+ "<rect transform=\"translate(1, 1)\" stroke-width=\"1\" stroke-opacity=\"0.15\" stroke=\"black\"  fill=\"none\" height=\"216\" width=\"514\" y=\"70\" x=\"76\"></rect>"
//					+ "<rect fill=\"#FFFFFF\" height=\"216\" width=\"514\" y=\"70\" x=\"76\"></rect>"
//					+ "<g  class=\"highcharts-grid\"></g>"
//					+ "<g  class=\"highcharts-grid\"><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 286.5 L 590 286.5\" fill=\"none\"></path>"
//					+ "<path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 277.5 L 590 277.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 269.5 L 590 269.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 260.5 L 590 260.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 251.5 L 590 251.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 243.5 L 590 243.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 234.5 L 590 234.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 226.5 L 590 226.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 217.5 L 590 217.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 208.5 L 590 208.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 200.5 L 590 200.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 191.5 L 590 191.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 182.5 L 590 182.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 174.5 L 590 174.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 165.5 L 590 165.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 156.5 L 590 156.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 148.5 L 590 148.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 139.5 L 590 139.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 130.5 L 590 130.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 122.5 L 590 122.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 113.5 L 590 113.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 105.5 L 590 105.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 96.5 L 590 96.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 87.5 L 590 87.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 79.5 L 590 79.5\" fill=\"none\"></path><path opacity=\"1\" stroke-dasharray=\"8,3\" stroke-width=\"1\" stroke=\"#C5EEFA\" d=\"M 76 69.5 L 590 69.5\" fill=\"none\"></path><path opacity=\"1\"  stroke-width=\"1\" stroke=\"#D8D8D8\" d=\"M 76 286.5 L 590 286.5\" fill=\"none\"></path><path opacity=\"1\"  stroke-width=\"1\" stroke=\"#D8D8D8\" d=\"M 76 243.5 L 590 243.5\" fill=\"none\"></path><path opacity=\"1\"  stroke-width=\"1\" stroke=\"#D8D8D8\" d=\"M 76 200.5 L 590 200.5\" fill=\"none\"></path><path opacity=\"1\"  stroke-width=\"1\" stroke=\"#D8D8D8\" d=\"M 76 156.5 L 590 156.5\" fill=\"none\"></path><path opacity=\"1\"  stroke-width=\"1\" stroke=\"#D8D8D8\" d=\"M 76 113.5 L 590 113.5\" fill=\"none\"></path><path opacity=\"1\"  stroke-width=\"1\" stroke=\"#D8D8D8\" d=\"M 76 69.5 L 590 69.5\" fill=\"none\"></path></g><g  class=\"highcharts-axis\"><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 118.5 286 L 118.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 161.5 286 L 161.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 204.5 286 L 204.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 246.5 286 L 246.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 289.5 286 L 289.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 332.5 286 L 332.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 375.5 286 L 375.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 418.5 286 L 418.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 461.5 286 L 461.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 503.5 286 L 503.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 546.5 286 L 546.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 590.5 286 L 590.5 296\" fill=\"none\"></path><path opacity=\"1\" stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 75.5 286 L 75.5 296\" fill=\"none\"></path><text y=\"337.2301236804734\" visibility=\"visible\" style=\"color:#707070;fill:#707070;\" class=\" highcharts-xaxis-title\" transform=\"translate(0,0)\" text-anchor=\"middle\"  x=\"333\">x轴</text><path visibility=\"visible\"  stroke-width=\"1\" stroke=\"#C0D0E0\" d=\"M 76 286.5 L 590 286.5\" fill=\"none\"></path></g><g  class=\"highcharts-axis\"><text y=\"178\" visibility=\"visible\" style=\"color:#707070;fill:#707070;\" class=\" highcharts-yaxis-title\" transform=\"translate(0,0) rotate(270 30.149999618530273 178)\" text-anchor=\"middle\"  x=\"30.149999618530273\">Y轴</text><path visibility=\"visible\"  stroke-width=\"1\" stroke=\"#C0C0C0\" d=\"M 75.5 70 L 75.5 286\" fill=\"none\"></path></g><g  class=\"highcharts-series-group\"><g clip-path=\"url(#highcharts-5)\" transform=\"translate(76,70) scale(1 1)\"  visibility=\"visible\" class=\"highcharts-series\"><path stroke-linecap=\"round\" stroke-linejoin=\"round\"  stroke-width=\"2\" stroke=\"#7cb5ec\" d=\"M 21.416666666666668 190.1664 L 64.25 154.224 L 107.08333333333334 124.07039999999999 L 149.91666666666666 104.37120000000002 L 192.75 91.584 L 235.58333333333334 63.93600000000001 L 278.4166666666667 98.8416 L 321.25000000000006 87.696 L 364.08333333333337 29.030399999999986 L 406.9166666666667 48.29760000000002 L 449.75000000000006 133.4016 L 492.58333333333337 168.9984\" fill=\"none\"></path></g><g clip-path=\"none\" transform=\"translate(76,70) scale(1 1)\"  visibility=\"visible\" class=\"highcharts-markers\"><path d=\"M 492 164.9984 C 497.328 164.9984 497.328 172.9984 492 172.9984 C 486.672 172.9984 486.672 164.9984 492 164.9984 Z\" fill=\"#7cb5ec\"></path><path d=\"M 449 129.4016 C 454.328 129.4016 454.328 137.4016 449 137.4016 C 443.672 137.4016 443.672 129.4016 449 129.4016 Z\" fill=\"#7cb5ec\"></path><path d=\"M 406 44.29760000000002 C 411.328 44.29760000000002 411.328 52.29760000000002 406 52.29760000000002 C 400.672 52.29760000000002 400.672 44.29760000000002 406 44.29760000000002 Z\" fill=\"#7cb5ec\"></path><path d=\"M 364 25.030399999999986 C 369.328 25.030399999999986 369.328 33.030399999999986 364 33.030399999999986 C 358.672 33.030399999999986 358.672 25.030399999999986 364 25.030399999999986 Z\" fill=\"#7cb5ec\"></path><path d=\"M 321 83.696 C 326.328 83.696 326.328 91.696 321 91.696 C 315.672 91.696 315.672 83.696 321 83.696 Z\" fill=\"#7cb5ec\"></path><path d=\"M 278 94.8416 C 283.328 94.8416 283.328 102.8416 278 102.8416 C 272.672 102.8416 272.672 94.8416 278 94.8416 Z\" fill=\"#7cb5ec\"></path><path d=\"M 235 59.93600000000001 C 240.328 59.93600000000001 240.328 67.936 235 67.936 C 229.672 67.936 229.672 59.93600000000001 235 59.93600000000001 Z\" fill=\"#7cb5ec\"></path><path d=\"M 192 87.584 C 197.328 87.584 197.328 95.584 192 95.584 C 186.672 95.584 186.672 87.584 192 87.584 Z\" fill=\"#7cb5ec\"></path><path d=\"M 149 100.37120000000002 C 154.328 100.37120000000002 154.328 108.37120000000002 149 108.37120000000002 C 143.672 108.37120000000002 143.672 100.37120000000002 149 100.37120000000002 Z\" fill=\"#7cb5ec\"></path><path d=\"M 107 120.07039999999999 C 112.328 120.07039999999999 112.328 128.0704 107 128.0704 C 101.672 128.0704 101.672 120.07039999999999 107 120.07039999999999 Z\" fill=\"#7cb5ec\"></path><path d=\"M 64 150.224 C 69.328 150.224 69.328 158.224 64 158.224 C 58.672 158.224 58.672 150.224 64 150.224 Z\" fill=\"#7cb5ec\"></path><path d=\"M 21 186.1664 C 26.328 186.1664 26.328 194.1664 21 194.1664 C 15.672 194.1664 15.672 186.1664 21 186.1664 Z\" fill=\"#7cb5ec\"></path></g></g><text y=\"24\" style=\"color:#333333;font-size:18px;fill:#333333;width:536px;\"  class=\"highcharts-title\" text-anchor=\"middle\" x=\"300\">数据监控</text><text y=\"49\" style=\"color:#555555;fill:#555555;width:536px;\"  class=\"highcharts-subtitle\" text-anchor=\"middle\" x=\"300\">来源:</text><g transform=\"translate(270,357)\"  class=\"highcharts-legend\"><g ><g><g transform=\"translate(8,3)\"  class=\"highcharts-legend-item\"><path stroke-width=\"2\" stroke=\"#7cb5ec\" d=\"M 0 11 L 16 11\" fill=\"none\"></path><path d=\"M 8 7 C 13.328 7 13.328 15 8 15 C 2.6719999999999997 15 2.6719999999999997 7 8 7 Z\" fill=\"#7cb5ec\"></path><text y=\"15\"  text-anchor=\"start\" style=\"color:#333333;font-size:12px;font-weight:bold;cursor:pointer;fill:#333333;\" x=\"21\">测试</text></g></g></g></g><g  class=\"highcharts-axis-labels highcharts-xaxis-labels\"><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 99.25 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"99.25\"><tspan>Jan</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 142.08333333333337 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"142.08333333333337\"><tspan>Feb</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 184.91666666666669 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"184.91666666666669\"><tspan>Mar</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 227.75000000000003 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"227.75000000000003\"><tspan>Apr</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 270.58333333333326 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"270.58333333333326\"><tspan>May</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 313.41666666666663 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"313.41666666666663\"><tspan>Jun</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 356.25 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"356.25\"><tspan>Jul</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 399.0833333333333 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"399.0833333333333\"><tspan>Aug</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 441.9166666666667 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"441.9166666666667\"><tspan>Sep</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 484.75 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"484.75\"><tspan>Oct</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 527.5833333333335 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"527.5833333333335\"><tspan>Nov</tspan></text><text opacity=\"1\" y=\"304\" transform=\"translate(0,0) rotate(-30 570.4166666666667 304)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:400px;text-overflow:ellipsis;\" x=\"570.4166666666667\"><tspan>Dec</tspan></text></g><g  class=\"highcharts-axis-labels highcharts-yaxis-labels\"><text opacity=\"1\" y=\"288\" transform=\"translate(0,0)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:188px;text-overflow:clip;\" x=\"61\">0</text><text opacity=\"1\" y=\"245\" transform=\"translate(0,0)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:188px;text-overflow:clip;\" x=\"61\">50</text><text opacity=\"1\" y=\"202\" transform=\"translate(0,0)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:188px;text-overflow:clip;\" x=\"61\">100</text><text opacity=\"1\" y=\"158\" transform=\"translate(0,0)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:188px;text-overflow:clip;\" x=\"61\">150</text><text opacity=\"1\" y=\"115\" transform=\"translate(0,0)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:188px;text-overflow:clip;\" x=\"61\">200</text><text opacity=\"1\" y=\"72\" transform=\"translate(0,0)\" text-anchor=\"end\" style=\"color:#606060;cursor:default;font-size:11px;fill:#606060;width:188px;text-overflow:clip;\" x=\"61\">250</text></g><g transform=\"translate(0,-9999)\" style=\"cursor:default;padding:0;white-space:nowrap;\"  class=\"highcharts-tooltip\"><path transform=\"translate(1, 1)\" stroke-width=\"5\" stroke-opacity=\"0.049999999999999996\" stroke=\"black\"  d=\"M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5\" fill=\"none\"></path><path transform=\"translate(1, 1)\" stroke-width=\"3\" stroke-opacity=\"0.09999999999999999\" stroke=\"black\"  d=\"M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5\" fill=\"none\"></path><path transform=\"translate(1, 1)\" stroke-width=\"1\" stroke-opacity=\"0.15\" stroke=\"black\"  d=\"M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5\" fill=\"none\"></path><path d=\"M 3.5 0.5 L 13.5 0.5 C 16.5 0.5 16.5 0.5 16.5 3.5 L 16.5 13.5 C 16.5 16.5 16.5 16.5 13.5 16.5 L 3.5 16.5 C 0.5 16.5 0.5 16.5 0.5 13.5 L 0.5 3.5 C 0.5 0.5 0.5 0.5 3.5 0.5\" fill=\"rgb(249, 249, 249)\" fill-opacity=\" .85\"></path><text y=\"20\" style=\"font-size:12px;color:#333333;fill:#333333;\"  x=\"8\"></text></g></svg>";

			SVGTools.convertToPng(svntext, "c:\\test.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	        byte[] bytes = svgCode.getBytes("utf-8");  
	        PNGTranscoder t = new PNGTranscoder();  
	        TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));  
	        TranscoderOutput output = new TranscoderOutput(outputStream);  
	        t.transcode(input, output);  
	        outputStream.flush();  
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    finally {  
	        if (outputStream != null) {  
	            try {  
	                outputStream.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	}  
	public static String readHtml(String fileName) {
		//LOGGER.info("检测文件："+fileName);
		File filename = new File(fileName); // 要读取以上路径的input。txt文件
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		boolean isCompatible = false;
		if(suffix.contentEquals("txt") || suffix.contentEquals("html") 
				|| suffix.contentEquals("htm") ||suffix.contentEquals("svg") 
				|| suffix.contentEquals("text"))
			isCompatible = true;
		// 大于1m或不是指定文件格式，就退出。
		if((filename.length()/1024/1024)>2 || !isCompatible ) return "";
		InputStreamReader reader = null;
		String ret = "";
		BufferedReader br = null;
		try {
			reader = new InputStreamReader(
					new FileInputStream(filename));
			br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			line = br.readLine();
			while (line != null) {
				ret = ret + line;
				line = br.readLine(); // 一次读入一行数据
			}
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} // 建立一个输入流对象reader
		catch(IOException e3) {
			e3.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
}
