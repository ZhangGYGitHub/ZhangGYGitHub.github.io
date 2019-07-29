package com.ningxun.feedback.vo;

import java.util.Date;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: FeedBack.java<br/>
 *<li>创建时间: 2017-7-23 下午1:25:08<br/>
 *
 *@author zyt
 *@version [v1.00]
 */
public class FeedBack {

	private Integer id;
	private Integer score;
	private String comment;
	private Integer userId;
	private Integer trId;
	private Integer type;
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTrId() {
		return trId;
	}
	public void setTrId(Integer trId) {
		this.trId = trId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
