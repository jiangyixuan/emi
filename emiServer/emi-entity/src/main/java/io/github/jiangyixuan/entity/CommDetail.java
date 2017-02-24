package io.github.jiangyixuan.entity;
/**
 * 商品详情
 * @author Administrator
 *
 */

public class CommDetail {
	
	private int commId;//商品Id
	private String material;//材质
	private String washing;//洗涤方式
	private String productionplace;//产地
	private String remark;//备注
	private String number;//编号
	private String img1;//图片url
	private String img2;
	private String img3;
	private String img4;
	private String imgDetail;//商品介绍图片url
	private String deadline;//截止日期
	private String brands;//商品品牌名
	private int storeId;
	private String storeTel;//店铺电话
	private String storePosition;
	public int getCommId() {
		return commId;
	}
	public void setCommId(int commId) {
		this.commId = commId;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getWashing() {
		return washing;
	}
	public void setWashing(String washing) {
		this.washing = washing;
	}
	public String getProductionplace() {
		return productionplace;
	}
	public void setProductionplace(String productionplace) {
		this.productionplace = productionplace;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public String getImgDetail() {
		return imgDetail;
	}
	public void setImgDetail(String imgDetail) {
		this.imgDetail = imgDetail;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getBrands() {
		return brands;
	}
	public void setBrands(String brands) {
		this.brands = brands;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getStoreTel() {
		return storeTel;
	}
	public void setStoreTel(String storeTel) {
		this.storeTel = storeTel;
	}
	public String getStorePosition() {
		return storePosition;
	}
	public void setStorePosition(String storePosition) {
		this.storePosition = storePosition;
	}
	@Override
	public String toString() {
		return "CommDetail [commId=" + commId + ", material=" + material
				+ ", washing=" + washing + ", productionplace="
				+ productionplace + ", remark=" + remark + ", number=" + number
				+ ", img1=" + img1 + ", img2=" + img2 + ", img3=" + img3
				+ ", img4=" + img4 + ", imgDetail=" + imgDetail + ", deadline="
				+ deadline + ", brands=" + brands + ", storeId=" + storeId
				+ ", storeTel=" + storeTel + ", storePosition=" + storePosition
				+ "]";
	}
	
	
}
