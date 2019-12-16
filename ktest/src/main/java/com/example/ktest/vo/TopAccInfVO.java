package com.example.ktest.vo; 

public class TopAccInfVO { 
	
	private int year;
    private String acc_nm; 
    private String acc_no; 
    private int sum_amt;

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
	
    public int getSum_amt() {
		return sum_amt;
	}
	
    public void setSum_amt(int sum_amt) {
		this.sum_amt = sum_amt;
	}
    
	@Override
	public String toString() {
		return "TopAccInfVO [year=" + year + ", acc_nm=" + acc_nm + ", acc_no=" + acc_no + ", sum_amt=" + sum_amt + "]";
	}
}