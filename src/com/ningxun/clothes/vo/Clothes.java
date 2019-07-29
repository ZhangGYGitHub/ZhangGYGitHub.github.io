package com.ningxun.clothes.vo;

/**
 * 队服
 * @author hujian
 * @date 2017年7月24日
 * @version 1.0
 */
public class Clothes {

	private Integer id;
	private Integer tid;// 球队id
	private String clothesColour;// 队服颜色
	private String clothesPicNewName;// 队服图片现名
	private String clothesPicOldName;// 队服图片原名

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getClothesColour() {
		return clothesColour;
	}

	public void setClothesColour(String clothesColour) {
		this.clothesColour = clothesColour;
	}

	public String getClothesPicNewName() {
		return clothesPicNewName;
	}

	public void setClothesPicNewName(String clothesPicNewName) {
		this.clothesPicNewName = clothesPicNewName;
	}

	public String getClothesPicOldName() {
		return clothesPicOldName;
	}

	public void setClothesPicOldName(String clothesPicOldName) {
		this.clothesPicOldName = clothesPicOldName;
	}

}
