package com.ningxun.feedback.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.feedback.dao.LeagueFeedBackDAO;
import com.ningxun.feedback.vo.Lable;
import com.ningxun.feedback.vo.LeagueComment;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.util.HtmlAjax;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class LeagueFeedBackAction extends BaseSupportAction {

	private LeagueFeedBackDAO leagueFeedBackDAO = new LeagueFeedBackDAO();
	private HtmlAjax htmlAjax = new HtmlAjax();
	private Log log = LogFactory.getLog(getClass());

	private Integer id;
	// 评价实体类
	public LeagueComment leagueComment = new LeagueComment();
	//比赛id
	private Integer lmid;
	// 星评等级
	private Integer score;
	// 评论内容
	private String content;
	//联赛中比赛的id
	private Integer matchId;

	/**
	* 
	* 描述: 显示联赛评论列表<br/>
	* @return String 返回类型
	* 创建时间：2018-3-21
	* @author liudongxin 
	* @version V1.0
	 */
	public String leagueFeedBackList() {
		try {
			Integer lmid = new Integer(getRequest().getParameter("lmid"));
			Object[] race =leagueFeedBackDAO.findRace(lmid);
			ActionContext.getContext().getValueStack().set("sign", 0);
			ActionContext.getContext().getValueStack().set("info", race);
			
			List<Object[]> feedBacks = leagueFeedBackDAO.getList(lmid,pageNo,PAGE_SIZE);
			ActionContext.getContext().getValueStack().set("feedBacks", feedBacks);
			// 将分数提取，作为总分数的统计
			List<Integer> members = new ArrayList<Integer>();
			for (int i = 0; i < feedBacks.size(); i++) {
				Integer score = (Integer) feedBacks.get(i)[3];
				// 如果分数为追评，则不计入分数统计之内
				if (score != 0) {
						members.add(score);
				}
			}
			
			ActionContext.getContext().getValueStack().set("score", score);
			// 继续将比赛id值栈，返还页面
			ActionContext.getContext().getValueStack().set("lmid", lmid);
			WXUserSS wxUserSS = (WXUserSS) getUser();
			ActionContext.getContext().getValueStack().set("wxuser", wxUserSS);
			
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 上拉加载更多评论列表
	 * @author
	 * @date 2018年3月21日
	 * @version 1.0
	 */
	public void ajaxLoadMoreLeagueFeedBackList() throws Exception{
		try {
			String sql = leagueFeedBackDAO.getFeedBackListSql(lmid);
			//参数一：,MySql、sqlServer、 Oracle
			//参数三：查询的sql
			//参数四：返回的json对象key的数组
			//参数五：当前页码
			//参数六：加载类型  0：正常加载、1：一次请求多页,从第二页到pageNo的所有数据
			//注意：这里每页加载的数据必须和第一次加载的数据条数一样,否则数据会出现重复或者遗漏
			htmlAjax.getJsonJdbc("MySql", getResponse(), sql, leagueFeedBackDAO.getTagName(), pageNo, 0);
		} catch (Exception e) {
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	/**
	 * 
	* 描述: 跳转添加评论<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2018-3-21
	* @author   
	* @version V1.0
	 */
	public String leagueFeedBackAdd() {
		try {
			if (lmid != null) {
				// 获取当前操作人是第一次评价还是追评，将状态值传到页面上
				WXUserSS wxUserSS = (WXUserSS) getUser();
				Integer score = leagueFeedBackDAO.getScoreByUserId(wxUserSS.getId(),lmid);
				List<Lable> lables = leagueFeedBackDAO.findLable();
				// 将分数传递至页面，如果为0，则为第一次评价，如果为其他，则为追评
				ActionContext.getContext().getValueStack().set("score", score);
				// 将传过来的数据放入值栈中，传给评价页面
				ActionContext.getContext().getValueStack().set("lmid", lmid);
				ActionContext.getContext().getValueStack().set("lables", lables);
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
	* 描述: 保存评论
	* @return String 返回类型
	* 创建时间：2018-3-23
	* @author liudongxin  
	* @version V1.0
	 */
	public String leagueFeedBackSave() {
		try {
				// 获取当前操作人的id
				WXUserSS wxUserSS = (WXUserSS) getUser();
				leagueComment.setScore(score);
				leagueComment.setCreator(wxUserSS.getId());
				leagueComment.setCreateTime(new Date());
				leagueComment.setMatchId(matchId);
				leagueComment.setCommentator(wxUserSS.getId());
				leagueComment.setDelState(0);
				//记录日志
				String content = "添加-编号为：\"" + leagueComment.getCommentator() + "\"的评论信息";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "添加");
				// 保存评论
				HibernateDAO.save(leagueComment);
				ActionContext.getContext().getValueStack().set("lmid", lmid);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	/**
	 * 
	* 描述: ajax追评
	* @return String 返回类型
	* 创建时间：2018-3-22
	* @author liudongxin 
	* @version V1.0
	 * @throws Exception 
	 */
	public void ajaxSaveCotent() throws Exception {
		try {
			if (lmid != null) {
				WXUserSS currentUser = (WXUserSS) getUser();
				leagueComment.setScore(score);
				leagueComment.setContent(content);
				// 将比赛id压入值栈，传递给显示列表方法
				lmid = leagueComment.getMatchId();
				leagueComment.setMatchId(lmid);
				leagueComment.setCreator(currentUser.getId());
				leagueComment.setCreateTime(new Date());
				Integer saveId = (Integer) HibernateDAO.save(leagueComment);
				HtmlAjax.getJson(getResponse(), saveId);
				//记录日志
				String content = "添加编号为：\"" + leagueComment.getCommentator() + "\"的评论信息";
				AddLog.addOperateLog(String.valueOf(currentUser.getId()), currentUser.getNickname(), "", content, "添加");
			}
		} catch (Exception e) {
			HtmlAjax.getJson(getResponse(), null);
			log.error(e);
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLmid() {
		return lmid;
	}

	public void setLmid(Integer lmid) {
		this.lmid = lmid;
	}


	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
	
}
