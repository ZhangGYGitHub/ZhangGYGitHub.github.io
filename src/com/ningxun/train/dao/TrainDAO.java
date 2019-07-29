package com.ningxun.train.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.race.vo.Race;
import com.ningxun.team.vo.UserTeam;
import com.ningxun.train.vo.Train;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: TrainDAO.java<br/>
 *<li>创建时间: 2017-7-20 上午9:36:34<br/>
 *
 *@author 高佳伟
 *@version [v1.00]
 */
@SuppressWarnings("all")
public class TrainDAO extends HibernateDAO {
	
	private String tagName[];//json对象的key数组
	
	/**
	 * 根据球队id查询训练列表
	 * @author hujian
	 * @date 2017年7月28日
	 * @version 1.0
	 * @param teamId 球队id
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> findTrainListByTid(Integer teamId,int pageNo,int pageSize) throws Exception {
		String sql = getTrainListSql(teamId);
		return findBySql(sql,pageNo,pageSize);
	}
	
	/**
	 * 获取查询训练列表sql
	 * @author hujian
	 * @date 2017年7月28日
	 * @version 1.0
	 * @param teamId 球队id
	 * @return
	 */
	public String getTrainListSql(Integer teamId) {
		String sql = "SELECT tr.id,tr.trainName,tr.trainPlace,tr.startTime,tr.endTime,tr.issign,tr.signTime,tr.isRelease,tr.createUser,tr.tid,(SELECT COUNT(s.signUp) num FROM sign s WHERE s.signUp = 1 AND s.raceortrain=1 AND s.raceortrainId = tr.id AND s.delState=0) number "
				+ " FROM train tr "
				+ " WHERE tr.tid = "+teamId+" AND tr.delState = 0 "
				+ " ORDER BY tr.startTime DESC";
		tagName = new String[] { "id", "trainName","trainPlace","startTime","endTime","issign","signTime","isRelease","createUser","tid","number"};
		return sql;
	}

	/**
	 * 根据用户id查询一条最新的训练
	 * @author hujian
	 * @date 2017年7月23日
	 * @version 1.0
	 * @param uid 用户id
	 * @return
	 * @throws Exception 
	 */
	public Train getOneTrainByUid(Integer uid) throws Exception {
		String sql = "SELECT t.id,t.trainName,t.trainPlace,t.startTime,t.tid "
				+ " FROM train t "
				+ " LEFT JOIN teaminfo tt ON t.tid = tt.id "
				+ " LEFT JOIN user_team ut ON ut.tid = tt.id "
				+ " LEFT JOIN user u ON u.id = ut.uid "
				+ " WHERE u.id = "+uid+" AND t.isRelease = 1 AND u.delState = 0 AND tt.delState = 0 AND t.delState = 0 "
				+ " ORDER BY t.startTime DESC "
				+ " LIMIT 0,1";
		List<Train> list = findBySql(sql,Train.class);
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	/**
	* 描述: 通过球队id查询该队所有队员<br/>
	*
	* @return List<UserTeam> 返回类型
	*
	* 创建时间：2017-7-28/下午4:20:27<br/>
	* @author 高佳伟  
	* @version V1.0  
	 * @throws Exception 
	*/
	public List<UserTeam> findUserList(Integer teamId) throws Exception {
		String sql = "SELECT uid FROM user_team ut WHERE tid = "+teamId;
		List<UserTeam> list = HibernateDAO.findBySql(sql, UserTeam.class);
		return list;
	}

	/**
	* 描述: 根据比赛id判断是否已经结束<br/>
	*
	* @return true 已经结束    false 没有结束
	* @param trainId 训练id
	* 创建时间：2017-8-1/上午8:42:51<br/>
	* @author 高佳伟  
	* @version V1.0  
	* @throws Exception 
	*/
	public boolean checkTimeOutByRid(Integer trainId) throws Exception {
		Train train = (Train) findById(Train.class,trainId);
		if (train.getEndTime().getTime() < new Date().getTime()) {
			//已经结束
			return true;
		}
		return false;
	}
	
	/**
	 * 根据球队id查询未过时的训练id列表
	 * @author hujian
	 * @date 2017年8月2日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 * @throws Exception
	 */
	public List<Train> getNoTimeOutTrainIdsByTid(Integer tid) throws Exception  {
		String sql = "SELECT id FROM train WHERE tid = "+tid+" AND endTime > '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"' AND delState = 0";
		return findBySql(sql, Train.class);
	}
	
	/**
	 * 根据球队id查询训练id列表
	 * @author hujian
	 * @date 2017年8月2日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 * @throws Exception
	 */
	public List<Train> getTrainIdsByTid(Integer tid) throws Exception  {
		String sql = "SELECT id FROM train WHERE tid = "+tid+" AND delState = 0";
		return findBySql(sql, Train.class);
	}
	
	public String[] getTagName() {
		return tagName;
	}

	public void setTagName(String[] tagName) {
		this.tagName = tagName;
	}

}
