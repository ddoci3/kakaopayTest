package com.example.ktest.mapper;

import java.util.List;

import com.example.ktest.vo.BrInfVO;
import com.example.ktest.vo.SumAmtBrInfVO;

public interface BrInfMapper {

	public BrInfVO selectBrInf(BrInfVO brInfVO);
	public BrInfVO selectBrInfByBrNm(BrInfVO brInfVO);
	public List<SumAmtBrInfVO> selectBrTrdSumAmtByYearNBrList(int year);
	public SumAmtBrInfVO selectBrTrdSumAmt(BrInfVO brInfVO);
	public int updateBrInf(BrInfVO brInfVO);
}
