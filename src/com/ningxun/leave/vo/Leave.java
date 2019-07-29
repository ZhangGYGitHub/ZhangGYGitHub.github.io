package com.ningxun.leave.vo;

import java.util.Date;



/**
 * Leave entity. @author MyEclipse Persistence Tools
 */

public class Leave  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String reason;
     private Integer raceOrTrain;
     private Integer raceOrTrainId;
     private Integer createUser;
     private Date createTime;
     private Integer modifyUser;
     private Date modifyTime;
     private Integer deleteUser;
     private Date deleteTime;
     private Integer delState;


    // Constructors

    /** default constructor */
    public Leave() {
    }

    
    /** full constructor */
    public Leave(String reason, Integer raceOrTrain, Integer raceOrTrainId, Integer createUser, Date createTime, Integer modifyUser, Date modifyTime, Integer deleteUser, Date deleteTime, Integer delState) {
        this.reason = reason;
        this.raceOrTrain = raceOrTrain;
        this.raceOrTrainId = raceOrTrainId;
        this.createUser = createUser;
        this.createTime = createTime;
        this.modifyUser = modifyUser;
        this.modifyTime = modifyTime;
        this.deleteUser = deleteUser;
        this.deleteTime = deleteTime;
        this.delState = delState;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getRaceOrTrain() {
        return this.raceOrTrain;
    }
    
    public void setRaceOrTrain(Integer raceOrTrain) {
        this.raceOrTrain = raceOrTrain;
    }

    public Integer getRaceOrTrainId() {
        return this.raceOrTrainId;
    }
    
    public void setRaceOrTrainId(Integer raceOrTrainId) {
        this.raceOrTrainId = raceOrTrainId;
    }

    public Integer getCreateUser() {
        return this.createUser;
    }
    
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModifyUser() {
        return this.modifyUser;
    }
    
    public void setModifyUser(Integer modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }
    
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getDeleteUser() {
        return this.deleteUser;
    }
    
    public void setDeleteUser(Integer deleteUser) {
        this.deleteUser = deleteUser;
    }

    public Date getDeleteTime() {
        return this.deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getDelState() {
        return this.delState;
    }
    
    public void setDelState(Integer delState) {
        this.delState = delState;
    }
   








}