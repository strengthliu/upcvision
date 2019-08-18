package com.surpass.vision.tools;

import java.util.Random;

import org.jsoup.helper.StringUtil;

import com.surpass.vision.appCfg.GlobalConsts;

public class IDTools {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] ss = {"|"};
		String [] r = IDTools.splitID("!aa,b|b");
		for(int i=0;i<r.length;i++)
			System.out.println(r[i]);
	}
	
	public static Double newID() {
		Integer i = new Random().nextInt(10);
		Long l = System.currentTimeMillis();
		String r = l.toString()+i.toString();
		return Double.parseDouble(r);
	}
	
	public static String [] splitID(String str,String... splitChar) {
		if(StringUtil.isBlank(str)) return new String[0];
		String sc = GlobalConsts.Key_splitChar;
		if(splitChar.length>1) {
			sc = "";
			for(int i =0;i<splitChar.length;i++){
				if(splitChar[i]!=null && splitChar[i]!="" && splitChar[i]!="|" && splitChar[i]!=".")
					sc+= splitChar[i]+"|";
				else if(splitChar[i]=="|" || splitChar[i]==".") {
					String sct = "\\"+splitChar[i];
					sc+= sct+"|";
				
				}
			}
		}else if(splitChar.length==1) {
			String sct = splitChar[0];
			if(splitChar[0]=="|" || splitChar[0]==".") {
				sct = "\\"+sct;
			}
			sc = sct+"|";
		}else if(splitChar.length==0)
			sc += "|";
		
		sc = sc.substring(0, sc.length()-1);
		System.out.println("sc = "+sc);
		
		return str.split(sc);
	}

	public static String merge(String[] aggrandizement) {
		String ret = "";
		for(int i=0;i<aggrandizement.length;i++) {
			ret += aggrandizement[i];
			ret += GlobalConsts.Key_splitChar;
		}
		if(ret.endsWith(GlobalConsts.Key_splitChar))
			ret = ret.substring(0,ret.length() - GlobalConsts.Key_splitChar.length());
		return ret;
	}


}
