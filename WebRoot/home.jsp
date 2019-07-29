<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="se" uri="/WEB-INF/security.tld"%>
<!DOCTYPE HTML>
<html>

<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico">
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<link rel="stylesheet" type="text/css" href="App_Themes/H-ui/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="App_Themes/H-ui/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="App_Themes/H-ui/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="App_Themes/H-ui/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="App_Themes/H-ui/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="App_Themes/H-ui/static/h-ui.admin/css/style.css" />
<style>
.navbar {
	background: rgb(0, 153, 205);
}
</style>
<title>凝讯后台管理系统</title>
</head>
<body>
	<header class="navbar-wrapper">
		<div class="navbar navbar-fixed-top">
			<div class="container-fluid cl">
				<a class="logo navbar-logo f-l mr-10 hidden-xs" href="index">凝讯官网</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="index">凝讯官网</a> <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>

				<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
					<ul class="cl">
						<li class="dropDown dropDown_hover"><a href="#" class="dropDown_A"> 当前用户： <s:property value="userInfoSS.realName" /> <i class="Hui-iconfont">&#xe6d5;</i>
						</a>
							<ul class="dropDown-menu menu radius box-shadow">
								<li><a data-title="修改个人信息" _href="user/userInformationEdit?id=<s:property value="userInfoSS.id" />" target="kan" onclick="Hui_admin_tab(this)" href="javascript:;">修改个人信息</a></li>
								<li><a onclick="article_detail('修改密码','user/editPassword?id=<s:property value="userInfoSS.id" />','','500')">修改密码</a></li>
								<li><a href="logout">退出</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</div>
	</header>
	<aside class="Hui-aside">
		<input runat="server" id="divScrollValue" type="hidden" value="" />
		<div class="menu_dropdown bk_2">
				<dl id="menu-article">
					<dt>
						<i class="Hui-iconfont">&#xe61d;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
					</dt>
					<dd>
						<ul>
							<li><a _href="user/showUserList" data-title="用户管理" href="javascript:void(0)" target="kan">用户管理</a></li>
							<li><a _href="role/roleList" data-title="角色管理" href="javascript:void(0)" target="kan">角色管理</a></li>
						</ul>
					</dd>
				</dl>
				<dl id="menu-article">
					<dt>
						<i class="Hui-iconfont">&#xe616;</i> 协会管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
					</dt>
					<dd>
						<ul>
							<li><a _href="log/loginLogList" data-title="登录日志" href="javascript:void(0)" target="kan">登录日志</a></li>
							<li><a _href="log/operationLogList" data-title="操作日志" href="javascript:void(0)" target="kan">操作日志</a></li>
						</ul>
					</dd>
				</dl>
		</div>
	</aside>	
	
	<div class="dislpayArrow hidden-xs">
		<a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a>
	</div>
	<section class="Hui-article-box">
		<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
			<div class="Hui-tabNav-wp">
				<ul id="min_title_list" class="acrossTab cl">
					<li class="active"><span title="我的桌面" data-href="welcome.html">我的桌面</span><em></em></li>
				</ul>
			</div>
			<div class="Hui-tabNav-more btn-group">
				<a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"> <i class="Hui-iconfont">&#xe6d4;</i>
				</a> <a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"> <i class="Hui-iconfont">&#xe6d7;</i>
				</a>
			</div>
		</div>
		<div id="iframe_box" class="Hui-article">
			<div class="show_iframe">
				<div style="display:none" class="loading"></div>
				<iframe scrolling="yes" frameborder="0" src="welcome" name="kan"></iframe>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="App_Themes/H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="App_Themes/H-ui/lib/layer/2.1/layer.js"></script>
	<script type="text/javascript" src="App_Themes/H-ui/static/h-ui/js/H-ui.js"></script>
	<script type="text/javascript" src="App_Themes/H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
	<script type="text/javascript" src="App_Themes/JS/layerShow.js"></script>
	<script type="text/javascript">
		function article_detail(title, url, width, height) {
			layer_show(title, url, width, height);
		}
	</script>
</body>
</html>
