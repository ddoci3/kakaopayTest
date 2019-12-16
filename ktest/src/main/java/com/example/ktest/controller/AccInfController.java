package com.example.ktest.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ktest.common.Consts;
import com.example.ktest.common.Logs;
import com.example.ktest.common.Utils;
import com.example.ktest.mapper.AccInfMapper;
import com.example.ktest.vo.NoTrdAccInfVO;
import com.example.ktest.vo.TopAccInfVO;

/*
 * 계좌정보
 */
@RestController
public class AccInfController {
	
	private Logs logs;

	@Autowired
	private AccInfMapper accInfMapper;
	
	public AccInfController() {
		this.logs = new Logs();
	}
	
	/*
	 * 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API
	 * 입력 : 없음
	 * 출력 : {연도, 계좌명, 계좌번호, 거래합계금액} 목록
	 */
	@GetMapping("/selectTopAccInfByYear")
	public String selectTopAccInfByYear() {
		
		// 오류 메시지 설정
		String sMsg = null;

		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		
		TopAccInfVO topAccInfVO = null;

		// 조회 대상 년도 : 2018년, 2019년 
		int[] nYearArray = {2018, 2019};
		
		for(int i=0; i<nYearArray.length; i++) {
		
			// 연도별 합계 금액 가장 많은 고객 단건 조회
			try {
				topAccInfVO = accInfMapper.selectTopAccInfByYear(nYearArray[i]);
			}
			catch(Exception e) {
				
				sMsg = "연도별 합계 금액 가장 많은 고객 단건 조회 처리 오류 발생.";
				
				logs.log(e, sMsg);
				
				return Utils.getRspMsg("500", sMsg);
			}

			// 결과 데이터 없으면 다음 연도 기준으로 조회
			if( null == topAccInfVO ) {
				
				sMsg = nYearArray[i] + "년도 결과 데이터 없음.";
				
				logs.log(Consts.LOG_TYPE_INFO, sMsg);
				
				continue;
			}
			
			if( null == jsonArray ) {
				jsonArray = new JSONArray();
			}
				
			// 결과 데이터 설정
			jsonObject = new JSONObject();
			
			jsonObject.put("year", topAccInfVO.getYear());
			jsonObject.put("name", topAccInfVO.getAcc_nm());
			jsonObject.put("acctNo", topAccInfVO.getAcc_no());
			jsonObject.put("sumAmt", topAccInfVO.getSum_amt());
			
			jsonArray.put(jsonObject);
		}
		
		if( null == jsonArray ) {
			
			sMsg = "조히 결과 데이터가 없습니다.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);

			return Utils.getRspMsg("200", sMsg);
		}

		return jsonArray.toString();
	}
	
	/*
	 * 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API
	 * 입력 : 없음
	 * 출력 : {연도, 계좌명, 계좌번호} 목록
	 */
	@GetMapping("/selectNoTrdAccInfByYearList")
	public String selectNoTrdAccInfByYearList() {
		
		// 오류 메시지 설정
		String sMsg = null;

		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		
		List<NoTrdAccInfVO> noTrdAccInfVOList = null;
		NoTrdAccInfVO noTrdAccInfVO = null;

		// 조회 대상 년도 : 2018년, 2019년 
		int[] nYearArray = {2018, 2019};
		
		int nCnt = 0;
		
		for(int i=0; i<nYearArray.length; i++) {
		
			// 해당 연도에 거래가 없는 고객 다건 조회
			try {
				noTrdAccInfVOList = accInfMapper.selectNoTrdAccInfByYearList(nYearArray[i]);
			}
			catch(Exception e) {
				
				sMsg = "연도별 거래가 없는 고객 다건 조회 처리 오류 발생.";
				
				logs.log(e, sMsg);
				
				return Utils.getRspMsg("500", sMsg);
			}

			// 결과 데이터 없으면 다음 연도 기준으로 조회
			if( null == noTrdAccInfVOList || noTrdAccInfVOList.size() < 1 ) {
				
				sMsg = nYearArray[i] + "년도 결과 데이터 없음.";
				
				logs.log(Consts.LOG_TYPE_INFO, sMsg);
				
				continue;
			}
			
			if( null == jsonArray ) {
				jsonArray = new JSONArray();
			}
				
			nCnt = noTrdAccInfVOList.size();
			
			// 결과 목록 데이터 설정
			for(int j=0; j<nCnt; j++) {
				
				noTrdAccInfVO = noTrdAccInfVOList.get(j);
			
				jsonObject = new JSONObject();
				
				jsonObject.put("year", noTrdAccInfVO.getYear());
				jsonObject.put("name", noTrdAccInfVO.getAcc_nm());
				jsonObject.put("acctNo", noTrdAccInfVO.getAcc_no());
				
				jsonArray.put(jsonObject);
			}
		}
		
		if( null == jsonArray ) {
			
			sMsg = "조히 결과 데이터가 없습니다.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);

			return Utils.getRspMsg("200", sMsg);
		}

		return jsonArray.toString();
	}
}
