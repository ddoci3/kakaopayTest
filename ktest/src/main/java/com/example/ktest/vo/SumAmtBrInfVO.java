package com.example.ktest.vo;

import java.math.BigDecimal;

public class SumAmtBrInfVO { 
	
    private String br_nm;
    private String br_cod;
    private BigDecimal sum_amt;
    
	public String getBr_nm() {
		return br_nm;
	}
	
	public void setBr_nm(String br_nm) {
		this.br_nm = br_nm;
	}
	
	public String getBr_cod() {
		return br_cod;
	}
	
	public void setBr_cod(String br_cod) {
		this.br_cod = br_cod;
	}
	
	public BigDecimal getSum_amt() {
		return sum_amt;
	}
	
	public void setSum_amt(BigDecimal sum_amt) {
		this.sum_amt = sum_amt;
	}
	
	@Override
	public String toString() {
		return "SumAmtBrInfVO [br_nm=" + br_nm + ", br_cod=" + br_cod + ", sum_amt=" + sum_amt + "]";
	}
}