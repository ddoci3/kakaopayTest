package com.example.ktest.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logs {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	/*
	 * 로그 출력
	 * 입력 : 로그타입, 로그문자열
	 */
	public void log(int type, String sMsg) {
		
		switch(type) {
		case Consts.LOG_TYPE_TRACE : logger.trace(sMsg); break;
		case Consts.LOG_TYPE_DEBUG : logger.debug(sMsg); break;
		case Consts.LOG_TYPE_INFO : logger.info(sMsg); break;
		case Consts.LOG_TYPE_WARN : logger.warn(sMsg); break;
		case Consts.LOG_TYPE_ERROR : logger.error(sMsg); break;
		default : logger.error(sMsg);
		}
	}

	/*
	 * 로그 출력
	 * 입력 : Exception, 로그문자열
	 */
	public void log(Exception e, String sMsg) {
		logger.error(sMsg);
		logger.error(e.getMessage());
	}
}
