<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
	<head>
		<title>录入结果</title>
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
						<div class="weui-cell">
							<div class="weui-cell__hd"><label>队员成绩统计</label></div>
						</div>
						<div class="weui-panel__bd" style="margin-bottom:50px;">
							<s:if test="signList !=null&& signList.size()>0">
								<s:iterator value="signList" status="status" var="sl">
									<div class="weui-media-box weui-media-box_appmsg">
										<div class="weui-media-box__hd" style="padding:10px auto">
											<s:if test="#sl[3] == null && #sl[4] == null">
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
		<script type="text/javascript">
			//点击提交	
			var teamId = '${teamId}';
			var raceId = '${raceId}';
			
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