package com.ningxun.train.vo;



import java.util.Date;

import com.ningxun.tools.FormatUtil;


/**
* <li>扩展类TrainCustom<br/>
* <li>创建时间：2017-7-28 下午12:50:48<br/>
*
* 描述:这里用一句话描述这个类的作用<br/>
* @author 高佳伟
*/
public class TrainCustom extends Train{

	public static final Integer RACE_STATUS_NO_START = 0;//未开始
	public static final Integer RACE_STATUS_PROCESSING = 1;//进行中
	public static final Integer RACE_STATUS_END = 2;//已结束
	
	private Integer number;// 报名人数
	
	public String getStartTimeView() {
		return FormatUtil.formatDateTime(this.getStartTime());
	}
	public String getSignTypeView() {
		// 签到类型         不签到   提前半小时  提前一小时  提前两小时
		//不签到
		if (this.getIssign() != null && this.getIssign() == 0) {
			return "无需签到";
		}
		//签到
		int sign = (this.getSignTime() == null) ? 3 : this.getSignTime();
		if (sign == 0) {
			return "提前半小时";
		} else if (sign == 1) {
			return " 提前一小时";
		} else if (sign == 2) {
			return " 提前两小时";
		} else {
			return "";
		}
	}

	public Integer getTrainStatus() {
		//比赛开始时间
		Date startTime = this.getStartTime();
		//比赛结束时间
		Date endTime = this.getEndTime();
		//当前时间
		Date currentTime = new Date();
		//比赛状态  0未开始 1 进行中  2已结束
		if (currentTime.getTime() > endTime.getTime()) {
			//已经结束
			return RACE_STATUS_END;
		} else if ((currentTime.getTime() >= startTime.getTime()) && (currentTime.getTime() <= endTime.getTime())) {
			//进行中
			return RACE_STATUS_PROCESSING;
		} else {
			//未开始
			return RACE_STATUS_NO_START;
		}
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}
