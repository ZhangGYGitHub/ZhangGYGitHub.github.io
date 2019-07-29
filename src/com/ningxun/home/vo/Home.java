package com.ningxun.home.vo;

import java.util.Date;

import com.ningxun.tools.FormatUtil;
import com.ningxun.util.DateTools;

/**
 * 主页扩展对象
 * @author hujian
 * @date 2017年8月13日
 * @version 1.0
 */
public class Home {

	private Integer id;// 主键
	private String itemName;// 条目名称(比赛名称或者训练名称)
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private String place;// 地点
	private String teamName;// 球队名称
	private String iconNewName;// 我方队徽
	private Integer myScore;// 我方分数
	private Integer opScore;// 对手分数
	private String opponent;// 对手名称
	private Integer oppoId;// 对手id
	private String oppoName;// 当opponent没有值时,对手名称在这个字段中
	private String oppoIcon;// 当opponent没有值时，对手队徽在这个字段中
	private String comments;// 评论条数
	private Integer tid;// 球队id
	private Integer source;// 1：比赛 2：训练
	private Integer signUp;// 报名   0为未报名，1为已报名
	private Integer qingJia;// 请假   该值为null则未请假,有值则为请假人id
	
	//状态: 未开始	    进行中...   已结束
	public String getStatusView() {
		//当前时间
		Date currentTime = new Date();
		//比赛  0未开始 1 进行中  2已结束
		if (currentTime.getTime() > endTime.getTime()) {
			//已经结束
			return "已结束";
		} else if ((currentTime.getTime() >= startTime.getTime()) && (currentTime.getTime() <= endTime.getTime())) {
			//进行中
			return "进行中...";
		} else {
			//未开始
			return "未开始";
		}
	}
	
	public String getTimeView() {
		//获取当天时间凌晨00:00:00的毫秒值
		String crruentDateStr = DateTools.getDateStr();
		//今天的开始时间,也就是00:00:00的毫秒值
		long todayStart = DateTools.dateToLong(crruentDateStr);
		//今天的结束时间,也就是23:59:59的毫秒值
		long todayEnd = todayStart + 1000 * 60 * 60 * 24 - 1000; 
		//昨天的开始时间
		long yesterdayStart = todayStart - 1000 * 60 * 60 * 24;
		//昨天的结束时间
		long yesterdayEnd = todayStart - 1000;
		//明天的开始时间
		long tomorrowStart = todayEnd + 1000;
		//明天的结束时间
		long tomorrowEnd = tomorrowStart + 1000 * 60 * 60 * 24 - 1000;
		long startTimeLong = this.startTime.getTime();
		String dateTime = "";
		if (startTimeLong >= yesterdayStart && startTimeLong <= yesterdayEnd) {
			//昨天
			dateTime = "昨天 "+FormatUtil.formatTime(this.startTime);
			return dateTime;
		} else if (startTimeLong >= todayStart && startTimeLong <= todayEnd) {
			//今天
			dateTime = "今天 "+FormatUtil.formatTime(this.startTime);
			return dateTime;
		} else if (startTimeLong >= tomorrowStart && startTimeLong <= tomorrowEnd) {
			//明天
			dateTime = "明天 "+FormatUtil.formatTime(this.startTime);
			return dateTime;
		} else {
			dateTime = FormatUtil.formatDateTime(this.startTime);
			String week = FormatUtil.formatWeek(this.startTime);
			return dateTime+" "+week;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getIconNewName() {
		return iconNewName;
	}

	public void setIconNewName(String iconNewName) {
		this.iconNewName = iconNewName;
	}

	public Integer getMyScore() {
		return myScore;
	}

	public void setMyScore(Integer myScore) {
		this.myScore = myScore;
	}

	public Integer getOpScore() {
		return opScore;
	}

	public void setOpScore(Integer opScore) {
		this.opScore = opScore;
	}

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public Integer getOppoId() {
		return oppoId;
	}

	public void setOppoId(Integer oppoId) {
		this.oppoId = oppoId;
	}

	public String getOppoName() {
		return oppoName;
	}

	public void setOppoName(String oppoName) {
		this.oppoName = oppoName;
	}

	public String getOppoIcon() {
		return oppoIcon;
	}

	public void setOppoIcon(String oppoIcon) {
		this.oppoIcon = oppoIcon;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getSignUp() {
		return signUp;
	}

	public void setSignUp(Integer signUp) {
		this.signUp = signUp;
	}

	public Integer getQingJia() {
		return qingJia;
	}

	public void setQingJia(Integer qingJia) {
		this.qingJia = qingJia;
	}

}
