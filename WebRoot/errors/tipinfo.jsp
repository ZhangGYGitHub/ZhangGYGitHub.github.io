<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>友情提示</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>
<body>
	<div id="mydiv">
		<div align="center" style="padding-top: 50%">
			<h2><font color="red"><s:actionmessage/></font></h2>
		</div>
		<div class="button_sp_area" align="center" style="padding-top: 5%">
			<button id="btn" class="weui-btn weui-btn_mini weui-btn_plain-primary">返回首页</button>
		</div>
	</div>	
	
</body>
<script type="text/javascript">
		$("#btn").click(function(){
			//跳转首页
			window.location.href = "${pageContext.request.contextPath}/home";
		});
	</script>
</html>