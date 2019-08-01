package com.surpass.vision.tools;

import com.surpass.vision.appCfg.GlobalConsts;

public class IDTools {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String [] ss = {"|"};
		String [] r = IDTools.splitID("!aa,b|b");
		for(int i=0;i<r.length;i++)
			System.out.println(r[i]);
	}
	public static String [] splitID(String str,String... splitChar) {
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

}
