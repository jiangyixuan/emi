package io.github.jiangyixuan.entity;

public class StoreMap {
	
	private int storeId;
	private String storeName;
	private String storePosition;
	private String storeLon;
	private String storeLat;
	private String brands;
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
	public String getStorePosition() {
		return storePosition;
	}
	public void setStorePosition(String storePosition) {
		this.storePosition = storePosition;
	}
	public String getStoreLon() {
		return storeLon;
	}
	public void setStoreLon(String storeLon) {
		this.storeLon = storeLon;
	}
	public String getStoreLat() {
		return storeLat;
	}
	public void setStoreLat(String storeLat) {
		this.storeLat = storeLat;
	}
	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	@Override
	public String toString() {
		return "StoreMap [storeId=" + storeId + ", storeName=" + storeName
				+ ", storePosition=" + storePosition + ", storeLon=" + storeLon
				+ ", storeLat=" + storeLat + ", brands=" + brands + "]";
	}
	
	

}
