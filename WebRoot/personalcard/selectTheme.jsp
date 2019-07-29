<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>我的名片</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<style type="text/css">
			li {
				position: relative;
				margin: 10px;
				width: 157px;
				height: 157px;
				float: left;
				list-style: none;
			}
			
			li:before {
				content: "\2714";
				display: block;
				position: absolute;
				margin: auto;
				top: 0;
				left: 0;
				bottom: 0;
				right: 0;
				width: 40px;
				height: 40px;
				line-height: 40px;
				background: #00c09e;
				border-radius: 50px;
				color: #fff;
				text-align: center;
				font-size: 16px;
				z-index: 10;
				opacity: 0;
				transition: 0.3s linear;
				-o-transition: 0.3s linear;
				-ms-transition: 0.3s linear;
				-moz-transition: 0.3s linear;
				-webkit-transition: 0.3s linear;
				-o-user-select: none;
				-moz-user-select: none;
				-webkit-user-select: none;
				cursor: pointer;
			}
			
			li.selected:before {
				opacity: 1;
			}
			/* img selection */
			li.selected img {
				box-shadow: 0 0 0 4px #00c09e;
				animation: selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
				-o-animation: selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
				-ms-animation: selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
				-moz-animation: selected 0.3s cubic-bezier(0.250, 0.100, 0.250, 1.000);
				-webkit-animation: selected 0.3s
					cubic-bezier(0.250, 0.100, 0.250, 1.000);
			}
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
	<body style="background-color: #F7F7F7;">
		<div class="loading">
			<div class="pic">
				<span></span>
				<b>0%</b>
			</div>
		</div>
		<div>
			<div align="center" style="background-color: #ffffff">
				请选择样式
			</div>
			<div align="center" style="clear: both; text-align:center;">
				<ul style="display:inline-block;margin-bottom: 50px;">
					<li>
						<img width="100%" src="${pageContext.request.contextPath}/common/img/mb1_result.jpg" />
					</li>
				</ul>
			</div>
			<div align="center" style="clear: both; text-align:center;  margin-bottom: 10px;">
				<ul style="display:inline-block;margin-bottom: 50px;">
					<li>
						<img width="100%" src="${pageContext.request.contextPath}/common/img/mb2_result.jpg" />
					</li>
				</ul>
			</div>
			<div align="center" style="clear: both; text-align:center;  margin-bottom: 10px;">
				<ul style="display:inline-block;margin-bottom: 50px;">
					<li>
						<img width="100%" src="${pageContext.request.contextPath}/common/img/mb3_result.jpg" />
					</li>
				</ul>
			</div>
		</div>
		<div class="weui-cell">
			<button id="gengrate" class="weui-btn weui-btn_primary">生成名片</button>
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
			var indexNum = null;
			$('li').click(function() {
				// alert($("li").index($(this)) + 1);
				// 获取索引值
				indexNum = $("li").index($(this)) + 1;
				// alert(indexNum);
				var list = document.getElementsByTagName('li');
				for (var i = 0; i < list.length; i++) {
					if(list[i].hasAttribute("class")) {
						list[i].removeAttribute("class");
					}
				}
				$('.select').addClass('selected');
				$(this).toggleClass('selected');
			});
			$("#gengrate").click(function() {
				if(indexNum == null || indexNum == '') {
					$.toptip('亲，您还没有选择样式哦', 'warning');
				} else {
					window.location = "${pageContext.request.contextPath}/personalcard/cardShow?indexNum="+indexNum;
				}
			});
		</script>
	</body>
</html>
