package com.example.ktest.common;

public class Utils {

	/*
	 * HTTP 응답메시지 리턴
	 * 입력 : 응답코드, 응답메시지
	 * 출력 : HTTP응답메시지
	 */
	public static String getRspMsg(String sCode, String sMsg) {
		
		StringBuilder response = new StringBuilder();
		
		response.append("{\"code\":\"");
		response.append(sCode);
		response.append("\", \"메세지\":\"");
		response.append(sMsg);
		response.append("\"}");
		
		return response.toString();
	}
}
