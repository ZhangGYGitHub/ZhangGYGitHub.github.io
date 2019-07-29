package com.ningxun.notice.vo;

import java.util.Date;

import com.ningxun.tools.FormatUtil;

/**
 * Notice entity. @author MyEclipse Persistence Tools
 */
public class Notice implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String content;
	private Integer noticeType;
	private String picNewName;
	private String picOldName;
	private String attachmentNewName;
	private String attachmentOldName;
	private Integer position;
	private String nickName;
	private Integer type;
	private Date createTime;
	private Integer createUser;
	private Date modifyTime;
	private Integer modifyUser;
	private Date deleteTime;
	private Integer deleteUser;
	private Integer delState;

	// Constructors

	/** default constructor */
	public Notice() {
	}

	/** full constructor */ 
	public Notice(String title, String content, Integer noticeType,
			String picNewName, String picOldName, String attachmentNewName,
			String attachmentOldName, Integer position,String nickName,Integer type,Date createTime, Integer createUser,
			Date modifyTime, Integer modifyUser, Date deleteTime,
			Integer deleteUser, Integer delState) {
		this.title = title;
		this.content = content;
		this.noticeType = noticeType;
		this.picNewName = picNewName;
		this.picOldName = picOldName;
		this.attachmentNewName = attachmentNewName;
		this.attachmentOldName = attachmentOldName;
		this.position = position;
		this.nickName = nickName;
		this.type = type;
		this.createTime = createTime;
		this.createUser = createUser;
		this.modifyTime = modifyTime;
		this.modifyUser = modifyUser;
		this.deleteTime = deleteTime;
		this.deleteUser = deleteUser;
		this.delState = delState;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getNoticeType() {
		return this.noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public String getPicNewName() {
		return this.picNewName;
	}

	public void setPicNewName(String picNewName) {
		this.picNewName = picNewName;
	}

	public String getPicOldName() {
		return this.picOldName;
	}

	public void setPicOldName(String picOldName) {
		this.picOldName = picOldName;
	}

	public String getAttachmentNewName() {
		return this.attachmentNewName;
	}

	public void setAttachmentNewName(String attachmentNewName) {
		this.attachmentNewName = attachmentNewName;
	}

	public String getAttachmentOldName() {
		return this.attachmentOldName;
	}

	public void setAttachmentOldName(String attachmentOldName) {
		this.attachmentOldName = attachmentOldName;
	}

	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getModifyUser() {
		return this.modifyUser;
	}

	public void setModifyUser(Integer modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getDeleteTime() {
		return this.deleteTime;
	}

	public void setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
	}

	public Integer getDeleteUser() {
		return this.deleteUser;
	}

	public void setDeleteUser(Integer deleteUser) {
		this.deleteUser = deleteUser;
	}

	public Integer getDelState() {
		return this.delState;
	}

	public void setDelState(Integer delState) {
		this.delState = delState;
	}

	public Integer getposition() {
		return position;
	}

	public void setposition(Integer position) {
		this.position = position;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}