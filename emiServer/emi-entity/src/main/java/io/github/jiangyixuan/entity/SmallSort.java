package io.github.jiangyixuan.entity;
/**
 * 小分类
 * @author Administrator
 *
 */
public class SmallSort {
	
	private int smallSortId;
	private String smallSortName;
	private String img;
	
	public String getSmallSortName() {
		return smallSortName;
	}
	public void setSmallSortName(String smallSortName) {
		this.smallSortName = smallSortName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	public int getSmallSortId() {
		return smallSortId;
	}
	public void setSmallSortId(int smallSortId) {
		this.smallSortId = smallSortId;
	}
	
	@Override
	public String toString() {
		return "SmallSort [smallSortId=" + smallSortId + ", smallSortName="
				+ smallSortName + ", img=" + img + "]";
	}
	

}
