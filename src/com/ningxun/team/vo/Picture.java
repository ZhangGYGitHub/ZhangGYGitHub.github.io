package com.ningxun.team.vo;

import java.util.Date;

/**
 * 全家福
 * 
 * @author hujian
 * @date 2017年7月20日
 * @version 1.0
 */
public class Picture {

	private Integer id;
	private Integer tid;// 球队id
	private String picNewName;// 全家福现名
	private String picOldName;// 全家福原
	private Date createTime;
	private Integer createUser;
	private Date modifyTime;
	private Integer modifyUser;
	private Date deleteTime;
	private Integer deleteUser;
	private Integer delState;// 删除状态:0正常,1删除

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

	public String getPicNewName() {
		return picNewName;
	}

	public void setPicNewName(String picNewName) {
		this.picNewName = picNewName;
	}

	public String getPicOldName() {
		return picOldName;
	}

	public void setPicOldName(String picOldName) {
		this.picOldName = picOldName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(Integer modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Integer getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(Integer deleteUser) {
		this.deleteUser = deleteUser;
	}

	public Integer getDelState() {
		return delState;
	}

	public void setDelState(Integer delState) {
		this.delState = delState;
	}

}
