<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>
	<head>
		<title>全家福</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
	</head>
<body>
	<div class="weui-tab">
		<div class="weui-tab__bd">
			<div  id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
			<form action="${pageContext.request.contextPath}/team/uploadPicture" id="fileForm" method="post" enctype="multipart/form-data">
				<div class="weui-cells weui-cells_form">
					<div class="weui-cell">
						<div class="weui-cell__bd">
							<div class="weui-uploader">
								<div class="weui-uploader__hd">
									<p class="weui-uploader__title">全家福上传</p>
									<div id="num" class="weui-uploader__info">0/1</div>
								</div>
								<div class="weui-uploader__bd" id="boxDiv">
									<div class="weui-uploader__input-box">
										<input id="uploaderInput" class="weui-uploader__input" name="upload" type="file" accept="image/*">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<input name="teaminfo.id" value="${teaminfo.id}" type="hidden">
			</form>
			<div onclick='hideBig();' class="weui-gallery" style="display: none" id="big_img">
				<span id="sp" class="weui-gallery__img" style="background-image: url(${pageContext.request.contextPath}/image/${picture.picNewName});"></span>
			</div>
			<input id="picNewName" name="picture.picNewName" value="${picture.picNewName}" type="hidden">
				<script type="text/javascript">
					$(function(){
						$("#uploaderInput").change(function(){
							 $.showLoading("正在上传中...");
							 var options ={
						        	success:function(data) {
						        		var json = $.parseJSON(data);
						        		var obj = json.data;
						        		//{"success":true,"message":"","data":{"newName":"1.jpg","oldName":"8c659a30-38de-4e8e-a820-a75d214dc8c6.jpg","success":true}}
						        		if(obj.success){
											//先移除第一个子元素
											$("#boxDiv img").remove();
											//再添加到最前面
											$("#boxDiv").prepend("<img onclick='showBig()' class='weui-uploader__file' src='${pageContext.request.contextPath}/image/"+obj.newName+"'>");
											$("#num").empty();
											$("#num").append("1/1");
											//替换大图链接
											$("#sp").attr("style","background-image: url(${pageContext.request.contextPath}/image/"+obj.newName+");");
										} else {
											$.toast("上传失败",'cancel');
										}
						        		 setTimeout(function() {
									          $.hideLoading();
									     }, 1)//1毫秒后直接消失
						        	}
						        }
						        $("#fileForm").ajaxSubmit(options);
						 });
					
						//回显全家福和计数
						var newName = $("#picNewName").val();
						if(newName != ''){
							//追加到开头
							$("#boxDiv").prepend("<img onclick='showBig()' class='weui-uploader__file' src='${pageContext.request.contextPath}/image/"+newName+"'>");
							$("#num").empty();
							$("#num").append("1/1");
						}	
						
					});
					/*图片显示或者隐藏*/
					function showBig(){
						$("#big_img").show();
					}
					function hideBig(){
						$("#big_img").hide();
					}
				</script>
			</div>
		</div>	
	</div>	
</body>
</html>