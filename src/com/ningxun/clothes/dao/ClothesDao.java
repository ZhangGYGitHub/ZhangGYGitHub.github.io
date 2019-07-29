package com.ningxun.clothes.dao;

import java.util.List;

import com.ningxun.clothes.vo.Clothes;
import com.ningxun.common.HibernateDAO;

@SuppressWarnings("all")
public class ClothesDao extends HibernateDAO {

	/**
	 * 根据球队id查询队服颜色列表
	 * @author hujian
	 * @date 2017年7月25日
	 * @version 1.0
	 * @param tid 球队id
	 * @return
	 */
	public List<Clothes> getColourListByTid(Integer tid) throws Exception{
		String sql ="SELECT * FROM clothes WHERE tid = "+tid+" ORDER BY id DESC";
		return findBySql(sql, Clothes.class);
	}
	
	/**
	* 描述: 根据球队id得到队服颜色列表<br/>
	*
	* @return List<Object[]> 返回类型
	*
	* 创建时间：2017-7-28/下午4:21:56<br/>
	* @author 高佳伟  
	* @version V1.0  
	 * @throws Exception 
	*/
	public List<Object[]> findClothesList(Integer teamId) throws Exception {
		String sql = "SELECT id,clothesColour FROM clothes WHERE tid = "+teamId;
		List<Object[]> list =  findBySql(sql);
		return (list == null || list.size() == 0 ) ? null : list;
	}

}
