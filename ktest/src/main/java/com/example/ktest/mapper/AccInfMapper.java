package com.example.ktest.mapper;

import java.util.List;

import com.example.ktest.vo.AccInfVO;
import com.example.ktest.vo.BrCodChngVO;
import com.example.ktest.vo.NoTrdAccInfVO;
import com.example.ktest.vo.TopAccInfVO;

public interface AccInfMapper {

	public AccInfVO selectAccInf(AccInfVO accInfVO);
	public TopAccInfVO selectTopAccInfByYear(int year);
	public List<NoTrdAccInfVO> selectNoTrdAccInfByYearList(int year);
	public int updateAccInfByMgmtBrCod(BrCodChngVO brCodChngVO);
}
