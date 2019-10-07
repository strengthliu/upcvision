package com.surpass.realkits;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.NativeLongByReference;

public class DllTest {
	GecService gec = null;

	public DllTest() {
		GecService.setDllPath("C:\\Program Files (x86)\\upcsurpass\\Real Kits\\geC.dll");
		try {
			gec = GecService.getGcService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// batch的接口暂时不考虑
	public static void main(String[] args) {
		DllTest test = new DllTest();
		 System.out.println(test.DBECGetLastError());
		// System.out.println(test.DBECGetErrorMessage());
		// System.out.println(test.DBEC_ExitInstance());
		// System.out.println(new Date(test.DBECGetServerCurrentTime() * 1000));
		// // System.out.println(test.DBECSetLocalServerTime()); //会篡改本地时间

		// System.out.println(test.DBECGetDeviceID());
//		 System.out.println(test.DBECGetDeviceName());
		// System.out.println(test.DBECGetTagID());
		// System.out.println(test.DBECGetFieldID());
		
		// System.out.println(test.DBECGetServerCount());
		// System.out.println(test.DBECGetTagMaxCount());
		// System.out.println(test.DBECGetClientMaxCount());
		// System.out.println(test.DBECGetClientCount());
		// System.out.println(test.DBECGetDeviceCount());
		// System.out.println(test.DBECGetTagCount());
		// System.out.println(test.DBECGetTagCountOfDevice());
		// System.out.println(test.DBECGetServerNameMaxLen());
		// System.out.println(test.DBECGetClientNameMaxLen());
		// System.out.println(test.DBECGetDeviceNameMaxLen());
		// System.out.println(test.DBECGetTagNameMaxLen());
		// System.out.println(test.DBECGetFieldNameMaxLen());
		// System.out.println(test.DBECGetFieldStringValueMaxLen());
		// System.out.println(test.DBECGetErrMsgMaxLen());
		// System.out.println(test.DBECGetEventNameMaxLen());
		// System.out.println(test.DBECGetEventConditionMaxLen());
		// System.out.println(test.DBECGetTagType());
		// System.out.println(test.DBECGetFieldType());
//		 System.out.println(test.DBECEnumServerName());
//		 System.out.println(test.DBECEnumDeviceName());
		// System.out.println(test.DBECGetTagRealField());// 成功，重要接口，目前沒問題
//		 System.out.println(test.DBECBatchGetTagRealField());//成功，重要接口，目前沒問題
//		 System.out.println(test.DBECEnumTagExtendType());
		
		
		
		
		
		
		
		
	/*	////// System.out.println(test.DBECEnumClientName());//错误2002 函数不能实现所需功能

		
		// System.out.println(test.DBECBatchGetDeviceID());//会卡住，中断下面的执行线程 怀疑超时
		// System.out.println(test.DBECBatchGetDeviceName());//错误2005 但类型都对了
		// System.out.println(test.DBECBatchGetTagID());//会卡住，中断下面的执行线程
		// 怀疑超时，因为当数据缓冲区不够会报错 长度只能是10000
		// System.out.println(test.DBECBatchGetTagName());//错误2005 但类型都对了

		// 长度31,但长度到32就会报错
		// System.out.println(test.DBECBatchGetTagIntField());//会卡住，怀疑超时，而且长度过长会报类型错误
		// System.out.println(test.DBECBatchGetTagStringField());//错误2006
		// 但给的内存已经很大了
		
		
		
		
		
		
		////// System.out.println(test.DBECGetFieldName());// 错误2002 函数不能实现所需功能
		// System.out.println(test.DBECEnumTagName());//返回false 错误码0   添加返回id接口
		// System.out.println(test.DBECEnumTagNameOfDevice());//返回false 错误码0  添加返回id接口
*/
		// 必须实现
		
//		test.DBECEnumTagID();
//		test.DBECEnumTagIDOfDevice();
		// System.out.println(test.DBECGetTagRealHistory()); //成功，能运行
		// System.out.println(test.DBECGetTagIntHistory()); //暂时不用实现
		// System.out.println(test.DBECGetTagStringHistory());//暂时不用实现

		// System.out.println(test.DBECGetTagIntField());// 成功，能运行
		// System.out.println(test.DBECGetTagStringField());// 成功，能运行
		// test.DBACGetCurrentAlarm();//成功，能运行
		// System.out.println(test.DBACGetHistoryAlarm());//成功，能运行

		System.out.println(test.DBECGetLastError());
	}

	public long DBECGetLastError() {
		return gec.DBECGetLastError();
	}

	public boolean DBECGetErrorMessage() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		boolean result = gec.DBECGetErrorMessage(2005L, sb, 129);
		System.out.println(sb.toString());
		return result;
	}

	public boolean DBEC_ExitInstance() {
		// TODO Auto-generated method stub
		return gec.DBEC_ExitInstance();
	}

	public long DBECGetServerCurrentTime() {
		// TODO Auto-generated method stub
		return gec.DBECGetServerCurrentTime("demo");
	}

	public boolean DBECSetLocalServerTime() {
		// TODO Auto-generated method stub
		return gec.DBECSetLocalServerTime("demo");
	}

	// 有数组CStringArray，暂不实现
	public boolean DBEC_ConfigServer() {
		// TODO Auto-generated method stub
		// return gec.DBEC_ConfigServer(bSettingBox, paryServerName,
		// paryServerNote, paryServerAddress, paryServerPort, pdwCurrentServer);
		return false;
	}

	// 有数组CStringArray，暂不实现
	public boolean DBEC_GetServerInfo() {
		// TODO Auto-generated method stub
		// return gec.DBEC_GetServerInfo(paryServerName, paryServerNote);
		return false;
	}

	public long DBECGetDeviceID() {
		// TODO Auto-generated method stub
		return gec.DBECGetDeviceID("demo", "QF");
	}

	public boolean DBECGetDeviceName() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		boolean result = gec.DBECGetDeviceName("demo", 1L, sb, 33);
		System.out.println(sb.toString());
		return result;
	}

	public long DBECGetTagID() {
		// TODO Auto-generated method stub
		return gec.DBECGetTagID("demo", "CJY_TI2181");
	}

	public boolean DBECGetTagName() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		boolean result = gec.DBECGetTagName("demo", 2, sb, 100);
		return result;
	}

	public long DBECGetFieldID() {
		// TODO Auto-generated method stub
		return gec.DBECGetFieldID("FN_ORITAGNAME");
	}

	/*public boolean DBECGetFieldName() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		boolean result = gec.DBECGetFieldName(3L, sb, 33);
		System.out.println(sb.toString());
		return result;
	}*/

	public long DBECGetServerCount() {
		// TODO Auto-generated method stub
		return gec.DBECGetServerCount();
	}

	public long DBECGetTagMaxCount() {
		// TODO Auto-generated method stub
		return gec.DBECGetTagMaxCount("demo");
	}

	public long DBECGetClientMaxCount() {
		// TODO Auto-generated method stub
		return gec.DBECGetClientMaxCount("demo");
	}

	public long DBECGetClientCount() {
		// TODO Auto-generated method stub
		return gec.DBECGetClientCount("demo");
	}

	public long DBECGetDeviceCount() {
		// TODO Auto-generated method stub
		return gec.DBECGetDeviceCount("demo");
	}

	public long DBECGetTagCount() {
		// TODO Auto-generated method stub
		return gec.DBECGetTagCount("demo");
	}

	public long DBECGetTagCountOfDevice() {
		// TODO Auto-generated method stub
		return gec.DBECGetTagCountOfDevice("demo", "CJY", 1);
	}

	public long DBECGetServerNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetServerNameMaxLen();
	}

	public long DBECGetClientNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetClientNameMaxLen();
	}

	public long DBECGetDeviceNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetDeviceNameMaxLen();
	}

	public long DBECGetTagNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetTagNameMaxLen();
	}

	public long DBECGetFieldNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetFieldNameMaxLen();
	}

	public long DBECGetFieldStringValueMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetFieldStringValueMaxLen();
	}

	public long DBECGetErrMsgMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetErrMsgMaxLen();
	}

	public long DBECGetEventNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetEventNameMaxLen();
	}

	public long DBECGetEventConditionMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetEventConditionMaxLen();
	}

	public long DBECGetTagType() {
		// TODO Auto-generated method stub
		return gec.DBECGetTagType("demo", "CJY_TI2181", 1L);
	}

	public long DBECGetFieldType() {
		// TODO Auto-generated method stub
		return gec.DBECGetFieldType("FN_ORITAGNAME");
	}

	public boolean DBECGetTagRealField() {
		// TODO Auto-generated method stub
		DoubleByReference db = new DoubleByReference();
		boolean result = gec.DBECGetTagRealField("demo", "CJY_TI2181", 1L, "FN_RTVALUE", db);
		System.out.println(db.getValue());
		return result;
	}

	public boolean DBECGetTagIntField() {
		// TODO Auto-generated method stub
		NativeLong pValue = new NativeLong();
		boolean result = gec.DBECGetTagIntField("demo", "CJY_DI101", 1L, "FN_TAGTYPE", pValue);
		System.out.println(pValue);
		return result;
	}

	public boolean DBECGetTagStringField() {
		// TODO Auto-generated method stub
		StringBuffer lpValueBuffer = new StringBuffer();
		boolean result = gec.DBECGetTagStringField("demo", "CJY_TI2181", 1L, "FN_TAGNOTE", lpValueBuffer);
		System.out.println(lpValueBuffer.toString());
		return result;
	}

	public boolean DBECEnumServerName() {
		// TODO Auto-generated method stub
		List<String> lpNameBuffer = new ArrayList<>();
		NativeLong pnNameCount = new NativeLong();
		NativeLong pnNameBytes = new NativeLong();
		boolean result = gec.DBECEnumServerName(lpNameBuffer, 80, pnNameCount, pnNameBytes);
		System.out.println(pnNameCount.longValue());
		System.out.println(pnNameBytes.longValue());
		for(String s : lpNameBuffer){
			System.out.println(s);
		}
		return result;
	}

	/*public boolean DBECEnumClientName() {
		// TODO Auto-generated method stub
		List<String> lpNameBuffer = new ArrayList<>();
		NativeLong pnNameCount = new NativeLong();
		NativeLong pnNameBytes = new NativeLong();
		boolean result = gec.DBECEnumClientName("demo", lpNameBuffer, 512, pnNameCount, pnNameBytes);
		System.out.println(pnNameCount.longValue());
		System.out.println(pnNameBytes.longValue());
		for(String s : lpNameBuffer){
			System.out.println(s);
		}
		return result;
	}*/

	public boolean DBECEnumDeviceName() {
		// TODO Auto-generated method stub
		List<String> lpNameBuffer = new ArrayList<>();
		NativeLong pnNameCount = new NativeLong();
		NativeLong pnNameBytes = new NativeLong();
		boolean result = gec.DBECEnumDeviceName("demo", lpNameBuffer, 33*9, pnNameCount, pnNameBytes);
		System.out.println(pnNameCount.longValue());
		System.out.println(pnNameBytes.longValue());
		for(String s : lpNameBuffer){
			System.out.println(s);
		}
		System.out.println(gec.DBECGetDeviceNameMaxLen() + "---" + gec.DBECGetDeviceCount("demo"));
		return result;
	}

	/*public boolean DBECEnumTagName() {
		// TODO Auto-generated method stub
		StringBuffer lpNameBuffer = new StringBuffer();
		NativeLongByReference pnNameCount = new NativeLongByReference();
		NativeLongByReference pnNameBytes = new NativeLongByReference();
		boolean result = gec.DBECEnumTagName("demo", lpNameBuffer, 33, pnNameCount, pnNameBytes);
		System.out.println(lpNameBuffer.toString());
		System.out.println(pnNameCount.getValue());
		System.out.println(pnNameBytes.getValue());
		return result;
	}*/
	
	public boolean DBECEnumTagID(){
		List<Long> pNameIDBuffer = new ArrayList<>();
		boolean result = gec.DBECEnumTagID("demo", pNameIDBuffer);
		for(long l : pNameIDBuffer){
			System.out.println(l);
		}
		return result;
	}

	/*public boolean DBECEnumTagNameOfDevice() {
		// TODO Auto-generated method stub
		StringBuffer lpNameBuffer = new StringBuffer();
		NativeLongByReference pnNameCount = new NativeLongByReference();
		NativeLongByReference pnNameBytes = new NativeLongByReference();
		boolean result = gec.DBECEnumTagNameOfDevice("demo", "CJY", 1L, lpNameBuffer, 33, pnNameCount, pnNameBytes);
		System.out.println(lpNameBuffer.toString());
		System.out.println(pnNameCount.getValue());
		System.out.println(pnNameBytes.getValue());
		return result;
	}*/
	
	public boolean DBECEnumTagIDOfDevice(){
		List<Long> pNameIDBuffer = new ArrayList<>();
		boolean result = gec.DBECEnumTagIDOfDevice("demo", "CJY", 1L,pNameIDBuffer);
		for(long l : pNameIDBuffer){
			System.out.println(l);
		}
		return result;
	}

	public boolean DBECEnumTagExtendType() {
		// TODO Auto-generated method stub
		StringBuffer lpNameBuffer = new StringBuffer();
		NativeLongByReference pnNameCount = new NativeLongByReference();
		NativeLongByReference pnNameBytes = new NativeLongByReference();
//		boolean result = gec.DBECEnumTagExtendType("demo", lpNameBuffer, 512, pnNameCount, pnNameBytes);
		System.out.println(lpNameBuffer.toString());
		System.out.println(pnNameCount.getValue());
		System.out.println(pnNameBytes.getValue());
		return true;
	}

	/*public boolean DBECBatchGetDeviceID() {
		// TODO Auto-generated method stub
		List<Long> pnIDArray = new ArrayList<>();
		System.out.println("==========");
		// new String[]{"CJY","QF"}
		List<String> pnNameArray = new ArrayList<>();
		pnNameArray.add("CJY");
		pnNameArray.add("QF");
		boolean result = gec.DBECBatchGetDeviceID("demo", pnNameArray, pnIDArray);
		System.out.println("--------");
		for (long l : pnIDArray) {
			System.out.println(l);
		}
		return result;
	}

	public boolean DBECBatchGetDeviceName() {
		// TODO Auto-generated method stub
		NativeLongByReference pnIDArray = new NativeLongByReference();
		NativeLongByReference pnNameBytes = new NativeLongByReference();
		StringBuffer lpNameBuffer = new StringBuffer();
		boolean result = gec.DBECBatchGetDeviceName("demo", pnIDArray, 100, lpNameBuffer, 1024, pnNameBytes);
		System.out.println(lpNameBuffer.toString());
		return result;
	}

	public boolean DBECBatchGetTagID() {
		// TODO Auto-generated method stub
		NativeLongByReference pnIDArray = new NativeLongByReference();
		boolean result = gec.DBECBatchGetTagID("demo", "CJY_TI2181", 10000, 10000, pnIDArray, 9999);
		System.out.println(pnIDArray.getPointer().getLongArray(0, 0).length);
		return result;
	}

	public boolean DBECBatchGetTagName() {
		// TODO Auto-generated method stub
		NativeLongByReference pnIDArray = new NativeLongByReference();
		NativeLongByReference pnNameBytes = new NativeLongByReference();
		StringBuffer lpNameBuffer = new StringBuffer();
		boolean result = gec.DBECBatchGetTagName("demo", pnIDArray, 1024, lpNameBuffer, 1024, pnNameBytes);
		System.out.println(lpNameBuffer.toString());
		return result;
	}*/

	public boolean DBECBatchGetTagRealField() {
		// TODO Auto-generated method stub
		// NativeLongByReference pnIDArray = new NativeLongByReference();
		List<Double> pValueArray = new ArrayList<>();
		List<Long> idList = new ArrayList<>();
		idList.add(1L);
		idList.add(3L);
		idList.add(4L);
		boolean result = gec.DBECBatchGetTagRealField("demo", idList, "FN_RTVALUE", pValueArray);
		for (Double d : pValueArray) {
			System.out.println(d);
		}
		return result;
	}
	/*
	public boolean DBECBatchGetTagIntField() {
		// TODO Auto-generated method stub
		NativeLongByReference pnIDArray = new NativeLongByReference();
		NativeLongByReference pValueArray = new NativeLongByReference();
		boolean result = gec.DBECBatchGetTagIntField("demo", pnIDArray, 1024, "FN_ORITAGNAME", pValueArray, 1024);
		return result;
	}

	public boolean DBECBatchGetTagStringField() {
		// TODO Auto-generated method stub
		NativeLongByReference pnIDArray = new NativeLongByReference();
		NativeLongByReference pnValueBytes = new NativeLongByReference();
		StringBuffer lpValueBuffer = new StringBuffer();
		boolean result = gec.DBECBatchGetTagStringField("demo", pnIDArray, 10000001, "FN_ORITAGNAME", lpValueBuffer,
				10000000, pnValueBytes);
		System.out.println(lpValueBuffer.toString());
		return result;
	}*/

	public boolean DBECGetTagRealHistory() {
		// TODO Auto-generated method stub
		// gec.DBECGetTagRealHistory(lpszServerName, lpszTagName, nTagID,
		// nBeginTime, nEndTime, pValueArray, nArraySize, pnValueTimeArray,
		// pnValueCount)
		List<Double> pValueArray = new ArrayList<>();
		List<Long> pnValueTimeArray = new ArrayList<>();

		boolean result = gec.DBECGetTagRealHistory("demo", "CJY_TI2181", 1L,
				(System.currentTimeMillis() - 1000 * 60 * 1) / 1000, System.currentTimeMillis() / 1000, pValueArray,
				5000, pnValueTimeArray);
		for (long l : pnValueTimeArray) {
			System.out.println(l);
		}
		for (Double d : pValueArray) {
			System.out.println(d);
		}
		// System.out.println(pnValueTimeArray.getPointer().getLongArray(0,
		// 1000));
		return result;
	}

	public boolean DBECGetTagIntHistory() {
		// TODO Auto-generated method stub
		// return true;
		List<Long> pValueArray = new ArrayList<>();
		List<Long> pnValueTimeArray = new ArrayList<>();
		boolean result = gec.DBECGetTagIntHistory("demo", null, 2925L,
				(System.currentTimeMillis() - 1000 * 60 * 1) / 1000, System.currentTimeMillis() / 1000, pValueArray,
				5000, pnValueTimeArray);
		for (long l : pnValueTimeArray) {
			System.out.println(l);
		}
		for (long d : pValueArray) {
			System.out.println(d);
		}

		return result;
	}

	public boolean DBECGetTagStringHistory() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean DBACGetCurrentAlarm() {
		// TODO Auto-generated method stub
		List<Long> pnTagIDArray = new ArrayList<>();
		pnTagIDArray.add(2064L);
		pnTagIDArray.add(2063L);
		pnTagIDArray.add(2003L);

		List<Long> pAlarmTypeArray = new ArrayList<>();
		List<Long> pOccuredTimeArray = new ArrayList<>();
		List<Double> pValueArray = new ArrayList<>();
		boolean result = gec.DBACGetCurrentAlarm("demo", pnTagIDArray, pAlarmTypeArray, 3, pValueArray,
				pOccuredTimeArray);

		for (Long l : pAlarmTypeArray) {
			System.out.println(l);
		}
		for (Long l : pOccuredTimeArray) {
			System.out.println(l);
		}
		for (double l : pValueArray) {
			System.out.println(l);
		}

		return result;
	}

	public boolean DBACGetHistoryAlarm() {
		// TODO Auto-generated method stub
		List<Long> pnTagIDArray = new ArrayList<>();
		pnTagIDArray.add(2064L);
		pnTagIDArray.add(2063L);
		pnTagIDArray.add(2003L);
		List<Long> pnAlarmTagIDArray = new ArrayList<>();
		List<Long> pnAlarmCount = new ArrayList<>();
		List<Long> pAlarmBeginTimeArray = new ArrayList<>();
		List<Long> pAlarmEndTimeArray = new ArrayList<>();
		List<Long> pAlarmTypeArray = new ArrayList<>();
		boolean result = gec.DBACGetHistoryAlarm("demo", pnTagIDArray, 1L, System.currentTimeMillis() / 1000,
				pnAlarmTagIDArray, 3, pnAlarmCount, pAlarmBeginTimeArray, pAlarmEndTimeArray, pAlarmTypeArray);
		for (long l : pnAlarmTagIDArray) {
			System.out.println(l);
		}
		for (long l : pnAlarmCount) {
			System.out.println(l);
		}
		for (long l : pAlarmBeginTimeArray) {
			System.out.println(l);
		}
		for (long l : pAlarmEndTimeArray) {
			System.out.println(l);
		}
		for (long l : pAlarmTypeArray) {
			System.out.println(l);
		}

		return result;
	}


}
