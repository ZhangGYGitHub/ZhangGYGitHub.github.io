package com.ningxun.wxuserinfo.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: MyCardDAO.java<br/>
 *<li>创建时间: 2017-7-24 上午10:26:51<br/>
 *
 *@author zyt
 *@version [v1.00]
 */
public class MyCardDAO extends HibernateDAO {
	/**
	 * 
	* 描述: 查询我的卡片的有关信息<br/>
	*
	* @return List 返回类型
	*
	* 创建时间：2017-7-24/上午10:56:30<br/>
	* @author Administrator  
	* @version V1.0
	 */
	@SuppressWarnings("rawtypes")
	public Object[] findMyCard(Integer teamId, Integer userId) throws Exception {
		String sql = "SELECT DISTINCT u.nickname, u.headPortrait, u.headPortraitNew, " +
				"u.sex, u.likeNum, ss.doverate, ss.signFourth, " +
				"u.height, u.weight, u.habitfoot, ut.playerPosition, " +
				"u.phone, u.birthday, u.perAutograph, u.synopsis, u.id, " +
				"ut.remakeName FROM `user` u " +
				"LEFT JOIN user_team ut ON u.id = ut.uid " +
				"LEFT JOIN signcount ss ON u.id = ss.uid " +
				"WHERE ut.tid = "+teamId+"  AND u.id = "+userId+" AND u.delState = 0";
		
		List list = findBySql(sql);
		if (list != null && list.size() > 0) {
			if (list.size() > 1) {
				String sql2 = "SELECT DISTINCT u.nickname, u.headPortrait, u.headPortraitNew, " +
						"u.sex, u.likeNum, ss.doverate, ss.signFourth, " +
						"u.height, u.weight, u.habitfoot, ut.playerPosition, " +
						"u.phone, u.birthday, u.perAutograph, u.synopsis, u.id, ut.remakeName " +
						"FROM user_team ut " +
						"LEFT JOIN `user` u ON u.id = ut.uid " +
						"LEFT JOIN signcount ss ON ut.uid = ss.uid " +
						"WHERE ut.tid = "+teamId+"  AND ut.uid = "+userId+" and ss.tid = " + teamId+
						" AND u.delState = 0";
				List list2 = findBySql(sql2);
				return (Object[]) list2.get(0);
			}
			return (Object[]) list.get(0);
		}
		return null;
	}
}
