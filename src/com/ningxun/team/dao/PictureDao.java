package com.ningxun.team.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.team.vo.Picture;

/**
* 全家福Dao 
* @author hujian
* @date 2017年7月24日
* @version 1.0
*/
@SuppressWarnings("all")
public class PictureDao extends HibernateDAO{
	
	/**
	 * 根据球队id获取全家福
	 * @author hujian
	 * @date 2017年7月20日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public Picture getPictureByTid(Integer tid) throws Exception{
		String sql = "SELECT * FROM picture WHERE tid = "+tid+" AND delState = 0 ";
		List<Picture> list = findBySql(sql,Picture.class);
		
		return (list != null && list.size()> 0) ? list.get(0) : null;
	}
}
