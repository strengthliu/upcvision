package com.surpass.realkits;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.surpass.realkits.exception.GecException;

public class JGecService extends ObjectPool<GecService> {

	String dllPath;
	
	public String getDllPath() {
		return dllPath;
	}

	public void setDllPath(String dllPath) {
		this.dllPath = dllPath;
	}

	@Override
	public PooledObject<GecService> create() {
		// TODO GecService里面使用了单例和静态变量，这将导致这里使用池没有任何意义。
//		return null;
		GecService gecService = new GecService();
		try {
			gecService.setDllPath(dllPath);
			if (gecService.gec == null) {
			synchronized (Gec.class) {
				if (gecService.gec == null && dllPath != null) {
					gecService.gec = (Gec) Native.loadLibrary(dllPath, Gec.class);
				}
				if (dllPath == null) {
					throw new GecException("please set dll path");
				}
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return new PooledObject<GecService>(gecService);
	}

	/**
	 * 从池里取一个可用的服务。
	 * @return
	 */
	private GecService gecService() {
		return getObject();
	}

	public JGecService() throws Exception {
		createPool();
	}

	public JGecService(String dllPath) throws GecException {
		this.setDllPath(dllPath);
		createPool();
	}

	public long DBECGetLastError() {
		GecService gs = gecService();
		long ret = gs.DBECGetLastError();
		this.returnObject(gs);
		return ret;
	}

	public String DBECGetErrorMessage(long nErrorCode) throws GecException {
		StringBuffer lpMsgBuffer = new StringBuffer();
		
		Long maxLen = DBECGetErrMsgMaxLen();
		GecService gs = gecService();
		boolean result = gs.DBECGetErrorMessage(nErrorCode, lpMsgBuffer, maxLen.intValue());
		this.returnObject(gs);
		if (!result) {
			throw new GecException();
		}
		return lpMsgBuffer.toString();
	}

	public void DBEC_ExitInstance() throws GecException {
		GecService gs = gecService();
		boolean r = gs.DBEC_ExitInstance();
		this.returnObject(gs);
		throwException(r);
	}

	public long DBECGetServerCurrentTime(String lpszServerName) {
		GecService gs = gecService();
		long r = gs.DBECGetServerCurrentTime(lpszServerName);
		this.returnObject(gs);
		return r;
	}

	public void DBECSetLocalServerTime(String lpszServerName) throws GecException {
		GecService gs = gecService();
		boolean r = gs.DBECSetLocalServerTime(lpszServerName);
		this.returnObject(gs);
		throwException(r);
	}

	public long DBECGetDeviceID(String lpszServerName, String lpszDeviceName) throws GecException {
		GecService gs = gecService();
		long id = gs.DBECGetDeviceID(lpszServerName, lpszDeviceName);
		this.returnObject(gs);
		throwException(id != 0);
		return id;
	}

	public String DBECGetDeviceName(String lpszServerName, long nDeviceID) throws GecException {
		GecService gs = gecService();
		StringBuffer lpNameBuffer = new StringBuffer();
		Long nBufLen = gs.DBECGetDeviceNameMaxLen();
		boolean r = gs.DBECGetDeviceName(lpszServerName, nDeviceID, lpNameBuffer, nBufLen.intValue());
		this.returnObject(gs);
		throwException(r);
		return lpNameBuffer.toString();
	}

	public long DBECGetTagID(String lpszServerName, String lpszTagName) throws GecException {
		GecService gs = gecService();
		long id = gs.DBECGetTagID(lpszServerName, lpszTagName);
		this.returnObject(gs);
		throwException(id != 0);
		return id;
	}

	public long DBECGetFieldID(String lpszFieldName) throws GecException {
		GecService gs = gecService();
		long id = gs.DBECGetFieldID(lpszFieldName);
		this.returnObject(gs);
		throwException(id != 0);
		return id;
	}

	public long DBECGetServerCount() {
		GecService gs = gecService();
		long id = gs.DBECGetServerCount();
		this.returnObject(gs);
		return id;
	}

	public long DBECGetTagMaxCount(String lpszServerName) {
		GecService gs = gecService();
		long id = gs.DBECGetTagMaxCount(lpszServerName);
		this.returnObject(gs);
		return id;
	}

	public long DBECGetClientMaxCount(String lpszServerName) {
		GecService gs = gecService();
		long id = gs.DBECGetClientMaxCount(lpszServerName);
		this.returnObject(gs);
		return id;
	}

	public long DBECGetClientCount(String lpszServerName) {
		GecService gs = gecService();
		long id = gs.DBECGetClientCount(lpszServerName);
		this.returnObject(gs);
		return id;
	}

	public long DBECGetDeviceCount(String lpszServerName) {
		GecService gs = gecService();
		long id = gs.DBECGetDeviceCount(lpszServerName);
		this.returnObject(gs);
		return id;
	}

	public long DBECGetTagCount(String lpszServerName) {
		GecService gs = gecService();
		long id = gs.DBECGetTagCount(lpszServerName);
		this.returnObject(gs);
		return id;
	}

	public long DBECGetTagCountOfDevice(String lpszServerName, long nDeviceID) {
		GecService gs = gecService();
		long id = gs.DBECGetTagCountOfDevice(lpszServerName, null, nDeviceID);
		this.returnObject(gs);
		return id;
	}

	public long DBECGetTagCountOfDeviceByDeviceName(String lpszServerName, String lpszDeviceName) {
		GecService gs = gecService();
		long id = gs.DBECGetTagCountOfDevice(lpszServerName, lpszDeviceName, 0L);
		this.returnObject(gs);
		return id;
	}
	// System.out.println(test.DBECGetTagCountOfDevice());

	public long DBECGetServerNameMaxLen() {
		GecService gs = gecService();
		long id = gs.DBECGetServerNameMaxLen();
		this.returnObject(gs);
		return id;
	}

	public long DBECGetClientNameMaxLen() {
		GecService gs = gecService();
		long id = gs.DBECGetClientNameMaxLen();
		this.returnObject(gs);
		return id;
	}

	public long DBECGetDeviceNameMaxLen() {
		GecService gs = gecService();
		long id = gs.DBECGetDeviceNameMaxLen();
		this.returnObject(gs);
		return id;
	}

	public long DBECGetTagNameMaxLen() {
		GecService gs = gecService();
		long id = gs.DBECGetTagNameMaxLen();
		this.returnObject(gs);
		return id;
	}

	public long DBECGetFieldNameMaxLen() {
		GecService gs = gecService();
		long id = gs.DBECGetFieldNameMaxLen();
		this.returnObject(gs);
		return id;
	}

	public long DBECGetFieldStringValueMaxLen() {
		GecService gs = gecService();
		long id = gs.DBECGetFieldStringValueMaxLen();
		this.returnObject(gs);
		return id;
	}

	public long DBECGetErrMsgMaxLen() {
		GecService gs = gecService();
		long id = gs.DBECGetErrMsgMaxLen();
		this.returnObject(gs);
		return id;
	}

	public long DBECGetEventNameMaxLen() {
		GecService gs = gecService();
		long id = gs.DBECGetEventNameMaxLen();
		this.returnObject(gs);
		return id;
	}

	public long DBECGetEventConditionMaxLen() {
		GecService gs = gecService();
		long id = gs.DBECGetEventConditionMaxLen();
		this.returnObject(gs);
		return id;
	}

	public long DBECGetTagType(String lpszServerName, long nTagID) {
		GecService gs = gecService();
		long id = gs.DBECGetTagType(lpszServerName, null, nTagID);
		this.returnObject(gs);
		return id;
	}

	public long DBECGetTagTypeByTagName(String lpszServerName, String lpszTagName) {
		GecService gs = gecService();
		long id = gs.DBECGetTagType(lpszServerName, lpszTagName, 0L);
		this.returnObject(gs);
		return id;
	}
	
	public String DBECGetTagName(String serverName,long tagId) throws GecException{
		GecService gs = gecService();
		StringBuffer lpNameBuffer = new StringBuffer();
		Long length = gs.DBECGetTagNameMaxLen();
//		System.out.println("debug lab DBECGetTagName begin ...");
		boolean r = gs.DBECGetTagName(serverName, tagId, lpNameBuffer, length.intValue());
//		System.out.println("debug lab DBECGetTagName end ...");
		this.returnObject(gs);
		throwException(r);
		return lpNameBuffer.toString();
	}

	public long DBECGetFieldType(String lpszFieldName) throws GecException {
		GecService gs = gecService();
		long id = gs.DBECGetFieldType(lpszFieldName);
		this.returnObject(gs);
		throwException(id != 0);
		return id;
	}

	public List<String> DBECEnumServerName()
			throws GecException {
		List<String> lpNameBuffer = new ArrayList<>();
		Long nBufLen = DBECGetServerNameMaxLen() * this.DBECGetServerCount();
		NativeLong pnNameCount = new NativeLong();
		NativeLong pnNameBytes = new NativeLong();
		GecService gs = gecService();
		boolean r = gs.DBECEnumServerName(lpNameBuffer, nBufLen.intValue(), pnNameCount, pnNameBytes);
		this.returnObject(gs);
		throwException(r);
		return lpNameBuffer;
	}

	public List<String> DBECEnumDeviceName(String lpszServerName) throws GecException {
		Long nBufLenLong = this.DBECGetDeviceNameMaxLen() * this.DBECGetDeviceCount(lpszServerName);
		NativeLong pnNameCount = new NativeLong();
		NativeLong pnNameBytes = new NativeLong();
		List<String> lpNameBuffer = new ArrayList<>();
		GecService gs = gecService();
		boolean r = gs.DBECEnumDeviceName(lpszServerName, lpNameBuffer,nBufLenLong.intValue() , pnNameCount, pnNameBytes);
		this.returnObject(gs);
		throwException(r);
		return lpNameBuffer;
	}

	public double DBECGetTagRealField(String lpszServerName, long nTagID, String lpszFieldName)
			throws GecException {
		DoubleByReference db = new DoubleByReference();
		GecService gs = gecService();
		boolean r = gs.DBECGetTagRealField(lpszServerName, null, nTagID, lpszFieldName, db);
		this.returnObject(gs);
		throwException(r);
		return db.getValue();
	}

	public double DBECGetTagRealFieldByTagName(String lpszServerName, String lpszTagName,
			String lpszFieldName) throws GecException {
		DoubleByReference db = new DoubleByReference();
		GecService gs = gecService();
		boolean r = gs.DBECGetTagRealField(lpszServerName, lpszTagName, 0L, lpszFieldName, db);
		this.returnObject(gs);
		throwException(r);
		return db.getValue();
	}

	// System.out.println(test.DBECGetTagRealField());// �ɹ�����Ҫ�ӿڣ�Ŀǰ�]���}
	public List<Double> DBECBatchGetTagRealField(String lpszServerName, List<Long> pnIDArray, String lpszFieldName)
			throws GecException {
		List<Double> pValueArray = new ArrayList<>();
		GecService gs = gecService();
		boolean r = gs.DBECBatchGetTagRealField(lpszServerName, pnIDArray, lpszFieldName, pValueArray);
		this.returnObject(gs);
		throwException(r);
		return pValueArray;
	}
	// System.out.println(test.DBECBatchGetTagRealField());//�ɹ�����Ҫ�ӿڣ�Ŀǰ�]���}

	public List<String> DBECEnumTagExtendType(String lpszServerName) throws GecException {
		Long nBufLen = this.DBECGetTagNameMaxLen();

		NativeLongByReference pnNameCount = new NativeLongByReference();
		NativeLongByReference pnNameBytes = new NativeLongByReference();
		List<String> lpNameBuffer = new ArrayList<>();
		GecService gs = gecService();
		boolean r = gs.DBECEnumTagExtendType(lpszServerName, lpNameBuffer, nBufLen.intValue(), pnNameCount,pnNameBytes);
		this.returnObject(gs);
		throwException(r);
		return lpNameBuffer;
	}
	// System.out.println(test.DBECEnumTagExtendType());

	public List<Long> DBECEnumTagID(String lpszServerName) throws GecException {
		List<Long> pNameIDBuffer = new ArrayList<>();
		GecService gs = gecService();
		boolean r = gs.DBECEnumTagID(lpszServerName, pNameIDBuffer);
		this.returnObject(gs);
		throwException(r);
		return pNameIDBuffer;
	}

	// test.DBECEnumTagID();
	public List<Long> DBECEnumTagIDOfDevice(String lpszServerName, long nDeviceID) throws GecException {
		List<Long> pNameIDBuffer = new ArrayList<>();
		GecService gs = gecService();
		boolean r = gs.DBECEnumTagIDOfDevice(lpszServerName, null, nDeviceID, pNameIDBuffer);
		this.returnObject(gs);
		throwException(r);
		return pNameIDBuffer;
	}

	public List<Long> DBECEnumTagIDOfDeviceByDeviceName(String lpszServerName, String lpszDeviceName)
			throws GecException {
		List<Long> pNameIDBuffer = new ArrayList<>();
		GecService gs = gecService();
		boolean r = gs.DBECEnumTagIDOfDevice(lpszServerName, lpszDeviceName, 0L, pNameIDBuffer);
		this.returnObject(gs);
		throwException(r);
		return pNameIDBuffer;
	}
	// test.DBECEnumTagIDOfDevice();

	public void DBECGetTagRealHistory(String lpszServerName, String lpszTagName, long nTagID, long nBeginTime,
			long nEndTime, List<Double> pValueArray, int nArraySize, List<Long> pnValueTimeArray) throws GecException {
		GecService gs = gecService();
		boolean r = gs.DBECGetTagRealHistory(lpszServerName, lpszTagName, nTagID, nBeginTime, nEndTime,
				pValueArray, nArraySize, pnValueTimeArray);
		this.returnObject(gs);
		throwException(r);
	}
	// System.out.println(test.DBECGetTagRealHistory()); //�ɹ���������

	public long DBECGetTagIntField(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName) throws GecException{
		NativeLong pValue = new NativeLong();
		GecService gs = gecService();
		boolean r = gs.DBECGetTagIntField(lpszServerName, lpszTagName, nTagID, lpszFieldName, pValue);
		this.returnObject(gs);
		throwException(r);
		return pValue.longValue();
	}
	// System.out.println(test.DBECGetTagIntField());// �ɹ���������
	public String DBECGetTagStringField(String lpszServerName, String lpszTagName, long nTagID, String lpszFieldName) throws GecException{
		StringBuffer lpValueBuffer = new StringBuffer();
		GecService gs = gecService();
		boolean r = gs.DBECGetTagStringField(lpszServerName, lpszTagName, nTagID, lpszFieldName, lpValueBuffer);
		this.returnObject(gs);
		throwException(r);
		return lpValueBuffer.toString();
	}
	
	// System.out.println(test.DBECGetTagStringField());// �ɹ���������
	
	public void DBACGetCurrentAlarm(String lpszServerName, List<Long> pnTagIDArray, List<Long> pAlarmTypeArray,
			int nArraySize, List<Double> pValueArray, List<Long> pOccuredTimeArray) throws GecException{
		GecService gs = gecService();
		boolean r = gs.DBACGetCurrentAlarm(lpszServerName, pnTagIDArray, pAlarmTypeArray, nArraySize, pValueArray, pOccuredTimeArray);
		this.returnObject(gs);
		throwException(r);
	}
	// test.DBACGetCurrentAlarm();//�ɹ���������
	public void DBACGetHistoryAlarm(String lpszServerName, List<Long> pnTagIDArray, long nBeginTime, long nEndTime,
			List<Long> pnAlarmTagIDArray, int nArraySize, List<Long> pnAlarmCount, List<Long> pAlarmBeginTimeArray,
			List<Long> pAlarmEndTimeArray, List<Long> pAlarmTypeArray) throws GecException{
		GecService gs = gecService();
		boolean r = gs.DBACGetHistoryAlarm(lpszServerName, pnTagIDArray, nBeginTime, nEndTime, pnAlarmTagIDArray, nArraySize, pnAlarmCount, pAlarmBeginTimeArray, pAlarmEndTimeArray, pAlarmTypeArray);
		this.returnObject(gs);
		throwException(r);
	}
	// System.out.println(test.DBACGetHistoryAlarm());//�ɹ���������

	private void throwException(boolean result) throws GecException {
		if (result) {
			return;
		}
		GecService gs = gecService();
		long errorCode = gs.DBECGetLastError();
		String message = this.DBECGetErrorMessage(errorCode);
		this.returnObject(gs);
		System.out.println("error: "+message);
		throw new GecException(message);
	}

	 public String DBECGetDeviceNote(String lpszServerName, String lpszDeviceName, long nDeviceID, int nBufLen)
		        throws GecException
		    {
		        StringBuffer lpNoteBuffer = new StringBuffer();
				GecService gs = gecService();
				boolean r = gs.DBECGetDeviceNote(lpszServerName, lpszDeviceName, nDeviceID, lpNoteBuffer, nBufLen);
				this.returnObject(gs);
				throwException(r);
		        return lpNoteBuffer.toString();
		    }

		public String DBECGetTagStringFields(String serverName, String tagName, Long pointId, ByteBuffer tagbuffer,
				String string) {
			GecService gs = gecService();
			String r = gs.DBECGetTagStringFields(serverName, tagName, pointId, string);
			this.returnObject(gs);
			return r;
			
		}

		public String DBECGetDeviceNote(String serverName, String deviceName, Long deviceId) {
			GecService gs = gecService();
			String r = gs.DBECGetDeviceNote(serverName, deviceName, deviceId);
			this.returnObject(gs);
			return r;
		}

}
