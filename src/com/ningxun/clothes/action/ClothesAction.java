package com.ningxun.clothes.action;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.clothes.dao.ClothesDao;
import com.ningxun.clothes.vo.Clothes;
import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.position.dao.PositionDao;
import com.ningxun.position.vo.Position;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.util.HtmlAjax;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ClothesAction extends BaseSupportAction {
	
	private ClothesDao clothesDao = new ClothesDao();
	public Clothes clothes = new Clothes();
	private PositionDao positionDao = new PositionDao();
	private Log log = LogFactory.getLog(getClass());
	private Integer teamId;//球队id

	public String clothesColourList(){
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(clothes.getTid(), loginUser.getId());
			if (position == null ) {
				//已经不是该球队成员,说明该用户已经被移除球队了
				//跳转提示被移除页面
				addActionMessage("很遗憾，您被移除该球队了...");
				return INPUT;
			} else if ("队员".equals(position.getPositionName())) {
				//已经不是管理员,不再具备该操作
				addActionMessage("您无权进行此操作！");
				return INPUT;
			}
			List<Clothes> list = clothesDao.getColourListByTid(clothes.getTid());
			ActionContext.getContext().put("list", list);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	public void clothesSave() throws Exception{
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(clothes.getTid(), loginUser.getId());
			if (position == null ) {
				//已经不是该球队成员,说明该用户已经被移除球队了
				HtmlAjax.getJson(getResponse(), null);//处理失败
				return;
			} else if ("队员".equals(position.getPositionName())) {
				//已经不是管理员,不再具备该操作
				HtmlAjax.getJson(getResponse(), null);//处理失败
				return;
			}
			if (HibernateDAO.save(clothes) == null) {
				HtmlAjax.getJson(getResponse(), null);
				return;
			}
			HtmlAjax.getJson(getResponse(), clothes.getId());
			// 操作日志
			String content = "添加编号为：\"" + clothes.getId() + "\"的队服颜色";
			AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "添加");
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), null);//失败
		}
	}
	
	public void clothesDelete() throws Exception{
		try {
			clothes = (Clothes) HibernateDAO.findById(Clothes.class,clothes.getId());
			WXUserSS loginUser = (WXUserSS) getUser();
			//获取职位
			Position position = positionDao.queryPositionByUidAndTid(clothes.getTid(), loginUser.getId());
			if (position == null ) {
				//已经不是该球队成员,说明该用户已经被移除球队了
				HtmlAjax.getJson(getResponse(), false);//处理失败
				return;
			} else if ("队员".equals(position.getPositionName())) {
				//已经不是管理员,不再具备该操作
				HtmlAjax.getJson(getResponse(), false);//处理失败
				return;
			}
			//队服颜色是真删
			if (clothes != null) {
				HibernateDAO.delete(clothes);
			}
			HtmlAjax.getJson(getResponse(), true);//删除成功
			// 操作日志
			String content = "删除编号为：\"" + clothes.getId() + "\"的队服颜色";
			AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "删除");
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			HtmlAjax.getJson(getResponse(), false);//删除失败
		}
	}

	/**
	* 描述: ajax获取队服颜色列表<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-28/下午4:12:06<br/>
	* @author 高佳伟  
	* @version V1.0  
	*/
	public String clothesAjax(){
		try {
			List<Object[]> clothesAjax  = clothesDao.findClothesList(teamId);
			if(clothesAjax != null){
				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray =new JSONArray();
				for (int i = 0; i < clothesAjax.size(); i++) {
					jsonObject.put("title",clothesAjax.get(i)[1]);
					jsonObject.put("value",clothesAjax.get(i)[0]);
					jsonArray.add(jsonObject);
				}

				JSONObject object = new JSONObject();
				object.put("array", jsonArray);
				HtmlAjax.getJson(getResponse(), object);
			}else{
				HtmlAjax.getJson(getResponse(), null);
			}
			return NONE;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
}
