<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>登录超时</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>
<body>
	<div id="mydiv">
		<div align="center">
			<img alt="" src="../errors/images/004.gif" width="100%">
			<div style="margin-top: -100px;">
				<div style="margin-bottom: 20px;">
					您的登录已超时，请点击重新登录
				</div>
				<button id="btn" class="weui-btn weui-btn_mini weui-btn_plain-primary">重新登录</button>
			</div>
		</div>
	</div>	
	
</body>
<script type="text/javascript">
		$("#btn").click(function(){
			//跳转首页
			window.location.href = "${pageContext.request.contextPath}/toLogin.jsp";
		});
	</script>
</html>