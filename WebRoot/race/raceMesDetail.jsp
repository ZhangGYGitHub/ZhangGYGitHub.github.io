<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
	<head>
		<title>比赛详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>

	<body>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">比赛名称</label></div>
				<div class="weui-cell__bd">
					${race.raceName}
				</div>
			</div>
			<div class="weui-cell" >
				<div class="weui-cell__hd">
					<label class="weui-label">比赛类型</label>
				</div>
				<div class="weui-cell__bd">
					<input type="hidden" id="type"  value="${race.type}"/>
					<div id="racetype"></div>
				</div>
			</div>
			<div class="weui-cell" id="racetypeselect">
				<div class="weui-cell__hd">
					<label class="weui-label">联赛名称</label>
				</div>
				<div class="weui-cell__bd">
					<div id="racetype"><s:property value="race.league"/></div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label for="" class="weui-label">比赛对手</label></div>
				<div class="weui-cell__bd">
					<s:property value="race.opponent"/>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label for="" class="weui-label">开始时间</label></div>
				<div class="weui-cell__bd">
					<s:date name="race.startTime"  format="yyyy-MM-dd HH:mm"/>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label for="" class="weui-label">结束时间</label></div>
				<div class="weui-cell__bd">
				<s:date name="race.endTime"  format="yyyy-MM-dd HH:mm"/>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label for="" class="weui-label">比赛地点</label></div>
				<div class="weui-cell__bd">
					<s:property value="race.place"/>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">服装颜色</label></div>
				<div class="weui-cell__bd">
					<s:property value="race.clothescolour"/>
				</div>
			</div>
			<div class="weui-cell" >
				<div class="weui-cell__hd">
					<label class="weui-label">签到类型</label>
				</div>
				<div class="weui-cell__bd">
					<input type="hidden" id="isSign" value="${race.signType}"/>
					<div id="signType"></div>
				</div>
			</div>
			<div class="weui-cell" id="signtimeselect">
				<div class="weui-cell__hd">
					<label class="weui-label">开始签到</label>
				</div>
				<div class="weui-cell__bd">
					<input type="hidden" id="sTime" value="${race.signTime}"/>
					<div id="signtime"></div>
				</div>
			</div>
			<s:if test="#position.positionName != \"队员\"">
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">资金耗费</label></div>
				<div class="weui-cell__bd">
					<s:property value="race.waste"/>
				</div>
			</div>
			</s:if>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">备注</label>
				</div>
				<div class="weui-cell__bd">
					<s:property value="race.remark"/>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			//加载数据回显
			$(function(){
				//比赛类型数据回显
				var type = $("#type").val();
				if(1 == type){
					$("#racetypeselect").hide();
			  		$("#racetype").text("训练赛");
			  	} else if (2 == type){
			  		$("#racetypeselect").hide();
			  		$("#racetype").text("友谊赛");
			  	} else if (3 == type){
			  		$("#racetype").text("联赛");
			  	}
			  	//回显是否签到
			  	if($("#isSign").val() == 1){
					$("#signType").text("需签到");
				} else {
					$("#signType").text("无需签到");
					$("#signtimeselect").hide();
				}
			  	//回显签到开始时间
			  	var sTime = $("#sTime").val();
				if(0 == sTime){
			  		$("#signtime").text("提前半小时");
			  	} else if (1 == sTime){
			  		$("#signtime").text("提前一小时");
			  	} else if(2== sTime){
			  		$("#signtime").text("提前两小时");
			  	}
			})
		</script>
	</body>

</html>