package com.example.ktest.vo; 

public class BrInfVO { 
	
    private String br_cod; 
    private String br_nm;
    private String br_sts_cod;
	
    public String getBr_cod() {
		return br_cod;
	}
	
    public void setBr_cod(String br_cod) {
		this.br_cod = br_cod;
	}
	
    public String getBr_nm() {
		return br_nm;
	}
	
    public void setBr_nm(String br_nm) {
		this.br_nm = br_nm;
	}
	
    public String getBr_sts_cod() {
		return br_sts_cod;
	}
	
    public void setBr_sts_cod(String br_sts_cod) {
		this.br_sts_cod = br_sts_cod;
	}
	
    @Override
	public String toString() {
		return "BrInfVO [br_cod=" + br_cod + ", br_nm=" + br_nm + ", br_sts_cod=" + br_sts_cod + "]";
	}
}