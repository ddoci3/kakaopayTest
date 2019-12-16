package com.example.ktest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/*
 * �������� �׽�Ʈ
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class BrInfControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	/*
	 * �׽�Ʈ : ������ �������� �ŷ��ݾ� �հ踦 ���ϰ� �հ�ݾ��� ū ������ ����ϴ� API
	 */
	@Test
	void testSelectBrTrdSumAmtByYearNBrList() throws Exception {
		
		// �����
		String expected = "[{\"year\":2018,\"dataList\":[{\"brCode\":\"B\",\"brName\":\"�д���\",\"sumAmt\":38484000},{\"brCode\":\"A\",\"brName\":\"�Ǳ���\",\"sumAmt\":20505700},{\"brCode\":\"C\",\"brName\":\"������\",\"sumAmt\":20232867},{\"brCode\":\"D\",\"brName\":\"�����\",\"sumAmt\":14000000}]},{\"year\":2019,\"dataList\":[{\"brCode\":\"A\",\"brName\":\"�Ǳ���\",\"sumAmt\":66795100},{\"brCode\":\"B\",\"brName\":\"�д���\",\"sumAmt\":45396700},{\"brCode\":\"C\",\"brName\":\"������\",\"sumAmt\":19500000},{\"brCode\":\"D\",\"brName\":\"�����\",\"sumAmt\":6000000}]}]";
		
		mockMvc.perform(get("/selectBrTrdSumAmtByYearNBrList"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
	}

	/*
	 * �׽�Ʈ : ���� ������ ó��
	 */
	@Test
	void testIntgBrInf() throws Exception {
		
		// �����
		String expected = "�д����� �Ǳ��� ������ ó�� �Ϸ�.";
		
		mockMvc.perform(get("/intgBrInf"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
	}

	/*
	 * �׽�Ʈ : �������� �Է��ϸ� �ش������� �ŷ��ݾ� �հ踦 ����ϴ� API
	 */
	@Test
	void testSelectBrTrdSumAmt() throws Exception {
		
		/*
		 * 1. �д������� ��ȸ�� ���� �߻�
		 */
		
		// �����
		String expected = "{\"code\":\"404\", \"�޼���\":\"br code not found error\"}";
		
		mockMvc.perform(get("/selectBrTrdSumAmt?brName=�д���"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
		
		/*
		 * 2. �Ǳ������� ��ȸ�� ���� ��ȸ
		 */

		// �����
		expected = "{\"brCode\":\"A\",\"brName\":\"�Ǳ���\",\"sumAmt\":171181500}";
		
		mockMvc.perform(get("/selectBrTrdSumAmt?brName=�Ǳ���"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
	}
}
