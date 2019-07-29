package com.ningxun.wxuserinfo.dao;

import java.util.List;

import com.ningxun.common.HibernateDAO;
import com.ningxun.wxuserinfo.vo.Location;
import com.ningxun.wxuserinfo.vo.UserLocation;

@SuppressWarnings("unchecked")
public class LocationDAO extends HibernateDAO {
	/**
	 * 
	* 描述: 查询常踢位置列表<br/>
	*
	* @return List<Location> 返回类型
	*
	* 创建时间：2017-7-21/下午5:10:45<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public List<Location> getList() throws Exception {
		String sql = "select * from location";
		List<Location> list = findBySql(sql, Location.class);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	/**
	 * 
	* 描述: 通过常踢位置的名称查询常踢位置<br/>
	*
	* @return Location 返回类型
	*
	* 创建时间：2017-7-21/下午4:27:54<br/>
	* @author Administrator
	* @version V1.0
	 */
	public Location findLocationByName(String locationName) throws Exception {
		String sql = "select * from location where locationName = '" + locationName + "'";
		List<Location> list = findBySql(sql, Location.class);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	/**
	 * 
	* 描述: 通过用户id查找常踢位置的列表<br/>
	*
	* @return List<String> 返回类型
	*
	* 创建时间：2017-7-21/下午5:32:34<br/>
	* @author Administrator  
	* @version V1.0
	 */
	
	public List<Location> findUserLocation(Integer uid) throws Exception {
		String sql1 = "select lid from user_location where uid = " + uid;
		List<UserLocation> lids = findBySql(sql1, UserLocation.class);
		String sqlId = "";
		if (lids != null && lids.size() > 0) {
			// 通过关联表查询常踢位置的对象
			for (int i = 0; i < lids.size(); i++) {
				if (i == lids.size() - 1) {
					sqlId += lids.get(i).getLid();
				} else {
					sqlId += lids.get(i).getLid() + ",";
				}
			}
			String sql2 = "select locationName from location where id in (" + sqlId + ")";
			List<Location> list = findBySql(sql2, Location.class);
			if (list != null && list.size() > 0) {
				return list;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	/**
	 * 
	* 描述: 通过用户id查找对应关系<br/>
	*
	* @return List<UserLocation> 返回类型
	*
	* 创建时间：2017-8-8/上午9:08:15<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public List<UserLocation> findUserLocationByUid(Integer userId) throws Exception {
		String sql = "select * from user_location where uid = " + userId;
		List<UserLocation> list = findBySql(sql, UserLocation.class);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
}
