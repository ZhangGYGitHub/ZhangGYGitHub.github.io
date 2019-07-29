<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>卡片</title>
    <style type="text/css">
    	.loading {
				width: 100%;
				height: 100%;
				position: fixed;
				top: 0;
				left: 0;
				z-index: 100;
				background-color: #fff;
			}
			.loading .pic {
				width: 100px;
				height: 100px;
				position: absolute;
				top: 0;
				bottom: 0;
				left: 0;
				right: 0;
				margin: auto;
				font-size: 30px;
				text-align: center;
				line-height: 108px;
			}
			.loading .pic span {
				display: block;
				width: 80px;
				height: 80px;
				position: absolute;
				top: 10px;
				left: 10px;
				border-radius: 50%;
				box-shadow: 0 3px 0 #666;
				/* 设置兼容IE */
				-webkit-animation: rotate 1s infinite linear;
				animation: rotate 1s infinite linear;
			}
			/* 定义加载旋转动画 */
			@-webkit-keyframes rotate{
				0% {
					-webkit-transform: rotate(0deg);
				}
				100% {
					-webkit-transform: rotate(360deg);
				}
			}
			@keyframes rotate{
				0% {
					transform: rotate(0deg);
				}
				100% {
					transform: rotate(360deg);
				}
			}
    </style>
    <script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
  </head>
  <body>
  	<div align="center" style="height: 100%;">
  		<div class="loading">
			<div class="pic">
				<span></span>
				<b>0%</b>
			</div>
		</div>
  		<div align="center">
	    	请长按图片保存至本地，分享给你的好友吧
	    </div>
  		<%-- <s:if test="indexNum == 1">
	  		<img alt="" id="myCard" width="100%" src="${pageContext.request.contextPath}/personalcard/showPersonalCard?indexNum=${indexNum}">
	  	</s:if>
	  	<s:else> --%>
	  		<img alt="" height="530px" id="myCard" width="100%" src="${pageContext.request.contextPath}/personalcard/showPersonalCard?indexNum=${indexNum}">
	  	<%-- </s:else> --%>
  	</div>
  	<script type="text/javascript">
			$(function(){
				var img = $("img");
				var num = 0;
				img.each(function(i){
					var oImg = new Image();
					/* 清除重复请求 */
					oImg.onload = null;
					oImg.onload = function(){
						num++;
						$(".loading b").html(parseInt(num/$("img").size()*100) + "%");
						/* 解决缓存问题 */
						if(num >= i) {
							$(".loading").fadeOut();
						}
					}
					oImg.src = img[i].src;
					
				});
			});
	</script>
  </body>
</html>
