<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
  <head>
    <title>裁判信息</title>
	
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
	<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
	<style type="text/css">
	.img_box {
		width: 90px;
		height: 90px;
	}
    </style>
  </head>
  
  <body>
  		<div  class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">昵称</label>
				</div>
				<div class="weui-cell__bd">
						${obj[1]}
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">头像</label>
				</div>
				<div class="weui-cell__bd weui-uploader__bd">
					<div class="img_box">
					  <s:if test="obj[2] == null || obj[2] == ''">
						<img class="img_box" id="img0" src="${pageContext.request.contextPath}/common/img/default.png" 
					  </s:if>
					  <s:else>
						  <s:if test="obj[2].indexOf('http') != -1">
							<img id="img0" src="${obj[2]}" class="img_box">
						 </s:if>
						 <s:else>
						 	<img id="img0" src="${pageContext.request.contextPath}/image/${obj[2]}" class="img_box">
						 </s:else>
					 </s:else>
					</div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label  class="weui-label">生日</label>
				</div>
				<div class="weui-cell__bd">
						${obj[3].toString().substring(0,10)}
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">性别</label>
				</div>
				<div class="weui-cell__bd" id="show-actions-bg">
					<s:if test="obj[4] == 1">
		               	<input class="weui-input" id="sex"  readonly type="text" value="男" style="text-align: left;">		   	  
	               	</s:if>
	               	<s:if test="obj[4] == 2">
		               	<input class="weui-input"  id="sex"  readonly type="text" value="女" style="text-align: left;">		   	  
	               	</s:if>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">身高</label>
				</div>
				<div class="weui-cell__bd">
						${obj[5]}
				</div>
				cm
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">体重</label>
				</div>
				<div class="weui-cell__bd">
						${obj[6]}
				</div>
				kg
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">习惯用脚</label>
				</div>
				<div class="weui-cell__bd">
					<s:if test="obj[7] == 1">
		               	<input class="weui-input"  id="#habitfoot" readonly type="text" value="左脚" style="text-align: left;">		   	  
	               	</s:if>
	               	<s:if test="obj[7] == 2">
		               	<input class="weui-input" id="#habitfoot"  readonly type="text" value="右脚" style="text-align: left;">		   	  
	               	</s:if>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">个性签名</label>
				</div>
				<div class="weui-cell__bd">
					${obj[8]}
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">个人简介</label>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd" style="margin-left:1em;">
					 ${obj[9]}
				</div>
			</div>
		</div>

  </body>
</html>
