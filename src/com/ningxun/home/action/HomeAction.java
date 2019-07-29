package com.ningxun.home.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ningxun.common.BaseSupportAction;
import com.ningxun.home.dao.HomeDao;
import com.ningxun.home.vo.Home;
import com.ningxun.security.action.WXUserSS;
import com.ningxun.tools.FormatUtil;
import com.ningxun.util.DateTools;
import com.ningxun.util.HtmlAjax;
import com.opensymphony.xwork2.ActionContext;

/**
* 主页Action
* @author hujian
* @date 2017年7月20日
* @version 1.0
*/
public class HomeAction extends BaseSupportAction{
	
	private static final long serialVersionUID = 1L;
	private HomeDao homeDao = new HomeDao();
	private Log log = LogFactory.getLog(getClass());
	private WXUserSS loginUser = (WXUserSS) getUser();//当前登录人
	
	public String home(){
		try {
			//首页数据显示规则 ：
			// 			前三天 + 今天  + 后三天   = 7天
			//			再取出前3天之前的最后5条历史数据
			//			如果这个范围中没有数据,则在历史数据中取出5条,未来数据中取出1条
			//			整个列表是按开始时间倒序
			
			List<Home> homeList = new ArrayList<Home>();
			//获取当天时间凌晨00:00:00的毫秒值
			String crruentDateStr = DateTools.getDateStr();
			//今天的开始时间,也就是00:00:00的毫秒值
			long todayStart = DateTools.dateToLong(crruentDateStr);
			//前三天 + 今天  + 后三天   = 7天
			long startLong = todayStart - 1000 * 60 * 60 * 24 * 3;//前三天的凌晨00:00:00
			long endLong = todayStart + 1000 * 60 * 60 * 24 * 4 - 1000;//后三天的结束23:59:59
			String startTime = FormatUtil.formatDateTime(startLong);
			String endTime = FormatUtil.formatDateTime(endLong);
			//时间段的数据
			List<Home> betweenList = homeDao.findAll(startTime, endTime, loginUser.getId());
			//未来数据
			List<Home> afterList = homeDao.findListByAfter(endTime,loginUser.getId(),pageNo,PAGE_SIZE);
			ActionContext.getContext().put("afterList", afterList);
			if (betweenList != null && betweenList.size() > 0 ) {
				//在这个时间段有数据
				homeList.addAll(betweenList);
			} else {
				//在这个时间段没有数据
				//未来的数据只取出1条
				if (afterList != null && afterList.size() > 0) {
					Home after = afterList.get(0);
					//未来的数据
					homeList.add(after);
				}
			}
			//在这时间段前面去取5条数据
			//过去的数据
			List<Home> beforeList = homeDao.findListByBefore(startTime,loginUser.getId(),pageNo,PAGE_SIZE);
			if (beforeList != null && beforeList.size() > 0) {
				//大于5条数据
				if (beforeList.size() > 5) {
					for (int i = 0; i <= 4; i++) {
						//只取出5条
						Home before = beforeList.get(i);
						homeList.add(before);
					}
				} else {
					//小于5条数据全部取出
					homeList.addAll(beforeList);
				}
				
			}
			ActionContext.getContext().put("homeList", homeList);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			addActionMessage("请点击[返回]后刷新页面，如不能解决请联系管理员！");
			return ERROR;
		}
	}
	
	/**
	 * 下拉加载更多未来数据
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @throws Exception 
	 */
	public void ajaxLoadMoreFutureList() throws Exception {
		try {
			//获取当天时间凌晨00:00:00的毫秒值
			String crruentDateStr = DateTools.getDateStr();
			//今天的开始时间,也就是00:00:00的毫秒值
			long todayStart = DateTools.dateToLong(crruentDateStr);
			//前三天 + 今天  + 后三天   = 7天
			long endLong = todayStart + 1000 * 60 * 60 * 24 * 4 - 1000;//后三天的结束23:59:59
			String endTime = FormatUtil.formatDateTime(endLong);
			//未来的数据
			List<Home> afterList = homeDao.findListByAfter(endTime,loginUser.getId(),pageNo,PAGE_SIZE);
			HtmlAjax.getJson(getResponse(), afterList);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
	/**
	 * 未来数据列表
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @throws Exception 
	 */
	public String futureDataList() throws Exception {
		try {
			//获取当天时间凌晨00:00:00的毫秒值
			String crruentDateStr = DateTools.getDateStr();
			//今天的开始时间,也就是00:00:00的毫秒值
			long todayStart = DateTools.dateToLong(crruentDateStr);
			//前三天 + 今天  + 后三天   = 7天
			long endLong = todayStart + 1000 * 60 * 60 * 24 * 4 - 1000;//后三天的结束23:59:59
			String endTime = FormatUtil.formatDateTime(endLong);
			//未来的数据
			List<Home> afterList = homeDao.findListByAfter(endTime,loginUser.getId(),pageNo,PAGE_SIZE);
			ActionContext.getContext().put("afterList", afterList);
			return SUCCESS;
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 上拉加载更多历史数据
	 * @author hujian
	 * @date 2017年7月26日
	 * @version 1.0
	 * @throws Exception 
	 */
	public void ajaxLoadMoreHistoryList() throws Exception {
		try {
			List<Home> beforeList = new ArrayList<Home>();
			//获取当天时间凌晨00:00:00的毫秒值
			String crruentDateStr = DateTools.getDateStr();
			//今天的开始时间,也就是00:00:00的毫秒值
			long todayStart = DateTools.dateToLong(crruentDateStr);
			//前三天 + 今天  + 后三天   = 7天
			long startLong = todayStart - 1000 * 60 * 60 * 24 * 3;//前三天的凌晨00:00:00
			String startTime = FormatUtil.formatDateTime(startLong);
			//过去的数据
			List<Home> list = homeDao.findListByBefore(startTime,loginUser.getId(),pageNo,PAGE_SIZE);
			//因为已经加载了最后5条数据在首页
			//所有加载历史数据的时候，在第一页时，需要移除5条数据
			if (list != null && list.size() > 0) {
				if (pageNo == 1) {
					if (list.size() > 5) {
						//移除5条数据
						//这里移除数据必须倒序移除，不然移除的时候索引改变会导致移除错误数据
						list.remove(4);
						list.remove(3);
						list.remove(2);
						list.remove(1);
						list.remove(0);
						beforeList.addAll(list);
					} 
				} else {
					beforeList.addAll(list);
				}
			}
			HtmlAjax.getJson(getResponse(), beforeList);
		} catch (Exception e) {
			log.error(e);// 记录错误日志
			e.printStackTrace();
			HtmlAjax.getJson(getResponse(), null);
		}
	}
	
}
