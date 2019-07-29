package com.ningxun.feedback.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ningxun.common.AddLog;
import com.ningxun.common.BaseSupportAction;
import com.ningxun.common.HibernateDAO;
import com.ningxun.feedback.dao.FeedBackDAO;
import com.ningxun.feedback.vo.FeedBack;
import com.ningxun.feedback.vo.Lable;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.train.vo.Train;
import com.ningxun.util.HtmlAjax;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
public class FeedBackAction extends BaseSupportAction {

	private FeedBackDAO feedBackDAO = new FeedBackDAO();
	private HtmlAjax htmlAjax = new HtmlAjax();
	private Log log = LogFactory.getLog(getClass());

	// 评价实体类
	public FeedBack feedBack = new FeedBack();
	// 比赛或者训练id
	private Integer trId;
	// 比赛或者训练类型id:0，比赛，1，训练
	private Integer type;
	// 队伍id
	private Integer tid;
	// 星评等级
	private Integer score;
	// 评论
	private String comment;

	/**
	* 
	* 描述: 显示反馈列表<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-23/下午2:46:19<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public String feedBackList() {
		try {
			// 查询比赛或者训练信息
			// 比赛或者训练类型id:0，比赛，1，训练
			if (type == 0) {
				Object[] race = feedBackDAO.findRace(trId);
				ActionContext.getContext().getValueStack().set("sign", 0);
				ActionContext.getContext().getValueStack().set("info", race);
			} else {
				Train train = feedBackDAO.findTrain(trId);
				ActionContext.getContext().getValueStack().set("sign", 1);
				ActionContext.getContext().getValueStack().set("info", train);
			}
			
			List<Object[]> feedBacks = feedBackDAO.getList(trId, type, tid,pageNo,PAGE_SIZE);
			ActionContext.getContext().getValueStack().set("feedBacks", feedBacks);
			// 将分数提取，作为总分数的统计
			List<Integer> members = new ArrayList<Integer>();
			Integer master = 0;
			for (int i = 0; i < feedBacks.size(); i++) {
				Integer score = (Integer) feedBacks.get(i)[2];
				// 如果分数为追评，则不计入分数统计之内
				if (score != 0) {
					// 判断职位如果是队长，则分数乘以百分之五十
					if (((Integer) feedBacks.get(i)[6]) == 1) {
						master = score;
					} else {
						members.add(score);
					}
				}
			}
			
			double finalScore = 0.0;
			if (members != null && members.size() != 0) {
				for (int i = 0; i < members.size(); i++) {
					//System.out.println(members.get(i)+"+++");
					finalScore += members.get(i);
				}
				// finalScore = finalScore / members.size() * 0.5;
			}
			// finalScore += master * 0.5;
			finalScore += master;
			if (master != 0) {
				finalScore /= (members.size() + 1);
			} else if (members != null && members.size() > 0) {
				finalScore /= members.size();
			}
			
			String scoreString = new BigDecimal(finalScore).setScale(0, BigDecimal.ROUND_HALF_DOWN).toString();
			Integer score = Integer.parseInt(scoreString);
			ActionContext.getContext().getValueStack().set("score", score);
			
			// 继续将比赛/训练id，以及比赛/训练类型传入值栈，返还页面
			ActionContext.getContext().getValueStack().set("trId", trId);
			ActionContext.getContext().getValueStack().set("type", type);
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
	 * @author hujian
	 * @date 2017年7月28日
	 * @version 1.0
	 */
	public void ajaxLoadMoreFeedBackList() throws Exception{
		try {
			String sql = feedBackDAO.getFeedBackListSql(trId, type, tid);
			//参数一：,MySql、sqlServer、 Oracle
			//参数三：查询的sql
			//参数四：返回的json对象key的数组
			//参数五：当前页码
			//参数六：加载类型  0：正常加载、1：一次请求多页,从第二页到pageNo的所有数据
			//注意：这里每页加载的数据必须和第一次加载的数据条数一样,否则数据会出现重复或者遗漏
			htmlAjax.getJsonJdbc("MySql", getResponse(), sql, feedBackDAO.getTagName(), pageNo, 0);
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
	* 创建时间：2017-7-23/下午5:18:27<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public String feedBackAdd() {
		try {
			if (trId != null && type != null) {
				// 获取当前操作人是第一次评价还是追评，将状态值传到页面上
				WXUserSS wxUserSS = (WXUserSS) getUser();
				Integer score = feedBackDAO.getScoreByUserId(wxUserSS.getId(), trId, type);
				List<Lable> lables = feedBackDAO.findLable();
				// 将分数传递至页面，如果为0，则为第一次评价，如果为其他，则为追评
				ActionContext.getContext().getValueStack().set("score", score);
				// 将传过来的数据放入值栈中，传给评价页面
				ActionContext.getContext().getValueStack().set("trId", trId);
				ActionContext.getContext().getValueStack().set("type", type);
				ActionContext.getContext().getValueStack().set("tid", tid);
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
	* 描述: 保存评论<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-23/下午3:04:08<br/>
	* @author Administrator  
	* @version V1.0
	 */
	public String feedBackSave() {
		try {
			if (feedBack.getTrId() != null && feedBack.getType() != null) {
				// 前台提交的score
				feedBack.setScore(score);
				//添加评价
				feedBack.setCreateTime(new Date());
				// 获取当前操作人的id
				WXUserSS wxUserSS = (WXUserSS) getUser();
				feedBack.setUserId(wxUserSS.getId());
				//记录日志
				String content = "添加-编号为：\"" + feedBack.getId() + "\"的评论信息";
				AddLog.addOperateLog(String.valueOf(wxUserSS.getId()), wxUserSS.getNickname(), "", content, "添加");
				// 保存评论
				HibernateDAO.save(feedBack);
				// 将比赛或者训练id压入值栈，传递给显示列表方法
				trId = feedBack.getTrId();
				type = feedBack.getType();
				ActionContext.getContext().getValueStack().set("tid", tid);
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
	* 描述: ajax追评<br/>
	*
	* @return String 返回类型
	*
	* 创建时间：2017-7-25/下午4:55:57<br/>
	* @author Administrator  
	* @version V1.0
	 * @throws Exception 
	 */
	public void ajaxSaveComment() throws Exception {
		try {
			if (trId != null && type != null) {
				WXUserSS currentUser = (WXUserSS) getUser();
				feedBack.setScore(score);
				feedBack.setComment(comment);
				feedBack.setTrId(trId);
				feedBack.setType(type);
				feedBack.setUserId(currentUser.getId());
				feedBack.setCreateTime(new Date());
				Integer saveId = (Integer) HibernateDAO.save(feedBack);
				HtmlAjax.getJson(getResponse(), saveId);
				//记录日志
				String content = "添加编号为：\"" + feedBack.getId() + "\"的评论信息";
				AddLog.addOperateLog(String.valueOf(currentUser.getId()), currentUser.getNickname(), "", content, "添加");
			}
		} catch (Exception e) {
			HtmlAjax.getJson(getResponse(), null);
			log.error(e);
		}
	}

	public FeedBack getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(FeedBack feedBack) {
		this.feedBack = feedBack;
	}

	public Integer getTrId() {
		return trId;
	}

	public void setTrId(Integer trId) {
		this.trId = trId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}
}
