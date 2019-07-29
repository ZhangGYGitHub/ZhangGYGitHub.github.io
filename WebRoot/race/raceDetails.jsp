 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<%@ taglib prefix="se" uri="/WEB-INF/security.tld"%>
<!DOCTYPE html>
<html>

	<head>
		<title>比赛详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>

	<body>
		
		<div class="weui-cells weui-cells_form">
	  
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">比赛名称</label></div>
				<div class="weui-cell__bd">
				<s:property value="race.raceName"/>
				</div>
			</div>
			<div class="weui-cell" >
				<div class="weui-cell__hd">
					<label class="weui-label">比赛类型</label>
				</div>
				<div class="weui-cell__bd">
					<input type="hidden" id="type"  value="${race.type}"/>
					<div id="racetype"></div>
				</div>
			</div>
			<div class="weui-cell" id="racetypeselect">
				<div class="weui-cell__hd">
					<label class="weui-label">联赛名称</label>
				</div>
				<div class="weui-cell__bd">
					<div id="racetype"><s:property value="race.league"/></div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label for="" class="weui-label">比赛双方</label></div>
				<div class="weui-cell__bd">
					<s:iterator value="list" status="status" var="st"></s:iterator>
					
					<s:property value="#t.name"/>&nbsp;&nbsp;VS&nbsp;&nbsp;<s:property value="#t2.name"/>
					
				</div>
			</div>
				
			<div class="weui-cell">
				<div class="weui-cell__hd"><label for="" class="weui-label">开始时间</label></div>
				<div class="weui-cell__bd">
					<s:date name="race.startTime"  format="yyyy-MM-dd HH:mm"/>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label for="" class="weui-label">结束时间</label></div>
				<div class="weui-cell__bd">
				<s:date name="race.endTime"  format="yyyy-MM-dd HH:mm"/>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label for="" class="weui-label">比赛地点</label></div>
				<div class="weui-cell__bd">
			
				<s:property value="race.place"/>
				
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">服装颜色</label></div>
				<div class="weui-cell__bd">
					<s:property value="race.clothescolour"/>
				</div>
			</div>
			<div class="weui-cell" >
				<div class="weui-cell__hd">
					<label class="weui-label">签到类型</label>
				</div>
				<div class="weui-cell__bd">
					<input type="hidden" id="isSign" value="${race.signType}"/>
					<div id="signType"></div>
				</div>
			</div>
			<div class="weui-cell" id="signtimeselect">
				<div class="weui-cell__hd">
					<label class="weui-label">开始签到</label>
				</div>
				<div class="weui-cell__bd">
					<input type="hidden" id="sTime" value="${race.signTime}"/>
					<div id="signtime"></div>
				</div>
			</div>
			<s:if test="#position.positionName != \"队员\"">
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">资金耗费</label></div>
				<div class="weui-cell__bd">
					<s:property value="race.waste"/>
				</div>
			</div>
			</s:if>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">备注</label>
				</div>
				<div class="weui-cell__bd">
					<s:property value="race.remark"/>
				</div>
			</div>
	
		<br>
		<div class="weui-panel__ft">
			<div class="button_sp_area" align="center">
					<s:if test="#position.positionName != \"队员\"">
					<%-- 	<button  onclick="jionOn(${race.id})" class="weui-btn weui-btn_mini weui-btn_default">参赛情况</button> --%>
						<button id="btn1" onclick="edit(${race.id})" class="weui-btn weui-btn_mini weui-btn_primary">编辑</button>
						<s:if test="#position.positionName == \"队长\"">
						<button onclick="del(${race.id})" class="weui-btn weui-btn_mini weui-btn_warn">删除</button>
						</s:if>
					</s:if>
				<br />
				<br />
			</div>
		</div>
		
		 <s:if test="#position.positionName != \"队员\" && raceState != 1 && race.isRelease == 1">
		
			<div class="weui-tab">
				<div class="weui-tab__bd">
					<div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
					
						<div class="weui-panel__bd" style="margin-bottom:50px;">
							<div align="center">
								<h2 class="weui-panel__hd">
									<font color="#09BB07" size="3px">成绩录入&nbsp;&nbsp;&nbsp;&nbsp;</font>
								</h2>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd"><label class="weui-label">我方得分</label></div>
								<div class="weui-cell__bd">
									<input class="weui-input" type="number" id="myScore" value="${race.myScore}" placeholder="请输入我方得分">
								</div>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">对手得分</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" type="number" id="opScore" value="${race.opScore}" placeholder="请输入对手得分"/>
								</div>
							</div>
							
							<div class="weui-cell"><button class="weui-btn weui-btn_primary" onclick="inputResult();">提交</button></div>
						</div>
					
						<div class="weui-cells weui-cells_form">
							<!-- <div class="weui-cell">
								<div class="weui-cell__hd"><label>队员成绩统计</label></div>
							</div> -->
							<div class="weui-panel__bd" style="margin-bottom:50px;">
								<s:if test="signList !=null && signList.size()>0">
									<s:iterator value="signList" status="status" var="sl">
										<div class="weui-media-box weui-media-box_appmsg">
											<div class="weui-media-box__hd" style="padding:10px auto">
												<s:if test="(#sl[3] == null || #sl[3] == '' )&&( #sl[4] == null || #sl[4] == '' )">
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="${pageContext.request.contextPath}/common/img/default.png" alt="" />
												</s:if>
												<s:elseif test="#sl[3] == null || #sl[3] == ''">
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="<s:property value="%{#sl[4]}"/>" alt="" />
												</s:elseif>
												<s:else>
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="${pageContext.request.contextPath}/image/<s:property value="%{#sl[4]}"/>" alt="" />
												</s:else>
											</div>
											<div class="weui-media-box__bd">
												<h4 class="weui-media-box__title">
													<s:if test="#sl[2] == null">
														<s:property value="%{#sl[1]}" />
													</s:if>
													<s:else>
														<s:property value="%{#sl[2]}" />
													</s:else>
												</h4>
												<s:if test="scoreList !=null && scoreList.size()>0">
													<s:iterator value="scoreList" status="status" var="st">
														<s:if test="#sl[5] == #st.uid">
															<p style="display:inline">
																<img style="width:15px;" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/score.png">
																    <span id="getscore${st.uid}"><s:property value="#st.getScore"/></span>&nbsp;
																<img style="width:15px;" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/yellowcard.png">
																	<span id="yellowcard${st.uid}"><s:property value="#st.yellowCard" /></span>&nbsp;
																<img style="width:15px;" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/redcard.png">
																	<span id="redcard${st.uid}"><s:property value="#st.redCard" /></span>&nbsp;
															</p>
														</s:if>
													</s:iterator>
												</s:if>
											</div>
											<div class="weui-cell__hd">
												<button class="weui-btn weui-btn_mini weui-btn_primary" id="getscore" onclick='getscore(<s:property value="%{#sl[5]}"/>)' >进球</button>
												<button class="weui-btn weui-btn_mini weui-btn_warn" id="getcard" onclick='getcard(<s:property value="%{#sl[5]}"/>)'>吃牌</button>
											</div>
											
										</div>
									</s:iterator>
								</s:if>
								<s:else>
									<div class="weui-media-box" style="text-align: center;">
										暂无签到人员
									</div>
								</s:else>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		
		</s:if>
		<s:else>
			<s:if test="race.myScore != null  || race.opScore != null"> 
				<div class="weui-panel weui-panel_access">
					<!-- 录入比赛结果后展示 -->
					<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">比分</label>
							</div>
							<div class="weui-cell__bd">
								（我方）<s:property value="race.myScore"/>:（对手）<s:property value="race.opScore"/>
							</div>
					</div>
				</div>
				<div class="weui-panel weui-panel_access">
					<s:if test="scoreMemberList !=null && scoreMemberList.size()>0">
					<div align="center">
						<h2 class="weui-panel__hd">
							<font color="#09BB07" size="2.5px">进球球员统计&nbsp;&nbsp;&nbsp;&nbsp;</font>
						</h2>
					</div>
						<s:iterator value="scoreMemberList" status="status" var="sm">
							<div class="weui-cell">
								<div class="weui-cell__hd" style="clear:both">
									<label>
										<s:if test='#sm[1] != null && #sm[1] != ""'>
											<s:property value="%{#sm[1]}"/>
										</s:if>
										<s:else>
											<s:property value="%{#sm[0]}"/>
										</s:else>
									</label>
								</div>
								<div class="weui-cell__bd">
									<div style="float:right;">
										<img style="width:15px;" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/score.png">
										<s:property value="%{#sm[2]}"/>
									</div>
								</div>
							</div>
						</s:iterator>
					</s:if>
				</div>
			</s:if>
		</s:else>
		 
	</div>
		<script type="text/javascript">
			//加载数据回显
			$(function(){
				//比赛类型数据回显
				var type = $("#type").val();
				if(1 == type){
					$("#racetypeselect").hide();
			  		$("#racetype").text("训练赛");
			  	} else if (2 == type){
			  		$("#racetypeselect").hide();
			  		$("#racetype").text("友谊赛");
			  	} else if (3 == type){
			  		$("#racetype").text("联赛");
			  	}
			  	//回显是否签到
			  	if($("#isSign").val() == 1){
					$("#signType").text("需签到");
				} else {
					$("#signType").text("无需签到");
					$("#signtimeselect").hide();
				}
			  	//回显签到开始时间
			  	var sTime = $("#sTime").val();
				if(0 == sTime){
			  		$("#signtime").text("提前半小时");
			  	} else if (1 == sTime){
			  		$("#signtime").text("提前一小时");
			  	} else if(2== sTime){
			  		$("#signtime").text("提前两小时");
			  	}
			  	//校验删除按钮
				var endTimes = "${race.endTime}";
				var startTimes = "${race.startTime}";
				function getTime(){
				   var time = new Date();
				   var endTime = new Date(endTimes);
				   var startTime = new Date(startTimes);
				    if(time.getTime()>startTime.getTime()){
					   	$("#flag").val(false);//比赛已开始
					   	$("#btn1").remove();
				    }else{
				    	$("#flag").val(true);//比赛未开始
				    }
				}
			    setInterval(getTime,10);
			})
 				
 				//编辑
				function edit(id){
					window.location.href ="${pageContext.request.contextPath}/race/toEdit?teamId="+"${race.tid}"+"&id="+id;
				}
				//删除
				function del(id){
					$.confirm("确认删除？","消息确认框", function() {
						window.location.href ="${pageContext.request.contextPath}/race/Delete?teamId="+"${race.tid}"+"&id="+id;
					}, function() {
					  	//点击取消后的回调函数
				 	});
				}
				/* //参赛情况
				function jionOn(id){
					//录入结果
					//window.location.href ="${pageContext.request.contextPath}/score/scoreAdd?raceId="+id+"&teamId="+"${race.tid}";	
					window.location.href ="${pageContext.request.contextPath}/race/joinOn?teamId="+"${race.tid}"+"&id="+id;
				} */
				
				
				
				
			//点击提交	
			var teamId = ${race.tid};
			var raceId = ${race.id};
			
			//录入结果
		 	function inputResult(){
		 		var myScore = $("#myScore").val();
		 		var opScore = $("#opScore").val();
		 		var reg = /^\d{1,3}$/;
		 		if((!reg.test(myScore)) || (!reg.test(opScore)) ){
					  $.toptip("得分请输入纯数字！", 'warning');
					  return;
				}
		        $.post("${pageContext.request.contextPath}/race/scoreAdd",{"myScore":myScore,"opScore":opScore,"id":raceId,"teamId":teamId},function(data){
					var json = $.parseJSON(data);
					var scoreAdd = json.data;
	        		if(scoreAdd) {
	        			$.toast("录入成功！");
	        		} else if(scoreAdd == 0){
	        			$.toast("我方得分和对手得分均不能为空！",'cancel');
	        		}
				});
		 	}
		 	
			function getscore(uid){
		        $.modal({
					title: "得失球",
					text: "该队员在本场比赛中为本球队",
					buttons: [
					  { text: "取消", onClick: function(){}},
					  { text: "失一球", onClick: function(){
							//点击确认后的回调函数
							$.post("${pageContext.request.contextPath}/score/scoreSave",{"getScore":-1,"raceId":raceId,"teamId":teamId,"uid":uid},function(data){
								var json = $.parseJSON(data);
								var uid = json.data;
				        		if(uid != null) {
				        			$("#getscore"+uid).text(parseInt($("#getscore"+uid).text())-1);
				        			//$("#myScore").val(parseInt($("#myScore").val())+1);
				        			$.toast("录入成功！");
				        		} else {
				        			$.toast("录入失败，请重新录入！",'cancel');
				        		}
							});
						 
						} },
					  { text: "进一球", onClick: function(){
							//点击确认后的回调函数
							$.post("${pageContext.request.contextPath}/score/scoreSave",{"getScore":1,"raceId":raceId,"teamId":teamId,"uid":uid},function(data){
								var json = $.parseJSON(data);
								var uid = json.data;
				        		if(uid != null) {
				        			$("#getscore"+uid).text(parseInt($("#getscore"+uid).text())+1);
				        			//$("#myScore").val(parseInt($("#myScore").val())+1);
				        			$.toast("录入成功！");
				        		} else {
				        			$.toast("录入失败，请重新录入！",'cancel');
				        		}
							});
						 
						} },
						
					  ]
			     });
			 }
			 
			function getcard(uid){
		        $.modal({
					title: "红黄牌",
					text: "该队员在本场比赛中不幸吃得一张",
					buttons: [
					  { text: "取消", onClick: function(){}},
					  { text: "黄牌", onClick: function(){
							//点击确认后的回调函数
							$.post("${pageContext.request.contextPath}/score/scoreSave",{"yellowCard":1,"raceId":raceId,"teamId":teamId,"uid":uid},function(data){
								var json = $.parseJSON(data);
								var uid = json.data;
				        		if(uid != null) {
				        			$("#yellowcard"+uid).text(parseInt($("#yellowcard"+uid).text())+1);
				        			$.toast("录入成功！");
				        		} else {
				        			$.toast("录入失败，请重新录入！",'cancel');
				        		}
							});
						} },
					  { text: "红牌", onClick: function(){ 
							$.post("${pageContext.request.contextPath}/score/scoreSave",{"redCard":1,"raceId":raceId,"teamId":teamId,"uid":uid},function(data){
								var json = $.parseJSON(data);
								var uid = json.data;
				        		if(uid != null) {
				        			$("#redcard"+uid).text(parseInt($("#redcard"+uid).text())+1);
				        			$.toast("录入成功！");
				        		} else {
				        			$.toast("录入失败，请重新录入！",'cancel');
				        		}
							});
						 }
					 },]
			     });
			 }
			 
		</script>
	</body>

</html>