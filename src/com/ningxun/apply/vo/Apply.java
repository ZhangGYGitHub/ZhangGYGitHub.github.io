package com.ningxun.apply.vo;

import java.util.Date;

/**
 * 
 * @author hujian
 * @date 2017年7月20日
 * @version 1.0
 */
public class Apply {

	public static final Integer APPLY_HANDLE_WAITHANDLE = 0;
	public static final Integer APPLY_HANDLE_AGREE = 1;
	public static final Integer APPLY_HANDLE_REJECT = 2;
	public static final Integer APPLY_HANDLE_IGNORE = 3;

	private Integer id;
	private Integer uid;// 用户id
	private Integer tid;// 球队id
	private Integer applyHandle;// 申请处理:0待处理,1已同意,2拒绝,3忽略
	private Date applyTime;// 申请时间
	private Date handleTime;// 处理时间
	private Integer handleUser;// 处理人
	private String reason;// 拒绝理由
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

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

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getApplyHandle() {
		return applyHandle;
	}

	public void setApplyHandle(Integer applyHandle) {
		this.applyHandle = applyHandle;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public Integer getHandleUser() {
		return handleUser;
	}

	public void setHandleUser(Integer handleUser) {
		this.handleUser = handleUser;
	}

}
