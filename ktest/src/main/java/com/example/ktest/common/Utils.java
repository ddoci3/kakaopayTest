package com.example.ktest.common;

public class Utils {

	/*
	 * HTTP ����޽��� ����
	 * �Է� : �����ڵ�, ����޽���
	 * ��� : HTTP����޽���
	 */
	public static String getRspMsg(String sCode, String sMsg) {
		
		StringBuilder response = new StringBuilder();
		
		response.append("{\"code\":\"");
		response.append(sCode);
		response.append("\", \"�޼���\":\"");
		response.append(sMsg);
		response.append("\"}");
		
		return response.toString();
	}
}
