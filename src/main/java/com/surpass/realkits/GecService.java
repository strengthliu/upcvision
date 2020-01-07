package com.surpass.realkits;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.surpass.realkits.exception.GecException;

public class GecService {

	protected static volatile GecService gecService = null;
	protected static volatile Gec gec = null;
	protected static volatile String dllPath = null;

	private GecService() {
	}

	public static GecService getGcService() throws GecException {
		if (gecService == null) {
			synchronized (GecService.class) {
				if (gecService == null) {
					gecService = new GecService();
				}
			}
		}
		if (gec == null) {
			synchronized (Gec.class) {
				if (gec == null && dllPath != null) {
					gec = (Gec) Native.loadLibrary(dllPath, Gec.class);
				}
				if (dllPath == null) {
					throw new GecException("please set dll path");
				}
			}
		}
		return gecService;
	}

	public static void setDllPath(String dllPath) {
		GecService.dllPath = dllPath;
	}

	public static Gec getGec() {
		return gec;
	}

	public long DBECGetLastError() {
		return gec.DBECGetLastError().longValue();
	}

	public boolean DBECGetErrorMessage(long nErrorCode, StringBuffer lpMsgBuffer, int nBufLen) {
		// TODO Auto-generated method stub
		ByteBuffer buffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECGetErrorMessage(new NativeLong(nErrorCode), buffer, nBufLen);
		byte[] sb = buffer.array();
		String note = null;
		try {
			note = new String(sb, "GBK");
//			System.out.println("DBECGetErrorMessage => "+note);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		note = note.trim();
		lpMsgBuffer.append(note);
		return result;
	}

	public boolean DBEC_ExitInstance() {
		// TODO Auto-generated method stub
		return gec.DBEC_ExitInstance();
	}

	public long DBECGetServerCurrentTime(String lpszServerName) {
		// TODO Auto-generated method stub
		return gec.DBECGetServerCurrentTime(lpszServerName).longValue();
	}

	public boolean DBECSetLocalServerTime(String lpszServerName) {
		// TODO Auto-generated method stub
		return gec.DBECSetLocalServerTime(lpszServerName);
	}

	/*public boolean DBEC_ConfigServer(boolean bSettingBox, com.surpass.realkits.rundll.Gec1.CStringArray paryServerName,
			com.surpass.realkits.rundll.Gec1.CStringArray paryServerNote,
			com.surpass.realkits.rundll.Gec1.CStringArray paryServerAddress,
			com.surpass.realkits.rundll.Gec1.CDWordArray paryServerPort, IntBuffer pdwCurrentServer) {
		// TODO Auto-generated method stub
		return gec.DBEC_ConfigServer(bSettingBox, paryServerName, paryServerNote, paryServerAddress, paryServerPort,
				pdwCurrentServer);
	}

	public boolean DBEC_GetServerInfo(com.surpass.realkits.rundll.Gec1.CStringArray paryServerName,
			com.surpass.realkits.rundll.Gec1.CStringArray paryServerNote) {
		// TODO Auto-generated method stub
		return gec.DBEC_GetServerInfo(paryServerName, paryServerNote);
	}*/

	public long DBECGetDeviceID(String lpszServerName, String lpszDeviceName) {
		// TODO Auto-generated method stub
		return gec.DBECGetDeviceID(lpszServerName, lpszDeviceName).longValue();
	}

	public boolean DBECGetDeviceName(String lpszServerName, long nDeviceID, StringBuffer lpNameBuffer, int nBufLen) {
		// TODO Auto-generated method stub
		ByteBuffer buffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECGetDeviceName(lpszServerName, new NativeLong(nDeviceID), buffer, nBufLen);
		lpNameBuffer.append(new String(buffer.array()));
		return result;
	}

	public long DBECGetTagID(String lpszServerName, String lpszTagName) {
		// TODO Auto-generated method stub
		return gec.DBECGetTagID(lpszServerName, lpszTagName).longValue();
	}

	public boolean DBECGetTagName(String lpszServerName, long nTagID, StringBuffer lpNameBuffer, int nBufLen) {
		// TODO Auto-generated method stub
		ByteBuffer buffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECGetTagName(lpszServerName, new NativeLong(nTagID), buffer, nBufLen);
		lpNameBuffer.append(new String(buffer.array()));
		return result;
	}

	public long DBECGetFieldID(String lpszFieldName) {
		// TODO Auto-generated method stub
		return gec.DBECGetFieldID(lpszFieldName).longValue();
	}

	/*public boolean DBECGetFieldName(long nFieldID, StringBuffer lpNameBuffer, int nBufLen) {
		// TODO Auto-generated method stub
		ByteBuffer buffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECGetFieldName(new NativeLong(nFieldID), buffer, nBufLen);
		lpNameBuffer.append(new String(buffer.array()));
		return result;
	}*/

	public long DBECGetServerCount() {
		// TODO Auto-generated method stub
		return gec.DBECGetServerCount().longValue();
	}

	public long DBECGetTagMaxCount(String lpszServerName) {
		// TODO Auto-generated method stub
		return gec.DBECGetTagMaxCount(lpszServerName).longValue();
	}

	public long DBECGetClientMaxCount(String lpszServerName) {
		// TODO Auto-generated method stub
		return gec.DBECGetClientMaxCount(lpszServerName).longValue();
	}

	public long DBECGetClientCount(String lpszServerName) {
		// TODO Auto-generated method stub
		return gec.DBECGetClientCount(lpszServerName).longValue();
	}

	public long DBECGetDeviceCount(String lpszServerName) {
		// TODO Auto-generated method stub
		return gec.DBECGetDeviceCount(lpszServerName).longValue();
	}

	public long DBECGetTagCount(String lpszServerName) {
		// TODO Auto-generated method stub
		return gec.DBECGetTagCount(lpszServerName).longValue();
	}

	public long DBECGetTagCountOfDevice(String lpszServerName, String lpszDeviceName, long nDeviceID) {
		// TODO Auto-generated method stub
		return gec.DBECGetTagCountOfDevice(lpszServerName, lpszDeviceName, new NativeLong(nDeviceID)).longValue();
	}

	public long DBECGetServerNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetServerNameMaxLen().longValue();
	}

	public long DBECGetClientNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetClientNameMaxLen().longValue();
	}

	public long DBECGetDeviceNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetDeviceNameMaxLen().longValue();
	}

	public long DBECGetTagNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetTagNameMaxLen().longValue();
	}

	public long DBECGetFieldNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetFieldNameMaxLen().longValue();
	}

	public long DBECGetFieldStringValueMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetFieldStringValueMaxLen().longValue();
	}

	public long DBECGetErrMsgMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetErrMsgMaxLen().longValue();
	}

	public long DBECGetEventNameMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetEventNameMaxLen().longValue();
	}

	public long DBECGetEventConditionMaxLen() {
		// TODO Auto-generated method stub
		return gec.DBECGetEventConditionMaxLen().longValue();
	}

	public long DBECGetTagType(String lpszServerName, String lpszTagName, long nTagID) {
		// TODO Auto-generated method stub
		return gec.DBECGetTagType(lpszServerName, lpszTagName, new NativeLong(nTagID)).longValue();
	}

	public long DBECGetFieldType(String lpszFieldName) {
		// TODO Auto-generated method stub
		return gec.DBECGetFieldType(lpszFieldName).longValue();
	}

	public boolean DBECGetTagRealField(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName,
			DoubleByReference pValue) {
		// TODO Auto-generated method stub
		DoubleBuffer buffer = DoubleBuffer.allocate(1);
		boolean result = gec.DBECGetTagRealField(lpszServerName, lpszTagName, new NativeLong(nTagID), lpszFieldName,
				buffer);
		// pValue = new DoubleByReference(buffer.array()[0]);
		// System.out.println(buffer.array());
		// System.out.println(buffer.array()[0]);
		pValue.setValue(buffer.array()[0]);
		return result;
	}

	public boolean DBECGetTagIntField(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName,
			NativeLong pValue) {
		// TODO Auto-generated method stub
		NativeLongByReference pvalueBuffer = new NativeLongByReference();
		boolean result = gec.DBECGetTagIntField(lpszServerName, lpszTagName, new NativeLong(nTagID), lpszFieldName,
				pvalueBuffer);
		pValue.setValue(pvalueBuffer.getValue().longValue());
		return result;
	}

	public boolean DBECGetTagStringField(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName,
			StringBuffer lpValueBuffer) {
		// TODO Auto-generated method stub
		int nBufLen = new Long(DBECGetFieldStringValueMaxLen()).intValue();
		ByteBuffer buffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECGetTagStringField(lpszServerName, lpszTagName, new NativeLong(nTagID), lpszFieldName,
				buffer, nBufLen);
		lpValueBuffer.append(new String(buffer.array()));
		return result;
	}

	public boolean DBECEnumServerName(List<String> lpNameBuffer, int nBufLen, NativeLong pnNameCount,
			NativeLong pnNameBytes) {
		// TODO Auto-generated method stub
		NativeLongByReference pnNameCountBuffer = new NativeLongByReference();
		NativeLongByReference pnNameBytesBuffer = new NativeLongByReference();
		ByteBuffer nameBuffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECEnumServerName(nameBuffer, nBufLen, pnNameCountBuffer, pnNameBytesBuffer);

		pnNameCount.setValue(pnNameCountBuffer.getValue().longValue());
		pnNameBytes.setValue(pnNameBytesBuffer.getValue().longValue());
		int size = pnNameBytesBuffer.getValue().intValue();
		for (int i = 0; i < nameBuffer.array().length / size; i++) {
			List<Byte> newB = new ArrayList<>();
			for (int j = i * size; j < (i + 1) * size; j++) {
				byte bbb = nameBuffer.array()[j];
				newB.add(bbb);
			}
			byte[] bytes = new byte[newB.size()];
			for (int k = 0; k < newB.size(); k++) {
				bytes[k] = newB.get(k);
			}
			String s = new String(bytes).trim();
			if (s != null && !s.equals("")) {
				lpNameBuffer.add(s);
			}
		}
		return result;
	}

	/*public boolean DBECEnumClientName(String lpszServerName, List<String> lpNameBuffer, int nBufLen,
			NativeLong pnNameCount, NativeLong pnNameBytes) {
		// TODO Auto-generated method stub
		NativeLongByReference pnNameCountBuffer = new NativeLongByReference();
		NativeLongByReference pnNameBytesBuffer = new NativeLongByReference();
		ByteBuffer nameBuffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECEnumClientName(lpszServerName, nameBuffer, nBufLen, pnNameCountBuffer,
				pnNameBytesBuffer);
		pnNameCount.setValue(pnNameCountBuffer.getValue().longValue());
		pnNameBytes.setValue(pnNameBytesBuffer.getValue().longValue());

		for (int i = 0; i < nameBuffer.array().length / 32; i++) {
			List<Byte> newB = new ArrayList<>();
			for (int j = i * 32; j < (i + 1) * 32; j++) {
				byte bbb = nameBuffer.array()[j];
				newB.add(bbb);
			}
			byte[] bytes = new byte[newB.size()];
			for (int k = 0; k < newB.size(); k++) {
				bytes[k] = newB.get(k);
			}
			String s = new String(bytes).trim();
			if (s != null && !s.equals("")) {
				lpNameBuffer.add(s);
			}
		}
		return result;
	}*/

	public boolean DBECEnumDeviceName(String lpszServerName, List<String> lpNameBuffer, int nBufLen,
			NativeLong pnNameCount, NativeLong pnNameBytes) {
		// TODO Auto-generated method stub
		NativeLongByReference pnNameCountBuffer = new NativeLongByReference();
		NativeLongByReference pnNameBytesBuffer = new NativeLongByReference();
		ByteBuffer nameBuffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECEnumDeviceName(lpszServerName, nameBuffer, nBufLen, pnNameCountBuffer,
				pnNameBytesBuffer);
		pnNameCount.setValue(pnNameCountBuffer.getValue().longValue());
		pnNameBytes.setValue(pnNameBytesBuffer.getValue().longValue());
		int size = pnNameBytes.intValue();
		for (int i = 0; i < nameBuffer.array().length / size; i++) {
			List<Byte> newB = new ArrayList<>();
			for (int j = i * size; j < (i + 1) * size; j++) {
				byte bbb = nameBuffer.array()[j];
				newB.add(bbb);
			}
			byte[] bytes = new byte[newB.size()];
			for (int k = 0; k < newB.size(); k++) {
				bytes[k] = newB.get(k);
			}
			String s = new String(bytes).trim();
			if (s != null && !s.equals("")) {
				lpNameBuffer.add(s);
			}
		}
		return result;
	}

/*	public boolean DBECEnumTagName(String lpszServerName, StringBuffer lpNameBuffer, int nBufLen,
			NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes) {
		// TODO Auto-generated method stub
		Long tagCount = DBECGetTagCount(lpszServerName);
		Long tagNameMaxLen = DBECGetTagNameMaxLen();
		// ByteBuffer nameBuffer = ByteBuffer.allocate(tagCount.intValue() *
		// tagNameMaxLen.intValue());
		PointerByReference nameBuffer = new PointerByReference();
		boolean result = gec.DBECEnumTagName(lpszServerName, nameBuffer, nBufLen, pnNameCount, pnNameBytes);
		// lpNameBuffer.append(new String(nameBuffer.array()));
		// System.out.println(nameBuffer.getString(0));
		System.out.println();
		return result;
	}*/

	public boolean DBECEnumTagID(String lpszServerName, List<Long> pNameIDBuffer) {
		// TODO Auto-generated method stub
		NativeLong[] idArray = new NativeLong[new Long(this.DBECGetTagCount(lpszServerName)).intValue()];
		boolean result = gec.DBECEnumTagID(lpszServerName, idArray);
		for (NativeLong l : idArray) {
			long ll = l.longValue();
			if (ll == 0) {
				break;
			}
			pNameIDBuffer.add(ll);
		}
		return result;
	}

/*	public boolean DBECEnumTagNameOfDevice(String lpszServerName, String lpszDeviceName, long nDeviceID,
			StringBuffer lpNameBuffer, int nBufLen, NativeLongByReference pnNameCount,
			NativeLongByReference pnNameBytes) {
		// TODO Auto-generated method stub
		ByteBuffer nameBuffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECEnumTagNameOfDevice(lpszServerName, lpszDeviceName, new NativeLong(nDeviceID),
				nameBuffer, nBufLen, pnNameCount, pnNameBytes);
		lpNameBuffer.append(new String(nameBuffer.array()));
		return result;
	}
*/
	public boolean DBECEnumTagIDOfDevice(String lpszServerName, String lpszDeviceName,long nDeviceID, List<Long> pNameIDBuffer) {
		NativeLong[] idArray = new NativeLong[new Long(this.DBECGetTagCount(lpszServerName)).intValue()];
		boolean result = gec.DBECEnumTagIDOfDevice(lpszServerName,lpszDeviceName,new NativeLong(nDeviceID), idArray);
		for (NativeLong l : idArray) {
			long ll = l.longValue();
			if (ll == 0) {
				break;
			}
			pNameIDBuffer.add(ll);
		}
		return result;
	}

	public boolean DBECEnumTagExtendType(String lpszServerName, List<String> lpNameBuffer, int nBufLen,
			NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes) {
		// TODO Auto-generated method stub
		ByteBuffer nameBuffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECEnumTagExtendType(lpszServerName, nameBuffer, nBufLen, pnNameCount, pnNameBytes);
//		lpNameBuffer.append(new String(nameBuffer.array()));
		int size = pnNameBytes.getValue().intValue();
		for (int i = 0; i < nameBuffer.array().length / size; i++) {
			List<Byte> newB = new ArrayList<>();
			for (int j = i * size; j < (i + 1) * size; j++) {
				byte bbb = nameBuffer.array()[j];
				newB.add(bbb);
			}
			byte[] bytes = new byte[newB.size()];
			for (int k = 0; k < newB.size(); k++) {
				bytes[k] = newB.get(k);
			}
			String s = new String(bytes).trim();
			if (s != null && !s.equals("")) {
				lpNameBuffer.add(s);
			}
		}
		return result;
	}

	/*public boolean DBECBatchGetDeviceID(String lpszServerName, List<String> lpNameBuffer, List<Long> pnIDArray) {
		// TODO Auto-generated method stub
		List<Byte> nameBufferByte = new ArrayList<>();
		for (String lpName : lpNameBuffer) {
			List<Byte> blist = new ArrayList<>();
			for (byte b : lpName.getBytes()) {
				blist.add(b);
			}
			nameBufferByte.addAll(blist);
		}

		byte[] nameBuffer = new byte[nameBufferByte.size()];
		for (int i = 0; i < nameBufferByte.size(); i++) {
			nameBuffer[i] = nameBufferByte.get(i);
		}

		LongBuffer idArray = LongBuffer.allocate(2);
		boolean result = gec.DBECBatchGetDeviceID(lpszServerName, nameBuffer, lpNameBuffer.size(), 33, idArray,
				lpNameBuffer.size());
		// for(int i = 0;i < pnIDArray.size();i++){
		// idArray[i] = new NativeLong(pnIDArray.get(i));
		// }
		for (Long l : idArray.array()) {
			pnIDArray.add(l);
		}
		return result;
	}*/

	/*public boolean DBECBatchGetDeviceName(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			StringBuffer lpNameBuffer, int nBufLen, NativeLongByReference pnNameBytes) {
		// TODO Auto-generated method stub
		ByteBuffer nameBuffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECBatchGetDeviceName(lpszServerName, pnIDArray, nIDCount, nameBuffer, nBufLen,
				pnNameBytes);
		lpNameBuffer.append(new String(nameBuffer.array()));
		return result;
	}*/

	/*public boolean DBECBatchGetTagID(String lpszServerName, String lpNameBuffer, int nNameCount, int nNameBytes,
			NativeLongByReference pnIDArray, int nArraySize) {
		// TODO Auto-generated method stub
		return gec.DBECBatchGetTagID(lpszServerName, lpNameBuffer, nNameCount, nNameBytes, pnIDArray, nArraySize);
	}

	public boolean DBECBatchGetTagName(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			StringBuffer lpNameBuffer, int nBufLen, NativeLongByReference pnNameBytes) {
		// TODO Auto-generated method stub
		ByteBuffer nameBuffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECBatchGetTagName(lpszServerName, pnIDArray, nIDCount, nameBuffer, nBufLen, pnNameBytes);
		lpNameBuffer.append(new String(nameBuffer.array()));
		return result;
	}*/

	public boolean DBECBatchGetTagRealField(String lpszServerName, List<Long> pnIDArray, String lpszFieldName,
			List<Double> pValueArray) {
		// TODO Auto-generated method stub
		// double[] valueArray = new double[nArraySize];
		DoubleBuffer valueArray = DoubleBuffer.allocate(pnIDArray.size());
		NativeLong[] pnIdArrayLong = new NativeLong[pnIDArray.size()];
		for (int i = 0; i < pnIDArray.size(); i++) {
			pnIdArrayLong[i] = new NativeLong(pnIDArray.get(i));
		}
		boolean result = gec.DBECBatchGetTagRealField(lpszServerName, pnIdArrayLong, pnIDArray.size(), lpszFieldName,
				valueArray, pnIDArray.size());
		for (double d : valueArray.array()) {
			pValueArray.add(d);
		}
		return result;
	}
	/*
	public boolean DBECBatchGetTagIntField(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			String lpszFieldName, NativeLongByReference pValueArray, int nArraySize) {
		// TODO Auto-generated method stub
		return gec.DBECBatchGetTagIntField(lpszServerName, pnIDArray, nIDCount, lpszFieldName, pValueArray, nArraySize);
	}

	public boolean DBECBatchGetTagStringField(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			String lpszFieldName, StringBuffer lpValueBuffer, int nBufLen, NativeLongByReference pnValueBytes) {
		// TODO Auto-generated method stub
		ByteBuffer valuebuffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECBatchGetTagStringField(lpszServerName, pnIDArray, nIDCount, lpszFieldName, valuebuffer,
				nBufLen, pnValueBytes);
		lpValueBuffer.append(new String(valuebuffer.array()));
		return result;
	}*/

	public boolean DBECGetTagRealHistory(String lpszServerName, String lpszTagName, long nTagID, long nBeginTime,
			long nEndTime, List<Double> pValueArray, int nArraySize, List<Long> pnValueTimeArray) {
		// TODO Auto-generated method stub
		NativeLong[] pnValueTimeArrayLong = new NativeLong[nArraySize];
		DoubleBuffer valueArray = DoubleBuffer.allocate(nArraySize);
		NativeLongByReference pnValueCount = new NativeLongByReference();
		boolean result = gec.DBECGetTagRealHistory(lpszServerName, lpszTagName, new NativeLong(nTagID),
				new NativeLong(nBeginTime), new NativeLong(nEndTime), valueArray, nArraySize, pnValueTimeArrayLong,
				pnValueCount);
		for (int i = 0; i < pnValueCount.getValue().longValue(); i++) {
			// System.out.println(valueArray.array()[i]);
			pValueArray.add(valueArray.array()[i]);
			pnValueTimeArray.add(pnValueTimeArrayLong[i].longValue());
		}
		return result;
	}

	public boolean DBECGetTagIntHistory(String lpszServerName, String lpszTagName, long nTagID, long nBeginTime,
			long nEndTime, List<Long> pValueArray, int nArraySize, List<Long> pnValueTimeArray) {
		// TODO Auto-generated method stub
		NativeLong[] pnValueTimeArrayLong = new NativeLong[nArraySize];
		NativeLong[] valueArray = new NativeLong[nArraySize];
		NativeLongByReference pnValueCount = new NativeLongByReference();
		boolean result = gec.DBECGetTagIntHistory(lpszServerName, lpszTagName, new NativeLong(nTagID),
				new NativeLong(nBeginTime), new NativeLong(nEndTime), valueArray, nArraySize, pnValueTimeArrayLong,
				pnValueCount);
		for (int i = 0; i < pnValueCount.getValue().longValue(); i++) {
			pValueArray.add(valueArray[i].longValue());
			pnValueTimeArray.add(pnValueTimeArrayLong[i].longValue());
		}
		return result;
	}

	public boolean DBECGetTagStringHistory(String lpszServerName, String lpszTagName, long nTagID, long nBeginTime,
			long nEndTime, StringBuffer pValueBuffer, int nBufLen, NativeLongByReference pnValueTimeArray,
			int nArraySize, NativeLongByReference pnValueCount, NativeLongByReference pnValueBytes) {
		// TODO Auto-generated method stub
		ByteBuffer valuebuffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECGetTagStringHistory(lpszServerName, lpszTagName, new NativeLong(nTagID),
				new NativeLong(nBeginTime), new NativeLong(nEndTime), valuebuffer, nBufLen, pnValueTimeArray,
				nArraySize, pnValueCount, pnValueBytes);
		pValueBuffer.append(new String(valuebuffer.array()));
		return result;
	}

	public boolean DBACGetCurrentAlarm(String lpszServerName, List<Long> pnTagIDArray, List<Long> pAlarmTypeArray,
			int nArraySize, List<Double> pValueArray, List<Long> pOccuredTimeArray) {
		// TODO Auto-generated method stub
		DoubleBuffer valueArray = DoubleBuffer.allocate(nArraySize);
		NativeLong[] pnTagIDArrayLong = new NativeLong[pnTagIDArray.size()];
		for (int i = 0; i < pnTagIDArray.size(); i++) {
			pnTagIDArrayLong[i] = new NativeLong(pnTagIDArray.get(i));
		}
		NativeLong[] pAlarmTypeArrayLong = new NativeLong[nArraySize];
		NativeLong[] pOccuredTimeArrayLong = new NativeLong[nArraySize];
		boolean result = gec.DBACGetCurrentAlarm(lpszServerName, pnTagIDArrayLong, pnTagIDArray.size(),
				pAlarmTypeArrayLong, nArraySize, valueArray, pOccuredTimeArrayLong);
		for (double d : valueArray.array()) {
			pValueArray.add(d);
		}
		for (int i = 0; i < nArraySize; i++) {
			pAlarmTypeArray.add(pAlarmTypeArrayLong[i].longValue());
			pOccuredTimeArray.add(pOccuredTimeArrayLong[i].longValue());
		}
		return result;
	}

	public boolean DBACGetHistoryAlarm(String lpszServerName, List<Long> pnTagIDArray, long nBeginTime, long nEndTime,
			List<Long> pnAlarmTagIDArray, int nArraySize, List<Long> pnAlarmCount, List<Long> pAlarmBeginTimeArray,
			List<Long> pAlarmEndTimeArray, List<Long> pAlarmTypeArray) {
		// TODO Auto-generated method stub
		NativeLong[] pnTagIDArrayLong = new NativeLong[pnTagIDArray.size()];
		for (int i = 0; i < pnTagIDArray.size(); i++) {
			pnTagIDArrayLong[i] = new NativeLong(pnTagIDArray.get(i));
		}
		NativeLong[] pnAlarmTagIDArrayLong = new NativeLong[nArraySize];
		NativeLong[] pnAlarmCountLong = new NativeLong[nArraySize];
		NativeLong[] pAlarmBeginTimeArrayLong = new NativeLong[nArraySize];
		NativeLong[] pAlarmEndTimeArrayLong = new NativeLong[nArraySize];
		NativeLong[] pAlarmTypeArrayLong = new NativeLong[nArraySize];
		boolean result = gec.DBACGetHistoryAlarm(lpszServerName, pnTagIDArrayLong, pnTagIDArray.size(),
				new NativeLong(nBeginTime), new NativeLong(nEndTime), pnAlarmTagIDArrayLong, nArraySize,
				pnAlarmCountLong, pAlarmBeginTimeArrayLong, pAlarmEndTimeArrayLong, pAlarmTypeArrayLong);
		for (int i = 0; i < nArraySize; i++) {
			pnAlarmTagIDArray.add(pnAlarmTagIDArrayLong[i].longValue());
			pnAlarmCount.add(pnAlarmCountLong[i].longValue());
			pAlarmBeginTimeArray.add(pAlarmBeginTimeArrayLong[i].longValue());
			pAlarmEndTimeArray.add(pAlarmEndTimeArrayLong[i].longValue());
			pAlarmTypeArray.add(pAlarmTypeArrayLong[i].longValue());
		}

		return result;
	}

    public boolean DBECGetDeviceNote(String lpszServerName, String lpszDeviceName, long nDeviceID, StringBuffer lpNoteBuffer, int nBufLen)
    {
        ByteBuffer buffer = ByteBuffer.allocate(nBufLen);
        boolean result = gec.DBECGetDeviceNote(lpszServerName, lpszDeviceName, new NativeLong(nDeviceID), buffer, new NativeLong(nBufLen));
        lpNoteBuffer.append(new String(buffer.array()));
        return result;
    }

	public String DBECGetTagStringFields(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName) {
		// TODO Auto-generated method stub
		int nBufLen = new Long(DBECGetFieldStringValueMaxLen()).intValue();
		ByteBuffer buffer = ByteBuffer.allocate(nBufLen);
		boolean result = gec.DBECGetTagStringField(lpszServerName, lpszTagName, new NativeLong(nTagID), lpszFieldName,
				buffer, nBufLen);
		byte[] sb = buffer.array();
		String deviceNote = null;
		try {
			deviceNote = new String(sb, "GBK");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		return deviceNote = deviceNote.trim();
		return deviceNote.trim();
		
//		return result;
	}

	public String DBECGetDeviceNote(String serverName, String deviceName, Long deviceId) {
		int len = 80;
        ByteBuffer buffer = ByteBuffer.allocate(len);
        boolean result = gec.DBECGetDeviceNote(serverName, deviceName, new NativeLong(deviceId), buffer, new NativeLong(len));
		byte[] sb = buffer.array();
		String deviceNote = null;
		try {
			deviceNote = new String(sb, "GBK");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		return deviceNote = deviceNote.trim();
		return deviceNote.trim();
	}

	/*public boolean DBACEnumEventRecordName(String lpszServerName, long nBeginTime, long nEndTime,
			StringBuffer lpNameBuffer, long nBufLen, NativeLongByReference pnNameCount,
			NativeLongByReference pnNameBytes) {
		// TODO Auto-generated method stub
		ByteBuffer namebuffer = ByteBuffer.allocate(new Long(nBufLen).intValue());
		boolean result = gec.DBACEnumEventRecordName(lpszServerName, new NativeLong(nBeginTime),
				new NativeLong(nEndTime), namebuffer, new NativeLong(nBufLen), pnNameCount, pnNameBytes);
		lpNameBuffer.append(new String(namebuffer.array()));
		return result;
	}

	public boolean DBACGetEventRecordData(String lpszServerName, String lpszEventRecordName, String lpszTagName,
			List<Double> pBeforeEventDataArray, long nBeforeArraySize, NativeLongByReference pnBeforeEventTimeArray,
			NativeLongByReference pnBeforeEventCount, List<Double> pAfterEventDataArray, long nAfterArraySize,
			NativeLongByReference pnAfterEventTimeArray, NativeLongByReference pnAfterEventCount) {
		// TODO Auto-generated method stub
		DoubleBuffer beforeArray = DoubleBuffer.allocate(new Long(nBeforeArraySize).intValue());
		DoubleBuffer afterArray = DoubleBuffer.allocate(new Long(nAfterArraySize).intValue());
		boolean result = gec.DBACGetEventRecordData(lpszServerName, lpszEventRecordName, lpszTagName, beforeArray,
				new NativeLong(nBeforeArraySize), pnBeforeEventTimeArray, pnBeforeEventCount, afterArray,
				new NativeLong(nAfterArraySize), pnAfterEventTimeArray, pnAfterEventCount);
		for (double d : beforeArray.array()) {
			pBeforeEventDataArray.add(d);
		}
		for (double d : afterArray.array()) {
			pAfterEventDataArray.add(d);
		}
		return result;
	}

	public boolean DBACEnumEventName(String lpszServerName, StringBuffer lpNameBuffer, long nBufLen,
			NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes) {
		// TODO Auto-generated method stub
		ByteBuffer nameBuffer = ByteBuffer.allocate(new Long(nBufLen).intValue());
		boolean result = gec.DBACEnumEventName(lpszServerName, nameBuffer, new NativeLong(nBufLen), pnNameCount,
				pnNameBytes);
		lpNameBuffer.append(new String(nameBuffer.array()));
		return result;
	}

	public boolean DBACEnumEventTagName(String lpszServerName, String lpszEventName, StringBuffer lpNameBuffer,
			long nBufLen, NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes) {
		// TODO Auto-generated method stub
		ByteBuffer nameBuffer = ByteBuffer.allocate(new Long(nBufLen).intValue());
		boolean result = gec.DBACEnumEventTagName(lpszServerName, lpszEventName, nameBuffer, new NativeLong(nBufLen),
				pnNameCount, pnNameBytes);
		lpNameBuffer.append(new String(nameBuffer.array()));
		return result;
	}

	public boolean DBACGetEventCondition(String lpszServerName, String lpszEventName, StringBuffer lpConditionBuffer,
			long nBufLen) {
		// TODO Auto-generated method stub
		ByteBuffer conditionBuffer = ByteBuffer.allocate(new Long(nBufLen).intValue());
		boolean result = gec.DBACGetEventCondition(lpszServerName, lpszEventName, conditionBuffer,
				new NativeLong(nBufLen));
		lpConditionBuffer.append(new String(conditionBuffer.array()));
		return result;
	}

	public boolean DBACGetEventName(String lpszServerName, String lpszEventRecordName, StringBuffer lpNameBuffer,
			long nBufLen) {
		// TODO Auto-generated method stub
		ByteBuffer nameBuffer = ByteBuffer.allocate(new Long(nBufLen).intValue());
		boolean result = gec.DBACGetEventName(lpszServerName, lpszEventRecordName, nameBuffer, new NativeLong(nBufLen));
		lpNameBuffer.append(new String(nameBuffer.array()));
		return result;
	}

	public boolean DBACGetEventRecordTime(String lpszServerName, String lpszEventRecordName,
			NativeLongByReference pnTime) {
		// TODO Auto-generated method stub
		return gec.DBACGetEventRecordTime(lpszServerName, lpszEventRecordName, pnTime);
	}

	public boolean DBECBatchWriteTagData(String lpszServerName, NativeLongByReference pnTagIDArray, int nTagCount,
			PointerByReference lpDataAddressArray, long nDataTime, int nDataType) {
		// TODO Auto-generated method stub
		return gec.DBECBatchWriteTagData(lpszServerName, pnTagIDArray, nTagCount, lpDataAddressArray,
				new NativeLong(nDataTime), nDataType);
	}

	public boolean DBECWriteHistory(String lpszServerName, String lpszTagName, long nTagID,
			PointerByReference lpDataAddressArray, NativeLongByReference pnDataTimeArray, int nDataCount,
			int nDataType) {
		// TODO Auto-generated method stub
		return gec.DBECWriteHistory(lpszServerName, lpszTagName, new NativeLong(nTagID), lpDataAddressArray,
				pnDataTimeArray, nDataCount, nDataType);
	}

	public boolean DBECSetTagRealField(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName,
			double dbValue) {
		// TODO Auto-generated method stub
		return gec.DBECSetTagRealField(lpszServerName, lpszTagName, new NativeLong(nTagID), lpszFieldName, dbValue);
	}

	public boolean DBECSetTagIntField(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName,
			long nValue) {
		// TODO Auto-generated method stub
		return gec.DBECSetTagIntField(lpszServerName, lpszTagName, new NativeLong(nTagID), lpszFieldName,
				new NativeLong(nValue));
	}

	public boolean DBECSetTagStringField(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName,
			StringBuffer lpValueString) {
		// TODO Auto-generated method stub
		ByteBuffer valueBuffer = ByteBuffer.allocate(256);
		boolean result = gec.DBECSetTagStringField(lpszServerName, lpszTagName, new NativeLong(nTagID), lpszFieldName,
				valueBuffer);
		lpValueString.append(new String(valueBuffer.array()));
		return result;
	}

	public boolean DBECBatchSetTagRealField(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			String lpszFieldName, List<Double> pdbValueArray, int nArraySize) {
		// TODO Auto-generated method stub
		DoubleBuffer valueArray = DoubleBuffer.allocate(nArraySize);
		boolean result = gec.DBECBatchSetTagRealField(lpszServerName, pnIDArray, nIDCount, lpszFieldName, valueArray,
				nArraySize);
		for (double d : valueArray.array()) {
			pdbValueArray.add(d);
		}
		return result;
	}

	public boolean DBECBatchSetTagIntField(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			String lpszFieldName, NativeLongByReference pnValueArray, int nArraySize) {
		// TODO Auto-generated method stub
		return gec.DBECBatchSetTagIntField(lpszServerName, pnIDArray, nIDCount, lpszFieldName, pnValueArray,
				nArraySize);
	}

	// lpValueStringBuffer ����ַ����Ļ����������Ի����û�����������ת��stringbuffer
	public boolean DBECBatchSetTagStringField(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			String lpszFieldName, ByteBuffer lpValueStringBuffer, int nBufLen, long nValueBytes) {
		// TODO Auto-generated method stub
		return gec.DBECBatchSetTagStringField(lpszServerName, pnIDArray, nIDCount, lpszFieldName, lpValueStringBuffer,
				nBufLen, new NativeLong(nValueBytes));
	}

	public long DBEXGetTimeT(int nYear, int nMonth, int nDay, int nHour, int nMinute, int nSecond) {
		// TODO Auto-generated method stub
		return gec.DBEXGetTimeT(nYear, nMonth, nDay, nHour, nMinute, nSecond).longValue();
	}

	public boolean DBEXGetDateTime(long nTimeT, NativeLongByReference pnYear, NativeLongByReference pnMonth,
			NativeLongByReference pnDay, NativeLongByReference pnHour, NativeLongByReference pnMinute,
			NativeLongByReference pnSecond) {
		// TODO Auto-generated method stub
		return gec.DBEXGetDateTime(new NativeLong(nTimeT), pnYear, pnMonth, pnDay, pnHour, pnMinute, pnSecond);
	}

	public boolean DBEXFreeDoubleMemory(PointerByReference lppBuffer) {
		// TODO Auto-generated method stub
		return gec.DBEXFreeDoubleMemory(lppBuffer);
	}

	public boolean DBEXFreeLongMemory(PointerByReference lppBuffer) {
		// TODO Auto-generated method stub
		return gec.DBEXFreeLongMemory(lppBuffer);
	}

	public boolean DBEC_FreeMemory(PointerByReference lppBuffer) {
		// TODO Auto-generated method stub
		return gec.DBEC_FreeMemory(lppBuffer);
	}

	public boolean DBECGetTagRealHistoryEx(String lpszServerName, String lpszTagName, long nTagID, long nBeginTime,
			long nEndTime, PointerByReference lpOutValueBuffer, PointerByReference lpOutTimeBuffer,
			NativeLongByReference pnValueCount) {
		// TODO Auto-generated method stub
		return gec.DBECGetTagRealHistoryEx(lpszServerName, lpszTagName, new NativeLong(nTagID),
				new NativeLong(nBeginTime), new NativeLong(nEndTime), lpOutValueBuffer, lpOutTimeBuffer, pnValueCount);
	}

	public boolean DBECGetTagRealHistoryEx_OK(PointerByReference lpOutValueBuffer, PointerByReference lpOutTimeBuffer) {
		// TODO Auto-generated method stub
		return gec.DBECGetTagRealHistoryEx_OK(lpOutValueBuffer, lpOutTimeBuffer);
	}

	public boolean DBECGetTagCumulate(String lpszServerName, String lpszTagName, long nTagID, long nBeginTime,
			long nEndTime, DoubleBuffer pValue, DoubleBuffer pLastValue, NativeLongByReference pnLastTime) {
		// TODO Auto-generated method stub
		return gec.DBECGetTagCumulate(lpszServerName, lpszTagName, new NativeLong(nTagID), new NativeLong(nBeginTime),
				new NativeLong(nEndTime), pValue, pLastValue, pnLastTime);
	}

	public boolean DBECEnumTagNameEx(String lpszServerName, ByteBuffer lpNameBuffer, long nBufLen,
			NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes, long nBeginNum, long nEndNum) {
		// TODO Auto-generated method stub
		return gec.DBECEnumTagNameEx(lpszServerName, lpNameBuffer, new NativeLong(nBufLen), pnNameCount, pnNameBytes,
				new NativeLong(nBeginNum), new NativeLong(nEndNum));
	}

	public boolean DBECGetDeviceNote(String lpszServerName, String lpszDeviceName, long nDeviceID,
			ByteBuffer lpNoteBuffer, long nBufLen) {
		// TODO Auto-generated method stub
		return gec.DBECGetDeviceNote(lpszServerName, lpszDeviceName, new NativeLong(nDeviceID), lpNoteBuffer,
				new NativeLong(nBufLen));
	}

	public boolean DBACGetHistoryAlarmEx(String lpszServerName, NativeLongByReference pnTagIDArray, int nTagCount,
			long nBeginTime, long nEndTime, PointerByReference lpnAlarmTagIDArray, NativeLongByReference pnAlarmCount,
			PointerByReference lpAlarmBeginTimeArray, PointerByReference lpAlarmEndTimeArray,
			PointerByReference lpAlarmTypeArray) {
		// TODO Auto-generated method stub
		return gec.DBACGetHistoryAlarmEx(lpszServerName, pnTagIDArray, nTagCount, new NativeLong(nBeginTime),
				new NativeLong(nEndTime), lpnAlarmTagIDArray, pnAlarmCount, lpAlarmBeginTimeArray, lpAlarmEndTimeArray,
				lpAlarmTypeArray);
	}

	public boolean DBACGetHistoryAlarmEx_OK(PointerByReference lpnAlarmTagIDArray,
			PointerByReference lpAlarmBeginTimeArray, PointerByReference lpAlarmEndTimeArray,
			PointerByReference lpAlarmTypeArray) {
		// TODO Auto-generated method stub
		return gec.DBACGetHistoryAlarmEx_OK(lpnAlarmTagIDArray, lpAlarmBeginTimeArray, lpAlarmEndTimeArray,
				lpAlarmTypeArray);
	}*/

}
