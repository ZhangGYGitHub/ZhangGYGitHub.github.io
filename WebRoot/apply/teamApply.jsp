<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>
	<head>
		<title>申请列表</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<script type="text/javascript">
			var tid = "${tid}";
			function handle(uid){
				$.actions({
					actions: [{
							text: "同意",
							onClick: function() {
								$.confirm("同意加入？", function() {
								  	//点击确认后的回调函数
								  	$.post("${pageContext.request.contextPath}/apply/agreeApply",{"uid":uid,"tid":tid},function(data){
										var json = $.parseJSON(data);
						        		if(json.data) {
						        			//获取头像链接
										  	var imgSrc = $("#img_"+uid)[0].src; 
										  	//获取昵称
										  	var nickname = $("#nickname_"+uid).text();
										  	//移除该div
							  				$("#div_"+uid).remove(); 
							  				 //追加一个div  id为#handleDiv的div下的第一个div的前面
						    				var firstDiv = $("#handleDiv").find("div:eq(0)");
						    				var $div = '<div class="weui-media-box weui-media-box_appmsg">'
				    							+'<div class="weui-media-box__hd" style="padding:10px auto">'
				    							+'<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="'+imgSrc+'">'
				    							+'</div>'
				    							+'<div  class="weui-media-box__bd">'
				    							+'<h4 class="weui-media-box__title">'+nickname+'</h4>'
				    							+'</div>'
				    							+'<div class="weui-cell__hd" >'
				    							+'<button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default">已同意</button>'
				    							+'</div>'
				    							+'</div>';
						        			if(firstDiv.length>0){
						        				//存在该div则添加到最前面 
						    					firstDiv.before($div);
						        			 } else {
						        				//不存在该div则追加
						    					$("#handleDiv").append($div);
						        			 }
						        			//改变条目上的数字
						        			var handleNum = $("#handleNum").text();
											var waitHandleNum = $("#waitHandleNum").text();
											var resultNum = parseInt(handleNum) + 1;
											var waitNum = parseInt(waitHandleNum) - 1;
											$("#handleNum").text(resultNum);
											$("#waitHandleNum").text(waitNum);
						        		} else {
						        			$.toast("处理失败",'cancel');
						        		}
									});
								 }, function() {
								  	//点击取消后的回调函数
							     });
							}
						},
						{
							text: "拒绝",
							onClick: function() {
								$.prompt("请说明拒绝理由", function(text) {
								  //点击确认后的回调函数
								  //text 是用户输入的内容
								  if(text.trim().length > 20){
									  $.toptip("请不要超过20个字符", 'warning');
									  return;
								  }
								  	$.post("${pageContext.request.contextPath}/apply/rejectApply",{"uid":uid,"tid":tid,"reason":text.trim()},function(data){
										var json = $.parseJSON(data);
						        		if(json.data) {
						        			//获取头像链接
										  	var imgSrc = $("#img_"+uid)[0].src; 
										  	//获取昵称
										  	var nickname = $("#nickname_"+uid).text();
										  	//移除该div
							  				$("#div_"+uid).remove(); 
							  				 //追加一个div  id为#handleDiv的div下的第一个div的前面
						    				var firstDiv = $("#handleDiv").find("div:eq(0)");
							  				var $div = '<div class="weui-media-box weui-media-box_appmsg">'
				    							+'<div class="weui-media-box__hd" style="padding:10px auto">'
				    							+'<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="'+imgSrc+'">'
				    							+'</div>'
				    							+'<div  class="weui-media-box__bd">'
				    							+'<h4 class="weui-media-box__title">'+nickname+'</h4>'
				    							+'</div>'
				    							+'<div class="weui-cell__hd" >'
				    							+'<button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default">已拒绝</button>'
				    							+'</div>'
				    							+'</div>';
						        			if(firstDiv.length>0){
						        				//存在该div则添加到最前面 
						    					firstDiv.before($div);
						        			 } else {
						        				//不存在该div则追加
						    					$("#handleDiv").append($div);
						        			 }
						        			//改变条目上的数字
						        			var handleNum = $("#handleNum").text();
											var waitHandleNum = $("#waitHandleNum").text();
											var resultNum = parseInt(handleNum) + 1;
											var waitNum = parseInt(waitHandleNum) - 1;
											$("#handleNum").text(resultNum);
											$("#waitHandleNum").text(waitNum);
						        		} else {
						        			$.toast("处理失败",'cancel');
						        		}
									});
								}, function() {
								  //点击取消后的回调函数
								});
							}
						},
						{
							text: "忽略",
							onClick: function() {
								$.confirm("确认忽略？", function() {
								  	//点击确认后的回调函数
								  	$.post("${pageContext.request.contextPath}/apply/ignoreApply",{"uid":uid,"tid":tid},function(data){
										var json = $.parseJSON(data);
						        		if(json.data) {
						        			//获取头像链接
										  	var imgSrc = $("#img_"+uid)[0].src; 
										  	//获取昵称
										  	var nickname = $("#nickname_"+uid).text();
										  	//移除该div
							  				$("#div_"+uid).remove(); 
							  				 //追加一个div  id为#handleDiv的div下的第一个div的前面
						    				var firstDiv = $("#handleDiv").find("div:eq(0)");
							  				var $div = '<div class="weui-media-box weui-media-box_appmsg">'
				    							+'<div class="weui-media-box__hd" style="padding:10px auto">'
				    							+'<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="'+imgSrc+'">'
				    							+'</div>'
				    							+'<div class="weui-media-box__bd">'
				    							+'<h4 class="weui-media-box__title">'+nickname+'</h4>'
				    							+'</div>'
				    							+'<div class="weui-cell__hd" >'
				    							+'<button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default">已忽略</button>'
				    							+'</div>'
				    							+'</div>'; 
						        			if(firstDiv.length>0){
						        				//存在该div则添加到最前面 
						    					firstDiv.before($div);
						        			 } else {
						        				//不存在该div则追加
						    					$("#handleDiv").append($div);
						        			 }
						        			//改变条目上的数字
						        			var handleNum = $("#handleNum").text();
											var waitHandleNum = $("#waitHandleNum").text();
											var resultNum = parseInt(handleNum) + 1;
											var waitNum = parseInt(waitHandleNum) - 1;
											$("#handleNum").text(resultNum);
											$("#waitHandleNum").text(waitNum);
						        		} else {
						        			$.toast("处理失败",'cancel');
						        		}
									});
								  	
								  }, function() {
								  //点击取消后的回调函数
							  	});
							}
						}
					]
				});
			}
			
			//加载更多已处理列表
			$(function(){
				var page = 2;//第一次加载更多肯定是加载第二页
				$(document.body).infinite(0);//滑到最底部才加载
				//加载的div
				var $div = $("#loadDiv");//加载的div
				var loading = false;  //状态标记
				var falg = true;//还有数据的标志
				$(document.body).infinite().on("infinite", function() {
				  $div.css("display","block");//显示加载div
				  if(loading) return;
				  loading = true;
				  if(falg){
					//请求数据
					  $.post("${pageContext.request.contextPath}/apply/ajaxLoadMoreApplyHandleList",{"pageNo":page,"tid":tid},function(data){
						  //请求到数据
						  var json = JSON.parse(data);
						  var list = json.data;
						  if(list != null && list.length >0 ){
							  for(var i = 0;i<list.length;i++){
								 var icon = '';
								 if((list[i].headPortrait == null || list[i].headPortrait == '') && (list[i].headPortraitNew == null || list[i].headPortraitNew == '')){
									 icon = "${pageContext.request.contextPath}/common/img/default.png";
								 } else {
									 if(list[i].headPortrait == null || list[i].headPortrait == ''){
										 icon = list[i].headPortraitNew;
									 } else{
										 icon = "${pageContext.request.contextPath}/image/"+list[i].headPortraitNew;
									 }
								 }
								 var type = '';
								 if(list[i].applyHandle != null && list[i].applyHandle == 1){
									 type = "已同意";
								 } else if (list[i].applyHandle != null && list[i].applyHandle == 2){
									 type = "已拒绝";
								 } else {
									 type = "已忽略";
								 }
							 	 $div.before('<div class="weui-media-box weui-media-box_appmsg">'
							 			 +'<div class="weui-media-box__hd" style="padding:10px auto">'
							 			 +'<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="'+icon+'">'
							 			 +'</div>'
							 			 +'<div class="weui-media-box__bd">'
							 			 +'<h4 class="weui-media-box__title">'+list[i].nickname
							 			 +'</h4>'
							 			 +'</div>'
							 			 +'<div class="weui-cell__hd">'
							 			 +'<button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default">'+type
							 			 +'</button>'
							 			 +'</div>'
							 			 +'</div>');
							  }
							  page++;//页数++
							  falg = true;//还有数据
						  } else {
							  //没有更多数据
							  $(document.body).destroyInfinite();
							  // $.toptip("没有更多数据了", 'warning');
							  falg = false;//没有更多数据了
						  }
						  loading = false;
						  $div.css("display","none");//隐藏加载div
					  });
				  } else{
					  $(document.body).destroyInfinite();
					  $div.css("display","none");//隐藏加载div
					  // $.toptip("没有更多数据了", 'warning');
				  }
				});
			});
		</script>
	</head>
<body>
	<div class="weui-tab">
		<div class="weui-tab__bd">
				
				<div class="weui-panel weui-panel_access">
					<div align="center" id="wait">
						<h2 class="weui-panel__hd"><font color="#09BB07" size="3px">待处理列表(<span id="waitHandleNum"><s:property value="#waitHandleList.size()"/></span>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
						<img id="img1" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px" >
						</h2>
					</div>
					
					<div class="weui-panel__bd" id="waitHandle">
						<s:if test="#waitHandleList != null && #waitHandleList.size()>0">
			    			<s:iterator value="#waitHandleList" status="status" var="waitHandle">
						   		<div id="div_${waitHandle.id}" class="weui-media-box weui-media-box_appmsg">
									<div class="weui-media-box__hd" style="padding:10px auto">
										<s:if test="(#waitHandle.headPortrait == null || #waitHandle.headPortrait == '') && (#waitHandle.headPortraitNew == null || #waitHandle.headPortraitNew == '')"> 
											<img id="img_${waitHandle.id}" style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/default.png"> 
									 	</s:if>
									    <s:else> 
									    	<s:if test="#waitHandle.headPortrait == null || #waitHandle.headPortrait == ''"> 
												<img id="img_${waitHandle.id}" style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${waitHandle.headPortraitNew}"> 
									 		</s:if>
									 		<s:else>
									    		<img id="img_${waitHandle.id}" style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/image/${waitHandle.headPortraitNew}" alt="">
									 		</s:else>   
									    </s:else>
									</div>
									<div onclick="showDetail()" class="weui-media-box__bd">
										<h4 id="nickname_${waitHandle.id}" class="weui-media-box__title">${waitHandle.nickname}</h4>
									</div>
									<div class="weui-cell__hd" >
								        <button onclick="handle(${waitHandle.id})" class="weui-btn weui-btn_mini weui-btn_primary">待处理</button>
									</div>
								</div>
						    </s:iterator>
						</s:if>

					</div>
					
				</div>
				
				<div class="weui-panel weui-panel_access">
					<div align="center" id="handle">
						<h2 class="weui-panel__hd"><font color="#09BB07" size="3px">已处理列表(<span id="handleNum"><s:property value="#tempHandleList.size()"/></span>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
						<img id="img2" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px" >
						</h2>
					</div>
					
					<div id="handleDiv" class="weui-panel__bd">
						<!-- 已处理列表 -->
						<s:if test="#handleList != null && #handleList.size()>0">
			    			<s:iterator value="#handleList" status="status" var="handle">
						   		<div class="weui-media-box weui-media-box_appmsg">
									<div class="weui-media-box__hd" style="padding:10px auto">
										<s:if test="(#handle[3] == null || #handle[3] == '') && (#handle[2] == null || #handle[2] == '')"> 
											<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/default.png"> 
									 	</s:if>
									    <s:else> 
									    	<s:if test="#handle[3] == null || #handle[3] == ''"> 
												<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${handle[2]}"> 
									 		</s:if>
									 		<s:else>
									    		<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/image/${handle[2]}" alt="">
									 		</s:else>   
									    </s:else>
									</div>
									<div  class="weui-media-box__bd">
										<h4 class="weui-media-box__title">${handle[1]}</h4>
									</div>
									<div class="weui-cell__hd" >
									   <s:if test="#handle[4] != null && #handle[4] == 1"> 
									   		<button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default">已同意</button>
									 	</s:if>	
									 	<s:elseif test="#handle[4] != null && #handle[4] == 2"> 
									   		<button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default">已拒绝</button>
									 	</s:elseif>	
									 	<s:else>
									 		<button class="weui-btn weui-btn_mini weui-btn_disabled weui-btn_default">已忽略</button>
									 	</s:else>
									</div>
								</div>
						    </s:iterator>
						</s:if>
						<div id="loadDiv" style="display: none;" class="weui-loadmore">
						  <i class="weui-loading"></i>
						  <span class="weui-loadmore__tips">正在加载</span>
						</div>
					</div>
					
				</div>
				
			</div>
			
		</div>	
	<script type="text/javascript">
		/* $(function(){
			$("#waitHandle").hide();
			$("#handleDiv").hide();
		}); */
		$("#wait").click(function(){
			$("#waitHandle").toggle();
			if($("#img1").attr('value')==2){
				$("#img1").attr('src','${pageContext.request.contextPath}/common/img/right.png');
				$("#img1").attr('value','1');
			}else{
				$("#img1").attr('src','${pageContext.request.contextPath}/common/img/bottom.png');
				$("#img1").attr('value','2');
			}
		});
		$("#handle").click(function(){
			$("#handleDiv").toggle();
			if($("#img2").attr('value')==2){
				$("#img2").attr('src','${pageContext.request.contextPath}/common/img/right.png');
				$("#img2").attr('value','1');
			}else{
				$("#img2").attr('src','${pageContext.request.contextPath}/common/img/bottom.png');
				$("#img2").attr('value','2');
			}
		});
		}
	</script>
</body>
</html>