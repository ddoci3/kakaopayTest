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
 * ��������
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
	 * ������ �������� �ŷ��ݾ� �հ踦 ���ϰ� �հ�ݾ��� ū ������ ����ϴ� API
	 * �Է� : ����
	 * ��� : {����, {������, �����ڵ�, �ŷ��հ�ݾ�}} ���
	 */
	@GetMapping("/selectBrTrdSumAmtByYearNBrList")
	public String selectBrTrdSumAmtByYearNBrList() {
		
		// ���� �޽��� ����
		String sMsg = null;

		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		JSONArray jsonArraySub = null;
		JSONObject jsonObjectSub = null;
				
		List<SumAmtBrInfVO> sumAmtBrInfVOList = null;
		SumAmtBrInfVO sumAmtBrInfVO = null;

		// ��ȸ ��� �⵵ : 2018��, 2019�� 
		int[] nYearArray = {2018, 2019};
		
		int nCnt = 0;
		
		for(int i=0; i<nYearArray.length; i++) {

			// ������ �������� �ŷ��ݾ� �հ踦 ���ϰ� �հ�ݾ��� ū ������ �ٰ� ��ȸ
			try {
				sumAmtBrInfVOList = brInfMapper.selectBrTrdSumAmtByYearNBrList(nYearArray[i]);
			}
			catch(Exception e) {
				
				sMsg = "������ �������� �ŷ��ݾ� �հ踦 ���ϰ� �հ�ݾ��� ū ������ �ٰ� ��ȸ ó�� ���� �߻�.";
				
				logs.log(e, sMsg);
				
				return Utils.getRspMsg("500", sMsg);
			}

			// ��� ������ ������ ���� ���� �������� ��ȸ
			if( null == sumAmtBrInfVOList || sumAmtBrInfVOList.size() < 1 ) {
				
				sMsg = nYearArray[i] + "�⵵ ��� ������ ����.";
				
				logs.log(Consts.LOG_TYPE_INFO, sMsg);
				
				continue;
			}
				
			if( null == jsonArray ) {
				jsonArray = new JSONArray();
			}

			nCnt = sumAmtBrInfVOList.size();
			
			jsonArraySub = new JSONArray();
			
			// ��� ��� ������ ����
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
			
			sMsg = "���� ��� �����Ͱ� �����ϴ�.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);

			return Utils.getRspMsg("200", sMsg);
		}

		return jsonArray.toString();
	}
	
	/*
	 * ���� ������ ó��
	 * �Է� : ����
	 * ��� : ó����� �޽���
	 */
	@GetMapping("/intgBrInf")
	public String intgBrInf() {
		
		/* �ڸ�Ʈ
		 * ������ �־��� �д����� �Ǳ����� �������Ͽ� �Ǳ������� �������� �̰��ϴ� �۾��� ó���Ѵ�.
		 * �����ڵ�� �Է°����� �����Ѵ�. �д���:B, �Ǳ���:A  
		 */
		
		// ���� �޽��� ����
		String sMsg = null;

		// �̰� �������� ���� ���� Ȯ��

		BrInfVO bfBrInfVO = new BrInfVO();
		
		bfBrInfVO.setBr_cod("B");  // �����ڵ� : B(�д���)
		
		try {
			bfBrInfVO = brInfMapper.selectBrInf(bfBrInfVO);
		}
		catch(Exception e) {
			
			sMsg = "������ �̰� �������� �ܰ� ��ȸ ó�� ���� �߻�.";
			
			logs.log(e, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		if( null == bfBrInfVO ) {
			
			sMsg = "������ �̰� ���������� �������� �ʽ��ϴ�.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);
			
			return Utils.getRspMsg("404", sMsg);
		}
		
		if( !"1".equals(bfBrInfVO.getBr_sts_cod()) ) {
			
			sMsg = "������ �̰� ������ ����� �ʴ� �����Դϴ�.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);
			
			return Utils.getRspMsg("404", sMsg);
		}

		// ���� �������� ���� ���� Ȯ��

		BrInfVO afBrInfVO = new BrInfVO();
		
		afBrInfVO.setBr_cod("B");  // �����ڵ� : B(�д���)
		
		try {
			afBrInfVO = brInfMapper.selectBrInf(afBrInfVO);
		}
		catch(Exception e) {
			
			sMsg = "������ ���� �������� �ܰ� ��ȸ ó�� ���� �߻�.";
			
			logs.log(e, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		if( null == afBrInfVO ) {
			
			sMsg = "������ ���� ���������� �������� �ʽ��ϴ�.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);
			
			return Utils.getRspMsg("404", sMsg);
		}
		
		if( !"1".equals(afBrInfVO.getBr_sts_cod()) ) {
			
			sMsg = "������ ���� ������ ����� �ʴ� �����Դϴ�.";
			
			logs.log(Consts.LOG_TYPE_INFO, sMsg);
			
			return Utils.getRspMsg("404", sMsg);
		}

		// �̰� �������� �� �����ڵ� ���� (1:��� -> 2:�̻��)
		
		bfBrInfVO.setBr_sts_cod("2");  // ���������ڵ� : 1(���) -> 2(�̻��)
		
		int nUdtCnt = 0;
		
		try {
			nUdtCnt = brInfMapper.updateBrInf(bfBrInfVO);
		}
		catch(Exception e) {
			
			sMsg = "������ �̰� ���� �����ڵ� ���� ó�� ���� �߻�.";
			
			logs.log(e, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		if( 1 != nUdtCnt ) {
			
			sMsg = "������ �̰� ���������ڵ� ���� ���� �߻�.";
			
			logs.log(Consts.LOG_TYPE_ERROR, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		// �̰� ������ �������� �� ���������ڵ� ����
		
		BrCodChngVO brCodChngVO = new BrCodChngVO();
		
		brCodChngVO.setBf_br_cod("B");  // ���� �����ڵ� : B(�д���)
		brCodChngVO.setAf_br_cod("A");  // ���� �����ڵ� : A(�Ǳ���)

		try {
			nUdtCnt = accInfMapper.updateAccInfByMgmtBrCod(brCodChngVO);
		}
		catch(Exception e) {
			
			sMsg = "������ �������� ���� ó�� ���� �߻�.";
			
			logs.log(e, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		if( nUdtCnt < 1 ) {
			
			sMsg = "������ ���� ���������ڵ� ���� ���� �߻�.";
			
			logs.log(Consts.LOG_TYPE_ERROR, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		return "�д����� �Ǳ��� ������ ó�� �Ϸ�.";
	}
	
	/*
	 * �������� �Է��ϸ� �ش������� �ŷ��ݾ� �հ踦 ����ϴ� API
	 * �Է� : ������
	 * ��� : {������, �����ڵ�, �ŷ��հ�ݾ�}
	 */
	@GetMapping("/selectBrTrdSumAmt")
	public String selectBrTrdSumAmt(@RequestParam String brName) throws NotFoundException {
		
		// ���� �޽��� ����
		String sMsg = null;
		
		// �Է� �������� ���� ���� Ȯ��
		
		BrInfVO brInfVO = new BrInfVO();

		brInfVO.setBr_nm(brName);
		brInfVO.setBr_sts_cod("1");  // 1(���)
		
		// ���������� ��ȸ�����ϴٴ� ���� �� �� �̻��� �������� ������ �������� ����� �� ���ٴ� �����Ͽ� ��� ��ȸ
		try {
			brInfVO = brInfMapper.selectBrInfByBrNm(brInfVO);
		}
		catch(Exception e) {
			
			sMsg = "�������� �ܰ� ��ȸ ó�� ���� �߻�.";
			
			logs.log(e, sMsg);
			
			return Utils.getRspMsg("500", sMsg);
		}
		
		if( null == brInfVO ) {
			
			sMsg = "�������� ����.";
			
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
