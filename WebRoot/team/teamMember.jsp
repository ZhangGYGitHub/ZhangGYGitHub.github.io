<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>
	<head>
		<title>球队成员</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
	</head>
	<body>
		<div class="weui-tab">
			<div class="weui-tab__bd">
				<div class="weui-tab__bd-item weui-tab__bd-item--active">
					<div class="weui-panel__bd">
						<div style="background-color: #eeeeee;height: 30px;padding-top: 10px;padding-left: 10px;">
							<font color="#000" size="3px">球队成员（${userNum}人）</font>
						</div>
						<!-- 队长 -->
						<div class="weui-media-box weui-media-box_appmsg">
							<div class="weui-media-box__hd" style="padding:10px auto">
								<s:if test="(#obj[2] == null || #obj[2] == '') && (#obj[6] == null || #obj[6] == '')"> 
							 		<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/default.png">
							 	</s:if>
							    <s:else> 
							    	<s:if test="#obj[6] == null || #obj[6] == ''"> 
							 			<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${obj[2]}">
							 		</s:if>
							 		<s:else>
							 			<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/image/${obj[2]}">
							 		</s:else>   
							    </s:else>
							    
							</div>
							<div onclick="showDetail(${obj[0]});" class="weui-media-box__bd">
								<p class="weui-media-box__desc"><img style="width:15px;" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/captain.png" >&nbsp;<strong>${obj[3]}</strong></p>
								<h4 id="nick_${obj[0]}" class="weui-media-box__title">
								<%-- <s:if test="#obj[5] == null || #obj[5] == ''"> 
							 		${obj[1]}
							 	</s:if>  
							    <s:else>  
							    	${obj[5]}
							    </s:else> --%>
							    ${obj[8]}
								</h4>
								<p id="num_${obj[0]}" class="weui-media-box__desc">球衣号码:&nbsp;${obj[4]}</p>
								<p class="weui-media-box__desc">踢球位置:&nbsp;${obj[7]}</p>
								<%-- <input class="weui-input" value="${obj[7]}" id="playerPosition_1" type="hidden"> --%>
								
							</div>
							<div class="weui-cell__hd" >
								<s:if test="#position.positionName != \"队员\"">
						       		<button onclick="handle(${obj[0]},'${obj[3]}')" class="weui-btn weui-btn_mini weui-btn_default">操作</button>
								</s:if>
							</div>
						</div>
						
						<!-- 除队长外的成员 -->
						<s:if test="#users != null && #users.size()>0">
			    			<s:iterator value="#users" var="u">
						    	<div id="remove_${u[0]}" class="weui-media-box weui-media-box_appmsg">
									<div class="weui-media-box__hd" style="padding:10px auto">
										<s:if test="(#u[2] == null || #u[2] == '') && (#u[6] == null || #u[6] == '')"> 
									 		<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/default.png">
									 	</s:if>
									    <s:else> 
									    	<s:if test="#u[6] == null || #u[6] == ''"> 
									 			<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${u[2]}">
									 		</s:if>
									 		<s:else>
									 			<img style="border-radius:50%;" height="70px" width="70px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/image/${u[2]}">
									 		</s:else>   
									    </s:else>
									</div>
									<div onclick="showDetail(${u[0]});" class="weui-media-box__bd">
										<p id="p_${u[0]}" class="weui-media-box__desc">
											<s:if test="#u[3] == \"教练\"">
												<img style="width:15px;" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/coach.png" >
											</s:if>
											<s:if test="#u[3] == \"副队长\"">
												<img style="width:15px;" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/cocaptain.png" >
											</s:if>
											&nbsp;<strong>${u[3]}</strong>
										</p>
										<h4 id="nick_${u[0]}" class="weui-media-box__title">
										<%-- <s:if test="#u[5] == null || #u[5] == ''"> 
									 		${u[1]}
									 	</s:if>  
									    <s:else>  
									    	${u[5]}
									    </s:else> --%>
									    ${u[8]}
										</h4>
										<p id="num_${u[0]}" class="weui-media-box__desc">球衣号码:&nbsp;${u[4]}</p>
										<p class="weui-media-box__desc">踢球位置:&nbsp;${u[7]}</p>
										<%-- <input class="weui-input" value="${u[7]}" id="playerPosition_${u[0]}" type="hidden"> --%>
									</div>
									<div class="weui-cell__hd" >
										<s:if test="#position.positionName == \"队长\"">
								       		<button onclick="editPosition(${u[0]})" class="weui-btn weui-btn_mini weui-btn_primary">职位</button>
										</s:if>
										<s:if test="#position.positionName != \"队员\"">
											<button onclick="handle(${u[0]},'${u[3]}')" class="weui-btn weui-btn_mini weui-btn_default">操作</button>
										</s:if>
										<!-- 当前人是队员，只出现自己的操作按钮 -->
										<s:elseif test="#u[0] == #currentUserId">
											<button onclick="handle(${u[0]},'${u[3]}')" class="weui-btn weui-btn_mini weui-btn_default">操作</button>
										</s:elseif>
									</div>
								</div>
						    </s:iterator>
						</s:if>
						
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		function showDetail(uid){
			window.location.href = "${pageContext.request.contextPath}/wxuser/showMyCard?teamId="+${teaminfo.id}+"&userId="+uid;
		}
	</script>
	<script>
			//从session中获取的用户
			var currentUserId ='${currentUserId}';
			var currentUserPositionName = '${position.positionName}';
			var tid = '${teaminfo.id}';
			function handle(uid,positionName){
				//登录人是队长
				if(currentUserPositionName == '队长'){
					if(positionName == '队长'){
						//被操作的用户也是队长
						$.actions({
							actions: [{
									text: "更改球衣号码",
									onClick: function() {
										editClothes(uid);
									}
								},
								/*{
									text: "更改踢球位置",
									onClick: function() {
										editPlayerPosition(uid);
									}
								},*/
								{
									text: "修改备注",
									onClick: function() {
										editRemarkName(uid);
									}
								}]
						});
					} else {
						//被操作的人非队长
						$.actions({
							actions: [{
									text: "更改球衣号码",
									onClick: function() {
										editClothes(uid);
									}
								},
								/*{
									text: "更改踢球位置",
									onClick: function() {
										editPlayerPosition(uid);
									}
								},*/
								{
									text: "移除该队员",
									onClick: function() {
										removeMember(uid);
									}
								},
								{
									text: "修改备注",
									onClick: function() {
										editRemarkName(uid);
									}
								}]
						});
						
					}
					
				} else if (currentUserPositionName == '教练' || currentUserPositionName == '副队长') {
					if(positionName == '队员'){
						//被操作的人是队员
						$.actions({
							actions: [{
									text: "更改球衣号码",
									onClick: function() {
										editClothes(uid);
									}
								},
								/*{
									text: "更改踢球位置",
									onClick: function() {
										editPlayerPosition(uid);
									}
								},*/
								{
									text: "移除该队员",
									onClick: function() {
										removeMember(uid);
									}
								},
								{
									text: "修改备注",
									onClick: function() {
										editRemarkName(uid);
									}
								}]
						});
					} else {
						//非队员
						$.actions({
							actions: [{
									text: "更改球衣号码",
									onClick: function() {
										editClothes(uid);
									}
								},
								/*{
									text: "更改踢球位置",
									onClick: function() {
										editPlayerPosition(uid);
									}
								},*/
								{
									text: "修改备注",
									onClick: function() {
										editRemarkName(uid);
									}
								}]
						});
						
					}
					
				} else {
					//当前登录人是队员
					if(currentUserId == uid){
						//自己
						$.actions({
							actions: [{
									text: "更改球衣号码",
									onClick: function() {
										editClothes(uid);
									}
								},
								/*{
									text: "更改踢球位置",
									onClick: function() {
										editPlayerPosition(uid);
									}
								},*/
								{
									text: "修改备注",
									onClick: function() {
										editRemarkName(uid);
									}
								}]
						});
					} 
				}
			}	
				
			function editRemarkName(uid){
				$.prompt("请输入备注名", function(text) {
				  //点击确认后的回调函数
				  //text 是用户输入的内容
				 if(text.trim().length > 20){
					  $.toptip("请不要超过20个字符", 'warning');
					  return;
				  }
				  $.post("${pageContext.request.contextPath}/team/changeRemakeName",{"uid":uid,"teaminfo.id":tid,"remakeName":text.trim()},function(data){
					  var json = JSON.parse(data);
						if(!json.data){
							$.toast("修改失败",'cancel');
							return;
						}
						//成功
						$("#nick_"+uid).text(text);
				  });
				  
				}, function() {
				  //点击取消后的回调函数
				});
			}	
			
			function editClothes(uid){
				$.prompt("请输入球衣号码", function(text) {
				  //点击确认后的回调函数
				  //text 是用户输入的内容
				  var reg = /^\d{1,3}$/;
				  if(!reg.test(text)){
					  $.toptip("请输入3位数以下的数字！", 'warning');
					  return;
				  }
				  $.post("${pageContext.request.contextPath}/team/changeClothesNum",{"uid":uid,"teaminfo.id":tid,"clothesNum":text},function(data){
					  var json = JSON.parse(data);
						if(!json.data){
							$.toast("修改失败",'cancel');
							return;
						}
						//成功
						$("#num_"+uid).text("号码: "+text);
				  });
				  
				}, function() {
				  //点击取消后的回调函数
				});
			}
		/*	
			function editPlayerPosition(uid){
				$("#playerPosition_1").select({
					title: "请选择踢球位置",
					multi: true,
					max:3,
					items: ${locations}
				});
			}
			$("#playerPosition_1").select({
					title: "请选择踢球位置",
					multi: true,
					max:3,
					items: ${locations}
				});
				*/
			
		    function removeMember(uid){
		    	$.confirm("确认移除该成员？", function() {
				  	//点击确认后的回调函数
		    		 $.post("${pageContext.request.contextPath}/team/removeMember",{"uid":uid,"teaminfo.id":tid},function(data){
						  var json = JSON.parse(data);
							if(!json.data){
								$.toast("移除失败",'cancel');
								return;
							}
							//成功
							$("#remove_"+uid).remove();
					  }); 	
		    	}, function() {
				  	//点击取消后的回调函数
			  	});
		    }	
			
			function editPosition(uid) {
				$.actions({
					actions: [{
							text: "副队长",
							onClick: function() {
								//先清空
								$('#p_'+uid).empty();
								//再追加
								$('#p_'+uid).append( '<img style="width:15px;" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/cocaptain.png" >&nbsp;<strong>副队长</strong>');
								//发送请求
								editPos(uid,2);
							}
						},
						{
							text: "教练",
							onClick: function() {
								$('#p_'+uid).empty();
								$('#p_'+uid).append( '<img style="width:15px;" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/coach.png" >&nbsp;<strong>教练</strong>');
								editPos(uid,3);
							}
						},
						{
							text: "队员",
							onClick: function() {
								$('#p_'+uid).empty();
								$('#p_'+uid).append( '&nbsp;<strong>队员</strong>');
								editPos(uid,4);
							}
						}
					]
				});
			}
			
			function editPos(uid,position) {
				$.post("${pageContext.request.contextPath}/team/changePosition",{"uid":uid,"teaminfo.id":tid,"position":position},function(data){
					  var json = JSON.parse(data);
						if(!json.data){
							$.toast("职位变更失败",'cancel');
						}
				  });
			}
			
		</script>
</html>