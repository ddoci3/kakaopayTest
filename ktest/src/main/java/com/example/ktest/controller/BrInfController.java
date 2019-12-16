package com.example.ktest.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ktest.common.Consts;
import com.example.ktest.common.Logs;
import com.example.ktest.common.Utils;
import com.example.ktest.mapper.AccInfMapper;
import com.example.ktest.mapper.BrInfMapper;
import com.example.ktest.vo.BrCodChngVO;
import com.example.ktest.vo.BrInfVO;
import com.example.ktest.vo.SumAmtBrInfVO;

/*
 * 지점정보
 */
@RestController
public class BrInfController {
	
	private Logs logs;

	@Autowired
	private BrInfMapper brInfMapper;

	@Autowired
	private AccInfMapper accInfMapper;
	
	public BrInfController() {
		this.logs = new Logs();
	}
	
	/*
	 * 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API
	 * 입력 : 없음
	 * 출력 : {연도, {지점명, 지점코드, 거래합계금액}} 목록
	 */
	@GetMapping("/selectBrTrdSumAmtByYearNBrList")
	public String selectBrTrdSumAmtByYearNBrList() {
		
		// 오류 메시지 설정
		String sMsg = null;

		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		JSONArray jsonArraySub = null;
		JSONObject jsonObjectSub = null;
				
		List<SumAmtBrInfVO> sumAmtBrInfVOList = null;
		SumAmtBrInfVO sumAmtBrInfVO = null;

		// 조회 대상 년도 : 2018년, 2019년 
		int[] nYearArray = {2018, 2019};
		
		int nCnt = 0;
		
		for(int i=0; i<nYearArray.length; i++) {

			// 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 다건 조회
			try {
				sumAmtBrInfVOList = brInfMapper.selectBrTrdSumAmtByYearNBrList(nYearArray[i]);
			}
			catch(Exception e) {
				
				sMsg = "연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 다건 조회 처리 오류 발생.";
				
				logs.log(e, sMsg);
				
				return Utils.getRspMsg("500", sMsg);
			}

			// 결과 데이터 없으면 다음 연도 기준으로 조회
			if( null == sumAmtBrInfVOList || sumAmtBrInfVOList.size() < 1 ) {
				
				sMsg = nYearArray[i] + "년도 결과 데이터 없음.";
				
				logs.log(Consts.LOG_TYPE_INFO, sMsg);
				
				continue;
			}
				
			if( null == jsonArray ) {
				jsonArray = new JSONArray();
			}

			nCnt = sumAmtBrInfVOList.size();
			
			jsonArraySub = new JSONArray();
			
			// 결과 목록 데이터 설정
			for(int j=0; j<nCnt; j++) {
				
				sumAmtBrInfVO = sumAmtBrInfVOList.get(j);
			
				jsonObjectSub = new JSONObject();
				
				jsonObjectSub.put("brName", sumAmtBrInfVO.getBr_nm());
				jsonObjectSub.put("brCode", sumAmtBrInfVO.getBr_cod());
				jsonObjectSub.put("sumAmt", sumAmtBrInfVO.getSum_amt());
				
				jsonArraySub.put(jsonObjectSub);
			}
			
			jsonObject = new JSONObject();
			
			jsonObject.put("year", nYearArray[i]);
			jsonObject.put("dataList", jsonArraySub);
			
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
	 * 지점 통폐합 처리
	 * 입력 : 없음
	 * 출력 : 처리결과 메시지
	 */
	@GetMapping("/intgBrInf")
	public String intgBrInf() {
		
		/* 코멘트
		 * 과제에 주어진 분당점과 판교점을 통폐합하여 판교점으로 관리점을 이관하는 작업을 처리한다.
		 * 지점코드는 입력값으로 간주한다. 분당점:B, 판교점:A  
		 */
		
		// 오류 메시지 설정
		String sMsg = null;

		// 이관 지점정보 존재 여부 확인

		BrInfVO bfBrInfVO = new BrInfVO();
		
		bfBrInfVO.setBr_cod("B");  // 지점코드 : B(분당점)
		
		try {
			bfBrInfVO = brInfMapper.selectBrInf(bfBrInfVO);
		}
		catch(Exception e) {
			
			sMsg = "통폐합 이관 지점정보 단건 조회 처리 오류 발생.";
			
			logs.log(e, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		if( null == bfBrInfVO ) {
			
			sMsg = "통폐합 이관 지점정보가 존재하지 않습니다.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);
			
			return Utils.getRspMsg("404", sMsg);
		}
		
		if( !"1".equals(bfBrInfVO.getBr_sts_cod()) ) {
			
			sMsg = "통폐합 이관 지점은 운영하지 않는 지점입니다.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);
			
			return Utils.getRspMsg("404", sMsg);
		}

		// 수관 지점정보 존재 여부 확인

		BrInfVO afBrInfVO = new BrInfVO();
		
		afBrInfVO.setBr_cod("B");  // 지점코드 : B(분당점)
		
		try {
			afBrInfVO = brInfMapper.selectBrInf(afBrInfVO);
		}
		catch(Exception e) {
			
			sMsg = "통폐합 수관 지점정보 단건 조회 처리 오류 발생.";
			
			logs.log(e, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		if( null == afBrInfVO ) {
			
			sMsg = "통폐합 수관 지점정보가 존재하지 않습니다.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);
			
			return Utils.getRspMsg("404", sMsg);
		}
		
		if( !"1".equals(afBrInfVO.getBr_sts_cod()) ) {
			
			sMsg = "통폐합 수관 지점은 운영하지 않는 지점입니다.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);
			
			return Utils.getRspMsg("404", sMsg);
		}

		// 이관 지점정보 중 상태코드 갱신 (1:사용 -> 2:미사용)
		
		bfBrInfVO.setBr_sts_cod("2");  // 지점상태코드 : 1(사용) -> 2(미사용)
		
		int nUdtCnt = 0;
		
		try {
			nUdtCnt = brInfMapper.updateBrInf(bfBrInfVO);
		}
		catch(Exception e) {
			
			sMsg = "통폐합 이관 지점 상태코드 갱신 처리 오류 발생.";
			
			logs.log(e, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		if( 1 != nUdtCnt ) {
			
			sMsg = "통폐합 이관 지점상태코드 갱신 오류 발생.";
			
			logs.log(Consts.LOG_TYPE_ERROR, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		// 이관 지점의 계좌정보 중 관리지점코드 갱신
		
		BrCodChngVO brCodChngVO = new BrCodChngVO();
		
		brCodChngVO.setBf_br_cod("B");  // 이전 지점코드 : B(분당점)
		brCodChngVO.setAf_br_cod("A");  // 이후 지점코드 : A(판교점)

		try {
			nUdtCnt = accInfMapper.updateAccInfByMgmtBrCod(brCodChngVO);
		}
		catch(Exception e) {
			
			sMsg = "계좌의 관리지점 갱신 처리 오류 발생.";
			
			logs.log(e, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		if( nUdtCnt < 1 ) {
			
			sMsg = "통폐합 계좌 관리지점코드 갱신 오류 발생.";
			
			logs.log(Consts.LOG_TYPE_ERROR, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		return "분당점과 판교점 통폐합 처리 완료.";
	}
	
	/*
	 * 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API
	 * 입력 : 지점명
	 * 출력 : {지점명, 지점코드, 거래합계금액}
	 */
	@GetMapping("/selectBrTrdSumAmt")
	public String selectBrTrdSumAmt(@RequestParam String brName) throws NotFoundException {
		
		// 오류 메시지 설정
		String sMsg = null;
		
		// 입력 지점정보 존재 여부 확인
		
		BrInfVO brInfVO = new BrInfVO();

		brInfVO.setBr_nm(brName);
		brInfVO.setBr_sts_cod("1");  // 1(사용)
		
		// 지점명으로 조회가능하다는 것은 두 개 이상의 지점에서 동일한 지점명을 사용할 수 없다는 전재하에 딘건 조회
		try {
			brInfVO = brInfMapper.selectBrInfByBrNm(brInfVO);
		}
		catch(Exception e) {
			
			sMsg = "지점정보 단건 조회 처리 오류 발생.";
			
			logs.log(e, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		if( null == brInfVO ) {
			
			sMsg = "지점정보 없음.";
			
			logs.log(Consts.LOG_TYPE_ERROR, sMsg);
			
			return Utils.getRspMsg("404", "br code not found error");
		}

		SumAmtBrInfVO sumAmtBrInfVO = brInfMapper.selectBrTrdSumAmt(brInfVO);
		
		JSONObject jsonObject = new JSONObject();
		
		if( null == sumAmtBrInfVO ) {
			
			jsonObject.put("brName", brInfVO.getBr_nm());
			jsonObject.put("brCode", brInfVO.getBr_cod());
			jsonObject.put("sumAmt", BigDecimal.ZERO);
			
			return jsonObject.toString();
		}
		
		jsonObject.put("brName", sumAmtBrInfVO.getBr_nm());
		jsonObject.put("brCode", sumAmtBrInfVO.getBr_cod());
		jsonObject.put("sumAmt", sumAmtBrInfVO.getSum_amt());
		
		return jsonObject.toString();
	}
}
