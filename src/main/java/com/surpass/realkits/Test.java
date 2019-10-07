package com.surpass.realkits;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
//		GecService.setDllPath("C:\\Program Files (x86)\\upcsurpass\\Real Kits\\geC.dll");
		try {
			JGecService gec = new JGecService("C:\\dev\\upcvision\\src\\tools\\geC.dll");
			System.out.println(gec.DBECGetErrorMessage(2005L));

//			gec.dbec
//			String deviceNote = gec.DBECGetDeviceNote("demo", "CJY", 1, 80);
//			deviceNote = deviceNote.trim();
//			System.out.println("Encoding: "+EncodingTools.getEncoding(deviceNote)+"  "+Charset.defaultCharset());

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
