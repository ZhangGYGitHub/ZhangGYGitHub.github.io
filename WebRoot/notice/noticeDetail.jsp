<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>通知详情</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/city-picker.min.js" charset="utf-8"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/swiper.min.js"></script>
	</head>

	<body>
		<br />
		<div align="center">
			<font>
				<s:property value="notice.title"/>
			</font>
		</div>
		
		<br />
		
		<div class="weui-cell">
			<ul class="weui-media-box__info ">
              	<li style="padding-left: 10px;" class="weui-media-box__info__meta">发布时间:</li>
              	<li class="weui-media-box__info__meta">
              		<s:date name="notice.createTime" format="yyyy年MM月dd日  HH:mm" />
              	</li>
              	<li class="weui-media-box__info__meta weui-media-box__info__meta_extra">
              		<s:property value="notice.nickName"/>
              	</li>
            </ul>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<p>
					<s:property value="notice.content"/>
				</p>
			</div>
		</div>
		<article class="weui-article">
			<section>
				<p>
					<s:if test="notice.attachmentNewName == null || notice.attachmentNewName == ''">
					</s:if>
					<s:else>
						
						附件：<div id="add"></div>
						<a id="down" download href="${pageContext.request.contextPath}/image/<s:property value='notice.attachmentNewName'/> " >
							<s:property value="notice.attachmentOldName"/>
						</a>
						<br />
					</s:else>
					<s:if test="notice.picNewName == null ||notice.picNewName == ''">
					</s:if>
					<s:else>
						<input value="${notice.picNewName}" type="hidden" id="aa"/>
						<img id="pb1" height="250px;" width="100%" src="${pageContext.request.contextPath}/image/max_<s:property value="notice.picNewName"/>">
					</s:else>
				</p>
			</section>
		</article>
	</body>
	<script type="text/javascript">
		
		window.onload = function(){ 
			if(isWeiXin()){ 
				$("#add").append("请点击右上角按钮，选择在游览器中打开，下载附件");
				$("#down").hide();
				//alert("111111111111111");
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
	      $("#pb1").click(function() {
	        	pb1.open();
	      });
	      var pb1 = $.photoBrowser({
	        items: [
	          "../image/max_"+$("#aa").val()
	        ]
	      });
	
	</script>
</html>