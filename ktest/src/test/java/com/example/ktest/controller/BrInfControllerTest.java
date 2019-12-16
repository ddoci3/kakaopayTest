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
 * 지점정보 테스트
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class BrInfControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	/*
	 * 테스트 : 연도별 관리점별 거래금액 합계를 구하고 합계금액이 큰 순서로 출력하는 API
	 */
	@Test
	void testSelectBrTrdSumAmtByYearNBrList() throws Exception {
		
		// 기대결과
		String expected = "[{\"year\":2018,\"dataList\":[{\"brCode\":\"B\",\"brName\":\"분당점\",\"sumAmt\":38484000},{\"brCode\":\"A\",\"brName\":\"판교점\",\"sumAmt\":20505700},{\"brCode\":\"C\",\"brName\":\"강남점\",\"sumAmt\":20232867},{\"brCode\":\"D\",\"brName\":\"잠실점\",\"sumAmt\":14000000}]},{\"year\":2019,\"dataList\":[{\"brCode\":\"A\",\"brName\":\"판교점\",\"sumAmt\":66795100},{\"brCode\":\"B\",\"brName\":\"분당점\",\"sumAmt\":45396700},{\"brCode\":\"C\",\"brName\":\"강남점\",\"sumAmt\":19500000},{\"brCode\":\"D\",\"brName\":\"잠실점\",\"sumAmt\":6000000}]}]";
		
		mockMvc.perform(get("/selectBrTrdSumAmtByYearNBrList"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
	}

	/*
	 * 테스트 : 지점 통폐합 처리
	 */
	@Test
	void testIntgBrInf() throws Exception {
		
		// 기대결과
		String expected = "분당점과 판교점 통폐합 처리 완료.";
		
		mockMvc.perform(get("/intgBrInf"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
	}

	/*
	 * 테스트 : 지점명을 입력하면 해당지점의 거래금액 합계를 출력하는 API
	 */
	@Test
	void testSelectBrTrdSumAmt() throws Exception {
		
		/*
		 * 1. 분당점으로 조회시 오류 발생
		 */
		
		// 기대결과
		String expected = "{\"code\":\"404\", \"메세지\":\"br code not found error\"}";
		
		mockMvc.perform(get("/selectBrTrdSumAmt?brName=분당점"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
		
		/*
		 * 2. 판교점으로 조회시 정상 조회
		 */

		// 기대결과
		expected = "{\"brCode\":\"A\",\"brName\":\"판교점\",\"sumAmt\":171181500}";
		
		mockMvc.perform(get("/selectBrTrdSumAmt?brName=판교점"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
	}
}
