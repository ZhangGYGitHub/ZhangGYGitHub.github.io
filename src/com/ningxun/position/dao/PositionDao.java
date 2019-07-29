package com.ningxun.position.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.position.vo.Position;

/**
* 职位Dao
* @author hujian
* @date 2017年7月21日
* @version 1.0
*/
public class PositionDao extends HibernateDAO{
	
	/**
	 * 根据用户id获取所在球队的职位
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @param tid 球队id
	 * @param uid 用户id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Position queryPositionByUidAndTid(Integer tid,Integer uid) throws Exception{
		String sql = "SELECT id,positionName FROM position WHERE id = (SELECT DISTINCT position FROM user_team WHERE uid = "+uid+" AND tid  = "+tid+") AND delState = 0";
		List<Position> list = findBySql(sql,Position.class);
		//一个用户在一个队只能有一个职位
		return (list != null && list.size()> 0) ? list.get(0) : null;
	}

}
