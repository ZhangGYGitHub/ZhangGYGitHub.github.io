package com.ningxun.leave.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.leave.vo.Leave;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: LeaveDAO.java<br/>
 *<li>创建时间: 2017-7-26 下午3:04:29<br/>
 *
 *@author 高佳伟
 *@version [v1.00]
 */
public class LeaveDAO extends HibernateDAO{
	
	/**
	 * 
	* 描述: 根据比赛id查询请假列表<br/>
	*
	* @return List 返回类型
	*
	* 创建时间：2017-7-28/下午2:38:05<br/>
	* @author 梦强  
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getLeaveList(Integer raceortrainId,Integer raceortrain,Integer teamId) throws Exception{
		String sql = "SELECT tb.id, tb.reason, u.nickname, ut.remakeName, u.headPortrait, u.headPortraitNew, s.isAttendance,IFNULL(ut.remakeName,u.nickname) AS finalName " +
				" FROM tb_leave AS tb" +
				" LEFT JOIN USER u ON u.id = tb.createUser" +
				" LEFT JOIN user_team ut ON ut.uid = u.id" +
				" LEFT JOIN sign AS s ON (s.uid = u.id AND s.tid = "+teamId+" AND s.raceortrain = "+raceortrain+" AND s.raceortrainId = "+raceortrainId+") " +
				"WHERE" +
				" ut.tid = "+teamId+" AND tb.raceOrTrain = "+raceortrain+" AND tb.raceOrTrainId = "+raceortrainId+" AND tb.delState = 0 AND u.delState = 0 " +
				"ORDER BY CONVERT (finalName USING gbk) ASC";
		return findBySql(sql);
	}
	
	/**
	* 描述: 重载：请假查重<br/>
	*
	* @return List<Sign> 返回类型
	*
	* 创建时间：2017-7-27/上午2:51:30<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	@SuppressWarnings("unchecked")
	public Leave findLeaveList(Integer raceOrTrainId,Integer uid,Integer raceOrTrain)throws Exception{
		String sql="SELECT * FROM tb_leave  WHERE createUser ="+uid+" AND raceOrTrain ="+raceOrTrain+" AND raceOrTrainId ="+raceOrTrainId+" AND delState = 0";
		List<Leave> list = HibernateDAO.findBySql(sql,Leave.class);
		return (list == null || list.size() == 0 ) ? null : list.get(0);
	}
		
}
