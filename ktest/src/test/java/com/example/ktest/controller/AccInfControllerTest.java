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
 * 계좌정보 테스트
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AccInfControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	/*
	 * 테스트 : 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객을 추출하는 API
	 */
	@Test
	void testSelectTopAccInfByYear() throws Exception {
		
		// 기대결과
		String expected = "[{\"acctNo\":\"11111114\",\"year\":2018,\"name\":\"테드\",\"sumAmt\":28992000},{\"acctNo\":\"11111112\",\"year\":2019,\"name\":\"에이스\",\"sumAmt\":40998400}]";
		
		mockMvc.perform(get("/selectTopAccInfByYear"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
	}

	/*
	 * 테스트 : 2018년 또는 2019년에 거래가 없는 고객을 추출하는 API
	 */
	@Test
	void testSelectNoTrdAccInfByYearList() throws Exception {
		
		// 기대결과
		String expected = "[{\"acctNo\":\"11111115\",\"year\":2018,\"name\":\"사라\"},{\"acctNo\":\"11111118\",\"year\":2018,\"name\":\"제임스\"},{\"acctNo\":\"11111114\",\"year\":2019,\"name\":\"테드\"},{\"acctNo\":\"11111118\",\"year\":2019,\"name\":\"제임스\"}]";
		
		mockMvc.perform(get("/selectNoTrdAccInfByYearList"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
	}

}
