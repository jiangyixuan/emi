package com.jyx.emi.bean;

/**
 * 店铺名和介绍图片
 * @author Administrator
 *
 */

public class RecommendStoreOverview {
	
	private int storeId;
	private String storeName;
	private String storeImg;

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreImg() {
		return storeImg;
	}

	public void setStoreImg(String storeImg) {
		this.storeImg = storeImg;
	}

	@Override
	public String toString() {
		return "RecommendStoreOverview{" +
				"storeId=" + storeId +
				", storeName='" + storeName + '\'' +
				", storeImg='" + storeImg + '\'' +
				'}';
	}
}
