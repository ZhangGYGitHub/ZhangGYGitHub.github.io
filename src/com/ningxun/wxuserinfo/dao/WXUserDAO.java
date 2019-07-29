package com.ningxun.wxuserinfo.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.wxuserinfo.vo.WXUser;

public class WXUserDAO extends HibernateDAO {

	/**
	 * 
	* 描述: 通过openID查找该用户<br/>
	*
	* @return WXUser 返回类型
	*
	* 创建时间：2017-7-20/下午7:07:07<br/>
	* @author Administrator  
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public WXUser findWxUserByOpenId(String openid) throws Exception {
		try {
			String sql = "select * from `user` where delState = 0 and openId = '" + openid + "'" +
					" and accountNonExpired = 0 and credentialsNonExpired = 0 and accountNonLocked = 0" +
					" and enabled = 0 and userType = 1";
			
			List<WXUser> list = findBySql(sql, WXUser.class);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 描述: 判断当前用户是否创建过球队<br/>
	 *
	 * @return List<Object> 返回类型
	 *
	 * 创建时间：2017-8-23/下午2:21:42<br/>
	 * @author 高佳伟  
	 * @version V1.0  
	 */
	@SuppressWarnings("unchecked")
	public List<Object> findTeamCreatorList(int uid) throws Exception{
		String sql="SELECT * FROM teaminfo t WHERE t.createUser = "+uid+" AND t.delState = 0";
		List<Object> list = HibernateDAO.findBySql(sql);
		return list;
	}
}
