package com.example.ktest.vo; 

public class AccInfVO { 
	
    private String acc_no; 
    private String acc_nm; 
    private String mgmt_br_cod;
    
	public String getAcc_no() {
		return acc_no;
	}

	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}

	public String getAcc_nm() {
		return acc_nm;
	}

	public void setAcc_nm(String acc_nm) {
		this.acc_nm = acc_nm;
	}

	public String getMgmt_br_cod() {
		return mgmt_br_cod;
	}

	public void setMgmt_br_cod(String mgmt_br_cod) {
		this.mgmt_br_cod = mgmt_br_cod;
	}

    @Override
	public String toString() {
		return "AccInfVO [acc_no=" + acc_no + ", acc_nm=" + acc_nm + ", mgmt_br_cod=" + mgmt_br_cod + "]";
	}
}