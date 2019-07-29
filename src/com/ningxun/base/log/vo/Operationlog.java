package com.ningxun.base.log.vo;

import java.util.Date;

/**
 * Operationlog entity. @author MyEclipse Persistence Tools
 */

public class Operationlog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userName;
	private String realName;
	private String companyId;
	private String operationType;
	private String operationContent;
	private Date operationTime;

	// Constructors

	/** default constructor */
	public Operationlog() {
	}

	/** minimal constructor */
	public Operationlog(Date operationTime) {
		this.operationTime = operationTime;
	}

	/** full constructor */
	public Operationlog(String userName, String realName, String companyId, String operationType, String operationContent, Date operationTime) {
		this.userName = userName;
		this.realName = realName;
		this.companyId = companyId;
		this.operationType = operationType;
		this.operationContent = operationContent;
		this.operationTime = operationTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getOperationType() {
		return this.operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getOperationContent() {
		return this.operationContent;
	}

	public void setOperationContent(String operationContent) {
		this.operationContent = operationContent;
	}

	public Date getOperationTime() {
		return this.operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

}