package com.ningxun.league.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.ningxun.apply.vo.ApplyLeague;
import com.ningxun.common.HibernateDAO;
import com.ningxun.league.vo.League;
import com.ningxun.league.vo.LeagueCard;
import com.ningxun.league.vo.LeagueMatch;
import com.ningxun.league.vo.LeagueScore;
import com.ningxun.league.vo.LeagueSoccer;
import com.ningxun.league.vo.MatchExamine;
import com.ningxun.league.vo.User;
import com.ningxun.team.vo.Teaminfo;
import com.ningxun.team.vo.UserTeam;
import com.ningxun.wxuserinfo.vo.WXUser;

@SuppressWarnings("unchecked")
public class LeagueDAO extends HibernateDAO{
	
	/**
	* @Description: 查询我创建的联赛
	* @author liyvpeng  
	* @date 2017-12-17
	* @version V2.0
	* @return
	* @throws Exception
	*/
	public List<League> findLeagueByUserId(int id) throws Exception {
		String sql = "SELECT * FROM league WHERE creatorId = " + id + " AND delState = 0";
		return HibernateDAO.findBySql(sql, League.class);
	}
	
	/**
	 * 
	* @Description: 查询我加入的联赛(按球队和裁判选)
	* @author liyvpeng  
	* @date 2017-12-18
	* @version V2.0
	* @return
	* @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<League> findMyJoinLeague(int uid) throws Exception {
			//按参加球队选lid
			String sql1 = "SELECT DISTINCT l.id FROM league l " +
						"LEFT JOIN league_match lm ON lm.leagueId = l.id " +
						"LEFT JOIN user_team ut ON (ut.tid = lm.teamOne OR ut.tid = lm.teamTwo) " +
						"WHERE ut.uid = "+uid+" AND l.delState = 0";
			List<Integer> list = HibernateDAO.findBySql(sql1);
			//按裁判选lid
			String sql2  = "SELECT DISTINCT l.id FROM apply_league al " +
							"LEFT JOIN league l ON al.lid = l.id " +
							"WHERE l.delState = 0 AND al.uid = "+uid+" AND al.applyHandle = 1 AND al.delState = 0 ";
			List<Integer> list2 = HibernateDAO.findBySql(sql2);
			//合并list
			list.addAll(list2);
			//去重lid
			List<Integer> list3 = new ArrayList<Integer>(new HashSet(list));
			List<League> neeList = new ArrayList<League>();
			for(int i = 0; i < list3.size(); i++){
				if(list3.get(i) != null)
					neeList.add((League)HibernateDAO.findById(League.class, list3.get(i)));
			}
			
		return neeList;
	}

	/**
	 * 
	* @Description: 查询所有联赛
	* @author liyvpeng  
	* @date 2017-12-18
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<League> findAllLeague() throws Exception {
		String sql = "SELECT * FROM league WHERE delState = 0";
		return HibernateDAO.findBySql(sql, League.class);
	}

	/**
	 * 
	* @Description: findAllUser
	* @author liyvpeng  
	* @date 2018-1-9
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<User> findAllUser(int lid) throws Exception {
		String sql = "SELECT u.* FROM league_soccer ls "+
				     "LEFT JOIN `user` u ON ls.sid = u.id "+
				     "WHERE ls.lid = "+ lid +" AND  ls.delState = 0 ORDER BY ls.num DESC";
		return HibernateDAO.findBySql(sql,User.class);
	}
	
	/**
	 * 
	* @Description: 赛程
	* @author liyvpeng  
	* @date 2018-1-7
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<LeagueMatch> findScheduleList(int lid) throws Exception {
		String sql = "SELECT * FROM league_match lm WHERE lm.leagueId = " + lid + " and delState = 0";
		return HibernateDAO.findBySql(sql, LeagueMatch.class);
	}
	
	/**
	 * 
	* @Description: 射手榜（球数降序）
	* @author liyvpeng  
	* @date 2018-1-7
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<Object[]> findShootList(int lid) throws Exception {
		String sql = "SELECT u.id, u.nickname, ls.num, u.headPortraitNew, t.`name`, COUNT(u.id) FROM league_soccer ls " +
					"LEFT JOIN `user` u ON u.id = ls.sid " +
					"LEFT JOIN user_team ut ON u.id = ut.uid "+
					"LEFT JOIN teaminfo t ON ut.tid = t.id "+
					"WHERE ls.lid = " + lid + " AND u.delState = 0 "+
					"GROUP BY u.id "+
					"ORDER BY ls.num DESC LIMIT 15";
		List<Object[]> list = HibernateDAO.findBySql(sql);
		//查队名放入list
		for(int i = 0; i < list.size(); i++){
			if(new Integer(list.get(i)[5].toString()) == 1 )
				continue;
			String sql2 = "SELECT t.`name` FROM teaminfo t " +
						"LEFT JOIN user_team ut ON t.id = ut.tid " +
						"WHERE ut.uid = " + list.get(i)[0] + " AND t.delState = 0 AND " +
						"(t.id IN(SELECT lm.teamOne FROM league_match lm WHERE lm.leagueId = " + lid + ") " +
						"OR t.id IN(SELECT lm.teamTwo FROM league_match lm WHERE lm.leagueId = " + lid + "))";
			List<Object[]> nameList = HibernateDAO.findBySql(sql2);
			String tname = "";
			for(int j = 0; j < nameList.size(); j++){
				if(j < nameList.size() - 1){
					tname += nameList.get(j) + "; ";
				}else {
					tname += nameList.get(j);
				}
			}
			list.get(i)[4] = tname;
		}
		
		return list;
	}

	/**
	 * 
	* @Description: find射手byId
	* @author liyvpeng  
	* @date 2018-1-9
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public Object findByShooterId(int id) throws Exception {
		String sql = "SELECT DISTINCT u.id, u.nickname, ls.num, ls.site, u.headPortraitNew, t.`name` FROM league_soccer ls "+ 
					 "LEFT JOIN `user` u ON ls.sid = u.id "+
					 "LEFT JOIN user_team ut ON ls.sid = ut.uid "+
					 "LEFT JOIN teaminfo t ON ut.tid = t.id "+
					 "WHERE u.id = "+ id +" AND ls.delState = 0 AND t.delState = 0";
		return HibernateDAO.findBySql(sql);
	}

	/**
	 * 
	* @Description: 积分榜（积分降序）
	* @author liyvpeng  
	* @date 2018-1-7
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<Object[]> findScoreList(int lid) throws Exception {
		String sql = "SELECT t.`name`, t.iconNewName, ls.win, ls.lose, ls.draw, ls.inNum, ls.outNum, ls.gd, ls.score "+
				"FROM league_score ls "+
				"LEFT JOIN teaminfo t ON ls.team = t.id "+
				"WHERE ls.lid = "+ lid +" AND ls.delState = 0 "+
				"ORDER BY ls.score DESC";
		return HibernateDAO.findBySql(sql);
	}

	/**
	 * 
	* @Description: id查询积分
	* @author liyvpeng  
	* @date 2018-1-9
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public Object findScoreById(int id) throws Exception {
		String sql = "SELECT DISTINCT t.id as tid, t.name as tname, t.iconNewName as pic, ls.win, ls.lose, ls.draw, ls.score "+
					 "FROM league_score ls "+
					 "LEFT JOIN teaminfo t ON ls.id = "+ id +" AND t.id = ls.team WHERE t.delState = 0";
		return HibernateDAO.findBySql(sql);
	}

	/**
	 * 
	* @Description: findAllScore
	* @author liyvpeng  
	* @date 2018-1-9
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<LeagueScore> findAllScore(Integer lid) throws Exception {
		String sql = "SELECT DISTINCT ls.*"+
					"FROM league_score ls "+
					"LEFT JOIN teaminfo t ON ls.lid = "+ lid +" AND t.id = ls.team WHERE t.delState = 0 order by ls.score desc";
		return HibernateDAO.findBySql(sql, LeagueScore.class);
	}
	
	/**
	 * 
	* @Description: 查轮次
	* @author liyvpeng  
	* @date 2018-3-8
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<LeagueMatch> CountTurnById(int lid) throws Exception {
		String sql = "SELECT lm.* FROM league_match lm WHERE lm.leagueId = "+ lid +" AND lm.delState = 0  GROUP BY lm.turn ORDER BY lm.turn ASC";
		return HibernateDAO.findBySql(sql, LeagueMatch.class);
		
	}

	/**
	 * 
	* @Description: 获取match
	* @author liyvpeng  
	* @date 2018-3-8
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<Object[]> findMatchByTurn(int lid, int turn) throws Exception {
		String sql = "SELECT lm.oneWin, lm.twoWin, lm.site, lm.startTime, lm.endTime, "+
						"t1.`name` AS team1, t2.`name` AS team2, t1.iconNewName AS onepic, t2.iconNewName AS twopic, t1.id AS id1, t2.id AS id2, lm.id, lm.finish " +
						"FROM league_match lm "+
						"LEFT JOIN (SELECT t.`name`, t.id, t.iconNewName FROM teaminfo t WHERE t.delState=0) AS t1 ON lm.teamOne = t1.id "+
						"LEFT JOIN (SELECT t.`name`, t.id, t.iconNewName FROM teaminfo t WHERE t.delState=0) AS t2 ON lm.teamTwo = t2.id "+
						"WHERE lm.leagueId = "+ lid +" AND lm.turn = "+ turn +" AND lm.delState = 0 ORDER BY lm.site";
	return HibernateDAO.findBySql(sql);
	}

	/**
	 * 
	* @Description: 校验同名联赛
	* @author liyvpeng  
	* @date 2018-3-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<League> findLeagueNameByName(String name) throws Exception {
		String sql = "SELECT * FROM league l WHERE l.`name` = '" + name + "' AND l.delState = 0";
		return HibernateDAO.findBySql(sql, League.class);
		
	}

	/**
	 * 
	* @Description: 通过id找league
	* @author liyvpeng  
	* @date 2018-3-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public League findLeagueById(int id) throws Exception {
		return (League) HibernateDAO.findById(League.class, id);
		
	}

	/**
	 * 
	* @Description: 通过name模糊查找team
	* @author liyvpeng  
	* @date 2018-3-14
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<Object[]> findTeamLikeName(String name) throws Exception {
		String sql = "SELECT t.id, t.`name`, t.iconNewName, u.nickname FROM teaminfo t "+
					"LEFT JOIN `user` u ON t.captainId = u.id "+
					"WHERE t.delState = 0 AND t.`name` LIKE '%"+name+"%'";
		return HibernateDAO.findBySql(sql);
	}
	
	/**
	 * 
	* @Description: 通过关键字模糊查找联赛
	* @author liudongxin  
	* @date 2018-3-14
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<Object[]> ajaxFindLeagueList(String keyword) throws Exception {
		String sql = "SELECT l.id AS lid,l.name,u.nickname,u.headPortraitNew,u.id " +
				"FROM user u "+
				"LEFT JOIN league l ON l.creatorId = u.id "+
				"WHERE l.name LIKE '%"+keyword+"%'"+
				"ORDER BY l.id DESC";
		return HibernateDAO.findBySql(sql);
	}
	
	/**
	 * 
	* @Description: 邀请时查找联赛 id和用户id
	* @author liudongxin  
	* @date 2018-3-14
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public Object[] findLeagueId(Integer lid) throws Exception {
		String sql = "SELECT u.id,l.id AS lid,l.creatorId,l.name " +
				"FROM league l " +
				"LEFT JOIN user u ON l.creatorId = u.id "+
				"LEFT JOIN user u2 ON u2.id = l.id "+
				"WHERE u.delState = 0 AND l.delState = 0 ";
		List<Object[]> list = HibernateDAO.findBySql(sql);
		if(list != null)
			return list.get(0);
		return null;
	}
	
	/**
	 * 根据联赛id获取管理员
	 * @author liudongxin
	 * @date 2018年3月14日
	 * @version 1.0
	 * @param lid 联赛id
	 * @return
	 */
	public List<WXUser> getAdminListByByLid(Integer lid) throws Exception{
		String sql = "SELECT u.* "
				+ " FROM user u "
				+ " LEFT JOIN league l ON l.creatorId = u.id "
				+ " LEFT JOIN apply_league alg ON alg.lid = l.id "
				+ " WHERE l.id = "+lid+" AND u.delState = 0 AND alg.delState = 0";
		return findBySql(sql,WXUser.class);
	}

	/**
	 * 根据联赛id获取创建联赛人昵称和联赛名称
	 * @author liudongxin
	 * @date 2018年3月17日
	 * @version 1.0
	 * @param lid 联赛id
	 * @return
	 */
	public Object[] getCreatorNameAndLeagueNameByLid(Integer lid) throws Exception{
		String sql ="SELECT u.nickname,l.name,l.id AS lid,u.id "+
				"FROM `user` u "+
				"LEFT JOIN apply_league alg ON alg.handleUser = u.id "+
				"LEFT JOIN league l ON l.id = alg.lid "+
				"WHERE  l.delState = 0 AND u.delState = 0 AND alg.delState = 0";
		List<Object[]> list = findBySql(sql);
		return (list != null && list.size()>0) ? list.get(0) : null ;
	}

	/**
	 * 
	* @Description: 根据联赛id和uid是否已加入裁判组
	* @author liudongxin  
	* @date 2018-3-16
	* @version V2.0
	* @return uid 登录人的id
	* @return lid  联赛id
	* @throws Exception
	 */
	public Object[] queryJoinByApplyLeagueUid(Integer lid,Integer uid) throws Exception{
		String sql = "SELECT uid,lid,applyHandle FROM apply_league alg where alg.lid = "+lid+" AND alg.uid = "+uid+" AND alg.applyHandle = 1 AND alg.delState = 0";
		List<Object[]> list = HibernateDAO.findBySql(sql);
		return (list != null && list.size()> 0) ? list.get(0) : null;
	}
	
	/**
	 * 
	* @Description: 携带分享联赛的基本信息
	* @author liudongxin  
	* @date 2018-3-16
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public Object[] getLeagueinfoDetail(Integer lid) throws Exception {
		String sql = "SELECT l.id,l.`name`,u.nickname,l.createTime,l.introduction,u.headPortraitNew,l.creatorId,l.teamNum,u.id AS uid "+
				    "FROM league l "+
				    "LEFT JOIN user u ON l.creatorId = u.id "+
				    "WHERE l.id = "+lid+" ";
		List<Object[]> list = findBySql(sql);
		return (list != null && list.size()> 0) ? list.get(0) : null;
	}

	/**
	 * 校验用户是否是某联赛的裁判
	 * @author liudongxin
	 * @date 2018年3月16日
	 * @version 2.0
	 * @param uid 用户id
	 * @param lid 联赛id
	 * @return  true： 是      false： 不是
	 * @throws Exception 
	 */
	public boolean checkUserIsReferee(Integer uid,Integer lid) throws Exception{
		String sql = "SELECT id FROM user_team WHERE uid = "+uid+" AND tid = "+lid;
		List<UserTeam> list = findBySql(sql,UserTeam.class);
		if (list != null && list.size() > 0) {
			//是
			return true;
		}
		//不是
		return false;
	}
	
	/**
	 * 查询裁判个人信息
	 * @author liudongxin
	 * @date 2018年3月16日
	 * @version 2.0
	 * @param uid 用户id
	 * @return  true： 是      false： 不是
	 * @throws Exception 
	 */

	public Object[] findRefereeCard(Integer uid,Integer lid) throws Exception {
		String sql = "SELECT u.id,u.nickname,u.headPortraitNew,u.birthday,u.sex, "+
				"u.height,u.weight,u.habitfoot,u.perAutograph,u.synopsis "+
				"FROM `user` u "+
				"LEFT JOIN apply_league alg ON alg.uid = u.id "+
				"LEFT JOIN league l ON l.id = alg.lid "+
				"WHERE l.id = "+lid+" AND u.id = "+uid+" AND u.delState = 0 AND alg.applyHandle = 1 AND alg.delState = 0 "+
				"ORDER BY alg.handleTime ASC ";
		List<Object[]> list = findBySql(sql);
		return (list != null && list.size()> 0) ? list.get(0) : null;
	}

	/**
	 * 删除裁判
	 * @author liudongxin
	 * @date 2018年3月19日
	 * @version 2.0
	 * @param lid 联赛id
	 * @param uid 裁判id
	 * @return  true： 是      false： 不是
	 * @throws Exception 
	 */
	public ApplyLeague findRefereeById(Integer uid) throws Exception {
		String sql = "SELECT alg.id,alg.lid,alg.uid,alg.delTime,alg.deletor,alg.delState "+
					"FROM apply_league alg "+
					"WHERE alg.uid = "+uid+" AND alg.delState = 0 "+
					"ORDER BY alg.id ";
		List<ApplyLeague> list = findBySql(sql, ApplyLeague.class);
		return (list != null && list.size()> 0) ? list.get(0) : null;
	}

	/**
	 * 
	* @Description: 通过tid找队员
	* @author liyvpeng  
	* @date 2018-3-18
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<Object[]> findTeamMemberByTid(int tid) throws Exception {
		String sql = "SELECT DISTINCT u.id, u.nickname FROM user_team ut "+
					"LEFT JOIN `user` u ON u.id = ut.uid "+
					"WHERE ut.tid = " + tid + " AND u.delState = 0";
		return HibernateDAO.findBySql(sql);
	}


	/**
	 * 
	* @Description: 找到每场联赛的裁判
	* @author liyvpeng  
	* @date 2018-3-17
	* @version V2.0
	 * @param joinLeagues 
	* @return
	* @throws Exception
	 */
	public List<Object[]> findReferee(List<League> joinLeagues, int uid) throws Exception {
		List<Object[]> list = new ArrayList<Object[]>();
		for(int i = 0; i < joinLeagues.size(); i++){
			int lid = joinLeagues.get(i).getId();
			String sql = "SELECT l.id, l.`name`, COUNT(al.lid)FROM league l "+
						"LEFT JOIN apply_league al ON al.lid = l.id AND al.applyHandle = 1 "+
						"WHERE l.id = "+lid+" AND l.delState = 0 OR (l.id = "+lid+" AND l.delState = 0 AND al.delState = 0) "+
						"GROUP BY l.id";
			List<Object[]> objects = HibernateDAO.findBySql(sql);
			if(objects.size() != 0)
				list.add(objects.get(0));
		}
		return list;
	}

	/**
	 * 
	* @Description: 通过lid找referee
	* @author liyvpeng  
	* @date 2018-3-17
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<Object[]> findRefereeByLid(Integer lid) throws Exception {
		String sql = "SELECT u.nickname, u.headPortraitNew, u.openId FROM apply_league al "+
					"LEFT JOIN `user` u ON al.uid = u.id "+
					"WHERE al.lid = " + lid + " AND al.applyHandle = 1 AND al.delState = 0";
		return HibernateDAO.findBySql(sql);
	}

	
	/**
	 * 
	* @Description: 判断是否为比赛的一位队长
	* @author liyvpeng  
	* @date 2018-3-19
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public Object[] isCaptainOfMatch(Integer lmid, int userid) throws Exception {
		String sql = "SELECT DISTINCT t.`name` FROM league_match lm "+
				"LEFT JOIN teaminfo t ON "+
				"t.id = lm.teamOne OR t.id = lm.teamTwo "+
				"WHERE lm.id = "+ lmid +" AND t.captainId = " + userid;
		List<Object[]> list = HibernateDAO.findBySql(sql);
		
		Object[] aObjects = {true, true};
		//说明不是队长
		if(list.size() == 0 || list == null){
			aObjects[0] = false;
			aObjects[1] = false;
		}else{
			//是否审核中
			String s = "SELECT * FROM match_examine "+
					"WHERE lmid = "+lmid+"  AND submitter = "+userid+" AND result = 0";
			List<Object[]> l = HibernateDAO.findBySql(s);
			if(l.size() == 0 || l == null){
				aObjects[0] = true;
				aObjects[1] = false;
			}
		}
		return aObjects;
	}

	/**
	 * 
	* @Description: 选择审核对象ByTid
	* @author liyvpeng  
	* @date 2018-3-20
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<Object[]> findscoreExamineByTid(Integer lmid, Integer uid, Integer tid, Integer showType) throws Exception {
		String sql = "";
		//0自己审核中的  1审核人看  2审核通过的
		if(showType == 1){
			sql = "SELECT u.nickname, me.num, me.submitter FROM match_examine me "+
					"LEFT JOIN `user` u ON me.sid = u.id "+
					"WHERE me.lmid = "+lmid+" AND me.tid = "+tid+" AND me.result = 0";
		}else{
			if(showType == 2){
				sql = "SELECT u.nickname, me.num, me.submitter FROM match_examine me "+
						"LEFT JOIN `user` u ON me.sid = u.id "+
						"WHERE lmid = "+lmid+" AND tid = "+tid+" AND me.result = 1";
			}else {
				sql = "SELECT u.nickname, me.num, me.submitter FROM match_examine me "+
						"LEFT JOIN `user` u ON me.sid = u.id "+
						"WHERE lmid = "+lmid+" AND me.submitter = "+uid+" AND tid = "+tid+" AND me.result = 0";
			}
		}
		return HibernateDAO.findBySql(sql);
	}

	/**
	 * 
	* @Description: 通过lmid submitter 查询提交记录
	* @author liyvpeng  
	* @date 2018-3-20
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<MatchExamine> findExamineByLmid(Integer lmid, int submitter, int tid) throws Exception {
		String sql = "SELECT * FROM match_examine "+
				"WHERE lmid = "+lmid+" AND submitter = "+submitter+" AND tid = "+tid+" AND result = 0";
		return HibernateDAO.findBySql(sql, MatchExamine.class);
	}

	/**
	 * 
	* @Description: 通过lid tid 查积分
	* @author liyvpeng  
	* @date 2018-3-20
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<LeagueScore> findScoreByLidAndTid(int lid, int tid) throws Exception {
		String sql = "SELECT * FROM league_score "+
					"WHERE lid = "+lid+" AND team = " + tid;
		return HibernateDAO.findBySql(sql, LeagueScore.class);
	}

	/**
	 * 
	* @Description: lid sid 找射手榜
	* @author liyvpeng  
	* @date 2018-3-20
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<LeagueSoccer> findSoccerByLidAndSid(int lid, Integer sid) throws Exception {
		String sql = "SELECT * FROM league_soccer "+
					"WHERE lid = "+lid+" AND sid = " + sid;
		return HibernateDAO.findBySql(sql, LeagueSoccer.class);
	}

	/**
	 * 
	* @Description: 根据联赛找score
	* @author liyvpeng  
	* @date 2018-3-20
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<LeagueScore> findScoreByLid(Integer id) throws Exception {
		String sql = "SELECT * FROM league_score "+
					"WHERE lid = " + id;
		return HibernateDAO.findBySql(sql, LeagueScore.class);
	}

	/**
	 * 
	* @Description: 根据lmid submitter 找审核
	* @author liyvpeng  
	* @date 2018-3-22
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<MatchExamine> findExamineByLmidAndUid(Integer lmid, int uid) throws Exception {
		String sql = "SELECT * FROM match_examine " +
					"WHERE lmid = "+lmid+" AND submitter = "+uid+" AND result = 0 ";	
		return HibernateDAO.findBySql(sql, MatchExamine.class);
	}

	/**
	 * 
	* @Description: 计算参加联赛的队伍数
	* @author liyvpeng  
	* @date 2018-3-22
	* @version V2.0
	* @return
	* @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public int countLeagueTeamBylid(Integer id) throws Exception {
		//t1
		String sql1 = "SELECT teamOne FROM league_match WHERE leagueId = "+id+" AND delState = 0";
		List<Object[]> list1 = HibernateDAO.findBySql(sql1);
		//t2
		String sql2 = "SELECT teamTwo FROM league_match WHERE leagueId = "+id+" AND delState = 0";
		List<Object[]> list2 = HibernateDAO.findBySql(sql2);
		//合并
		list1.addAll(list2);
		//去重
		List<League> needList = new ArrayList<League>(new HashSet(list1));
		for(int i = 0; i < needList.size(); i++){
			if(needList.get(i) == null)
				needList.remove(i);
		}
		return needList.size();
	}

	/**
	 * 
	* @Description: 通过lid找裁判组
	* @author liyvpeng  
	* @date 2018-3-24
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<User> findRefereeGroupByLid(int lid) throws Exception {
		String sql = "SELECT u.* FROM apply_league al "  +
					"LEFT JOIN `user` u ON al.uid = u.id " +
					"WHERE al.lid = " + lid + " AND al.applyHandle = 1 AND al.delState = 0 AND u.delState = 0";
		return HibernateDAO.findBySql(sql, User.class);
	}

	/**
	 * 
	* @Description: 找到比赛双方所有成员
	* @author liyvpeng  
	* @date 2018-3-24
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<User> findUserByTwoTid(Integer t1, Integer t2) throws Exception {
		String sql = "SELECT u.* FROM user_team ut " +
					"LEFT JOIN `user` u ON ut.uid = u.id " +
					"WHERE ut.tid = " + t1 + " OR ut.tid = " + t2 + " AND u.delState = 0";
		return HibernateDAO.findBySql(sql, User.class);
	}

	/**
	 * 
	* @Description: TODO
	* @author liyvpeng  
	* @date 2018-3-27
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<LeagueMatch> findByTurntoDel(Integer turn, int lid) throws Exception {
		String sql = "SELECT * FROM league_match " +
					"WHERE leagueId = " + lid + " AND delState = 0 AND turn = " + turn;
		return HibernateDAO.findBySql(sql, LeagueMatch.class);
	}
	
	/**
	 * 
	* @Description: 通过sid,lmid,submitter找未审核的红黄牌
	* @author liyvpeng  
	* @date 2018-4-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<LeagueCard> findLeagueCardsBySidAndlmid(Integer sid, Integer lmid, Integer submitter) throws Exception{
		String sql = "SELECT * FROM league_card lc " +
				"WHERE sid = " + sid + " AND lmid = " + lmid + " AND submitter = " + submitter + " AND result = 0";
		return HibernateDAO.findBySql(sql, LeagueCard.class);
	}

	/**
	 * 
	* @Description: lmid找红牌
	* @author liyvpeng  
	* @date 2018-4-11
	* @version V2.0
	 * @param showType 
	 * @param showType2 
	* @return
	* @throws Exception
	 */
	public List<Object[]> findRedCardBylmid(Integer lmid, Integer uid, Integer showType) throws Exception {
		String sql = "";
		//0自己审核中的  1审核人看  2审核通过的
		if(showType == 1){
			sql = "SELECT u.nickname, lc.redNum FROM league_card lc " +
					"LEFT JOIN `user` u ON lc.sid = u.id " +
					"WHERE lc.result = 0 AND lc.lmid = " + lmid + " AND lc.redNum != 0";
		}else{
			if(showType == 2){
				sql = "SELECT u.nickname, lc.redNum FROM league_card lc " +
						"LEFT JOIN `user` u ON lc.sid = u.id " +
						"WHERE lc.result = 1 AND lc.lmid = " + lmid + " AND lc.redNum != 0";
			}else {
				sql = "SELECT u.nickname, lc.redNum FROM league_card lc " +
						"LEFT JOIN `user` u ON lc.sid = u.id " +
						"WHERE lc.result = 0 AND lc.lmid = " + lmid + " AND lc.redNum != 0 And lc.submitter = " + uid;
			}
		}
		return HibernateDAO.findBySql(sql);
	}
	
	/**
	 * 
	* @Description: lmid找黄牌
	* @author liyvpeng  
	* @date 2018-4-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<Object[]> findYellowCardBylmid(Integer lmid, Integer uid, Integer showType) throws Exception {
		String sql = "";
		//0自己审核中的  1审核人看  2审核通过的
		if(showType == 1){
			sql = "SELECT u.nickname, lc.yellowNum FROM league_card lc " +
					"LEFT JOIN `user` u ON lc.sid = u.id " +
					"WHERE lc.result = 0 AND lc.lmid = " + lmid + " AND lc.yellowNum != 0";
		}else{
			if(showType == 2){
				sql = "SELECT u.nickname, lc.yellowNum FROM league_card lc " +
						"LEFT JOIN `user` u ON lc.sid = u.id " +
						"WHERE lc.result = 1 AND lc.lmid = " + lmid + " AND lc.yellowNum != 0";
			}else {
				sql = "SELECT u.nickname, lc.yellowNum FROM league_card lc " +
						"LEFT JOIN `user` u ON lc.sid = u.id " +
						"WHERE lc.result = 0 AND lc.lmid = " + lmid + " AND lc.yellowNum != 0 And lc.submitter = " + uid;
			}
		}
		return HibernateDAO.findBySql(sql);
	}
	
	/**
	 * 
	* @Description: 通过lmid,submitter找未审核的红黄牌
	* @author liyvpeng  
	* @date 2018-4-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public List<LeagueCard> findLeagueCardsBylmid(Integer lmid, Integer submitter) throws Exception{
		String sql = "SELECT * FROM league_card lc " +
				"WHERE lmid = " + lmid + " AND submitter = " + submitter + " AND result = 0";
		return HibernateDAO.findBySql(sql, LeagueCard.class);
	}
	
	/**
	 * 
	* @Description: 通过uid统计红牌数
	* @author liyvpeng  
	* @date 2018-4-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public Integer countRedNumByUid(Integer uid, Integer lid) throws Exception {
		String sql = "SELECT sum(lc.redNum) FROM league_card lc WHERE sid = " + uid + " AND lid = " + lid + " AND result = 1";
		List<Object> list = HibernateDAO.findBySql(sql);
		if(list.size() == 0)
			return 0;
		else
			return new Integer(list.get(0).toString());
		
	}

	/**
	 * 
	* @Description: 通过uid统计黄牌数
	* @author liyvpeng  
	* @date 2018-4-11
	* @version V2.0
	* @return
	* @throws Exception
	 */
	public Integer countYellowNumByUid(Integer uid, Integer lid) throws Exception {
		String sql = "SELECT sum(lc.yellowNum) FROM league_card lc WHERE sid = " + uid + " AND lid = " + lid + " AND result = 1";
		List<Object> list = HibernateDAO.findBySql(sql);
		if(list.size() == 0)
			return 0;
		else
			return (Integer) list.get(0);
		
	}
}
