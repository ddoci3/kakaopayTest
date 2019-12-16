package com.example.ktest.vo; 

public class NoTrdAccInfVO { 
	
	private int year;  // ¿¬µµ
    private String acc_nm;  // °èÁÂ¸í
    private String acc_no;  // °èÁÂ¹øÈ£

    public int getYear() {
		return year;
	}
	
    public void setYear(int year) {
		this.year = year;
	}
	
    public String getAcc_nm() {
		return acc_nm;
	}
	
    public void setAcc_nm(String acc_nm) {
		this.acc_nm = acc_nm;
	}
	
    public String getAcc_no() {
		return acc_no;
	}
	
    public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	
	@Override
	public String toString() {
		return "TopAccInfVO [year=" + year + ", acc_nm=" + acc_nm + ", acc_no=" + acc_no + "]";
	}
}