<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
	<head>
		<title>我的申请</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>
<body style="background-color: #F7F7F7">
	<div class="weui-tab">
		<div class="weui-tab__bd">
			<div  id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
				<div class="weui-panel weui-panel_access">
					<div class="weui-panel__bd">
					  <p style="border-bottom:1px solid #ddd;width:100%;height:2em;margin:0.3em 0.1em">我的球队申请</p>
						<s:if test="myApplyList != null && myApplyList.size() > 0">
							<s:iterator value="myApplyList" status="status" id="ml">
								<div href="" class="weui-media-box weui-media-box_appmsg">
									<div class="weui-media-box__hd" style="padding:10px auto">
										<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" 
										src="
											<s:if test="#ml[2] == null || #ml[2] == ''">
												${pageContext.request.contextPath}/common/img/default.png
											</s:if>
											<s:else>
												${pageContext.request.contextPath}/image/<s:property value='%{#ml[2]}'/>
											</s:else>
											">
									</div>
									<div onclick="showDetail()" class="weui-media-box__bd">
										<h4 class="weui-media-box__title">
											<!-- 申请队伍 -->
											<s:property value="%{#ml[0]}"/>
										</h4>
									</div>
									<div class="weui-cell__hd" >
										<!-- 审批状态 -->
										<s:if test="#ml[1] == 0">
											待处理
										</s:if>
										<s:elseif test="#ml[1] == 1">
											已同意
											<!-- <button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default" 
											style="margin-right:20px"></button> -->
										</s:elseif>
										<s:elseif test="#ml[1] == 2">
											已拒绝<%-- <br>
											<s:if test="#ml[5] == null || #ml[5] == ''">
												对方无条件地拒绝了您/(ㄒoㄒ)/~~
											</s:if>
											<s:else>
												<s:property value="%{#ml[5]}"/>
											</s:else> --%>
											<!-- <button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default" 
											style="margin-right:20px"></button> -->
										</s:elseif>
										<s:elseif test="#ml[1] == 3">
											已忽略
											<!-- <button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default" 
											style="margin-right:20px"></button> -->
										</s:elseif>
									</div>
								</div>
							</s:iterator>
						</s:if>
						<s:else>
							<div align="center">
								暂无消息列表
							</div>
						</s:else>
				</div>
			</div>	
			<div  id="tab2" class="weui-tab__bd-item weui-tab__bd-item--active">
				<div class="weui-panel weui-panel_access">
					<div class="weui-panel__bd">
					  <div style="border-bottom:1px solid #ddd;width:100%;height:2em;margin:0.3em 0.1em">我的联赛申请</div>
						<s:if test="myApplyLeagueList != null && myApplyLeagueList.size() > 0">
							<s:iterator value="myApplyLeagueList" status="status" id="mylist">
								<div href="" class="weui-media-box weui-media-box_appmsg">
									<div class="weui-media-box__hd" style="padding:10px auto">
										<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" 
										src="
											<s:if test="#mylist[2] == null || #mylist[2] == ''">
												${pageContext.request.contextPath}/common/img/default.png
											</s:if>
											<s:else>
												<s:if test="#mylist[2].indexOf('http') != -1">
												  ${mylist[2]}
												</s:if>
												<s:else>
													${pageContext.request.contextPath}/image/${mylist[2]}
												</s:else>
											</s:else>
											">
									</div>
									<div onclick="showDetail()" class="weui-media-box__bd">
										<h4 class="weui-media-box__title">
											<!-- 申请联赛 -->
											<s:property value="%{#mylist[0]}"/>
										</h4>
									</div>
									<div class="weui-cell__hd" >
										<!-- 审批状态 -->
										<s:if test="#mylist[1] == 0">
											待处理
										</s:if>
										<s:elseif test="#mylist[1] == 1">
											已同意
											<!-- <button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default" 
											style="margin-right:20px"></button> -->
										</s:elseif>
										<s:elseif test="#mylist[1] == 2">
											已拒绝<%-- <br>
											<s:if test="#mylist[5] == null || #mylist[5] == ''">
												对方无条件地拒绝了您/(ㄒoㄒ)/~~
											</s:if>
											<s:else>
												<s:property value="%{#mylist[5]}"/>
											</s:else> --%>
											<!-- <button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default" 
											style="margin-right:20px"></button> -->
										</s:elseif>
										<s:elseif test="#mylist[1] == 3">
											已忽略
											<!-- <button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default" 
											style="margin-right:20px"></button> -->
										</s:elseif>
									</div>
								</div>
							</s:iterator>
						</s:if>
						<s:else>
							<div align="center">
								暂无消息列表
							</div>
						</s:else>
					</div>
				</div>
			</div>
		</div>	
	</div>	
</body>
</html>