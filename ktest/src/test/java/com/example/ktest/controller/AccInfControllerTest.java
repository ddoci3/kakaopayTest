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
class AccInfControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	/*
	 * �׽�Ʈ : 2018��, 2019�� �� ������ �հ� �ݾ��� ���� ���� ���� �����ϴ� API
	 */
	@Test
	void testSelectTopAccInfByYear() throws Exception {
		
		// �����
		String expected = "[{\"acctNo\":\"11111114\",\"year\":2018,\"name\":\"�׵�\",\"sumAmt\":28992000},{\"acctNo\":\"11111112\",\"year\":2019,\"name\":\"���̽�\",\"sumAmt\":40998400}]";
		
		mockMvc.perform(get("/selectTopAccInfByYear"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
	}

	/*
	 * �׽�Ʈ : 2018�� �Ǵ� 2019�⿡ �ŷ��� ���� ���� �����ϴ� API
	 */
	@Test
	void testSelectNoTrdAccInfByYearList() throws Exception {
		
		// �����
		String expected = "[{\"acctNo\":\"11111115\",\"year\":2018,\"name\":\"���\"},{\"acctNo\":\"11111118\",\"year\":2018,\"name\":\"���ӽ�\"},{\"acctNo\":\"11111114\",\"year\":2019,\"name\":\"�׵�\"},{\"acctNo\":\"11111118\",\"year\":2019,\"name\":\"���ӽ�\"}]";
		
		mockMvc.perform(get("/selectNoTrdAccInfByYearList"))
		.andExpect(status().isOk())
		.andExpect(content().string(expected))
		.andDo(print());
	}

}
