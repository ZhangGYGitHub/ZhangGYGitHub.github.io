package com.ningxun.goods.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.goods.vo.TeamGoods;

/**
*<li>技术支持:河北凝讯科技有限公司<br/>
*<li>项目名称: rongqiu<br/>
*<li>文件名: goodsDao.java<br/>
*<li>创建时间: 2017年7月23日 下午6:40:46<br/>
*
*@author 梦强
*@version [v1.00]
*/
public class goodsDao extends HibernateDAO{
	/**
	 * 
	* 描述: 根据用户id和球队id查询物资列表<br/>
	* @param tid 球队id
	* @return List  物资列表集合
	*
	* 创建时间：2017年7月23日/下午6:52:21<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public List findGoodList(Integer tid) throws Exception{
		String sql = "SELECT g.id , g.goodsName,g.number,g.unit ,"
				+ "tg.tid FROM goods AS g LEFT JOIN team_goods AS "
				+ "tg ON tg.gid = g.id  WHERE tg.tid = "+tid+" AND g.delState = 0 "; 
		return findBySql(sql);
	}
	/**
	 * 
	* 描述: 根据队伍id和物资id查询关联表数据<br/>
	* @param gid 物资id
	* @param tid 球队id
	* @return TeamGoods 返回teamgoods关联表对象
	*
	* 创建时间：2017年7月23日/下午9:21:18<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public TeamGoods findTGid(Integer gid,Integer tid) throws Exception{
		String sql = "SELECT * from team_goods WHERE gid = "+gid+" AND tid = "+ tid+" LIMIT 1";
		TeamGoods teamGoods = (TeamGoods)findBySqlStars(sql, TeamGoods.class).get(0);
		return teamGoods;
	}
	
}
