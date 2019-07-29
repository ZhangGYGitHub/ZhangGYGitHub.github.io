<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>签到列表</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>
	<body style="background-color: #F7F7F7;">
		<div class="weui-tab">
			<div style="padding-bottom: 50px;" class="weui-tab__bd">
				<div  id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
					
					<div class="weui-panel weui-panel_access">
						<div style="background-color: #EFEFF4" align="center">
							<h2 class="weui-panel__hd"><font color="#09BB07">我的比赛</font></h2>
						</div>
						<div class="weui-panel__bd">
							<s:set name="nowTime" value="new java.util.Date()"></s:set>
							<s:if test="raceList != null && raceList.size() > 0">
								<s:iterator value="raceList" status="status" var="rl">
									<s:if test="#rl[3].getTime() > #nowTime.getTime() || #rl[8] == 1">
										<a href="javascript:void(0);" id ="time${rl[6]}" class="weui-media-box weui-media-box_appmsg">									
											<div onclick="showDetail();" class="weui-media-box__bd">
												<h4>${rl[1]}</h4>
												<h5 style="font-weight:normal;">对手:
													<s:if test="#rl[4]">
														${rl[5]}
													</s:if>
													<s:else>
														${rl[4]}
													</s:else>
												</h5>
												<h5 style="font-weight:normal;">
													开始时间：<s:date name="%{#rl[2]}" format="yyyy-MM-dd HH:mm" />
												</h5>
											</div>
											<div class="weui-media-box__hd" id="r${rl[6]}">
												<s:if test="#rl[7] == 0 || #rl[7] == null">
													<img class="weui-media-box__thumb" onclick="Rsign(${rl[6]})"  src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />
												</s:if>
												<s:elseif test="#rl[7] == 3">
													<img class="weui-media-box__thumb" onclick="noSign()" src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />
												</s:elseif>
												<s:elseif test="#rl[7] == 1 ||#rl[7] == 2">
													<img class="weui-media-box__thumb" onclick="siSign()" src="${pageContext.request.contextPath}/common/img/sign1.png"  alt="" />
												</s:elseif>
											</div>
										</a>
										<div <%-- href="${pageContext.request.contextPath}/sign/findRSignList?rid=" --%> class="weui-cell weui-cell_access weui-cell_link">
							            	<div class="weui-cell__bd" onclick="nameList(${rl[0]},${rl[8]})">查看签到名单</div>
							            	<span class="weui-cell__ft"></span>
											<s:if test="#rl[8] == 1">
								            	<div class="weui-cell__bd" onclick="leaveList(${rl[0]},1)">代替队员请假</div>
								            	<span class="weui-cell__ft"></span>
						          			</s:if>
							          	</div> 
							         </s:if>
								</s:iterator>
							</s:if>
						</div>
						
					</div>
			
					<div class="weui-panel weui-panel_access">
						<div style="background-color: #EFEFF4" align="center">
							<h2 class="weui-panel__hd"><font color="#09BB07">我的训练</font></h2>
						</div>
						<div class="weui-panel__bd">
						
							<s:if test="trainList != null && trainList.size() > 0">
								<s:iterator value="trainList" status="status" var="tl">
									<s:if test="#tl[7].getTime() > #nowTime.getTime() || #tl[5] == 1">
									<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">									
										<div onclick="showDetail();" class="weui-media-box__bd">
											<h4>${tl[2]}</h4>
											<h5 style="font-weight:normal;">训练内容:${tl[3]}</h5>
											<h5 style="font-weight:normal;">
												开始时间：<s:date name="%{#tl[4]}" format="yyyy-MM-dd HH:mm" />
											</h5>
										</div>
										<div class="weui-media-box__hd" id="t${tl[0]}">
											<s:if test="#tl[6] == 0 || #tl[6] ==  null">
												<img class="weui-media-box__thumb" onclick="Tsign(${tl[0]})" src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />
											</s:if>
											<s:elseif test="#tl[6] == 3">
												<img class="weui-media-box__thumb" onclick="noSign()" src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />
											</s:elseif>
											<s:elseif test="#tl[6] == 1 ||#tl[6] == 2">
												<img class="weui-media-box__thumb" onclick="siSign()" src="${pageContext.request.contextPath}/common/img/sign1.png"  alt="" />
											</s:elseif>
										</div>
									</a>
									<div  class="weui-cell weui-cell_access weui-cell_link">
						            	<div class="weui-cell__bd"  onclick="nameTList(${tl[1]},${tl[5]})">查看签到名单</div>
						            	<span class="weui-cell__ft"></span>
										<s:if test="#tl[5] == 1">
							            	<div class="weui-cell__bd" onclick="leaveList(${tl[1]},2)">代替队员请假</div>
								            <span class="weui-cell__ft"></span>
					          			</s:if>
						          	</div> 
						          	</s:if>
								</s:iterator>
							
							</s:if>
						</div>
					</div>
					
				</div>	
				
				<div class="weui-tabbar">
					<a href="${pageContext.request.contextPath}/home" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/home.png" alt="">
						</div>
						<p class="weui-tabbar__label">动态</p>
					</a>
					<a href="javascript:void(0)" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/qiandao_select.png" alt="">
						</div>
						<p class="weui-tabbar__label">签到</p>
					</a>
					<a href="${pageContext.request.contextPath}/notice/findNoticeList" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/manage.png" alt="">
						</div>
						<p class="weui-tabbar__label">通知</p>
					</a>
					<a href="${pageContext.request.contextPath}/wxuser/userDetail" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/my.png" alt="">
						</div>
						<p class="weui-tabbar__label">我</p>
					</a>
				</div>
				
			</div>	
		</div>	
		<script type="text/javascript">
			/*************ajax比赛签到*******************/
			function Rsign(id){
				$.ajax({
					url:'sign/ajaxSign.action?sid='+id,
					type:'post',  
				    success: function (data) {
				    	//alert(data);
				    	var flag = $.parseJSON(data);
				    	if(flag.data == 1){
					    	$.toast("签到成功");
					    	$("#r"+id).empty();
					    	$("#r"+id).append("<img class='weui-media-box__thumb' onclick='siSign()' src='${pageContext.request.contextPath}/common/img/sign1.png'  alt=' ' />");
				    	}else if(flag.data == 2){
				    		$.toast("签到失败，已超过签到时间", "forbidden");
				    	}else if (flag.data == 3) {
				    		$.toast("签到还未开始", "cancel");
						}
				    }
				});
			}
			/*************ajax训练签到**************/
			function Tsign(id){
				$.ajax({
					url:'sign/ajaxTSign.action?sid='+id,
					type:'post',  
				    success: function (data) {
				    	//alert(data);
				    	var flag = $.parseJSON(data);
				    	if(flag.data == 1 ){
					    	$.toast("签到成功");
					    	$("#t"+id).empty();
					    	$("#t"+id).append("<img class='weui-media-box__thumb' onclick='siSign()' src='${pageContext.request.contextPath}/common/img/sign1.png'  alt=' ' />");
				    	}else if(flag.data == 2){
				    		$.toast("签到失败，已超过签到时间", "forbidden");
				    	}else if (flag.data == 3) {
				    		$.toast("签到还未开始", "cancel");
						}
				    }
				});
			}
			
			function siSign(){
				$.toast("您已经签到，无需重复签到", "cancel");
			}
			
			function noSign(){
				 $.toast("sorry,队长判定您未到场", "forbidden");
			}
			
			function nameList(rid,type){
				window.location.href = "${pageContext.request.contextPath}/sign/findRSignList.action?rid="+rid+"&type="+type
			}
			
			function nameTList(trid,type){
				window.location.href = "${pageContext.request.contextPath}/sign/findTSignList.action?trid="+trid+"&type="+type
			}
			
			function leaveList(rid,type){
				//alert(rid);
				window.location.href = "${pageContext.request.contextPath}/sign/showLeaveList.action?rid="+rid+"&type="+type
			}
		</script>
	</body>
</html>