package com.ningxun.sign.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.leave.vo.Leave;
import com.ningxun.race.dao.RaceDAO;
import com.ningxun.race.vo.Race;
import com.ningxun.sign.vo.Sign;
import com.ningxun.sign.vo.SignCount;
import com.ningxun.team.dao.TeaminfoDao;
import com.ningxun.team.vo.UserTeam;
import com.ningxun.train.dao.TrainDAO;
import com.ningxun.train.vo.Train;

/**
*<li>技术支持:河北凝讯科技有限公司<br/>
*<li>项目名称: rongqiu<br/>
*<li>文件名: signDao.java<br/>
*<li>创建时间: 2017年7月23日 下午6:37:05<br/>
*
*@author jmq
*@version [v1.00]
*/
public class signDao extends HibernateDAO{

	private RaceDAO raceDAO = new RaceDAO();
	private TrainDAO trainDAO = new TrainDAO();
	private TeaminfoDao teaminfoDao = new TeaminfoDao();
	private String tagName[];//json对象的key数组
	
	/**
	 * 
	* 描述: 查询所需签到的比赛列表<br/>
	*	
	* @param uid 用户id
	* @return List 返回类型
	*
	* 创建时间：2017年7月23日/下午9:55:00<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findRaceList(Integer uid) throws Exception{
		String sql = "SELECT r.id,r.raceName,r.startTime,r.endTime,"
				+ "r.opponent,(SELECT t.name FROM teaminfo t WHERE "
				+ "t.id = r.oppoId AND t.delState = 0) opponentName,s.id AS sid, "
				+ "s.isAttendance,ut.position  FROM race r LEFT JOIN sign s ON "
				+ "s.raceortrainId = r.id LEFT JOIN user_team AS ut ON ( ut.uid = "
				+ "s.uid AND ut.tid = r.tid ) WHERE r.signType = 1 AND "
				+ "r.isRelease = 1 AND s.uid = "+uid+" AND s.raceortrain = "
				+ "2 AND r.delState = 0 AND s.delState = 0 ORDER "
				+ "BY r.startTime DESC";
		return findBySql(sql);
	}
	/**
	 * 
	* 描述: 根据用户id查询训练签到列表<br/>
	* 
	* @param uid 用户id
	* @return List 返回类型
	*
	* 创建时间：2017年7月24日/上午8:13:55<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findTrainList(Integer uid) throws Exception{
		String sql = "SELECT s.id, t.id AS trid, t.trainName, t.trainContent, "
				+ "t.startTime, ut.position, s.isAttendance,t.endTime FROM train AS t "
				+ "LEFT JOIN sign AS s ON s.raceortrainId = t.id LEFT JOIN "
				+ "user_team AS ut ON ( ut.tid = t.tid AND ut.uid = s.uid ) WHERE t.issign = 1 AND "
				+ "t.isRelease = 1 AND t.delState = 0 AND s.uid = "+uid+" AND "
				+ "s.delState = 0 AND s.raceortrain = 1 ORDER BY t.startTime DESC";
		return findBySql(sql);
	}
	/**
	 * 
	* 描述: 根据用户id查询所有比赛训练信息<br/>
	*
	* @return List 返回类型
	*
	* 创建时间：2017-8-11/上午9:46:18<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findAllListTRList(Integer uid) throws Exception{
		String sql = "SELECT r.id AS rtid, r.raceName AS rtName, r.startTime AS startTime, " +
				"r.endTime AS endTime, r.opponent,( SELECT t.`name` FROM teaminfo t WHERE " +
				"t.id = r.oppoId AND t.delState = 0 ) AS t2, s.id AS sid, s.isAttendance AS isAttendance, " +
				"ut.position as position, s.raceortrain AS raceortrain FROM race r LEFT JOIN sign " +
				"s ON s.raceortrainId = r.id LEFT JOIN user_team AS ut ON ( ut.uid = s.uid AND ut.tid = r.tid ) " +
				"WHERE r.signType = 1 AND r.isRelease = 1 AND s.uid = "+uid+" AND s.raceortrain = 2 AND r.delState = 0 " +
				"AND s.delState = 0 UNION ALL SELECT t.id AS rtid, t.trainName AS rtName, t.startTime AS startTime, " +
				"t.endTime AS endTime,t.trainPlace,'' AS t2,  s.id AS sid, s.isAttendance AS " +
				"isAttendance, ut.position as position, s.raceortrain AS raceortrain FROM train AS t LEFT JOIN " +
				"sign AS s ON s.raceortrainId = t.id LEFT JOIN user_team AS ut ON ( ut.tid = t.tid AND ut.uid = " +
				"s.uid ) WHERE t.issign = 1 AND t.isRelease = 1 AND t.delState = 0 AND s.uid = "+uid+" AND s.delState = 0 " +
				"AND s.raceortrain = 1 ORDER BY startTime DESC";
		return findBySql(sql);
	}
	/**
	 * 
	* 描述: 下拉加载比赛训练<br/>
	*
	* @return List 返回类型
	*
	* 创建时间：2017-8-13/下午4:43:41<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findAllListTRList(Integer uid,int pageNo,int pageSize) throws Exception{
		String sql =getAListllTRListSql(uid);
		return findBySql(sql, pageNo, pageSize);
	}
	/**
	 * 
	* 描述: 下拉加载比赛训练sql<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-8-13/下午4:44:10<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String getAListllTRListSql(Integer uid) throws Exception{
		String sql = "SELECT r.id AS rtid, r.raceName AS rtName, r.startTime AS startTime, " +
				"r.endTime AS endTime, r.opponent,( SELECT t.`name` FROM teaminfo t WHERE " +
				"t.id = r.oppoId AND t.delState = 0 ) AS t2, s.id AS sid, s.isAttendance AS isAttendance, " +
				"ut.position as position, s.raceortrain AS raceortrain FROM race r LEFT JOIN sign " +
				"s ON s.raceortrainId = r.id LEFT JOIN user_team AS ut ON ( ut.uid = s.uid AND ut.tid = r.tid ) " +
				"WHERE r.signType = 1 AND r.isRelease = 1 AND s.uid = "+uid+" AND s.raceortrain = 2 AND r.delState = 0 " +
				"AND s.delState = 0 UNION ALL SELECT t.id AS rtid, t.trainName AS rtName, t.startTime AS startTime, " +
				"t.endTime AS endTime,t.trainPlace,'' AS t2,  s.id AS sid, s.isAttendance AS " +
				"isAttendance, ut.position as position, s.raceortrain AS raceortrain FROM train AS t LEFT JOIN " +
				"sign AS s ON s.raceortrainId = t.id LEFT JOIN user_team AS ut ON ( ut.tid = t.tid AND ut.uid = " +
				"s.uid ) WHERE t.issign = 1 AND t.isRelease = 1 AND t.delState = 0 AND s.uid = "+uid+" AND s.delState = 0 " +
				"AND s.raceortrain = 1 ORDER BY startTime DESC";
		tagName = new String[] { "rtid", "rtName","startTime","endTime","opponent","t2","sid","isAttendance","position","raceortrain"};
		return sql;
	}
	
	
	/**
	 * 
	* 描述: 查询比赛签到名单<br/>
	* 
	* @param rid 比赛id
	* @return List 返回类型
	*
	* 创建时间：2017年7月24日/上午9:59:06<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findRSignUser(Integer rid) throws Exception{
		String sql = "SELECT s.id AS sid, u.id, IFNULL(ut.remakeName,u.nickname) AS finalName,u.nickname, s.isAttendance, " +
				" ut.position, tl.id AS tlid, r.endTime, s.signUp, r.startTime, r.signTime " +
				" FROM sign AS s" +
				" LEFT JOIN race AS r ON r.id = s.raceortrainId" +
				" LEFT JOIN user_team AS ut ON ( ut.uid = s.uid AND ut.tid = r.tid )" +
				" LEFT JOIN tb_leave AS tl ON ( tl.raceOrTrainId = s.raceortrainId AND " +
				" tl.createUser = s.uid AND tl.createTime > IFNULL(s.modifyTime, '1900-1-1') )" +
				" LEFT JOIN `user` AS u ON u.id = s.uid WHERE" +
				" s.raceortrainId = "+rid+" AND s.delState = 0 AND s.raceortrain = 2 AND r.delState = 0 AND u.delState = 0 AND r.signType = 1" +
				" ORDER BY CONVERT (finalName USING gbk) ASC";
		return findBySql(sql);
	}
	
	/**
	 * 
	* 描述: 查询训练签到名单<br/>
	* 
	* @param trid 训练id
	* @return List 返回类型
	*
	* 创建时间：2017年7月24日/上午10:07:19<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findTSignUser(Integer trid) throws Exception{
		String sql ="SELECT s.id AS sid, u.id,  IFNULL(ut.remakeName,u.nickname) AS finalName,u.nickname, s.isAttendance, ut.position, " +
				"tl.id AS tlid, t.endTime, s.signUp, t.startTime, t.signTime FROM sign AS s " +
				"LEFT JOIN train AS t ON t.id = s.raceortrainId " +
				"LEFT JOIN user_team AS ut ON ( ut.uid = s.uid AND t.tid = ut.tid ) " +
				"LEFT JOIN tb_leave AS tl ON ( tl.raceOrTrainId = s.raceortrainId AND tl.createUser = s.uid AND tl.createTime > IFNULL(s.modifyTime, '1900-1-1') )" +
				" LEFT JOIN `user` AS u ON u.id = s.uid WHERE" +
				" s.raceortrainId = "+trid+" AND s.raceortrain = 1 AND s.delState = 0 AND t.delState = 0 AND t.issign = 1" +
				" ORDER BY CONVERT (finalName USING gbk) ASC";
		return findBySql(sql);
	}
	/**
	 * 
	* 描述: 统计签到数<br/>
	* @param userId 用户id
	* @return void 返回类型
	*
	* 创建时间：2017-7-26/下午7:24:45<br/>
	* @author 梦强
	* @version V1.0
	 */
	public void countSign(Integer userId,Date now) throws Exception {
		if (userId != null) {
			SignCount signCount = null;
			String teamSql = "SELECT * FROM user_team AS ut WHERE ut.uid = "+userId;
			//查询出该人所在的球队信息
			List<UserTeam> tIdList =findBySql(teamSql,UserTeam.class);
			//如果集合为空，证明该人没有加入任何球队，则不去统计签到信息
			if (tIdList != null && tIdList.size() > 0) {
				for (int i = 0; i < tIdList.size(); i++) {
					//查询是否统计过该条数据
					String querySql = "SELECT * FROM signcount WHERE uid = "+userId+" AND tid = " + tIdList.get(i).getTid();
					List<SignCount> resultList = findBySql(querySql,SignCount.class);
					//如果有，更新记录，没有则创建新的记录
					if (resultList != null && resultList.size() > 0) {
						signCount = resultList.get(0);
					}else {
						signCount = new SignCount();
					}
					//初始化uid信息
					signCount.setUid(userId);
					//初始化tid信息
					signCount.setTid(tIdList.get(i).getTid());
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					//查询该球队总的比赛次数，不管比赛是否删除，目前删除比赛不删除签到信息，故统计，未来可能修改
					String queryString1 = "SELECT * FROM race WHERE tid = " + tIdList.get(i).getTid() + " AND signType = 1 AND endTime < '"+format.format(now)+"'";
					//查询该球队总的训练次数，不管训练是否删除，目前删除训练不删除签到信息，故统计，未来可能修改
					String queryString2 = "SELECT * FROM train WHERE tid = " + tIdList.get(i).getTid() + " AND issign = 1 AND endTime < '"+format.format(now)+"'";
					List<Race> raceList = findBySql(queryString1, Race.class);
					List<Train> trainList = findBySql(queryString2, Train.class);
					String queryString7 = "SELECT * FROM sign WHERE uid = "+userId+" AND tid = "+ tIdList.get(i).getTid() +" AND delState = 0 ";
					List<Sign> allSignList = findBySql(queryString7, Sign.class);
					//统计总共需要签到的场数
					double sumNumber = 0.0;
					
					for (int j = 0; j < allSignList.size(); j++) {
						for (int j2 = 0; j2 < raceList.size(); j2++) {
							if (raceList.get(j2).getId().equals(allSignList.get(j).getRaceortrainId()) && allSignList.get(j).getRaceortrain().equals(2)) {
								sumNumber++;
							}
						}
					}
					
					for (int j = 0; j < allSignList.size(); j++) {
						for (int j2 = 0; j2 < trainList.size(); j2++) {
							if (trainList.get(j2).getId().equals(allSignList.get(j).getRaceortrainId()) && allSignList.get(j).getRaceortrain().equals(1)) {
								sumNumber++;
							}
						}
					}
					//统计不需要签到的次数
					double unSumNumber = raceList.size() + trainList.size() - sumNumber;
					String queryString3 = "SELECT * FROM sign WHERE uid = "+userId+" AND tid = "+tIdList.get(i).getTid()+" AND delState = 0 AND isAttendance in (1,2)";
					List<Sign> signList= findBySql(queryString3, Sign.class);
					//统计此人签到的次数
					double signNumber = signList.size();
					//计算总的赴约率
					if (sumNumber > 0 && sumNumber >= signNumber) {
						signCount.setSignFourth(signNumber/sumNumber*100);
						//初始化近一个月，半年，一年的总比赛训练场次
						double monthSumNumber = 0.0;
						double halfYearSumNumber = 0.0;
						double yearSumNumber = 0.0;
						//统计进一个月，半年，一年的总比赛场次
						for (int j = 0; j < raceList.size(); j++) {
							if (raceList.get(j).getEndTime().getTime() > (now.getTime()-30*24*60*60*1000L) ) {
								monthSumNumber++;
							}
							if (raceList.get(j).getEndTime().getTime() > (now.getTime()-180*24*60*60*1000L) ) {
								halfYearSumNumber++;
							}
							if (raceList.get(j).getEndTime().getTime() > (now.getTime()-365*24*60*60*1000L) ) {
								yearSumNumber++;
							}
						}
						//统计进一个月，半年，一年的总训练场次
						for (int j = 0; j < trainList.size(); j++) {
							if (trainList.get(j).getEndTime().getTime() > (now.getTime() - 30*24*60*60*1000L) ) {
								monthSumNumber++;
							}
							if (trainList.get(j).getEndTime().getTime() > (now.getTime() - 180*24*60*60*1000L) ) {
								halfYearSumNumber++;
							}
							if (trainList.get(j).getEndTime().getTime() > (now.getTime() - 365*24*60*60*1000L) ) {
								yearSumNumber++;
							}
						}
						//初始化近一个月，半年，一年的签到情况
						double monthSignNum = 0.0;
						double halfYearSignNum = 0.0;
						double yearSignNum = 0.0;
						//统计近一个月，近半年，近一年的签到情况
						for (int j = 0; j < signList.size(); j++) {
							if (signList.get(j).getCreateTime().getTime() > (now.getTime() - 30*24*60*60*1000L)) {
								monthSignNum++;
							}
							if (signList.get(j).getCreateTime().getTime() > (now.getTime() - 180*24*60*60*1000L)) {
								halfYearSignNum++;
							}
							if (signList.get(j).getCreateTime().getTime() > (now.getTime() - 365*24*60*60*1000L)) {
								yearSignNum++;
							}
						}
						//如果近一个月，近半年，近一年的比赛训练总场次不为0，计算赴约率
						if (monthSumNumber-unSumNumber > 0) {
							signCount.setSignFirst(monthSignNum/(monthSumNumber-unSumNumber)*100);
						}else {
							signCount.setSignFirst(100.0);
						}
						if (halfYearSumNumber-unSumNumber >0) {
							signCount.setSignSecond(halfYearSignNum/(halfYearSumNumber-unSumNumber)*100);
						}else {
							signCount.setSignSecond(100.0);
						}
						if (yearSumNumber-unSumNumber > 0) {
							signCount.setSignThird(yearSignNum/(yearSumNumber-unSumNumber)*100);
						}else {
							signCount.setSignThird(100.0);
						}
					}else {
						signCount.setSignFourth(100.0);
						signCount.setSignFirst(100.0);
						signCount.setSignSecond(100.0);
						signCount.setSignThird(100.0);
					}
					
					String queryString4 = "SELECT * FROM sign WHERE uid = "+userId+" AND tid = "+tIdList.get(i).getTid()+" AND signUp = 1 AND isAttendance in(-1,0,3) AND delState = 0 ";
					List<Sign> unSignList = findBySql(queryString4, Sign.class);
					//统计报名但是没有签到次数
					double unSignNumber = 0.0;
					for (int j = 0; j < unSignList.size(); j++) {
						if (unSignList.get(j).getCreateTime().getTime() < now.getTime()) {
							unSignNumber++;
						}
					}
					
					String queryString5 = "SELECT * FROM sign WHERE uid = "+userId+" AND tid = "+tIdList.get(i).getTid()+" AND signUp = 0 AND isAttendance in(-1,0,3) AND delState = 0 ";
					List<Sign> unSignUpList = findBySql(queryString5, Sign.class);
					double unSignUpNumber = 0.0;
					
					for (int j = 0; j < unSignUpList.size(); j++) {
						if (unSignUpList.get(j).getCreateTime().getTime() < now.getTime()) {
							
							unSignUpNumber++;
						}
					}
					//统计没有报名没有签到次数
					
					String queryString6 = "SELECT ( SELECT tl.id FROM tb_leave AS tl WHERE tl.raceortrain = s.raceortrain " +
							"AND tl.raceOrTrainId = s.raceortrainId AND tl.createUser = s.uid ) AS tlid FROM sign AS s WHERE" +
							" uid = "+userId+" AND tid = "+tIdList.get(i).getTid()+" AND signUp = 0 AND isAttendance IN ( -1, 0, 3) AND delState = 0 AND createTime < '"+format.format(now)+"' ";
					List leaveList = findBySql(queryString6);
					//统计请假次数，请假不计算鸽子率
					double leaveNumber = 0.0;
					for (int j = 0; j < leaveList.size(); j++) {
						if (leaveList.get(j) != null) {
							leaveNumber++;
						}
					}
					if (sumNumber > 0) {
						signCount.setDoverate((unSignNumber+(unSignUpNumber-leaveNumber))/sumNumber*100);
					}else {
						signCount.setDoverate(0.0);
					}
					
					//保存或修改签到统计信息
					merge(signCount);
				}
			}
		}
	}
	
	/**
	* 描述: 报名查重<br/>
	*
	* @return List<Sign> 返回类型
	*
	* 创建时间：2017-7-27/上午2:51:30<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public Sign findSignUpList(Integer raceOrTrainId,Integer uid,Integer type)throws Exception{
		String sql="SELECT * FROM sign WHERE uid ="+uid+" AND raceortrain ="+type+" AND raceortrainId ="+raceOrTrainId+" AND delState = 0";
		List<Sign> list = findBySql(sql,Sign.class);
		return (list == null || list.size() == 0 ) ? null : list.get(0);
	}
	
	/**
	* 描述: 获取报名列表<br/>
	*
	* @return List<Sign> 返回类型
	*
	* 创建时间：2017-7-29/下午1:33:23<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public List<Object[]> getSignUpList(Integer raceOrTrainId,Integer type,Integer teamId) throws Exception{
		String sql=" SELECT s.id,u.nickname,ut.remakeName, u.headPortrait, u.headPortraitNew ,s.uid ,  IFNULL(ut.remakeName,u.nickname) AS finalName "
				+ " FROM sign s "
				+ " LEFT JOIN user u ON s.uid = u.id "
				+ " LEFT JOIN user_team ut ON u.id = ut.uid "
				+ " WHERE s.signUp = 1 AND ut.tid = "+teamId+" AND s.raceortrain="+type+" AND s.raceortrainId = "+raceOrTrainId+" AND s.delState=0 AND u.delState = 0 "
				+ " ORDER BY ut.position,CONVERT (finalName USING gbk) ASC ";
		return findBySql(sql);
	}
	
	/**
	 * 
	* 描述: 查询所有用户id<br/>
	* 
	* @return List 返回类型
	*
	* 创建时间：2017年7月24日/上午10:07:19<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List<Integer> findUserId() throws Exception{
		String sql ="SELECT u.id FROM `user` AS u WHERE u.delState "
				+ "= 0 AND u.accountNonExpired = 0 AND "
				+ "u.credentialsNonExpired = 0 AND u.accountNonLocked = 0 "
				+ "AND u.enabled = 0";
		return findBySql(sql);
	}
	
	/**
	 * 根据用户id、球队id获取删除sql的集合
	 * @author hujian
	 * @date 2017年8月2日
	 * @version 1.0
	 * @param uid 用户id
	 * @param tid 球队id
	 * @return
	 * @throws Exception
	 */
	public List<String> getDeleteSignListSqlByTid(Integer uid,Integer tid) throws Exception{
		List<String> sqlList = new ArrayList<String>();
		//训练
		List<Train> trains = trainDAO.getTrainIdsByTid(tid);
		if (trains != null && trains.size() > 0 ) {
			for (int i = 0; i < trains.size(); i++) {
				String sql = "UPDATE sign SET delState = 1 WHERE uid = "+uid+" AND tid = "+tid+" AND raceortrain = 1 AND raceortrainId = "+trains.get(i).getId();
				sqlList.add(sql);
			}
		}
		//比赛
		List<Race> races = raceDAO.getRaceIdsByTid(tid);
		if (races != null && races.size() > 0 ) {
			for (int i = 0; i < races.size(); i++) {
				String sql = "UPDATE sign SET delState = 1 WHERE uid = "+uid+" AND tid = "+tid+" AND raceortrain = 2 AND raceortrainId = "+races.get(i).getId();
				sqlList.add(sql);
			}
		}
		return sqlList;
	}
	/**
	 * 根据uuid和uid查询比赛训练签到列表
	 * @param uuid
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public List<Object[]> findSignList(String uuid,Integer uid) throws Exception{
		String sql ="( SELECT s.id, s.isAttendance, s.uid, t.startTime, t.endTime, "
				+ "t.signTime FROM sign AS s LEFT JOIN train AS t ON t.id = "
				+ "s.raceortrainId LEFT JOIN device AS d ON d.teamId = t.tid WHERE "
				+ "s.uid = "+uid+" AND d.deviceId = \""+uuid+"\" AND s.delState = 0 AND "
				+ "t.delState = 0 AND s.raceortrain = 1 AND t.issign = 1 ) UNION ALL ( "
				+ "SELECT s.id, s.isAttendance, s.uid, r.startTime, r.endTime, r.signTime "
				+ "FROM sign AS s LEFT JOIN race AS r ON r.id = s.raceortrainId LEFT JOIN "
				+ "device AS d ON d.teamId = r.tid WHERE s.uid = "+uid+" AND d.deviceId = \""+uuid+"\" "
				+ "AND s.delState = 0 AND r.delState = 0 AND s.raceortrain = 2 AND r.signType = 1 )";
		return findBySql(sql);
	}
	
	
	/**
	 * 根据球队id获取删除sql的集合
	 * @author hujian
	 * @date 2017年8月2日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 * @throws Exception
	 */
	public List<String> getDeleteSignListSqlByTid(Integer tid) throws Exception{
		List<String> sqlList = new ArrayList<String>();
		//先根据球队id获取所有成员列表
		List<Object[]> memberList = teaminfoDao.getMemberListByTid(tid);
		if (memberList != null && memberList.size() > 0 ) {
			for (int i = 0; i < memberList.size(); i++) {
				 Integer uid = (Integer) memberList.get(i)[0];
				 //再根据成员id获取sql集合
				 List<String> tempSqlList = this.getDeleteSignListSqlByTid(uid, tid);
				 if (tempSqlList != null && tempSqlList.size() > 0 ) {
					 sqlList.addAll(tempSqlList);
				 }
			}
		}
		return sqlList;
	}
	/**
	 * 
	* 描述: 根据rid未签到人员名单<br/>
	* 
	* @return List 返回类型
	*
	* 创建时间：2017年7月24日/上午10:07:19<br/>
	* @author 梦强  
	* @version V1.0
	 * @throws Exception 
	 */
	public List findRLeaveList(Integer rid) throws Exception{
		String sql = "SELECT s.id AS sid, u.id, u.nickname, ut.remakeName, s.isAttendance, r.id AS rid, " +
				"ut.position, tl.id AS tlid FROM sign AS s LEFT JOIN race AS r ON r.id = s.raceortrainId " +
				"LEFT JOIN user_team AS ut ON ( ut.uid = s.uid AND ut.tid = r.tid ) LEFT JOIN tb_leave AS " +
				"tl ON ( tl.raceOrTrainId = s.raceortrainId AND tl.raceOrTrain = 2 AND tl.createUser = s.uid ) LEFT JOIN `user` AS " +
				"u ON u.id = s.uid WHERE s.delState = 0 AND r.delState = 0 AND u.delState = 0 AND " +
				"s.isAttendance <> 1 AND s.isAttendance <> 2 AND s.raceortrain = 2 AND s.raceortrainId = "+rid+"  ORDER BY  " +
				" ut.position ASC, ut.remakeName ASC, u.nickname ASC";
		return findBySql(sql);
	}
	/**
	 * 
	* 描述: 查询训练未签到列表<br/>
	*
	* @return List 返回类型
	*
	* 创建时间：2017-8-10/上午9:13:35<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findTLeavelist(Integer trid) throws Exception{
		String sql = "SELECT s.id AS sid, u.id, u.nickname, ut.remakeName, s.isAttendance, "
				+ "t.id AS trid, ut.position, tl.id AS tlid FROM sign AS s LEFT JOIN train "
				+ "AS t ON t.id = s.raceortrainId LEFT JOIN user_team AS ut ON ( ut.uid = "
				+ "s.uid AND ut.tid = t.tid ) LEFT JOIN tb_leave AS tl ON ( tl.raceOrTrainId "
				+ "= s.raceortrainId AND tl.raceOrTrain = 1 AND tl.createUser = s.uid ) LEFT JOIN `user` AS u ON "
				+ "u.id = s.uid WHERE s.delState = 0 AND t.delState = 0 AND u.delState = 0 "
				+ "AND s.isAttendance <> 1 AND s.isAttendance <> 2 AND s.raceortrain = 1 "
				+ "AND s.raceortrainId = "+trid+"  ORDER BY  ut.position ASC, ut.remakeName ASC, u.nickname ASC";
		return findBySql(sql);
	}
	/**
	 * 
	* 描述: 根据比赛训练id查询签到人员名单<br/>
	*
	* @return List 返回类型
	*
	* 创建时间：2017-8-10/下午1:35:44<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findSYsignList(Integer id,Integer tid,Integer type) throws Exception{
		String sql = "SELECT s.id, u.nickname, ut.remakeName,u.headPortrait, u.headPortraitNew ,s.uid,IFNULL(ut.remakeName,u.nickname) AS finalName FROM sign AS s LEFT " +
				"JOIN `user` AS u ON u.id = s.uid LEFT JOIN user_team AS ut ON " +
				"(ut.uid = u.id AND ut.tid = "+tid+") WHERE s.delState =0 AND s.raceortrain = " +type +
				" AND s.isAttendance in (1,2) AND s.raceortrainId = "+id+" ORDER BY ut.position ASC,CONVERT (finalName USING gbk) ASC ";
		return findBySql(sql);
	}
	
	/**
	 * 
	 * 描述: 查询所有队员名单<br/>
	 * 
	 * @param trid 训练id
	 * @return List 返回类型
	 *
	 * 创建时间：2017年7月24日/上午10:07:19<br/>
	 * @author 梦强  
	 * @version V1.0
	 */
	public List findTSignUserList(Integer id,Integer tid,Integer type) throws Exception{
		String sql = "SELECT DISTINCT u.nickname nick, u.nickname, ut.remakeName,u.headPortrait, u.headPortraitNew ,s.uid FROM sign AS s LEFT " +
				"JOIN `user` AS u ON u.id = s.uid LEFT JOIN user_team AS ut ON " +
				"(ut.uid = u.id AND ut.tid = "+tid+") WHERE s.delState =0 AND s.raceortrain = " +type +
				" AND s.raceortrainId = "+id+" ORDER BY ut.position ASC,ut.remakeName ASC, u.nickname ASC ";
		return findBySql(sql);
	}

	/**
	 * 
	* 描述: 根据用户id，比赛或训练类型，比赛或训练id查询是否已经请假<br/>
	* @param uid 用户id
	* @param type 判断比赛还是训练 1是训练，2是比赛
	* @param id 比赛或训练的id
	*
	* @return Leave 返回类型
	*
	* 创建时间：2017-9-24/下午8:51:24<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public Leave findUserIsLeave(Integer uid,Integer type,Integer id) throws Exception{
		String queryString = "SELECT * FROM tb_leave WHERE createUser = "+uid+" AND raceOrTrain = "+type+" AND raceOrTrainId =  "+id;
		List<Leave> resultList = findBySql(queryString, Leave.class);
		return resultList != null && resultList.size() > 0 ? resultList.get(0) : new Leave() ;
 	}
	/**l;
	 * 
	* 描述: 根据比赛或训练id查询未到场人数<br/>
	*
	* @return List<Object[]> 返回类型
	*
	* 创建时间：2017-10-8/下午10:42:25<br/>
	* @author 纪梦强  
	* @version V1.0
	 * @throws Exception 
	 */
	public List<Object[]> findUnSignList(Integer type,Integer trid) throws Exception{
		String queryString = "SELECT u.nickname, ut.remakeName, u.headPortrait, u.headPortraitNew , tl.id,IFNULL(ut.remakeName,u.nickname) AS finalName " +
				" FROM sign AS s" +
				" LEFT JOIN tb_leave AS tl ON (tl.createUser = s.uid AND tl.raceOrTrainId = s.raceortrainId AND tl.raceOrTrain = s.raceortrain)" +
				" LEFT JOIN `user` AS u ON u.id = s.uid" +
				" LEFT JOIN user_team AS ut ON (ut.uid = s.uid AND ut.tid = s.tid)" +
				" WHERE s.raceortrainId = "+trid+" AND s.raceortrain = "+type+
				" AND s.isAttendance <> 1  AND s.isAttendance <> 2 AND s.signUp = 0"+
				" ORDER BY ut.position ASC,CONVERT (finalName USING gbk) ASC ";
		List<Object[]> resultList = findBySql(queryString);
		return resultList;
	}
	
	/** 
     * 提供精确的小数位四舍五入处理。 
     *  
     * @param value 
     *            需要四舍五入的数字 
     * @param scale 
     *            小数点后保留几位 
     * @return 四舍五入后的结果 
     */  
    public Double round(Double value, Integer scale) {  
        BigDecimal b = new BigDecimal(Double.toString(value));  
        BigDecimal one = new BigDecimal("1");  
        return b.divide(one, scale, BigDecimal.ROUND_HALF_EVEN).doubleValue();  
    }
	

	public String[] getTagName() {
		return tagName;
	}
	public void setTagName(String[] tagName) {
		this.tagName = tagName;
	}
	

	
}
