<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>查看签到名单</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>
	<body>
		<div class="weui-tab">
			<div class="weui-tab__bd">
				<div  id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
					<div class="weui-cells weui-cells_checkbox">
						<s:set name="nowTime" value="new java.util.Date()"></s:set>
						<s:iterator value="raceList" status="status" var="rl">
							<label class="weui-cell weui-check__label">
								<div class="weui-cell__hd" style="width:70%;">
									<p>${rl[2]}</p>
								</div>
								<div class="weui-cell__hd" id="${rl[1]}">
									<s:if test="#startTime.getTime() > #nowTime.getTime()">
										<s:if test="#rl[6] != null">
											<button class="weui-btn weui-btn_mini weui-btn_primary" style="background:#B0A817;">已请假</button>
										</s:if>
										<s:elseif test="#rl[6] == null && #rl[8] == 1">
											<button class="weui-btn weui-btn_mini weui-btn_primary">已报名</button>
										</s:elseif>
										<s:elseif test="#rl[6] == null && #rl[8] == 0">
											<button class="weui-btn weui-btn_mini weui-btn_warn">未报名</button>
										</s:elseif>
										<s:else>
											<button class="weui-btn weui-btn_mini weui-btn_warn">未报名</button>
										</s:else>
									</s:if>
									<s:else>
										<s:if test="#rl[6] != null">
											<button class="weui-btn weui-btn_mini weui-btn_primary" style="background:#B0A817;">已请假</button>
										</s:if>
										<s:elseif test="#rl[6] == null && #rl[4] == 1">
											<button class="weui-btn weui-btn_mini weui-btn_default">已签到</button>
										</s:elseif>
										<s:elseif test="#rl[6] == null && #rl[4] == 2">
											<button class="weui-btn weui-btn_mini weui-btn_default">已签到</button>
										</s:elseif>
										<s:elseif test="#rl[6] == null && (#rl[4] == 0 || #rl[4] == -1) && #rl[8] == 1">
											<button class="weui-btn weui-btn_mini weui-btn_primary">已报名</button>
										</s:elseif>
										<s:else>
											<button class="weui-btn weui-btn_mini weui-btn_warn">未到场</button>
										</s:else>
									</s:else>
								</div>
							</label>
						</s:iterator>
					</div>
				</div>
			</div>	
		</div>	
	</body>
</html>