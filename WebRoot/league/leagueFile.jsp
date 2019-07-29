<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>附件下载</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/city-picker.min.js" charset="utf-8"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/swiper.min.js"></script>
	</head>

	<body>
		<article class="weui-article">
			<s:if test="league.attachmentNewName == null || league.attachmentNewName == ''">
			</s:if>
			<s:else>
				附件：<div id="add"></div>
				<a id="down" download href="${pageContext.request.contextPath}/image/<s:property value='league.attachmentNewName'/> " >
					<s:property value="league.attachmentOldName"/>
				</a>
				<br />
			</s:else>
		</article>
	</body>
	<script type="text/javascript">
		
		window.onload = function(){ 
			if(isWeiXin()){ 
				$("#add").append("请点击右上角按钮，选择在游览器中打开，下载附件");
				$("#down").hide();
			} 
		}
		function isWeiXin(){ 
			var ua = window.navigator.userAgent.toLowerCase(); 
			if(ua.match(/MicroMessenger/i) == 'micromessenger'){ 
				return true; 
			}else{ 
				return false; 
			} 
		} 
	</script>
</html>