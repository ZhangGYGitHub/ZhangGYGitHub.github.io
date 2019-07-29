package com.ningxun.card.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;

/**
 *<li>技术支持:河北凝讯科技有限公司<br/>
 *<li>项目名称: rongqiu<br/>
 *<li>文件名: PersonalCardDAO.java<br/>
 *<li>创建时间: 2017-7-28 上午8:28:55<br/>
 *
 *@author zyt
 *@version [v1.00]
 */
public class PersonalCardDAO extends HibernateDAO {
	/**
	 * 
	* 描述: 查询个人卡片信息<br/>
	*
	* @return List<String> 返回类型
	*
	* 创建时间：2017-7-28/上午8:44:06<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public List<Object[]> findPersonal(Integer userId) throws Exception {
		String sql = "select u.nickname, ut.remakeName, " +
				"t.`name`, p.positionName, u.phone from " +
				"`user` u left join user_team ut on " +
				"u.id = ut.uid left join position p on " +
				"p.id = ut.position left join teaminfo t on t.id = ut.tid " +
				"where u.delState = 0 and u.id = " + userId;
		List<Object[]> list = findBySql(sql);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
}
