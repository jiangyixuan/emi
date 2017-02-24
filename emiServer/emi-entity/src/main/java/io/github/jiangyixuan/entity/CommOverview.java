package io.github.jiangyixuan.entity;

/**
 * 商品概况
 * @author Administrator
 *
 */
public class CommOverview {
	
	private int commId;
	private String commName;
	private String commImg;
	private String nowPrice;
	private String oldPrice;
	
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
	public String getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}
	
	@Override
	public String toString() {
		return "CommOverview [commId=" + commId + ", commName=" + commName
				+ ", commImg=" + commImg + ", nowPrice=" + nowPrice
				+ ", oldPrice=" + oldPrice + "]";
	}
	
}
