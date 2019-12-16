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
 * ��������
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
	 * 2018��, 2019�� �� ������ �հ� �ݾ��� ���� ���� ���� �����ϴ� API
	 * �Է� : ����
	 * ��� : {����, ���¸�, ���¹�ȣ, �ŷ��հ�ݾ�} ���
	 */
	@GetMapping("/selectTopAccInfByYear")
	public String selectTopAccInfByYear() {
		
		// ���� �޽��� ����
		String sMsg = null;

		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		
		TopAccInfVO topAccInfVO = null;

		// ��ȸ ��� �⵵ : 2018��, 2019�� 
		int[] nYearArray = {2018, 2019};
		
		for(int i=0; i<nYearArray.length; i++) {
		
			// ������ �հ� �ݾ� ���� ���� �� �ܰ� ��ȸ
			try {
				topAccInfVO = accInfMapper.selectTopAccInfByYear(nYearArray[i]);
			}
			catch(Exception e) {
				
				sMsg = "������ �հ� �ݾ� ���� ���� �� �ܰ� ��ȸ ó�� ���� �߻�.";
				
				logs.log(e, sMsg);
				
				return Utils.getRspMsg("500", sMsg);
			}

			// ��� ������ ������ ���� ���� �������� ��ȸ
			if( null == topAccInfVO ) {
				
				sMsg = nYearArray[i] + "�⵵ ��� ������ ����.";
				
				logs.log(Consts.LOG_TYPE_INFO, sMsg);
				
				continue;
			}
			
			if( null == jsonArray ) {
				jsonArray = new JSONArray();
			}
				
			// ��� ������ ����
			jsonObject = new JSONObject();
			
			jsonObject.put("year", topAccInfVO.getYear());
			jsonObject.put("name", topAccInfVO.getAcc_nm());
			jsonObject.put("acctNo", topAccInfVO.getAcc_no());
			jsonObject.put("sumAmt", topAccInfVO.getSum_amt());
			
			jsonArray.put(jsonObject);
		}
		
		if( null == jsonArray ) {
			
			sMsg = "���� ��� �����Ͱ� �����ϴ�.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);

			return Utils.getRspMsg("200", sMsg);
		}

		return jsonArray.toString();
	}
	
	/*
	 * 2018�� �Ǵ� 2019�⿡ �ŷ��� ���� ���� �����ϴ� API
	 * �Է� : ����
	 * ��� : {����, ���¸�, ���¹�ȣ} ���
	 */
	@GetMapping("/selectNoTrdAccInfByYearList")
	public String selectNoTrdAccInfByYearList() {
		
		// ���� �޽��� ����
		String sMsg = null;

		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		
		List<NoTrdAccInfVO> noTrdAccInfVOList = null;
		NoTrdAccInfVO noTrdAccInfVO = null;

		// ��ȸ ��� �⵵ : 2018��, 2019�� 
		int[] nYearArray = {2018, 2019};
		
		int nCnt = 0;
		
		for(int i=0; i<nYearArray.length; i++) {
		
			// �ش� ������ �ŷ��� ���� �� �ٰ� ��ȸ
			try {
				noTrdAccInfVOList = accInfMapper.selectNoTrdAccInfByYearList(nYearArray[i]);
			}
			catch(Exception e) {
				
				sMsg = "������ �ŷ��� ���� �� �ٰ� ��ȸ ó�� ���� �߻�.";
				
				logs.log(e, sMsg);
				
				return Utils.getRspMsg("500", sMsg);
			}

			// ��� ������ ������ ���� ���� �������� ��ȸ
			if( null == noTrdAccInfVOList || noTrdAccInfVOList.size() < 1 ) {
				
				sMsg = nYearArray[i] + "�⵵ ��� ������ ����.";
				
				logs.log(Consts.LOG_TYPE_INFO, sMsg);
				
				continue;
			}
			
			if( null == jsonArray ) {
				jsonArray = new JSONArray();
			}
				
			nCnt = noTrdAccInfVOList.size();
			
			// ��� ��� ������ ����
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
			
			sMsg = "���� ��� �����Ͱ� �����ϴ�.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);

			return Utils.getRspMsg("200", sMsg);
		}

		return jsonArray.toString();
	}
}
