<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>球队详情</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/city-picker.min.js" charset="utf-8"></script>
		<style>
			.divcssleft{
				width:35%;
				float:left;
				color: #000000;
				text-align:left;
				font-size:15px;
				
			}
			.divcssright{
				width:65%;
				float:right;
				text-align: left;
				color: #000000;
				font-size:15px;
			}
		</style>
	</head>
	<body>
		<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" >
			<div class="weui-media-box__hd" style="padding:20px auto">
				<s:if test="#obj[5] == null || #obj[5] == ''">  
			 		<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/common/img/default.png">
			 	</s:if>  
			    <s:else>  
			    	<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/image/${obj[5]}">
			    </s:else>
			</div>
			<div class="weui-media-box__bd">
				<div>
					<div class="divcssleft">创建时间</div>
					<div class="divcssright"><s:date name="%{#obj[1]}" format="yyyy-MM-dd" /></div>
				</div>
				<div>
					<div class="divcssleft">球队队长</div>
					<div class="divcssright">
						<s:if test="#obj[2].length()>10">  
				    		<s:property value="#obj[2].substring(0,10)"/>...  
					 	</s:if>  
					    <s:else>  
					   		<s:property value="#obj[2]"/>
					    </s:else>
					</div>
				</div>
				<div>
					<div class="divcssleft">球队宣言</div>
					<div class="divcssright"><s:property value="#obj[3]"/></div>
				</div>
				
			</div>
		</a>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell" >
				<div class="weui-cell__hd">
					<label class="weui-label">所在地址</label>
				</div>
				<div class="weui-cell__bd">
					<div><s:property value="#obj[6]"/>&nbsp;&nbsp;<s:property value="#obj[7]"/></div>
				</div>
			</div>
		</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell__bd">
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">球队简介</label>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<s:if test="#obj[4] == null || #obj[4] == ''">  
					 		暂无球队简介
					 	</s:if>  
					    <s:else>  
					    	<s:property value="#obj[4]"/>
					    </s:else>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell__bd">
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">规章制度</label>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__bd" style="display: block" id="less">
						<s:if test="#obj[8] == null || #obj[8] == ''">  
					 		暂无规章制度
					 	</s:if>  
					    <s:else>  
					    	<s:if test="#obj[8].length()>50">  
				    			<s:property value="#obj[8].substring(0,50)"/>...  
				    			<a onclick="showMore();" href="javascript:void(0);" class="weui-cell_link">
								    <div class="weui-cell__bd" align="right">展开更多</div>
								</a>
						 	</s:if>  
						    <s:else>  
						   		<s:property value="#obj[8]"/>
						    </s:else>
					    </s:else>
					</div>
					<div class="weui-cell__bd" style="display: none" id="more">
					    <s:property value="#obj[8]"/>
					    <a onclick="closeMore();" href="javascript:void(0);" class="weui-cell_link">
						    <div class="weui-cell__bd" align="right">收起</div>
						</a>
					</div>
				</div>
			</div>
		</div>
		
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell__bd">
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">全家福</label>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-media-box__bd">
						<s:if test="#picture.picNewName != null && #picture.picNewName != ''">  
							<div class="weui-uploader__bd">
								<img onclick='showBig()' class='weui-uploader__file' src='${pageContext.request.contextPath}/image/${picture.picNewName}'>
							</div>
					 	</s:if> 
					 	 <s:else>  
						   	暂无全家福
						 </s:else> 
					</div>
				</div>
			</div>
		</div>
		<div onclick='hideBig();' class="weui-gallery" style="display: none" id="big_img">
			<span class="weui-gallery__img" style="background-image: url(${pageContext.request.contextPath}/image/${picture.picNewName});"></span>
		</div>
	</body>
	<script type="text/javascript">
		/*图片显示或者隐藏*/
		function showBig(){
			$("#big_img").show();
		}
		function hideBig(){
			$("#big_img").hide();
		}
		//更多
		function showMore(){
			$("#less").hide();
			$("#more").show();
			
		}
		//收起
		function closeMore(){
			$("#more").hide();
			$("#less").show();
		}
	</script>
</html>