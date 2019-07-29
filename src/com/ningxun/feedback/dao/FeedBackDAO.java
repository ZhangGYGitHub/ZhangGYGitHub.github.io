package com.ningxun.feedback.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.feedback.vo.FeedBack;
import com.ningxun.feedback.vo.Lable;
import com.ningxun.train.vo.Train;

@SuppressWarnings("unchecked")
public class FeedBackDAO extends HibernateDAO {
	
	private String tagName[];//json对象的key数组
	/**
	 * 
	* 描述: 通过比赛id查询该场比赛信息<br/>
	*
	* @return Race 返回类型
	*
	* 创建时间：2017-8-8/下午4:59:02<br/>
	* @author Administrator
	* @version V1.0
	*/
	public Object[] findRace(Integer raceId) throws Exception {
		String sql = "select r.raceName, r.type, r.leagueId, " +
				"r.place, r.oppoId, t.`name` as t1name, r.opponent, " +
				"r.opScore, r.myScore, t2.`name` as t2name from " +
				"race r left join teaminfo t on r.oppoId = t.id " +
				"left join teaminfo t2 on r.tid = t2.id where " +
				"r.id = " + raceId + " and r.delState = 0";
		List<Object[]> list = findBySql(sql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 
	* 描述: 通过训练id查询训练<br/>
	*
	* @return Train 返回类型
	*
	* 创建时间：2017-8-8/下午4:59:30<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public Train findTrain(Integer trainId) throws Exception {
		String sql = "select * from train t where " +
				"t.id = " + trainId + " and t.delState = 0 ";
		List<Train> list = findBySql(sql, Train.class);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 
	* 描述: 查找评价列表<br/>
	*
	* @return List<FeedBack> 返回类型
	*
	* 创建时间：2017-7-23/下午2:45:13<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public List<Object[]> getList(Integer trId, Integer type) throws Exception {
		// 类别判断，type为0，为比赛，1为训练
		String sql = "select u.headPortraitNew, u.nickname, " +
				"fb.score, fb.`comment`, fb.createTime, u.headPortrait " +
				"from feedback fb left join " +
				"user u on fb.userId = u.id where " +
				"fb.trId = " + trId + " and fb.type = " + type +
				" order by fb.createTime desc";
		List<Object[]> list = findBySql(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	/**
	 * 
	* 描述: 通过用户id查找评价分数，如果<br/>
	*
	* @return Integer 返回类型
	*
	* 创建时间：2017-7-28/下午6:08:43<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public Integer getScoreByUserId(Integer userId, Integer trId, Integer type) throws Exception {
		String sql = "select * from feedback where userId = " + 
				userId + " and trId = " + trId + " and type = " + type;
		List<FeedBack> list = findBySql(sql, FeedBack.class);
		if (list != null && list.size() > 0) {
			return list.get(0).getScore();
		}
		return null;
	}
	
	/**
	 * 根据比赛或者训练id和类型获取评价列表
	 * @author hujian
	 * @date 2017年7月28日
	 * @version 1.0
	 * @param trId 比赛或者训练id
	 * @param type 类型
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 * 
	 */
	public List<Object[]> getList(Integer trId, Integer type, Integer tId, int pageNo, int pageSize)throws Exception{
		String sql = getFeedBackListSql(trId, type, tId);
		return findBySql(sql, pageNo, pageSize);
	}
	
	/**
	 * 根据比赛或者训练id和类型获取评价列表sql
	 * @author hujian
	 * @date 2017年7月28日
	 * @version 1.0
	 * @param trId 比赛或者训练id
	 * @param type 类型
	 * @return
	 */			  
	public String getFeedBackListSql(Integer trId, Integer type, Integer tId) {
		String sql = "select DISTINCT u.headPortraitNew, u.nickname, " +
				"fb.score, fb.`comment`, fb.createTime, " +
				"u.headPortrait, ut.position " +
				"from feedback fb left join `user` u " +
				"on fb.userId = u.id left join user_team ut " +
				"on u.id = ut.uid left join teaminfo t on ut.tid = t.id " +
				"where fb.trId = " + trId + " and fb.type = " + type + " order by fb.createTime desc ";
		tagName = new String[] { "headPortraitNew", "nickname","score","comment","createTime","headPortrait", "position"};
		return sql;
	}

	/**
	 * 根据比赛id获取两条评价
	 * @author hujian
	 * @date 2017年7月23日
	 * @version 1.0
	 * @param rid 比赛id
	 * @return
	 * @throws Exception 
	 */
	public List<Object[]> getTwoFeedBackByRid(Integer rid) throws Exception {
		String sql = "SELECT DISTINCT f.id,u.nickname,f.comment FROM feedback f LEFT JOIN user u ON f.userId = u.id WHERE f.trId = (SELECT r.id FROM race r WHERE r.id = "+rid+" AND r.endTime < '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"') AND f.type = 0 ORDER BY f.createTime DESC LIMIT 0,2";
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
