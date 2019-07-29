<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
	response.setHeader("Cache-Control",
			"no-store,no-cache,must-revalidate");
	response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	response.setHeader("Pragma", "no-cache");
	session.invalidate();
	session.setMaxInactiveInterval(-1);
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="App_Themes/H-ui/static/h-ui/lib/html5.js"></script>
<script type="text/javascript" src="App_Themes/H-ui/static/h-ui/lib/respond.min.js"></script>
<script type="text/javascript" src="App_Themes/H-ui/static/h-ui/lib/PIE_IE678.js"></script>
<![endif]-->
<link href="App_Themes/H-ui/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="App_Themes/H-ui/static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
<link href="App_Themes/H-ui/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
<link href="App_Themes/H-ui/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>凝讯官网后台</title>
<meta name="keywords" content="">
<meta name="description" content="">

</head>
<body>
	<div class="header"><b>凝讯官网后台</b></div>
	<div class="loginWraper">
		<div id="loginform" class="loginBox">
			<form class="form form-horizontal" id='mylogin' action="${pageContext.request.contextPath}/nxback" method="post">
				<div class="row cl">					
					<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
					<div class="formControls col-xs-8">
						<input id="username" name="username" value="5015" type="text" placeholder="用户名" class="input-text size-L">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe63f;</i></label>
					<div class="formControls col-xs-8">
						<input id="password" name="password" value="123456" type="password" placeholder="密码" class="input-text size-L">
					</div>
				</div>
				<div class="row cl">
				<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
					<div class="formControls col-xs-8">
						<input class="input-text size-L" style="width:263px;" placeholder="验证码" name="verifyCode" type="text" id="verifyCode" />
						<img src="" onclick="changeValidateCode()" title="看不清？换一张" class="popImg" id="img"  style="height: 41px;"/>
						<input type="hidden" id="login_hidden" value="0"/>
						<br>
						</div>
					</div>
					
					<div class="row cl">
					<label class="form-label col-xs-3"></label>
						<div class="formControls col-xs-8">
							<a name="" onclick="loginsubmit()" type="button" class="btn btn-success radius size-L" style="width:360px" value="登录">登录</a>
							
						</div>
					</div>
					<a href="user/forgetPassword" class="zc" style="float:right;margin-right: 55px;margin-top: 15px;"><font>忘记密码了?</font></a>
			</form>
		</div>
	</div>
	<div class="footer">
		Copyright
		<a href='http://www.ningxun.net.cn'>河北凝讯科技有限公司</a>
	</div>
	<script type="text/javascript" src="App_Themes/JS/ningXun-1.8.mini.js"></script>
	<script type="text/javascript" src="App_Themes/JS/login.js"></script>
	<script type="text/javascript">
	$(document).ready(function(e) {		
		<%String error = (String) request.getAttribute("error");
			if (error != null) {%>
			alert("<%=error%>");
		<%}%>
		
		$(document).keyup(function(event) {
				if (event.keyCode == 13) {				
					loginsubmit();
				}
			});
			changeValidateCode();
		});
		function changeValidateCode() {
			//获取当前的时间作为参数，无具体意义  
			var timenow = new Date().getTime();
			//每次请求需要一个不同的参数，否则可能会返回同样的验证码         
			document.getElementById("img").src = "verifyCode?d=" + timenow;
		}

	</script>
</body>
</html>