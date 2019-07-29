package com.ningxun.sign.vo;
/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: SignCount.java<br/>
 *<li>创建时间: 2017-7-26 下午6:56:09<br/>
 *
 *@author zyt
 *@version [v1.00]
 */
public class SignCount {

	private Integer id;
	private Integer uid;
	private Integer tid;
	private Double signFirst;
	private Double signSecond;
	private Double signThird;
	private Double signFourth;
	private Double doverate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Double getSignFirst() {
		return signFirst;
	}
	public void setSignFirst(Double signFirst) {
		this.signFirst = signFirst;
	}
	public Double getSignSecond() {
		return signSecond;
	}
	public void setSignSecond(Double signSecond) {
		this.signSecond = signSecond;
	}
	public Double getSignThird() {
		return signThird;
	}
	public void setSignThird(Double signThird) {
		this.signThird = signThird;
	}
	public Double getSignFourth() {
		return signFourth;
	}
	public void setSignFourth(Double signFourth) {
		this.signFourth = signFourth;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public Double getDoverate() {
		return doverate;
	}
	public void setDoverate(Double doverate) {
		this.doverate = doverate;
	}
}
