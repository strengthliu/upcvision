package com.surpass.realkits;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;

public interface Gec extends DllFactoryBean {
	/*******************************************************************************
	 * ��һ�ࣺ��������
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��1.1���õ����һ�β����Ĵ�����룺 */
	NativeLong DBECGetLastError();
	/*
	 * ˵���� DBECAPI���ص����д���������2000Ϊ������2000���µĴ���Ϊϵͳ������Ҫ��
	 * ������Ĵ�����Ϣ������ͨ��������1.2������ת�����ַ�����Ϣ��
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��1.2�����������ת�����ַ�����Ϣ�� */
	boolean DBECGetErrorMessage(NativeLong nErrorCode, ByteBuffer lpMsgBuffer, int nBufLen);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��1.3������DBEC�� */
	boolean DBEC_ExitInstance();

	/*******************************************************************************
	 * �ڶ��ࣺ������������
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��2.1���õ��������ĵ�ǰʱ�䣺 */
	NativeLong DBECGetServerCurrentTime(String lpszServerName);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��2.2�������ػ�ʱ�����óɷ�����ʱ�䣺 */
	boolean DBECSetLocalServerTime(String lpszServerName);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��2.3�����÷������� */
//	boolean DBEC_ConfigServer(boolean bSettingBox, Gec1.CStringArray paryServerName, Gec1.CStringArray paryServerNote,
//			Gec1.CStringArray paryServerAddress, Gec1.CDWordArray paryServerPort, IntBuffer pdwCurrentServer);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��2.4�����÷������� */
//	boolean DBEC_GetServerInfo(Gec2.CStringArray paryServerName, Gec2.CStringArray paryServerNote);

	/*******************************************************************************
	 * �����ࣺ������ID���໥ת������
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��3.1������ĳ���豸���õ���Ӧ���豸ID�� */
	NativeLong DBECGetDeviceID(String lpszServerName, String lpszDeviceName);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��3.2������ĳ���豸ID�õ���Ӧ���豸���� */
	boolean DBECGetDeviceName(String lpszServerName, NativeLong nDeviceID, ByteBuffer lpNameBuffer, int nBufLen);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��3.3������ĳ��λ�����õ���Ӧ��λ��ID�� */
	NativeLong DBECGetTagID(String lpszServerName, String lpszTagName);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��3.4������ĳ��λ��ID�õ���Ӧ��λ������ */
	boolean DBECGetTagName(String lpszServerName, NativeLong nTagID, ByteBuffer lpNameBuffer, int nBufLen);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��3.5�������ֶ������õ���Ӧ���ֶ�ID�� */
	NativeLong DBECGetFieldID(String lpszFieldName);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��3.6�������ֶ�ID�õ���Ӧ���ֶ������� */
	boolean DBECGetFieldName(NativeLong nFieldID, ByteBuffer lpNameBuffer, int nBufLen);

	/*
	 * ˵���� ������3.5���ͺ�����3.6���Ķ�Ӧ��ϵΪ��׼���壬�ǹ̶�����ģ����ǲ����� Ϊ���л����Ĳ�ͬ�������ı䣬����ԱҲ���Բ��ı��ֲ�ĸ�¼һ��
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * �����ࣺ�õ���������ֵ�ĺ���
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��4.1���õ����ݿ�ϵͳ�ķ����������� */
	NativeLong DBECGetServerCount();
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��4.2���õ�ĳ����������Ȩ�����λ�Ÿ����� */
	NativeLong DBECGetTagMaxCount(String lpszServerName);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��4.3���õ�ĳ����������Ȩ�����ͻ������� */
	NativeLong DBECGetClientMaxCount(String lpszServerName);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��4.4���õ�ĳ�����������Ѿ���¼�Ŀͻ������� */
	NativeLong DBECGetClientCount(String lpszServerName);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��4.5���õ�ĳ���������ϵ������豸������ */
	NativeLong DBECGetDeviceCount(String lpszServerName);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��4.6���õ�ĳ���������ϵ�����λ�Ÿ����� */
	NativeLong DBECGetTagCount(String lpszServerName);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��4.7���õ�ĳ���������ϵ�ĳ���豸��λ�Ÿ����� */
	NativeLong DBECGetTagCountOfDevice(String lpszServerName, String lpszDeviceName, NativeLong nDeviceID);

	/*
	 * ˵���� ������4.7����������������lpszDeviceName��nDeviceID���ֱ��ʾ�豸���ƺ�
	 * �豸ID������ʵ��ʹ��ʱ������Աֻ��Ҫѡ����������һ����Ϊ��������Ч�������ɣ�
	 * ��ʹ��lpszDeviceName��Ϊ��Ч����ʱ����nDeviceID���㣬��ʹ��nDeviceID��Ϊ��
	 * Ч����ʱ����lpszDeviceName���㡣 ���Ժ�����к����У�����ͬʱ�õ����ƺ�ID���������ĵط����÷��������ͬ��
	 * ֻ��Ҫѡ����������һ����Ϊ��Ч������������һ���������㼴�ɡ�
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * �����ࣺ�õ��ַ�����������ֽڳ��ȵĺ���
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��5.1���õ�һ����������������ֽڳ��ȣ� */
	NativeLong DBECGetServerNameMaxLen();
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��5.2���õ�һ���ͻ���������ֽڳ��ȣ� */
	NativeLong DBECGetClientNameMaxLen();
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��5.3���õ�һ���豸��������ֽڳ��ȣ� */
	NativeLong DBECGetDeviceNameMaxLen();
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��5.4���õ�һ��λ����������ֽڳ��ȣ� */
	NativeLong DBECGetTagNameMaxLen();
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��5.5���õ�һ���ֶ�����������ֽڳ��ȣ� */
	NativeLong DBECGetFieldNameMaxLen();
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��5.6���õ�һ���ֶ����ַ������ݵ�����ֽڳ��ȣ� */
	NativeLong DBECGetFieldStringValueMaxLen();
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��5.7���õ�һ��������Ϣ������ֽڳ��ȣ� */
	NativeLong DBECGetErrMsgMaxLen();
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��5.8���õ�һ���¹���������ֽڳ��ȣ� */
	NativeLong DBECGetEventNameMaxLen();
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��5.9���õ�һ���¹�����������ֽڳ��ȣ� */
	NativeLong DBECGetEventConditionMaxLen();
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * �����ࣺ�õ�λ�����ֶ������͵ĺ���
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��6.1���õ�ĳ��λ�ŵ����ͣ� */
	NativeLong DBECGetTagType(String lpszServerName, String lpszTagName, NativeLong nTagID);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��6.2���õ�ĳ���ֶ�����������ͣ� */
	NativeLong DBECGetFieldType(String lpszFieldName);
	/*
	 * ˵���� ���������������ص��������ͣ��������ֻ����������ͣ�ʵ�͡����͡��ַ����ͣ�
	 * �ֱ����Ӧ�ŷ�������1��2��3�����������ڽ��������������͵��������䡣
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * �����ࣺ�õ���������ֵ����̬��Ϣ�뾲̬��Ϣ���ĺ���
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��7.1���õ�ĳ��λ�ŵ�ʵ���ֶ���ֵ�� */
	boolean DBECGetTagRealField(String lpszServerName, String lpszTagName, NativeLong nTagID, String lpszFieldName,
			DoubleBuffer pValue);

	/*
	 * ˵���� ʹ�øú������Եõ�λ�ŵ�����ʵ���ֶ��������ֵ���ֶ�����FieldNameָ����
	 * �磺ģ������ʵʱ���ݣ����������ޣ����ֱ����޵�ʵ��ֵ��ȱʡFieldName����ʱ�� ʾ��ʵʱ���ݡ�
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��7.2���õ�ĳ��λ�ŵ������ֶ���ֵ�� */
	boolean DBECGetTagIntField(String lpszServerName, String lpszTagName, NativeLong nTagID, String lpszFieldName,
			NativeLongByReference pValue);
	/*
	 * ˵���� ʹ�øú������Եõ�λ�ŵ����������ֶ��������ֵ���ֶ�����FieldNameָ����
	 * �磺��������ʵʱ���ݣ�����״̬������ֵ��ȱʡFieldName����ʱ��ʾ��ʵʱ���ݡ�
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��7.3���õ�ĳ��λ�ŵ��ַ������ֶ���ֵ�� */
	boolean DBECGetTagStringField(String lpszServerName, String lpszTagName, NativeLong nTagID, String lpszFieldName,
			ByteBuffer lpValueBuffer, int nBufLen);
	/*
	 * ˵���� ʹ�øú������Եõ�λ�ŵ������ַ������ֶ��������ֵ���ֶ�����FieldName
	 * ָ�����磺�ַ�������ʵʱ���ݣ�λ��˵�����ַ�����ֵ��ȱʡFieldName����ʱ�� ʾ��ʵʱ���ݡ�
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * �ڰ��ࣺö�ٻ������Ƶĺ���
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��8.1��ö�����ݿ�ϵͳ�����з��������ƣ� */
	boolean DBECEnumServerName(ByteBuffer lpNameBuffer, int nBufLen, NativeLongByReference pnNameCount,
			NativeLongByReference pnNameBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��8.2��ö��ĳ�����������Ѿ���¼�����пͻ����ƣ� */
	boolean DBECEnumClientName(String lpszServerName, ByteBuffer lpNameBuffer, int nBufLen,
			NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��8.3��ö��ĳ���������ϵ������豸�����ƣ� */
	boolean DBECEnumDeviceName(String lpszServerName, ByteBuffer lpNameBuffer, int nBufLen,
			NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��8.4��ö��ĳ���������ϵ�����λ�ŵ����ƣ� */
	boolean DBECEnumTagName(String lpszServerName, PointerByReference lpNameBuffer, int nBufLen,
			NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes);

	boolean DBECEnumTagID(String lpszServerName, NativeLong[] pNameIDBuffer);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��8.5��ö��ĳ���������ϵ�ĳ���豸������λ�ŵ����ƣ� */
	boolean DBECEnumTagNameOfDevice(String lpszServerName, String lpszDeviceName, NativeLong nDeviceID,
			ByteBuffer lpNameBuffer, int nBufLen, NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes);
	
	boolean DBECEnumTagIDOfDevice(String lpszServerName,String lpszDeviceName,NativeLong nDeviceID, NativeLong[] pNameIDBuffer);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��8.6��ö��ĳ���������ϵ����е���չλ�����ͣ� */
	boolean DBECEnumTagExtendType(String lpszServerName, ByteBuffer lpNameBuffer, int nBufLen,
			NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * �ھ��ࣺ�����������������ID�໥ת���ĺ���
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��9.1�����ݳ����豸���õ���Ӧ�ĳ����豸ID�� */
	boolean DBECBatchGetDeviceID(String lpszServerName, byte[] lpNameBuffer, int nNameCount, int nNameBytes,
			LongBuffer pnIDArray, int nArraySize);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��9.2�����ݳ����豸ID�õ���Ӧ�ĳ����豸���� */
	boolean DBECBatchGetDeviceName(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			ByteBuffer lpNameBuffer, int nBufLen, NativeLongByReference pnNameBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��9.3�����ݳ���λ�����õ���Ӧ�ĳ���λ��ID�� */

	boolean DBECBatchGetTagID(String lpszServerName, String lpNameBuffer, int nNameCount, int nNameBytes,
			NativeLongByReference pnIDArray, int nArraySize);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��9.4�����ݳ���λ��ID�õ���Ӧ�ĳ���λ������ */
	boolean DBECBatchGetTagName(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			ByteBuffer lpNameBuffer, int nBufLen, NativeLongByReference pnNameBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * ��ʮ�ࣺ�õ�����λ���ֶ������ݵĺ���
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��10.1���õ�����λ�ŵ�ĳ��ʵ���ֶ������ݣ� */
	boolean DBECBatchGetTagRealField(String lpszServerName, NativeLong[] pnIDArray, int nIDCount, String lpszFieldName,
			DoubleBuffer pValueArray, int nArraySize);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��10.2���õ�����λ�ŵ�ĳ�������ֶ������ݣ� */
	boolean DBECBatchGetTagIntField(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			String lpszFieldName, NativeLongByReference pValueArray, int nArraySize);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��10.3���õ�����λ�ŵ�ĳ���ַ������ֶ������ݣ� */
	boolean DBECBatchGetTagStringField(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			String lpszFieldName, ByteBuffer lpValueBuffer, int nBufLen, NativeLongByReference pnValueBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * ��ʮһ�ࣺ�õ���ʷ���ݵĺ���
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��11.1���õ�ĳ��λ�ŵ�ʵ����ʷ���ݣ� */
	boolean DBECGetTagRealHistory(String lpszServerName, String lpszTagName, NativeLong nTagID, NativeLong nBeginTime,
			NativeLong nEndTime, DoubleBuffer pValueArray, int nArraySize, NativeLong[] pnValueTimeArray,
			NativeLongByReference pnValueCount);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��11.2���õ�ĳ��λ�ŵ�������ʷ���ݣ� */
	boolean DBECGetTagIntHistory(String lpszServerName, String lpszTagName, NativeLong nTagID, NativeLong nBeginTime,
			NativeLong nEndTime, NativeLong[] pValueArray, int nArraySize, NativeLong[] pnValueTimeArray,
			NativeLongByReference pnValueCount);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��11.3���õ�ĳ��λ�ŵ��ַ�������ʷ���ݣ� */
	boolean DBECGetTagStringHistory(String lpszServerName, String lpszTagName, NativeLong nTagID, NativeLong nBeginTime,
			NativeLong nEndTime, ByteBuffer pValueBuffer, int nBufLen, NativeLongByReference pnValueTimeArray,
			int nArraySize, NativeLongByReference pnValueCount, NativeLongByReference pnValueBytes);
	/*
	 * ˵���� Ҫ�õ�ĳ��ʱ������ʷ���ݣ�ֻ��Ҫ����ʼʱ�����ֹʱ�丳Ϊͬһ��ʱ��ֵ ���ɣ�����ʷ�����������ʱ�̵�����Ϊ׼��
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * ��ʮ���ࣺ������ѯ����
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��12.1��ʵʱ������ѯ�� */
	boolean DBACGetCurrentAlarm(String lpszServerName, NativeLong[] pnTagIDArray, int nTagCount,
			NativeLong[] pAlarmTypeArray, int nArraySize, DoubleBuffer pValueArray, NativeLong[] pOccuredTimeArray);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��12.2����ʷ������ѯ�� */
	boolean DBACGetHistoryAlarm(String lpszServerName, NativeLong[] pnTagIDArray, int nTagCount, NativeLong nBeginTime,
			NativeLong nEndTime, NativeLong[] pnAlarmTagIDArray, int nArraySize, NativeLong[] pnAlarmCount,
			NativeLong[] pAlarmBeginTimeArray, NativeLong[] pAlarmEndTimeArray, NativeLong[] pAlarmTypeArray);
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * ��ʮ���ࣺ�¹�׷�亯��
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��13.1��ö�������¹ʼ�¼���ƣ� */
	boolean DBACEnumEventRecordName(String lpszServerName, NativeLong nBeginTime, NativeLong nEndTime,
			ByteBuffer lpNameBuffer, NativeLong nBufLen, NativeLongByReference pnNameCount,
			NativeLongByReference pnNameBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��13.2����ѯĳ���¹ʼ�¼�����ݣ� */
	boolean DBACGetEventRecordData(String lpszServerName, String lpszEventRecordName, String lpszTagName,
			DoubleBuffer pBeforeEventDataArray, NativeLong nBeforeArraySize,
			NativeLongByReference pnBeforeEventTimeArray, NativeLongByReference pnBeforeEventCount,
			DoubleBuffer pAfterEventDataArray, NativeLong nAfterArraySize, NativeLongByReference pnAfterEventTimeArray,
			NativeLongByReference pnAfterEventCount);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��13.3��ö��������̬�¹����ƣ� */
	boolean DBACEnumEventName(String lpszServerName, ByteBuffer lpNameBuffer, NativeLong nBufLen,
			NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��13.4��ö��ĳ���¹ʵ��������λ�����ƣ� */
	boolean DBACEnumEventTagName(String lpszServerName, String lpszEventName, ByteBuffer lpNameBuffer,
			NativeLong nBufLen, NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��13.5���õ�ĳ���¹ʷ����������� */
	boolean DBACGetEventCondition(String lpszServerName, String lpszEventName, ByteBuffer lpConditionBuffer,
			NativeLong nBufLen);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��13.6���õ�ĳ���¹ʼ�¼�������¹����ƣ� */
	boolean DBACGetEventName(String lpszServerName, String lpszEventRecordName, ByteBuffer lpNameBuffer,
			NativeLong nBufLen);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��13.7���õ�ĳ���¹ʼ�¼���¹ʷ���ʱ�䣺 */
	boolean DBACGetEventRecordTime(String lpszServerName, String lpszEventRecordName, NativeLongByReference pnTime);
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * ��ʮ���ࣺ���ݿ�д��������
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��14.1�������ݿ�д�����λ����һ��ʱ�������ݣ���ʵʱ���ݻ���ʷ���ݣ� */

	boolean DBECBatchWriteTagData(String lpszServerName, NativeLongByReference pnTagIDArray, int nTagCount,
			PointerByReference lpDataAddressArray, NativeLong nDataTime, int nDataType);

	/*
	 * ˵���� ʱ�����nDataTimeȱʡֵΪ�㣬��nDataTime=0ʱд�����ݿ���Ƿ������ϵ�ǰ ʱ��ĵ�ǰ����ֵ��
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��14.2�������ݿ�д��ĳ��λ����һ��ʱ���ڵ����ݣ�����ʷ���ݣ� */

	boolean DBECWriteHistory(String lpszServerName, String lpszTagName, NativeLong nTagID,
			PointerByReference lpDataAddressArray, NativeLongByReference pnDataTimeArray, int nDataCount,
			int nDataType);

	/*
	 * ˵���� ����������������ֵ����lpDataAddressArray��������ֵ�ĵ�ַ���飬������
	 * ֱ���������ݣ������ڲ�ͨ����ַָ��������ݣ�ͨ��nDataType����ָ�����ͣ�ʹ �����ַ�ʽ������һ������д�������͵����ݡ�
	 */

	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��14.3��д��ĳ��λ�ŵ�ʵ���ֶ���ֵ�� */
	boolean DBECSetTagRealField(String lpszServerName, String lpszTagName, NativeLong nTagID, String lpszFieldName,
			double dbValue);
	/*
	 * ˵���� ʹ�øú�������д��λ�ŵ�����ʵ���ֶ��������ֵ���ֶ�����FieldNameָ����
	 * �磺ģ������ʵʱ���ݣ����������ޣ����ֱ����޵�ʵ��ֵ��ȱʡFieldName����ʱ�� ʾ��ʵʱ���ݡ�
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��14.4��д��ĳ��λ�ŵ������ֶ���ֵ�� */
	boolean DBECSetTagIntField(String lpszServerName, String lpszTagName, NativeLong nTagID, String lpszFieldName,
			NativeLong nValue);
	/*
	 * ˵���� ʹ�øú�������д��λ�ŵ����������ֶ��������ֵ���ֶ�����FieldNameָ����
	 * �磺��������ʵʱ���ݣ�����״̬������ֵ��ȱʡFieldName����ʱ��ʾ��ʵʱ���ݡ�
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��14.5��д��ĳ��λ�ŵ��ַ������ֶ���ֵ�� */
	boolean DBECSetTagStringField(String lpszServerName, String lpszTagName, NativeLong nTagID, String lpszFieldName,
			ByteBuffer lpValueString);
	/*
	 * ˵���� ʹ�øú�������д��λ�ŵ������ַ������ֶ��������ֵ���ֶ�����FieldName
	 * ָ�����磺�ַ�������ʵʱ���ݣ�λ��˵�����ַ�����ֵ��ȱʡFieldName����ʱ�� ʾ��ʵʱ���ݡ�
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��14.6��д�����λ�ŵ�ĳ��ʵ���ֶ������ݣ� */
	boolean DBECBatchSetTagRealField(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			String lpszFieldName, DoubleBuffer pdbValueArray, int nArraySize);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��14.7��д�����λ�ŵ�ĳ�������ֶ������ݣ� */

	boolean DBECBatchSetTagIntField(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			String lpszFieldName, NativeLongByReference pnValueArray, int nArraySize);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��14.8��д�����λ�ŵ�ĳ���ַ������ֶ������ݣ� */
	boolean DBECBatchSetTagStringField(String lpszServerName, NativeLongByReference pnIDArray, int nIDCount,
			String lpszFieldName, ByteBuffer lpValueStringBuffer, int nBufLen, NativeLong nValueBytes);
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * ��ʮ���ࣺ����Ӧ�ú���
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��15.1���õ�time_tʱ�䣺 */
	NativeLong DBEXGetTimeT(int nYear, int nMonth, int nDay, int nHour, int nMinute, int nSecond);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��15.2���õ������ա�ʱ���룺 */
	boolean DBEXGetDateTime(NativeLong nTimeT, NativeLongByReference pnYear, NativeLongByReference pnMonth,
			NativeLongByReference pnDay, NativeLongByReference pnHour, NativeLongByReference pnMinute,
			NativeLongByReference pnSecond);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��15.3���ͷ�DBEC�ڲ������double���ڴ棺 */

	boolean DBEXFreeDoubleMemory(PointerByReference lppBuffer);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��15.4���ͷ�DBEC�ڲ������long���ڴ棺 */

	boolean DBEXFreeLongMemory(PointerByReference lppBuffer);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��15.5���ͷ�DBEC�ڲ���������������ڴ棺 */
	boolean DBEC_FreeMemory(PointerByReference lppBuffer);
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * ��ʮ���ࣺDBEC��չ����
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��16.1���õ�ĳ��λ�ŵ�ʵ����ʷ���ݣ� */
	boolean DBECGetTagRealHistoryEx(String lpszServerName, String lpszTagName, NativeLong nTagID, NativeLong nBeginTime,
			NativeLong nEndTime, PointerByReference lpOutValueBuffer, PointerByReference lpOutTimeBuffer,
			NativeLongByReference pnValueCount);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��16.2���ͷ�ʵ����ʷ���ݵ��ڴ棺����16.1����ʹ�ã� double** long** */
	boolean DBECGetTagRealHistoryEx_OK(PointerByReference lpOutValueBuffer, PointerByReference lpOutTimeBuffer);
	/*
	 * ˵���� lpOutValueBuffer��lpOutTimeBuffer���ݸ��������ڶ�̬���ӿ��ڲ������ڴ棬
	 * ����16.1������ȡ�����ݺ󣬱����ٵ���16.2�����ͷŶ�̬���ӿ��е��������ڴ档
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��16.3���õ�λ����һ��ʱ������ۻ����� */
	boolean DBECGetTagCumulate(String lpszServerName, String lpszTagName, NativeLong nTagID, NativeLong nBeginTime,
			NativeLong nEndTime, DoubleBuffer pValue, DoubleBuffer pLastValue, NativeLongByReference pnLastTime);

	/*
	 * ˵���� nEndTimeȱʡֵNULL��ʾ��ǰʱ�䣬nBeginTimeȱʡֵNULL��ʾnEndTime ǰ24Сʱ��ʱ�䡣
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��16.4��ö��ĳ���������ϵ�����λ�ŵ����ƣ� */
	boolean DBECEnumTagNameEx(String lpszServerName, ByteBuffer lpNameBuffer, NativeLong nBufLen,
			NativeLongByReference pnNameCount, NativeLongByReference pnNameBytes, NativeLong nBeginNum,
			NativeLong nEndNum);
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��16.5����ȡĳ���豸�������� */
	boolean DBECGetDeviceNote(String lpszServerName, String lpszDeviceName, NativeLong nDeviceID,
			ByteBuffer lpNoteBuffer, NativeLong nBufLen);
	/*
	 * =========================================================================
	 * ===
	 */

	/*******************************************************************************
	 * ��ʮ���ࣺDBAC��չ����
	 *******************************************************************************/

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��17.1����ʷ������ѯ�� ����ָ��PointerByReference����long** */
	boolean DBACGetHistoryAlarmEx(String lpszServerName, NativeLongByReference pnTagIDArray, int nTagCount,
			NativeLong nBeginTime, NativeLong nEndTime, PointerByReference lpnAlarmTagIDArray,
			NativeLongByReference pnAlarmCount, PointerByReference lpAlarmBeginTimeArray,
			PointerByReference lpAlarmEndTimeArray, PointerByReference lpAlarmTypeArray);
	/*
	 * ˵���� lpnAlarmTagIDArray, lpAlarmBeginTimeArray, lpAlarmEndTimeArray,
	 * lpAlarmTypeArray��DBEC�ڲ�������ڴ棬ע���뺯��12.2������ִ����� ������ú���17.2�����ߺ���15.4���ͷ��ڴ档
	 */
	/*
	 * =========================================================================
	 * ===
	 */

	/*
	 * =========================================================================
	 * ===
	 */
	/* ��17.2���ͷ���ʷ������ѯ�ڴ棺 ����ָ��PointerByReference����long** */
	boolean DBACGetHistoryAlarmEx_OK(PointerByReference lpnAlarmTagIDArray, PointerByReference lpAlarmBeginTimeArray,
			PointerByReference lpAlarmEndTimeArray, PointerByReference lpAlarmTypeArray);

	/** Pointer to unknown (opaque) type */
	public static class APIENTRY extends PointerType {
		public APIENTRY(Pointer address) {
			super(address);
		}

		public APIENTRY() {
			super();
		}
	};

	/** Pointer to unknown (opaque) type */
	public static class CDWordArray extends PointerType {
		public CDWordArray(Pointer address) {
			super(address);
		}

		public CDWordArray() {
			super();
		}
	};

	/** Pointer to unknown (opaque) type */
	public static class CStringArray extends PointerType {
		public CStringArray(Pointer address) {
			super(address);
		}

		public CStringArray() {
			super();
		}
	};
}
