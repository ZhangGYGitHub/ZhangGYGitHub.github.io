<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<!--[if lt IE 9]>
<script type="text/javascript" src="../App_Themes/H-ui/lib/html5.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/lib/respond.min.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/static/h-ui.admin/css/style.css" />
<script type="text/javascript" src="../App_Themes/JS/ningXun-1.8.mini.js"></script>

<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>管理员详情</title>
</head>
<body>
	<nav class="breadcrumb">管理员详情</nav>
	<div class="page-container">
		<div class="row">
			<label>用户名：</label>
			<span>
				<s:property value="userinfo.userName" />
			</span>
		</div>

		<div class="row">
			<label>真实姓名：</label>
			<span>
				<s:property value="userinfo.realName" />
			</span>
		</div>

		<div class="row">
			<label>电子邮箱：</label>
			<span>
				<s:property value="userinfo.email" />
			</span>
		</div>

		<div class="row">
			<label>手机号码：</label>
			<span>
				<s:property value="userinfo.mobilNo" />
			</span>
		</div>

		<div class="row">
			<label>单位：</label>
			<span>
				<s:property value="userinfo.companyLevelName" />
			</span>
		</div>

		<div class="row">
			<label>性别：</label>
			<span>
				<s:if test="userinfo.sex==0">
			男
			</s:if>
				<s:else>
			女
			</s:else>
			</span>
		</div>

		<div class="row">
			<label>固定电话：</label>
			<span>
				<s:property value="userinfo.phone" />
			</span>
		</div>

		<div class="row">
			<label>联系地址：</label>
			<span>
				<s:property value="userinfo.address" />
			</span>
		</div>

		<div class="row">
			<label>账号是否锁定：</label>
			<span>
				<s:if test="userinfo.accountNonLocked==0">
			未锁定
			</s:if>
				<s:else>
			锁定
			</s:else>
			</span>
		</div>


		<div class="row">
			<label class="kong"></label>
			<span>
				<button onclick="removeIframe();" type="button">关闭</button>
			</span>			
		</div>
	</div>
</body>

<script type="text/javascript" src="../App_Themes/H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/static/h-ui.admin/js/wo.js"></script>

</html>
