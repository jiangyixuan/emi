package io.github.jiangyixuan.entity;

public class CommsortBig {
	
	private Integer bigSortId;
	private String bigSortName;
	public Integer getBigSortId() {
		return bigSortId;
	}
	public void setBigSortId(Integer bigSortId) {
		this.bigSortId = bigSortId;
	}
	public String getBigSortName() {
		return bigSortName;
	}
	public void setBigSortName(String bigSortName) {
		this.bigSortName = bigSortName;
	}
	@Override
	public String toString() {
		return "Commsortbig [bigSortId=" + bigSortId + ", bigSortName=" + bigSortName + "]";
	}
	
}
