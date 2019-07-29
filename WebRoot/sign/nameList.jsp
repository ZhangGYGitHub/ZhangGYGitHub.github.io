<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>查看签到列表</title>
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
											<button onclick="changeBeforeType(${rl[0]},0,${rl[1]})" class="weui-btn weui-btn_mini weui-btn_primary" style="background:#B0A817;">已请假</button>
										</s:if>
										<s:elseif test="#rl[6] == null && #rl[8] == 1">
											<button onclick="changeBeforeType(${rl[0]},${rl[4]},${rl[1]})" class="weui-btn weui-btn_mini weui-btn_primary">已报名</button>
										</s:elseif>
										<s:else>
											<button onclick="changeBeforeType(${rl[0]},${rl[4]},${rl[1]})" class="weui-btn weui-btn_mini weui-btn_warn">未报名</button>
										</s:else>
									</s:if>
									<s:else>
										<s:if test="#rl[6] != null">
											<button onclick="changeAfterType(${rl[0]},0,${rl[1]})" class="weui-btn weui-btn_mini weui-btn_primary" style="background:#B0A817;">已请假</button>
										</s:if>
										<s:elseif test="#rl[4] == 1 || #rl[4] == 2">
											<button onclick="changeAfterType(${rl[0]},${rl[4]},${rl[1]})" class="weui-btn weui-btn_mini weui-btn_default">已签到</button>
										</s:elseif>
										<s:elseif test="(#rl[4] == 0 || #rl[4] == null || #rl[4] == -1) && #rl[8] == 1">
											<button onclick="changeAfterType(${rl[0]},0,${rl[1]})" class="weui-btn weui-btn_mini weui-btn_primary">已报名</button>
										</s:elseif>
										<s:elseif test="#rl[4] == 3 && #rl[8] == 1">
											<button onclick="changeAfterType(${rl[0]},${rl[4]},${rl[1]})" class="weui-btn weui-btn_mini weui-btn_primary">已报名</button>
										</s:elseif>
										<s:else>
											<button onclick="changeAfterType(${rl[0]},${rl[4]},${rl[1]})" class="weui-btn weui-btn_mini weui-btn_warn">未到场</button>
										</s:else>
									</s:else>
								</div>
							</label>
						</s:iterator>
						
					  
					</div>
					
					<script type="text/javascript">
						
						/***************比赛开始之前可以进行的操作********************/
						function changeBeforeType(sid,type,id){
							$.actions({
								actions: [{
										text: "代报名",
										onClick: function() {
											signUp(sid,type,id);
										}
									},
									{
										text: "代请假",
										onClick: function() {
											leave(sid,type,id);
										}
									}]
							});
						}
						/********************队长代替队员报名***************************/
						function signUp(sid,type,id){
							$.ajax({
								url:'sign/ajaxSignUp.action?sid='+sid,
								type:'post',  
							    success: function (data) {
							    	var json = JSON.parse(data);
							    	if(json.data == 1){
							    		$("#"+id).empty();
							    		$("#"+id).append("<button onclick='changeBeforeType("+sid+","+type+","+id+")' class='weui-btn weui-btn_mini weui-btn_primary'>已报名</button>");
							    	}else{
							    		$.toptip("该队员已报名", 'warning');
							    	}
							    }
							});
						}
						/********************队长代替请假***************************/
						function leave(sid,type,id){
							$.ajax({
								url:'sign/ajaxLeave.action?sid='+sid,
								type:'post',  
							    success: function (data) {
							    	var json = JSON.parse(data);
							    	if(json.data == true){
							    		$("#"+id).empty();
							    		$("#"+id).append("<button onclick='changeBeforeType("+sid+","+type+","+id+")' class='weui-btn weui-btn_mini weui-btn_primary' style='background:#B0A817;'>已请假</button>");
							    	}else{
							    		$.toptip("网络异常，请刷新页面重试", 'warning');
							    	}
							    }
							});
						}
						/***************比赛开始之后可以进行的操作********************/
						function changeAfterType(sid,type,id){
							$.actions({
								actions: [{
										text: "代报名",
										onClick: function() {
											signUp1(sid,type,id);
										}
									},
									{
										text: "代请假",
										onClick: function() {
											leave1(sid,type,id);
										}
									},
									{
										text: "代签到",
										onClick: function() {
											attendance(sid,type,id);
										}
									}]
							});
						}
						/****************队长代替队员签到*******************/
						function attendance(sid,type,id){
							$.ajax({
								url:'sign/ajaxChangeType.action?sid='+sid+'&type ='+type,
								type:'post',  
							    success: function (data) {
							    	var json = JSON.parse(data);
							    	if(json.data == 2){
							    		if(type == 1 || type == 2){
								    		$("#"+id).empty();
								    		$("#"+id).append("<button  onclick='changeAfterType("+sid+",3,"+id+")' class='weui-btn weui-btn_mini weui-btn_warn'>未到场</button>");
								    	}else{
								    		$("#"+id).empty();
								    		$("#"+id).append("<button  onclick='changeAfterType("+sid+",2,"+id+")' class='weui-btn weui-btn_mini weui-btn_default'>已签到</button>");
										}
							    		
							    	}else if (json.data == 1) {
										$("#"+id).empty();
								    	$("#"+id).append("<button class='weui-btn weui-btn_mini weui-btn_default'>已签到</button>");
									}else if (json.data == 3) {
										$.toptip("签到未开始，无法签到", 'warning');
									}else{
							    		$.toptip("网络异常，请刷新页面重试", 'warning');
							    	}
							    }
							});
						}
						/********************队长代替队员报名***************************/
						function signUp1(sid,type,id){
							$.ajax({
								url:'sign/ajaxSignUp.action?sid='+sid,
								type:'post',  
							    success: function (data) {
							    	var json = JSON.parse(data);
							    	if(json.data == 1){
							    		$("#"+id).empty();
							    		$("#"+id).append("<button onclick='changeAfterType("+sid+","+type+","+id+")' class='weui-btn weui-btn_mini weui-btn_primary'>已报名</button>");
							    	}else if(json.data == 2){
							    		$("#"+id).empty();
							    		$("#"+id).append("<button  onclick='changeAfterType("+sid+",2,"+id+")' class='weui-btn weui-btn_mini weui-btn_default'>已签到</button>");
							    	}else{
							    		$.toptip("该队员已报名", 'warning');
							    	}
							    }
							});
						}
						/********************队长代替请假***************************/
						function leave1(sid,type,id){
							$.ajax({
								url:'sign/ajaxLeave.action?sid='+sid,
								type:'post',  
							    success: function (data) {
							    	var json = JSON.parse(data);
							    	if(json.data == true){
							    		$("#"+id).empty();
							    		$("#"+id).append("<button onclick='changeAfterType("+sid+","+type+","+id+")' class='weui-btn weui-btn_mini weui-btn_primary' style='background:#B0A817;'>已请假</button>");
							    	}else{
							    		$.toptip("网络异常，请刷新页面重试", 'warning');
							    	}
							    }
							});
						}
					</script>
		
				</div>
			</div>	
		</div>	
	</body>
</html>