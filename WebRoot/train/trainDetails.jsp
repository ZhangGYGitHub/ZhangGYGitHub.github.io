<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<%@ taglib prefix="se" uri="/WEB-INF/security.tld"%>
<!DOCTYPE html>
<html>

	<head>
		<title>训练详情</title>
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
				<div class="weui-cell__hd">
					<label class="weui-label">训练名称</label>
				</div>
				<div class="weui-cell__bd">${train.trainName}</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label for="" class="weui-label">开始时间</label></div>
				<div class="weui-cell__bd">
					<s:date name="train.startTime"  format="yyyy-MM-dd HH:mm"/>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label for="" class="weui-label">结束时间</label></div>
				<div class="weui-cell__bd">
					<s:date name="train.endTime"  format="yyyy-MM-dd HH:mm"/>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">训练地点</label></div>
				<div class="weui-cell__bd">${train.trainPlace}</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">服装颜色</label></div>
				<div class="weui-cell__bd">${train.clothescolour}</div>
			</div>
			<div class="weui-cell" >
				<div class="weui-cell__hd">
					<label class="weui-label">签到类型</label>
				</div>
				<div class="weui-cell__bd">
					<input type="hidden" id="isSign" value="${train.issign}"/>
					<div id="signType"></div>
				</div>
			</div>
			<div class="weui-cell" id="signtimeselect">
				<div class="weui-cell__hd">
					<label class="weui-label">签到开始</label>
				</div>
				<div class="weui-cell__bd">
					<input type="hidden" id="sTime" value="${train.signTime}"/>
					<div id="signtime"></div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">训练内容</label>
				</div>
				<div class="weui-cell__bd">
					${train.trainContent}
				</div>
			</div>
		</div>
		<br/>
		<div class="weui-panel__ft">
			<div class="button_sp_area" align="center">
				<%-- 	<button onclick="joinon(${train.id})" class="weui-btn weui-btn_mini weui-btn_default">参训情况</button> --%>
					<s:if test="#position.positionName != \"队员\"">
						<button id="btn1" onclick="edit(${train.id})" class="weui-btn weui-btn_mini weui-btn_primary">编辑</button>
						<s:if test="#position.positionName == \"队长\"">
							<button onclick="del(${train.id})" class="weui-btn weui-btn_mini weui-btn_warn">删除</button>
						</s:if>
			 		</s:if> 
				<br />
				<br />
			</div>
		</div>
		<script type="text/javascript">
			//加载回显
			$(function(){
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
			  	}else if (""== sTime){
			  	$("#signtime").text("");
			  	}
			  	
			  	//校验删除按钮
				var endTimes = "${train.endTime}";
				var startTimes = "${train.startTime}";
				function getTime(){
				   var time = new Date();
				   var endTime = new Date(endTimes);
				   var startTime = new Date(startTimes);
				    if(time.getTime()>startTime.getTime()){
					   	$("#flag").val(false);//训练已开始
					   	$("#btn1").remove();
				    }else{
				    	$("#flag").val(true);//训练未开始
				    }
				}
			    setInterval(getTime,10);
			})
			
			//编辑
			function edit(id){
				window.location.href ="${pageContext.request.contextPath}/train/toEdit?teamId="+"${train.tid}"+"&id="+id;
			}
			//删除
			function del(id){
				$.confirm("确认删除？","消息确认框", function() {
					window.location.href ="${pageContext.request.contextPath}/train/Delete?teamId="+"${train.tid}"+"&id="+id;
				}, function() {
				  	//点击取消后的回调函数
			 	});
			}
			/* //参训情况
			function joinon(id){
				window.location.href ="${pageContext.request.contextPath}/train/joinOn?teamId="+"${train.tid}"+"&id="+id;
			} */
		</script>
	</body>

</html>