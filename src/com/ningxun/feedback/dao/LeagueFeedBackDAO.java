package com.ningxun.feedback.dao;

import java.util.List;
import com.ningxun.common.HibernateDAO;
import com.ningxun.feedback.vo.FeedBack;
import com.ningxun.feedback.vo.Lable;
import com.ningxun.feedback.vo.LeagueComment;

@SuppressWarnings("unchecked")
public class LeagueFeedBackDAO extends HibernateDAO {
	
	private String tagName[];//json对象的key数组
	/**
	* 描述: 通过lmid查询该场比赛信息<br/>
	* @return Race 返回类型
	* 创建时间：2018-3-22
	* @author liudongxin
	* @version V1.0
	 */
	public Object[] findRace(Integer lmid) throws Exception {
		String sql = "SELECT l.id,l.name,l.creatorId,lm.id AS lmid,t1.name AS t1Name,t2.`name` AS t2Name, " +
				"lm.teamOne,lm.teamTwo,lm.oneWin,lm.twoWin "+
				"FROM league_match lm "+
				"LEFT JOIN league l ON lm.leagueId = l.id "+
				"LEFT JOIN teaminfo t1 ON lm.teamOne = t1.id "+
				"LEFT JOIN teaminfo t2 ON lm.teamTwo = t2.id "+
				"WHERE lm.id = "+lmid+" AND lm.delState = 0 ";
		List<Object[]> list = findBySql(sql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	* 
	* 描述: 查找评价列表<br/>
	* @return List<FeedBack> 返回类型
	* 创建时间：2018-3-22
	* @author liudongxin  
	* @version V1.0
	 */
	public List<Object[]> getList(Integer lmid) throws Exception {
		String sql = "SELECT u.headPortraitNew,u.headPortrait,u.nickname,lc.score,lc.content,lc.createTime "+
					"FROM league_comment lc "+ 
					"LEFT JOIN user u on lc.commentator = u.id "+
					"where lc.matchId = "+lmid+" "+
					"ORDER BY lc.createTime DESC ";
		List<Object[]> list = findBySql(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	/**
	 * 
	* 描述: 通过用户id查找评价分数
	* @return Integer 返回类型
	* 创建时间：2018-3-22
	* lmid  联赛中比赛id
	* @author liudongxin
	* @version V1.0
	 */
	public Integer getScoreByUserId(Integer userId, Integer lmid) throws Exception {
		String sql = "select * from league_comment lc "+
				    "where lc.commentator = "+userId+" AND lc.matchId = "+lmid+" AND lc.delState = 0 ";
		List<LeagueComment> list = findBySql(sql, LeagueComment.class);
		if (list != null && list.size() > 0) {
			return list.get(0).getScore();
		}
		return null;
	}
	
	/**
	 * 根据比赛id获取评价列表
	 * @author liudongxin
	 * @date 2017年7月28日
	 * @version 1.0
	 * @param lmid 比赛id
	 * @param pageNo
	 * @param pageSize
	 * @throws Exception
	 * 
	 */
	public List<Object[]> getList(Integer lmid,int pageNo, int pageSize)throws Exception{
		String sql = getFeedBackListSql(lmid);
		return findBySql(sql, pageNo, pageSize);
	}
	
	/**
	 * 根据比赛id获取评价列表sql
	 * @author liudongxin
	 * @date 2018年3月21日
	 * @version 1.0
	 * @param lmid 比赛id
	 * @return
	 */			  
	public String getFeedBackListSql(Integer lmid) {
		String sql = "SELECT DISTINCT u.headPortraitNew,u.headPortrait, " +
				"u.nickname,lc.score,lc.content,lc.createTime "+
				"FROM league_comment lc "+
				"LEFT JOIN `user` u ON lc.commentator = u.id "+
				"LEFT JOIN league_match lm ON lm.id = lc.matchId "+
				"WHERE lm.id = "+lmid+" "+ 
				"ORDER BY lc.createTime DESC ";
		tagName = new String[] { "headPortraitNew", "nickname","score","cotent","createTime","headPortrait"};
		return sql;
	}

	/**
	 * 根据球队id获取两条评价
	 * @author liudongxin
	 * @date 2018年3月23日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 * @throws Exception 
	 */
	public List<Object[]> getTwoFeedBackByTid(Integer tid) throws Exception {
		String sql = "SELECT DISTINCT lc.id,u.nickname,lc.content "+ 
                     "FROM league_comment lc "+
                     "LEFT JOIN user u ON lc.commentator = u.id "+
                     "LEFT JOIN league_match lm ON lm.id = lc.matchId "+
                     "WHERE lm.teamOne = "+tid+" OR lm.teamTwo = "+tid+" "+
                     "ORDER BY lc.id DESC ";
		return findBySql(sql);
	}

	public String[] getTagName() {
		return tagName;
	}

	public void setTagName(String[] tagName) {
		this.tagName = tagName;
	}
	public List<Lable> findLable() throws Exception {
		String sql =  "select * from lable lab where lab.delState = 0 ";
		return HibernateDAO.findBySql(sql, Lable.class);
	}

}
