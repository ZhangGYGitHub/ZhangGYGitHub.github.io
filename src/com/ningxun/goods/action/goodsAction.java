package com.ningxun.goods.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.goods.dao.goodsDao;
import com.ningxun.goods.vo.Goods;
import com.ningxun.goods.vo.TeamGoods;
import com.ningxun.position.dao.PositionDao;
import com.ningxun.position.vo.Position;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.team.dao.UserTeamDao;
import com.ningxun.util.HtmlAjax;
import com.opensymphony.xwork2.ActionContext;

/**
 * <li>技术支持:河北凝讯科技有限公司<br/>
 * <li>项目名称: rongqiu<br/>
 * <li>文件名: goodsAction.java<br/>
 * <li>创建时间: 2017年7月23日 下午6:53:23<br/>
 *
 * @author 梦强
 * @version [v1.00]
 */
public class goodsAction extends BaseSupportAction {
	private List<Object[]> list;
	//物资id
	private Integer gid;
	//球队id
	private Integer tid;
	private Goods goods = new Goods();
	private goodsDao goodsDao = new goodsDao();
	private TeamGoods teamGoods = new TeamGoods();
	private PositionDao positionDao = new PositionDao();
	private UserTeamDao userTeamDao = new UserTeamDao();
	private Log log = LogFactory.getLog(getClass());

	/**
	 * 
	 * 描述: 展示物资列表<br/>
	 *
	 * @return String 返回类型
	 *
	 *         创建时间：2017年7月23日/下午6:57:14<br/>
	 * @author 梦强
	 * @version V1.0
	 */
	public String findGList() {
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			list = goodsDao.findGoodList(tid);
			Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
			ActionContext.getContext().put("position", position);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}

	/**
	 * 
	 * 描述: 添加或编辑物资信息<br/>
	 *
	 * @return String 返回类型
	 *
	 *         创建时间：2017年7月23日/下午6:57:51<br/>
	 * @author 梦强
	 * @version V1.0
	 */
	public String addOrEditGoods() {
		try {
			WXUserSS loginUser = (WXUserSS) getUser();
			boolean check = userTeamDao.checkUserIsTeamMember(loginUser.getId(), tid);
			if(!check){
				addActionMessage("很遗憾，您被移除该球队了...");
				return INPUT;
			}
			Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
			if (position != null && position.getId() == 4) {
				addActionError("您没有权限进行此操作");
				return INPUT;
			}
			if (gid != null) {
				goods = (Goods) HibernateDAO.findById(Goods.class, gid);
			} 
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: 保存物资<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月23日/下午7:14:18<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String saveGoods(){
		try {
			boolean check = check(goods);
			if (check) {
				WXUserSS loginUser = (WXUserSS) getUser();
				Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
				if (position == null) {
					HtmlAjax.getJson(getResponse(), false);
					return INPUT;
				}
				if (position != null && position.getId() == 4) {
					HtmlAjax.getJson(getResponse(), false);
					return INPUT;
				}
				if (goods.getId() == null) {
					goods.setCreateTime(new Date());
					goods.setCreateUser(loginUser.getId());
					goods.setDelState(0);
					HibernateDAO.save(goods);
					teamGoods.setGid(goods.getId());
					teamGoods.setTid(tid);
					Integer saveId = (Integer) HibernateDAO.save(teamGoods);
					if (saveId != null) {
						// 操作日志
						String content = "添加-编号为：\"" + saveId + "\"的物资";
						AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "添加");
					}
				}else {
					Goods good = (Goods) HibernateDAO.findById(Goods.class, goods.getId());
					goods.setCreateTime(good.getCreateTime());
					goods.setCreateUser(good.getCreateUser());
					goods.setModifyTime(new Date());
					goods.setModifyUser(loginUser.getId());
					goods.setDelState(0);
					Integer updateId = HibernateDAO.update(goods);
					if (updateId != null) {
						// 操作日志
						String content = "编辑-编号为：\"" + updateId + "\"的物资";
						AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "编辑");
					}
				}
				HtmlAjax.getJson(getResponse(), true);
				return NONE;
			}else {
				HtmlAjax.getJson(getResponse(), false);
				return INPUT;
			}
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
		
	}
	
	/**
	 * 
	* 描述: 删除物资信息<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017年7月23日/下午7:04:14<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public String deleteGoods(){
		try {
			if (gid != null && tid != null) {
				WXUserSS loginUser = (WXUserSS) getUser();
				Position position = positionDao.queryPositionByUidAndTid(tid,loginUser.getId());
				if (position == null) {
					HtmlAjax.getJson(getResponse(), false);
					return INPUT;
				}else if (position != null && position.getId() == 4) {
					HtmlAjax.getJson(getResponse(), false);
					return INPUT;
				}else {
					goods = (Goods) HibernateDAO.findById(Goods.class, gid);
					if (goods.getDelState() == 1) {
						HtmlAjax.getJson(getResponse(), false);
						return NONE;
					}
					goods.setDelState(1);
					goods.setDeleteTime(new Date());
					goods.setDeleteUser(loginUser.getId());
					Integer updateId = HibernateDAO.update(goods);
					if (updateId != null) {
						// 操作日志
						String content = "删除-编号为：\"" + updateId + "\"的物资";
						AddLog.addOperateLog(String.valueOf(loginUser.getId()), loginUser.getNickname(), "", content, "删除");
					}
					TeamGoods teamGoods = goodsDao.findTGid(gid, tid);
					HibernateDAO.delete(teamGoods);
					HtmlAjax.getJson(getResponse(), true);
				}
			}else {
				HtmlAjax.getJson(getResponse(), false);
			}
			return NONE;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: 后台校验<br/>
	*
	* @return boolean 返回类型
	*
	* 创建时间：2017年7月25日/下午6:19:16<br/>
	* @author 梦强  
	* @version V1.0
	 */
	public boolean check(Goods goods){
		boolean flag = true;
		if (goods.getGoodsName().equals("")||goods.getGoodsName().length()>20) {
			flag = false;
		}
		if (goods.getNumber() == null || goods.getNumber()>99999) {
			flag = false;
		}
		if (goods.getUnit().length()>5 || goods.getUnit().equals("")) {
			flag = false;
		}
		return flag;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public TeamGoods getTeamGoods() {
		return teamGoods;
	}

	public void setTeamGoods(TeamGoods teamGoods) {
		this.teamGoods = teamGoods;
	}
}
