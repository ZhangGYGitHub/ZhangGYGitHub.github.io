package com.ningxun.race.vo;



/**
 * TeamRace entity. @author MyEclipse Persistence Tools
 */

public class TeamRace  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer tid;
     private Integer rid;


    // Constructors

    /** default constructor */
    public TeamRace() {
    }

    
    /** full constructor */
    public TeamRace(Integer tid, Integer rid) {
        this.tid = tid;
        this.rid = rid;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return this.tid;
    }
    
    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getRid() {
        return this.rid;
    }
    
    public void setRid(Integer rid) {
        this.rid = rid;
    }
   








}