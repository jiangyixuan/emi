package com.jyx.emi.bean;

import java.util.List;

/**
 * 增加了按照拼音排序
 * Created by Administrator on 2016/5/14.
 */
public class AllStoreOverview implements Comparable<AllStoreOverview>{
    private int storeId;
    private String brands;
    private String storeImg;
    private String storeName;
    private String pinyin;

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}
   

    public String getStoreImg() {
        return storeImg;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Override
	public String toString() {
		return "AllStoreOverview [storeId=" + storeId + ", brands=" + brands
				+ ", storeImg=" + storeImg + ", storeName=" + storeName
				+ ", pinyin=" + pinyin + "]";
	}


    @Override
    public int compareTo(AllStoreOverview another) {
        return this.pinyin.compareTo(another.getPinyin());
    }
}
