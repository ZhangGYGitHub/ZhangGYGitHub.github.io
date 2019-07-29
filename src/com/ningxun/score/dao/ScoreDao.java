package com.ningxun.score.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.score.vo.Racescore;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: ScoreDao.java<br/>
 *<li>创建时间: 2017-8-20 下午3:19:04<br/>
 *
 *@author 高佳伟
 *@version [v1.00]
 */
public class ScoreDao extends HibernateDAO {
	
	/**
	* 描述: 分数列表<br/>
	*
	* @return List<Racescore> 返回类型
	*
	* 创建时间：2017-8-21/上午9:21:37<br/>
	* @author 高佳伟  
	* @version V1.0  
	 * @param teamId 
	 * @param raceId 
	*/
	@SuppressWarnings("unchecked")
	public List<Racescore> findRaceScoreList(Integer raceId, Integer teamId) throws Exception {
		String sqlString = "SELECT rs.getScore,rs.yellowCard,rs.redCard,rs.uid FROM racescore rs WHERE raceId = "+raceId+" AND tid = "+teamId+" AND delState = 0 ";
		List<Racescore> list = findBySql(sqlString, Racescore.class);
		return list;
	}

	/**
	* 描述: 查询得分纪录<br/>
	*
	* @return Racescore 返回类型
	*
	* 创建时间：2017-8-21/下午2:56:20<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	@SuppressWarnings("unchecked")
	public Racescore findScoreByUidTidRid(Integer uid, Integer raceId, Integer teamId) throws Exception {
		String sqlString = "SELECT * FROM racescore WHERE uid = "+uid+" AND tid = "+teamId+" AND raceId = "+raceId+" AND delState = 0";
		List<Racescore> list = findBySql(sqlString, Racescore.class);
		return list.size()>0?list.get(0):null;
	}
	
	/**
	* 描述: 得分人员列表<br/>
	*
	* @return List<Racescore> 返回类型
	*
	* 创建时间：2017-8-22/下午4:06:24<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	@SuppressWarnings("unchecked")
	public List<Object> findGetScoreMemberList(Integer raceId, Integer teamId) throws Exception {
		String sqlString = "SELECT DISTINCT u.nickname,ut.remakeName,rs.getScore FROM racescore rs LEFT JOIN user u ON rs.uid = u.id LEFT JOIN user_team ut ON u.id = ut.uid WHERE rs.tid = "+teamId+" AND rs.raceId = "+raceId+" AND rs.delState = 0 AND u.delState = 0 AND getScore > 0 AND ut.tid = "+teamId+" ORDER BY ut.position ASC,ut.remakeName ASC, u.nickname ASC ";
		List<Object> list = findBySql(sqlString);
		return list;
	}
	
	/**
	* 描述: 得牌人员列表<br/>
	*
	* @return List<Racescore> 返回类型
	*
	* 创建时间：2017-8-22/下午4:06:41<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	@SuppressWarnings("unchecked")
	public List<Object> findGetCardMemberList(Integer raceId, Integer teamId) throws Exception {
		String sqlString = "SELECT DISTINCT u.nickname,ut.remakeName,rs.yellowCard,rs.redCard FROM racescore rs LEFT JOIN user u ON rs.uid = u.id LEFT JOIN user_team ut ON u.id = ut.uid WHERE rs.tid = "+teamId+" AND rs.raceId = "+raceId+" AND rs.delState = 0 AND u.delState = 0 AND ( yellowCard != 0 OR redCard != 0 )";
		List<Object> list = findBySql(sqlString);
		return list;
	}
	
	/**
	* 描述: 得分查重<br/>
	*
	* @return List<Object> 返回类型
	*
	* 创建时间：2017-9-28/下午5:53:05<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	@SuppressWarnings("unchecked")
	public Racescore findScore(Integer userId,Integer raceId, Integer teamId) throws Exception {
		String sqlString = "SELECT DISTINCT rs.id FROM racescore rs WHERE rs.tid = "+teamId+" AND rs.raceId = "+raceId+" AND rs.uid = "+userId+" AND rs.delState = 0";
		List<Racescore> list = findBySql(sqlString,Racescore.class);
		return (list == null || list.size() == 0 ) ? null : list.get(0);
	}
}
