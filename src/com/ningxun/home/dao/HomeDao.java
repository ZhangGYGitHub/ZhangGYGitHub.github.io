package com.ningxun.home.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.ningxun.common.HibernateDAO;
import com.ningxun.common.HibernateSessionFactory;
import com.ningxun.home.vo.Home;

/**
* 主页dao
* @author hujian
* @date 2017年8月13日
* @version 1.0
*/
@SuppressWarnings("all")
public class HomeDao extends HibernateDAO {

	public List<Home> findAll(String startTime,String endTime,Integer uid) throws Exception{
		String sql = "SELECT * FROM "
				+ "	(SELECT r.id id,r.raceName itemName,r.startTime startTime,r.endTime endTime,r.place place,t.name teamName,t.iconNewName iconNewName,r.myScore myScore,r.opScore opScore, r.opponent opponent,r.oppoId oppoId,"
				+ " (SELECT t2.name FROM teaminfo t2 WHERE t2.id = r.oppoId ) oppoName,"
				+ "	(SELECT t3.iconNewName FROM teaminfo t3 WHERE t3.id = r.oppoId ) oppoIcon,"
				+ " (SELECT COUNT(f.id) FROM feedback f WHERE f.trId = r.id AND type = 0) comments,r.tid tid,'1' as source,"
				+ " (SELECT DISTINCT s.signUp FROM sign s WHERE s.uid = "+uid+" AND s.raceortrain = 2 AND s.raceortrainId = r.id AND s.delState = 0) signUp,"
				+ " (SELECT DISTINCT tb.createUser FROM tb_leave tb WHERE tb.createUser ="+uid+" AND tb.raceOrTrain = 2 AND tb.raceOrTrainId = r.id AND tb.delState = 0) qingjia"
				+ " FROM race r "
				+ " LEFT JOIN teaminfo t ON r.tid = t.id "
				+ " LEFT JOIN user_team ut ON t.id = ut.tid "
				+ " LEFT JOIN user u ON u.id = ut.uid "
				+ " WHERE u.id = "+uid+" AND r.isRelease = 1 AND (r.startTime >= '"+startTime+"' AND r.endTime <= '"+endTime+"') AND u.delState = 0 AND t.delState = 0 AND r.delState = 0 "
				+ " UNION "
				+ " SELECT t.id id,t.trainName itemName,t.startTime startTime,t.endTime endTime, t.trainPlace place,'','','','','','','','','',t.tid tid,'2' as source,"
				+ "	(SELECT DISTINCT s.signUp FROM sign s WHERE s.uid = "+uid+" AND s.raceortrain = 1 AND s.raceortrainId = t.id AND s.delState = 0) signUp,"
				+ " (SELECT DISTINCT tb.createUser FROM tb_leave tb WHERE tb.createUser = "+uid+" AND tb.raceOrTrain = 1 AND tb.raceOrTrainId = t.id AND tb.delState = 0) qingjia "
				+ " FROM train t "
				+ " LEFT JOIN teaminfo tt ON t.tid = tt.id "
				+ " LEFT JOIN user_team ut ON ut.tid = tt.id "
				+ " LEFT JOIN user u ON u.id = ut.uid "
				+ " WHERE u.id = "+uid+" AND t.isRelease = 1 AND (t.startTime >= '"+startTime+"' AND t.endTime <= '"+endTime+"') AND u.delState = 0 AND tt.delState = 0 AND t.delState = 0) as a "
				+ " ORDER BY a.startTime DESC, a.endTime DESC";
	    Session session = HibernateSessionFactory.getSession();
	    SQLQuery query = session.createSQLQuery(sql);
	    List<Home> list = this.doMapping(query).setResultTransformer(Transformers.aliasToBean(Home.class)).list();
	    session.close();
	    return list;
	}

	/**
	 * 查询比某个时间小的数据
	 * @author hujian
	 * @date 2017年8月13日
	 * @version 1.0
	 * @param startTime 开始时间
	 * @param uid 用户id
	 * @return
	 * @throws Exception 
	 */
	public List<Home> findListByBefore(String startTime, Integer uid, int pageNo, int pageSize) throws Exception {
		String sql = "SELECT * FROM "
				+ "	(SELECT r.id id,r.raceName itemName,r.startTime startTime,r.endTime endTime,r.place place,t.name teamName,t.iconNewName iconNewName,r.myScore myScore,r.opScore opScore, r.opponent opponent,r.oppoId oppoId,"
				+ " (SELECT t2.name FROM teaminfo t2 WHERE t2.id = r.oppoId ) oppoName,"
				+ "	(SELECT t3.iconNewName FROM teaminfo t3 WHERE t3.id = r.oppoId ) oppoIcon,"
				+ " (SELECT COUNT(f.id) FROM feedback f WHERE f.trId = r.id AND type = 0) comments,r.tid tid,'1' as source,"
				+ " (SELECT DISTINCT s.signUp FROM sign s WHERE s.uid = "+uid+" AND s.raceortrain = 2 AND s.raceortrainId = r.id AND s.delState = 0) signUp,"
				+ " (SELECT DISTINCT tb.createUser FROM tb_leave tb WHERE tb.createUser ="+uid+" AND tb.raceOrTrain = 2 AND tb.raceOrTrainId = r.id AND tb.delState = 0) qingjia"
				+ " FROM race r "
				+ " LEFT JOIN teaminfo t ON r.tid = t.id "
				+ " LEFT JOIN user_team ut ON t.id = ut.tid "
				+ " LEFT JOIN user u ON u.id = ut.uid "
				+ " WHERE u.id = "+uid+" AND r.isRelease = 1 AND r.startTime < '"+startTime+"' AND u.delState = 0 AND t.delState = 0 AND r.delState = 0 "
				+ " UNION "
				+ " SELECT t.id id,t.trainName itemName,t.startTime startTime,t.endTime endTime, t.trainPlace place,'','','','','','','','','',t.tid tid,'2' as source,"
				+ "	(SELECT DISTINCT s.signUp FROM sign s WHERE s.uid = "+uid+" AND s.raceortrain = 1 AND s.raceortrainId = t.id AND s.delState = 0) signUp,"
				+ " (SELECT DISTINCT tb.createUser FROM tb_leave tb WHERE tb.createUser = "+uid+" AND tb.raceOrTrain = 1 AND tb.raceOrTrainId = t.id AND tb.delState = 0) qingjia "
				+ " FROM train t "
				+ " LEFT JOIN teaminfo tt ON t.tid = tt.id "
				+ " LEFT JOIN user_team ut ON ut.tid = tt.id "
				+ " LEFT JOIN user u ON u.id = ut.uid "
				+ " WHERE u.id = "+uid+" AND t.isRelease = 1 AND t.startTime < '"+startTime+"' AND u.delState = 0 AND tt.delState = 0 AND t.delState = 0) as a "
				+ " ORDER BY a.startTime DESC, a.endTime DESC";
	    Session session = HibernateSessionFactory.getSession();
	    SQLQuery query = session.createSQLQuery(sql);
	    query.setFirstResult((pageNo - 1) * pageSize);
	    query.setMaxResults(pageSize);
	    List<Home> list = this.doMapping(query).setResultTransformer(Transformers.aliasToBean(Home.class)).list();
	    session.close();
	    return list;
	}

	/**
	 * 查询比某个时间大的数据
	 * @author hujian
	 * @date 2017年8月13日
	 * @version 1.0
	 * @param endTime 结束时间
	 * @param uid 用户id
	 * @return
	 * @throws Exception 
	 */
	public List<Home> findListByAfter(String endTime, Integer uid, int pageNo, int pageSize) throws Exception {
		String sql = "SELECT * FROM "
				+ "	(SELECT r.id id,r.raceName itemName,r.startTime startTime,r.endTime endTime,r.place place,t.name teamName,t.iconNewName iconNewName,r.myScore myScore,r.opScore opScore, r.opponent opponent,r.oppoId oppoId,"
				+ " (SELECT t2.name FROM teaminfo t2 WHERE t2.id = r.oppoId ) oppoName,"
				+ "	(SELECT t3.iconNewName FROM teaminfo t3 WHERE t3.id = r.oppoId ) oppoIcon,"
				+ " (SELECT COUNT(f.id) FROM feedback f WHERE f.trId = r.id AND type = 0) comments,r.tid tid,'1' as source,"
				+ " (SELECT DISTINCT s.signUp FROM sign s WHERE s.uid = "+uid+" AND s.raceortrain = 2 AND s.raceortrainId = r.id AND s.delState = 0) signUp,"
				+ " (SELECT DISTINCT tb.createUser FROM tb_leave tb WHERE tb.createUser ="+uid+" AND tb.raceOrTrain = 2 AND tb.raceOrTrainId = r.id AND tb.delState = 0) qingjia"
				+ " FROM race r "
				+ " LEFT JOIN teaminfo t ON r.tid = t.id "
				+ " LEFT JOIN user_team ut ON t.id = ut.tid "
				+ " LEFT JOIN user u ON u.id = ut.uid "
				+ " WHERE u.id = "+uid+" AND r.isRelease = 1 AND r.endTime > '"+endTime+"' AND u.delState = 0 AND t.delState = 0 AND r.delState = 0 "
				+ " UNION "
				+ " SELECT t.id id,t.trainName itemName,t.startTime startTime,t.endTime endTime, t.trainPlace place,'','','','','','','','','',t.tid tid,'2' as source,"
				+ "	(SELECT DISTINCT s.signUp FROM sign s WHERE s.uid = "+uid+" AND s.raceortrain = 1 AND s.raceortrainId = t.id AND s.delState = 0) signUp,"
				+ " (SELECT DISTINCT tb.createUser FROM tb_leave tb WHERE tb.createUser = "+uid+" AND tb.raceOrTrain = 1 AND tb.raceOrTrainId = t.id AND tb.delState = 0) qingjia "
				+ " FROM train t "
				+ " LEFT JOIN teaminfo tt ON t.tid = tt.id "
				+ " LEFT JOIN user_team ut ON ut.tid = tt.id "
				+ " LEFT JOIN user u ON u.id = ut.uid "
				+ " WHERE u.id = "+uid+" AND t.isRelease = 1 AND t.endTime > '"+endTime+"' AND u.delState = 0 AND tt.delState = 0 AND t.delState = 0) as a "
				+ " ORDER BY a.startTime ASC, a.endTime DESC";
	    Session session = HibernateSessionFactory.getSession();
	    SQLQuery query = session.createSQLQuery(sql);
	    query.setFirstResult((pageNo - 1) * pageSize);
	    query.setMaxResults(pageSize);
	    List<Home> list = this.doMapping(query).setResultTransformer(Transformers.aliasToBean(Home.class)).list();
	    session.close();
	    return list;
	}
	
	/**
	 * 数据映射
	 * @author hujian
	 * @date 2017年8月28日
	 * @version 1.0
	 * @param query
	 * @return
	 */
	private SQLQuery doMapping(SQLQuery query) {
		//数据映射
		query = query.addScalar("id", Hibernate.INTEGER)
					.addScalar("itemName", Hibernate.STRING)
					.addScalar("startTime", Hibernate.TIMESTAMP)
					.addScalar("endTime", Hibernate.TIMESTAMP)
					.addScalar("place", Hibernate.STRING)
					.addScalar("teamName", Hibernate.STRING)
					.addScalar("iconNewName", Hibernate.STRING)
					.addScalar("myScore", Hibernate.INTEGER)
					.addScalar("opScore", Hibernate.INTEGER)
					.addScalar("opponent", Hibernate.STRING)
					.addScalar("oppoName", Hibernate.STRING)
					.addScalar("oppoId", Hibernate.INTEGER)
					.addScalar("oppoIcon", Hibernate.STRING)
					.addScalar("comments", Hibernate.STRING)
					.addScalar("tid", Hibernate.INTEGER)
					.addScalar("source", Hibernate.INTEGER)
					.addScalar("signUp", Hibernate.INTEGER)
					.addScalar("qingJia", Hibernate.INTEGER);
		return query;
	}
}
