package com.jyx.emi.bean;

public class SearchComm {
	
	private int commId;
	private String commName;
	private String commImg;
	private String nowPrice;
	private String storeName;
	
	public int getCommId() {
		return commId;
	}
	public void setCommId(int commId) {
		this.commId = commId;
	}
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public String getCommImg() {
		return commImg;
	}
	public void setCommImg(String commImg) {
		this.commImg = commImg;
	}
	public String getNowPrice() {
		return nowPrice;
	}
	public void setNowPrice(String nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	@Override
	public String toString() {
		return "SearchComm [commId=" + commId + ", commName=" + commName
				+ ", commImg=" + commImg + ", nowPrice=" + nowPrice
				+ ", storeName=" + storeName + "]";
	}
	

}
