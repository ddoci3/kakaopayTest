package com.example.ktest.vo; 

public class BrCodChngVO { 
	
    private String bf_br_cod;  // ���� �����ڵ� 
    private String af_br_cod;  // ���� �����ڵ�
    
	public String getBf_br_cod() {
		return bf_br_cod;
	}

	public void setBf_br_cod(String bf_br_cod) {
		this.bf_br_cod = bf_br_cod;
	}
	
	public String getAf_br_cod() {
		return af_br_cod;
	}
	
	public void setAf_br_cod(String af_br_cod) {
		this.af_br_cod = af_br_cod;
	}
	
	@Override
	public String toString() {
		return "BrChngVO [bf_br_cod=" + bf_br_cod + ", af_br_cod=" + af_br_cod + "]";
	} 
}