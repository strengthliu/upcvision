package com.surpass.realkits;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.surpass.realkits.exception.GecException;

public class JGecService {
	private GecService gecService;

	public JGecService() throws Exception {
		gecService = GecService.getGcService();
	}

	public JGecService(String dllPath) throws GecException {
		GecService.setDllPath(dllPath);
		gecService = GecService.getGcService();
	}

	public GecService getGecService() {
		return gecService;
	}

	public long DBECGetLastError() {
		return gecService.DBECGetLastError();
	}

	public String DBECGetErrorMessage(long nErrorCode) throws GecException {
		StringBuffer lpMsgBuffer = new StringBuffer();
		
		Long maxLen = DBECGetErrMsgMaxLen();
		boolean result = gecService.DBECGetErrorMessage(nErrorCode, lpMsgBuffer, maxLen.intValue());
		if (!result) {
			throw new GecException();
		}
		return lpMsgBuffer.toString();
	}

	public void DBEC_ExitInstance() throws GecException {
		throwException(gecService.DBEC_ExitInstance());
	}

	public long DBECGetServerCurrentTime(String lpszServerName) {
		return gecService.DBECGetServerCurrentTime(lpszServerName);
	}

	public void DBECSetLocalServerTime(String lpszServerName) throws GecException {
		throwException(gecService.DBECSetLocalServerTime(lpszServerName));
	}

	public long DBECGetDeviceID(String lpszServerName, String lpszDeviceName) throws GecException {
		long id = gecService.DBECGetDeviceID(lpszServerName, lpszDeviceName);
		throwException(id != 0);
		return id;
	}

	public String DBECGetDeviceName(String lpszServerName, long nDeviceID) throws GecException {
		StringBuffer lpNameBuffer = new StringBuffer();
		Long nBufLen = gecService.DBECGetDeviceNameMaxLen();
		throwException(gecService.DBECGetDeviceName(lpszServerName, nDeviceID, lpNameBuffer, nBufLen.intValue()));
		return lpNameBuffer.toString();
	}

	public long DBECGetTagID(String lpszServerName, String lpszTagName) throws GecException {
		long id = gecService.DBECGetTagID(lpszServerName, lpszTagName);
		throwException(id != 0);
		return id;
	}

	public long DBECGetFieldID(String lpszFieldName) throws GecException {
		long id = gecService.DBECGetFieldID(lpszFieldName);
		throwException(id != 0);
		return id;
	}

	public long DBECGetServerCount() {
		return gecService.DBECGetServerCount();
	}

	public long DBECGetTagMaxCount(String lpszServerName) {
		return gecService.DBECGetTagMaxCount(lpszServerName);
	}

	public long DBECGetClientMaxCount(String lpszServerName) {
		return gecService.DBECGetClientMaxCount(lpszServerName);
	}

	public long DBECGetClientCount(String lpszServerName) {
		return gecService.DBECGetClientCount(lpszServerName);
	}

	public long DBECGetDeviceCount(String lpszServerName) {
		return gecService.DBECGetDeviceCount(lpszServerName);
	}

	public long DBECGetTagCount(String lpszServerName) {
		return gecService.DBECGetTagCount(lpszServerName);
	}

	public long DBECGetTagCountOfDevice(String lpszServerName, long nDeviceID) {
		return gecService.DBECGetTagCountOfDevice(lpszServerName, null, nDeviceID);
	}

	public long DBECGetTagCountOfDeviceByDeviceName(String lpszServerName, String lpszDeviceName) {
		return gecService.DBECGetTagCountOfDevice(lpszServerName, lpszDeviceName, 0L);
	}
	// System.out.println(test.DBECGetTagCountOfDevice());

	public long DBECGetServerNameMaxLen() {
		return gecService.DBECGetServerNameMaxLen();
	}

	public long DBECGetClientNameMaxLen() {
		return gecService.DBECGetClientNameMaxLen();
	}

	public long DBECGetDeviceNameMaxLen() {
		return gecService.DBECGetDeviceNameMaxLen();
	}

	public long DBECGetTagNameMaxLen() {
		return gecService.DBECGetTagNameMaxLen();
	}

	public long DBECGetFieldNameMaxLen() {
		return gecService.DBECGetFieldNameMaxLen();
	}

	public long DBECGetFieldStringValueMaxLen() {
		return gecService.DBECGetFieldStringValueMaxLen();
	}

	public long DBECGetErrMsgMaxLen() {
		return gecService.DBECGetErrMsgMaxLen();
	}

	public long DBECGetEventNameMaxLen() {
		return gecService.DBECGetEventNameMaxLen();
	}

	public long DBECGetEventConditionMaxLen() {
		return gecService.DBECGetEventConditionMaxLen();
	}

	public long DBECGetTagType(String lpszServerName, long nTagID) {
		return gecService.DBECGetTagType(lpszServerName, null, nTagID);
	}

	public long DBECGetTagTypeByTagName(String lpszServerName, String lpszTagName) {
		return gecService.DBECGetTagType(lpszServerName, lpszTagName, 0L);
	}
	
	public String DBECGetTagName(String serverName,long tagId) throws GecException{
		StringBuffer lpNameBuffer = new StringBuffer();
		Long length = this.DBECGetTagNameMaxLen();
		throwException(gecService.DBECGetTagName(serverName, tagId, lpNameBuffer, length.intValue()));
		return lpNameBuffer.toString();
	}

	public long DBECGetFieldType(String lpszFieldName) throws GecException {
		long id = gecService.DBECGetFieldType(lpszFieldName);
		throwException(id != 0);
		return id;
	}

	public List<String> DBECEnumServerName()
			throws GecException {
		List<String> lpNameBuffer = new ArrayList<>();
		Long nBufLen = DBECGetServerNameMaxLen() * this.DBECGetServerCount();
		NativeLong pnNameCount = new NativeLong();
		NativeLong pnNameBytes = new NativeLong();
		throwException(gecService.DBECEnumServerName(lpNameBuffer, nBufLen.intValue(), pnNameCount, pnNameBytes));
		return lpNameBuffer;
	}

	public List<String> DBECEnumDeviceName(String lpszServerName) throws GecException {
		Long nBufLenLong = this.DBECGetDeviceNameMaxLen() * this.DBECGetDeviceCount(lpszServerName);
//		, int nBufLen,
		NativeLong pnNameCount = new NativeLong();
		NativeLong pnNameBytes = new NativeLong();
		List<String> lpNameBuffer = new ArrayList<>();
		throwException(gecService.DBECEnumDeviceName(lpszServerName, lpNameBuffer,nBufLenLong.intValue() , pnNameCount, pnNameBytes));
		return lpNameBuffer;
	}

	public double DBECGetTagRealField(String lpszServerName, long nTagID, String lpszFieldName)
			throws GecException {
		DoubleByReference db = new DoubleByReference();
		throwException(gecService.DBECGetTagRealField(lpszServerName, null, nTagID, lpszFieldName, db));
		return db.getValue();
	}

	public double DBECGetTagRealFieldByTagName(String lpszServerName, String lpszTagName,
			String lpszFieldName) throws GecException {
		DoubleByReference db = new DoubleByReference();
		throwException(gecService.DBECGetTagRealField(lpszServerName, lpszTagName, 0L, lpszFieldName, db));
		return db.getValue();
	}

	// System.out.println(test.DBECGetTagRealField());// �ɹ�����Ҫ�ӿڣ�Ŀǰ�]���}
	public List<Double> DBECBatchGetTagRealField(String lpszServerName, List<Long> pnIDArray, String lpszFieldName)
			throws GecException {
		List<Double> pValueArray = new ArrayList<>();
		throwException(gecService.DBECBatchGetTagRealField(lpszServerName, pnIDArray, lpszFieldName, pValueArray));
		return pValueArray;
	}
	// System.out.println(test.DBECBatchGetTagRealField());//�ɹ�����Ҫ�ӿڣ�Ŀǰ�]���}

	public List<String> DBECEnumTagExtendType(String lpszServerName) throws GecException {
		Long nBufLen = this.DBECGetTagNameMaxLen();

		NativeLongByReference pnNameCount = new NativeLongByReference();
		NativeLongByReference pnNameBytes = new NativeLongByReference();
		List<String> lpNameBuffer = new ArrayList<>();
		throwException(gecService.DBECEnumTagExtendType(lpszServerName, lpNameBuffer, nBufLen.intValue(), pnNameCount,
				pnNameBytes));
		return lpNameBuffer;
	}
	// System.out.println(test.DBECEnumTagExtendType());

	public List<Long> DBECEnumTagID(String lpszServerName) throws GecException {
		List<Long> pNameIDBuffer = new ArrayList<>();
		throwException(gecService.DBECEnumTagID(lpszServerName, pNameIDBuffer));
		return pNameIDBuffer;
	}

	// test.DBECEnumTagID();
	public List<Long> DBECEnumTagIDOfDevice(String lpszServerName, long nDeviceID) throws GecException {
		List<Long> pNameIDBuffer = new ArrayList<>();
		throwException(gecService.DBECEnumTagIDOfDevice(lpszServerName, null, nDeviceID, pNameIDBuffer));
		return pNameIDBuffer;
	}

	public List<Long> DBECEnumTagIDOfDeviceByDeviceName(String lpszServerName, String lpszDeviceName)
			throws GecException {
		List<Long> pNameIDBuffer = new ArrayList<>();
		throwException(gecService.DBECEnumTagIDOfDevice(lpszServerName, lpszDeviceName, 0L, pNameIDBuffer));
		return pNameIDBuffer;
	}
	// test.DBECEnumTagIDOfDevice();

	public void DBECGetTagRealHistory(String lpszServerName, String lpszTagName, long nTagID, long nBeginTime,
			long nEndTime, List<Double> pValueArray, int nArraySize, List<Long> pnValueTimeArray) throws GecException {
		throwException(gecService.DBECGetTagRealHistory(lpszServerName, lpszTagName, nTagID, nBeginTime, nEndTime,
				pValueArray, nArraySize, pnValueTimeArray));
	}
	// System.out.println(test.DBECGetTagRealHistory()); //�ɹ���������

	public long DBECGetTagIntField(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName) throws GecException{
		NativeLong pValue = new NativeLong();
		throwException(gecService.DBECGetTagIntField(lpszServerName, lpszTagName, nTagID, lpszFieldName, pValue));
		return pValue.longValue();
	}
	// System.out.println(test.DBECGetTagIntField());// �ɹ���������
	public String DBECGetTagStringField(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName) throws GecException{
		StringBuffer lpValueBuffer = new StringBuffer();
		throwException(gecService.DBECGetTagStringField(lpszServerName, lpszTagName, nTagID, lpszFieldName, lpValueBuffer));
		return lpValueBuffer.toString();
	}
	
	// System.out.println(test.DBECGetTagStringField());// �ɹ���������
	
	public void DBACGetCurrentAlarm(String lpszServerName, List<Long> pnTagIDArray, List<Long> pAlarmTypeArray,
			int nArraySize, List<Double> pValueArray, List<Long> pOccuredTimeArray) throws GecException{
		throwException(gecService.DBACGetCurrentAlarm(lpszServerName, pnTagIDArray, pAlarmTypeArray, nArraySize, pValueArray, pOccuredTimeArray));
	}
	// test.DBACGetCurrentAlarm();//�ɹ���������
	public void DBACGetHistoryAlarm(String lpszServerName, List<Long> pnTagIDArray, long nBeginTime, long nEndTime,
			List<Long> pnAlarmTagIDArray, int nArraySize, List<Long> pnAlarmCount, List<Long> pAlarmBeginTimeArray,
			List<Long> pAlarmEndTimeArray, List<Long> pAlarmTypeArray) throws GecException{
		throwException(gecService.DBACGetHistoryAlarm(lpszServerName, pnTagIDArray, nBeginTime, nEndTime, pnAlarmTagIDArray, nArraySize, pnAlarmCount, pAlarmBeginTimeArray, pAlarmEndTimeArray, pAlarmTypeArray));
	}
	// System.out.println(test.DBACGetHistoryAlarm());//�ɹ���������

	private void throwException(boolean result) throws GecException {
		if (result) {
			return;
		}
		long errorCode = gecService.DBECGetLastError();
		String message = this.DBECGetErrorMessage(errorCode);
		System.out.println("error: "+message);
		throw new GecException(message);
	}

	 public String DBECGetDeviceNote(String lpszServerName, String lpszDeviceName, long nDeviceID, int nBufLen)
		        throws GecException
		    {
		        StringBuffer lpNoteBuffer = new StringBuffer();
		        throwException(gecService.DBECGetDeviceNote(lpszServerName, lpszDeviceName, nDeviceID, lpNoteBuffer, nBufLen));
		        return lpNoteBuffer.toString();
		    }

		public String DBECGetTagStringFields(String serverName, String tagName, Long pointId, ByteBuffer tagbuffer,
				String string) {
			return gecService.DBECGetTagStringFields(serverName, tagName, pointId, string);
			
		}

		public String DBECGetDeviceNote(String serverName, String deviceName, Long deviceId) {
			return gecService.DBECGetDeviceNote(serverName, deviceName, deviceId);
		}

}
