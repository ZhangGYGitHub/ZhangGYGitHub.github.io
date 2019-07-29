package com.ningxun.team.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.team.vo.UserTeam;

@SuppressWarnings("all")
public class UserTeamDao extends HibernateDAO {

	/**
	 * 解散球队
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 * @throws Exception
	 */
	public void deleteMemberListByTid(Integer tid) throws Exception{
		String sql = "DELETE FROM user_team WHERE tid = "+tid;
		this.batchBySql(sql);
	}

	/**
	 * 用户退出指定球队
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid
	 * @param uid
	 */
	public void quitTeamByUid(Integer tid, Integer uid) throws Exception{
		String sql = "DELETE FROM user_team WHERE tid = "+tid+" AND uid ="+uid;
		this.executeBySql(sql);
	}
	
	/**
	 * 根据成员id和球队id修改所在球队的球衣号码
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @param uid 成员id
	 * @param tid 球队id
	 * @param clothesNum 球衣号码
	 */
	public void changeClothesNumByUidAndTid(Integer uid, Integer tid, String clothesNum) throws Exception{
		String sql = "UPDATE user_team SET clothesNum = '"+clothesNum.trim()+"' WHERE uid = "+uid+" AND tid = " + tid;
		this.executeBySql(sql);
	}

	/**
	 * 根据球员id和球队id修改所在球队的职位
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @param uid
	 * @param tid
	 * @param position
	 * @throws Exception
	 */
	public void changePositionByUidAndTid(Integer uid, Integer tid, Integer position) throws Exception{
		String sql = "UPDATE user_team SET position = "+position+" WHERE uid = "+uid+" AND tid = "+tid;
		this.executeBySql(sql);
	}
	
	/**
	 * 根据成员id移除成员
	 * @author hujian
	 * @date 2017年7月21日
	 * @version 1.0
	 * @param uid 成员id
	 * @param tid 球队id
	 * @throws Exception 
	 */
	public void removeMemberByUid(Integer uid, Integer tid) throws Exception {
		String sql = "DELETE FROM user_team WHERE tid = "+tid+" AND uid = " + uid;
		this.executeBySql(sql);
	}

	/**
	 * 根据用户id和球队id修改指定用户指定球队的球队备注名
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @param uid 用户id
	 * @param tid 球队id
	 * @param remakeName 备注名
	 */
	public void changeRemakeName(Integer uid, Integer tid,String remakeName) throws Exception{
		String sql ="UPDATE user_team SET remakeName = '"+remakeName+"' WHERE uid = "+uid+" AND tid = "+tid;
		this.executeBySql(sql);
	}

	/**
	 * 根据用户id和球队id修改所在球队的所踢位置
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @param uid 用户id
	 * @param tid 球队id
	 * @param playerPosition 所踢位置
	 */
	public void changePlayerPosition(Integer uid, Integer tid, String playerPosition) throws Exception{
		String sql ="UPDATE user_team SET playerPosition = '"+playerPosition+"' WHERE uid = "+uid+" AND tid = "+tid;
		this.executeBySql(sql);
	}

	/**
	 * 根据用户id和球队id获取常踢位置列表
	 * @author hujian
	 * @date 2017年7月24日
	 * @version 1.0
	 * @param uid 用户id
	 * @param tid 球队id
	 * @return
	 */
	public UserTeam getPlayerPositionByUid(Integer uid, Integer tid) throws Exception{
		String sql = "SELECT id,playerPosition FROM user_team WHERE uid ="+uid+" AND tid = "+tid;
		List<UserTeam> list = findBySql(sql, UserTeam.class);
		return (list != null && list.size() >0) ? list.get(0):null;
	}
	
	/**
	 * 校验用户是否是某球队的成员
	 * @author hujian
	 * @date 2017年8月9日
	 * @version 1.0
	 * @param uid 用户id
	 * @param tid 球队id
	 * @return  true： 是      false： 不是
	 * @throws Exception 
	 */
	public boolean checkUserIsTeamMember(Integer uid,Integer tid) throws Exception{
		String sql = "SELECT id FROM user_team WHERE uid = "+uid+" AND tid = "+tid;
		List<UserTeam> list = findBySql(sql,UserTeam.class);
		if (list != null && list.size() > 0) {
			//是
			return true;
		}
		//不是
		return false;
	}

}
