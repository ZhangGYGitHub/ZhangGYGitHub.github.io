<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<%@ taglib prefix="se" uri="/WEB-INF/security.tld"%>
<!DOCTYPE html>
<html>
<head>
	
	<s:if test="id == null">
		<title>创建比赛</title>
	</s:if>
	<s:else>
		<title>编辑比赛</title>
	</s:else>
	<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
	<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
</head>

	<body>
		<s:form action="saveRace" namespace="/race" id="myForm" method="post" theme="simple" >
			<s:hidden name="race.id" />
			<s:hidden name="race.createTime" />
			<s:hidden name="race.createUser" />
			<s:hidden name="race.modifyTime" />
			<s:hidden name="race.modifyUser" />
			<s:hidden name="race.delState" />
			<input name="race.tid" value="${race.tid}"  type="hidden"/>
			<input id="teamId" name="teamId" value="${teamId}"  type="hidden"/>
			<input name="race.oppoId" value="${race.oppoId}" id="oppoId" type="hidden"/>
			<input name="race.isRelease" value="${race.isRelease}" id="isRelease" type="hidden"/>
			<input name="race.leagueId" value="${race.leagueId}" id="leagueId" type="hidden"/>
			<input value="" id="colourChange" type="hidden"/>
			<input value="" id="leagueChange" type="hidden"/>
			<div class="weui-cells weui-cells_form">
				<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">比赛名称</label></div>
					<div class="weui-cell__bd">
						<input class="weui-input" type="text" reg1="^.{1,20}$" desc="比赛名称20个任意字符以内，不能为空" name="race.raceName" value="<s:property value="race.raceName"/>" placeholder="请输入比赛">
					</div>
				</div>
				<div class="weui-cell" id="racetypeselect">
					<div class="weui-cell__hd">
						<label class="weui-label">比赛类型</label>
					</div>
					<div class="weui-cell__bd">
						<input type="hidden" id="type" reg1="^.{1,}$" desc="比赛类型不能为空" name="race.type" value="${race.type}"/>
						<div id="racetype"  >
						</div>
					</div>
				</div>
				<script>
					//比赛类型			
					$("#racetypeselect").click(function(){
						$.actions({
							actions: [
								{
									text: "训练赛",
									onClick: function() {
										$("#league").hide();
										$("#leagues").val("");
										$('#racetype').empty();
										$('#racetype').append("训练赛");
										$('#type').val("1");
									}
								},
								{
									text: "友谊赛",
									onClick: function() {
										$("#league").hide();
										$("#leagues").val("");
										$('#racetype').empty();
										$('#racetype').append("友谊赛");
										$('#type').val("2");
			
									}
								},
								{
									text: "联赛",
									onClick: function() {
										$('#racetype').empty();
										$('#racetype').append("联赛");
										$('#type').val("3");
										$("#league").show();
									}
								}
							]
						});
					});			
				</script>
				<div class="weui-cell" id="league" style="display:none">
					<div class="weui-cell__hd"><label class="weui-label">联赛名称</label></div>
					<div class="weui-cell__bd" id="leagueselect">
						<input class="weui-input" type="text" id="leagues" name="league" value='<s:property value="race.league"/>'  readonly="readonly">
					</div>
				</div>
		
				 <div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">比赛对手</label></div>
					<div class="weui-cell__bd">
						<input class="weui-input"  id="opponent"  type="text" reg1="^.{1,20}$" desc="比赛对手20个任意字符以内，不能为空" name="race.opponent" value="${race.opponent}" placeholder="请输入对手手名称">
					</div>
					
					<div id="full" class='weui-popup__container'>
						<div class="weui-popup__overlay"></div>
				      	<div class="weui-popup__modal" style="background-color: #ffffff">
				      		<div id="searchResult" style="background-color: #ffffff">
				      			
				      		</div>
				       		<a href="javascript:;" class="weui-btn weui-btn_primary close-popup" style="margin: 15px;">关闭</a>
				      	</div>
				    </div>
					<div class="weui-cell__hd" style="margin-right: 4%" >
						<a href="javascript:;" style="margin-bottom: -6%" class="weui-btn weui-btn_mini weui-btn_primary open-popup" onclick="searchOpponent()" data-target="#full">搜索</a>
					</div>
					
					
					
				</div> 
				
			<script type="text/javascript">
			var teamId = ${teamId};
			//搜索球队弹出层
			 	function seleOpponent(opponentId,opponentName){
			 		//隐藏域设置值
			 		$("#opponent").val(opponentName);
			 		$("#oppoId").val(opponentId);
			 		$.closePopup();
			 	}
			 	
				function searchOpponent(){
					var lid = $("#leagueId").val();
					var searchResult = $("#searchResult");
					var leagueName = $("#leagues").val();
					if($("#type").val() == 3){
						
						//当输入对手名称是空，且为联赛，罗列出所有联赛队伍
						if(leagueName != ''){
						//联赛名称不为空
						 	$.post("${pageContext.request.contextPath}/race/searchTeam",{"teamName":$("#opponent").val(),"lid":lid,"teamId":teamId},function(data){
						 		searchResult.empty();
						 		var json = $.parseJSON(data);
						 		var list = json.data;
						 		if(list != null && list.length >0 ){
									  for(var i = 0;i<list.length;i++){
										 if(list[i][3] == null || list[i][3] == ''){
											 list[i][3] = "common/img/default.png";
										 } else {
											 list[i][3] = "image/"+list[i][3];
										 }
									 	searchResult.append('<div onclick="seleOpponent('+list[i][0]+',\''+list[i][1]+'\');" class="weui-media-box weui-media-box_appmsg">'
									 	 			+'<div class="weui-media-box__hd" style="padding:10px auto">'
									 	 			+'<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/'+list[i][3]+'">'
									 	 			+'</div>'
									 	 			+'<div class="weui-media-box__bd">'
									 	 			+'<h4 class="weui-media-box__title">'+list[i][1]+'</h4>'
									 	 			+'<p class="weui-media-box__desc" style="margin-top: 3%">队长:'+list[i][2]+'</p>'
									 	 			+'</div>');
									  }
						 		}else {
						 			//没有数据
						 			searchResult.empty();
						 			searchResult.append("<div class='weui-media-box__bd'><div class='weui-media-box' style='text-align: center;'><font color='red'>该球队尚未加入此联赛</font></div></div>");
						 			return;
						 		}
							 });
						}else{
							//联赛名称为空
							searchResult.empty();
							searchResult.append("<div class='weui-media-box__bd'><div class='weui-media-box' style='text-align: center;'><font color='red'>请先选择联赛名称</font></div></div>");
							return;
						}
						
					}else if($("#opponent").val().trim().length == 0 ){
						//不为联赛，且对手名称为空
						searchResult.empty();
						searchResult.append("<div class='weui-media-box__bd'><div class='weui-media-box' style='text-align: center;'><font color='red'>暂无该球队信息</font></div></div>");
						return;
					}else{
						//不为联赛，对手名称不为空
						$.post("${pageContext.request.contextPath}/race/searchTeam",{"teamName":$("#opponent").val(),"lid":lid,"teamId":teamId},function(data){
						 		searchResult.empty();
						 		var json = $.parseJSON(data);
						 		var list = json.data;
						 		if(list != null && list.length >0 ){
									  for(var i = 0;i<list.length;i++){
										 if(list[i][3] == null || list[i][3] == ''){
											 list[i][3] = "common/img/default.png";
										 } else {
											 list[i][3] = "image/"+list[i][3];
										 }
									 	searchResult.append('<div onclick="seleOpponent('+list[i][0]+',\''+list[i][1]+'\');" class="weui-media-box weui-media-box_appmsg">'
									 	 			+'<div class="weui-media-box__hd" style="padding:10px auto">'
									 	 			+'<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/'+list[i][3]+'">'
									 	 			+'</div>'
									 	 			+'<div class="weui-media-box__bd">'
									 	 			+'<h4 class="weui-media-box__title">'+list[i][1]+'</h4>'
									 	 			+'<p class="weui-media-box__desc" style="margin-top: 3%">队长:'+list[i][2]+'</p>'
									 	 			+'</div>');
									  }
						 		}else {
						 			//没有数据
						 			searchResult.append("<div class='weui-media-box__bd'><div class='weui-media-box' style='text-align: center;'><font color='red'>暂无该球队信息</font></div></div>");
						 		}
							 });
					}
				} 
 
			</script>
				
				<div class="weui-cell">
					<div class="weui-cell__hd"><label for="" class="weui-label">开始时间</label></div>
					<div class="weui-cell__bd">
						<input type="text" id='picker1' name="picker1" value="<s:date name="race.startTime"  format="yyyy-MM-dd HH:mm"/>" style="border: hidden; font-size: inherit;" />
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__hd"><label for="" class="weui-label">结束时间</label></div>
					<div class="weui-cell__bd">
						<input type="text" id='picker2' name="picker2" value="<s:date name="race.endTime"  format="yyyy-MM-dd HH:mm"/>"  style="border: hidden; font-size: inherit;" />
					</div>
				</div>
				<script>
					//当前时间拼接
				    //var nTime = new Date();
				    //var format = nTime.getFullYear() + "-" + (nTime.getMonth()+1) + "-" + nTime.getDate() + " " + (nTime.getHours()) + ":" + nTime.getMinutes();
					//时间控件
					$("#picker1").datetimePicker({
						title: '开始时间',
						min: "1970-01-01 00:00",
						max: "2050-12-31 23:59",
						onChange: function(picker, values, displayValues) {
						},
						onClose: function(){
							// 结束时间默认开始时间后的100分钟
							var start = $("#picker1").val();
							//兼容苹果手机
			    			var arr = start.split(/[- : \/]/);
       						var date = new Date(arr[0], arr[1]-1, arr[2], arr[3], arr[4], "00");
							// 获取当前分钟
							var min=date.getMinutes();
							// 设置当前时间+100分钟：把当前分钟数+100后的值重新设置为date对象的分钟数
							date.setMinutes(min+100);
							// 格式化时间处理
							var endTime = date.toLocaleString('chinese',{hour12:false});
							// 按日期和时间分割  2017/01/01 00:00:00
							var timeStrs = endTime.split(" ");
							var year = date.getFullYear();
							var month = date.getMonth()+1; 
				            var day = date.getDate(); 
				            // 拼接结束时间
				            var currentDate = year + "-";
				            if (month >= 10 ) { 
					            currentDate += month + "-"; 
					        } else { 
					        	currentDate += "0" + month + "-"; 
					        } 
				           if (day >= 10 ){ 
				        	   currentDate += day ; 
				           } else { 
				        	   currentDate += "0" + day ; 
				           }
				           // 格式  2017-01-01 00:00:00
				           currentDate += " "+timeStrs[1];
				           // 截取成需要的结束时间
						   var end = currentDate.substring(0,currentDate.length - 3);
						   //塞回结束时间
				           $("#picker2").val(end);
			            }
					});
					$("#picker2").datetimePicker({
						title: '结束时间',
						min: "1970-01-01 00:00",
						max: "2050-12-31 23:59",
						onChange: function(picker, values, displayValues) {
						}
					});
					
				</script>
				<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">比赛地点</label></div>
					<div class="weui-cell__bd">
						<input class="weui-input" type="text" reg1="^.{1,20}$" desc="比赛地点20个任意字符以内，不能为空" name="race.place" value="<s:property value="race.place"/>" placeholder="请输入地点">
					</div>
				</div>
				<div class="weui-cell" id="clothcolor">
					<div class="weui-cell__hd">
						<label class="weui-label">服装颜色</label>
					</div>
					<div class="weui-cell__bd" id="colorselect">
					<input class="weui-input" type="text" id="clothescolour" name="race.clothescolour" value="${race.clothescolour}" readonly="readonly"/>
					</div>
				</div>
				<div class="weui-cell weui-cell_switch">
				<div class="weui-cell__bd">是否签到</div>
				<div class="weui-cell__ft">
					<input type="hidden" id="isSign" name="race.signType" value="${race.signType}"/>
					<input class="weui-switch" type="checkbox" id="signType"   checked="checked">
				</div>
			</div>
			<div id="sType" class="weui-cell" >
				<div class="weui-cell__hd">
					<label class="weui-label">开始签到</label>
				</div>
				<div class="weui-cell__bd">
						<input type="hidden" id="signTime" name="race.signTime" value="${race.signTime}"/>
						<div id="sTime"  >
						</div>
					</div>
			</div>
			<script type="text/javascript">
				//是否签到
				$("#signType").click(function(){
					if($("#signType").is(":checked")) {
						$("#sType").show();
						$("#isSign").val(1);
					} else {
						$("#sType").hide();
						$("#signTime").val(null);
						$("#sTime").empty();
						$("#sTime").append("提前半小时");
						$("#isSign").val(0);
					}
				});
				//签到开始时间		
				$("#sType").click(function(){
					$.actions({
						actions: [
							{
								text: "提前半小时",
								onClick: function() {
									$('#sTime').empty();
									$('#sTime').append("提前半小时");
									$("#signTime").val(0);
								}
							},
							{
								text: "提前一小时",
								onClick: function() {
									$('#sTime').empty();
									$('#sTime').append("提前一小时");
									$("#signTime").val(1);
								}
							},
							{
								text: "提前两小时",
								onClick: function() {
									$('#sTime').empty();
									$('#sTime').append("提前两小时");
									$("#signTime").val(1);
								}
							}
						]
					});
				});			
			</script>
			
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">资金耗费</label></div>
				<div class="weui-cell__bd">
					<input id="waste" class="weui-input" type="number" reg1="^\d{0,10}(\.\d{1,2})?$" desc="资金耗费不能超过10位，最多保留两位小数" name="race.waste" value="<s:property value="race.waste"/>"  placeholder="请输入金额">
				</div>元
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">备注</label>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<textarea id="remark" class="weui-textarea" reg1="^.{0,200}$" desc="备注200个任意字符以内" name="race.remark"  placeholder="请填写备注,不超过200字" rows="3"><s:property value="race.remark"/></textarea>
					<div class="weui-textarea-counter"><span id="rNum">0</span>/200</div>
				</div>
			</div>
			<script type="text/javascript">
				//备注计数
				$("#remark").keyup(function(){
					var rNum = $("#remark").val().length;
					if(rNum <= 500){
						$("#rNum").text(rNum);
					} else{
						$("#remark").val($("#remark").val().substring(0, 200));
					}
				});
				//去除换行 
			/* 	$("#remark").keyup(function (){ 
					var str = --;
					str = str.replace(/<\/?.+?>/g,""); 
					str = str.replace(/[\r\n]/g, ""); 
					alert();
					$("#remarker").val(str);
				}); */
			</script>
		</div>
	</s:form>
		<div class="weui-msg__opr-area">
			<p class="weui-btn-area">
				<!-- <input type="button"  id="confirm" class="weui-btn weui-btn_primary" value="提交" /> -->
				<a href="javascript:;" id="confirm" class="weui-btn weui-btn_primary">提交</a>
			</p>
		</div>
		<script type="text/javascript">
			function search(){
				$("#searchInput").val($("#opponent").val());
			}
			
			$("#leagues").change(function(){
				var lid = $("#leagues").attr("data-values");
				$("#leagueId").val(lid);
				//alert(lid);
			});
			//加载即回显
			$(function(){
				//比赛类型数据回显
				var type = $("#type").val();
				if(1 == type){
			  		$("#racetype").text("训练赛");
			  	} else if (2 == type){
			  		$("#racetype").text("友谊赛");
			  	} else if (3== type){
			  		$("#racetype").text("联赛");
			  		$("#league").show();
			  	}else{
			  		$("#racetype").text("");
			  	}
			  	//回显是否签到
			  	if($("#isSign").val() == 1 || $("#isSign").val() ==""){
					$("#signType").attr("checked","checked");
				} else {
			  		$("#signType").removeAttr("checked");
					$("#sType").hide();
				}
			  	//回显签到开始时间
			  	var signTime = $("#signTime").val();
				if(2 == signTime){
			  		$("#sTime").text("提前两小时");
			  	} else if (1 == signTime){
			  		$("#sTime").text("提前一小时");
			  	} else{
			  		$("#sTime").text("提前半小时");
			  	}
			  	
			  	//回显计数
				$("#rNum").text($("#remark").val().length);
			 	//选择队服颜色
				var teamId = $("#teamId").val();
				$.ajax({
					url:'${pageContext.request.contextPath}/clothes/clothesAjax.action?teamId='+teamId,
					type:'post',  
		            dataType:'json', 
				    success: function (data) {
				    	if (data.data != null) {
							$("#clothescolour").select({
								title: "选择队服颜色",
								items: data.data.array,
							});
						}else{
							$("#colourChange").val();
								$("#colorselect").empty();
								$("#colorselect").append('<input class="weui-input" type="text" id="clothescolour" name="race.clothescolour" value="${race.clothescolour}" readonly="readonly"/>');
								$("#clothescolour").click(function(){
									$.toptip("还没有队服颜色，可以前往球队添加", 'warning');
								});
						}
				    }
				});
			 	//选择联赛
				var teamId = $("#teamId").val();
				$.ajax({
					url:'${pageContext.request.contextPath}/race/leagueAjax.action?teamId='+teamId,
					type:'post',  
		            dataType:'json', 
				    success: function (data) {
				    	if (data.data != null) {
							$("#leagues").select({
								title: "选择联赛",
								items: data.data.array,
							});
						}else{
							$("#leagueChange").val();
								$("#leagueselect").empty();
								$("#leagueselect").append('<input class="weui-input" type="text" id="leagues" name="race.league" value="<s:property value="race.league"/>" readonly="readonly">');
								$("#leagues").click(function(){
									$.toptip("本球队未加入任何处于开启状态的联赛", 'warning');
								});
						}
				    }
				});
				
			});
				    
				
			

			//校验时间
			function checkTime(){
		       var date1= $("#picker1").val();
		       var startLogTimeDate = new Date(Date.parse(date1.replace(/-/g, "/"))).getTime();
		       var date2=  $("#picker2").val(); 
		       var endLogTimeDate = new Date(Date.parse(date2.replace(/-/g, "/"))).getTime();
		       if(date1 == ""){
		       		$.toptip("开始时间不能为空", 'warning');
		  			return false;
	  			}else if(date2 == ""){
				 	$.toptip("结束时间不能为空", 'warning');
				  	return false;
				}else{
	       			if(endLogTimeDate>startLogTimeDate){
				       	//时间逻辑正确
			       		return true;
				     }else{
				       	//时间逻辑错误
			       		$.toptip("开始时间应小于结束时间", 'warning');
				  		return false;
				     }
	       		}
	       	} 
			
			//点击提交	
			var teamId = $("#teamId").val();
			$("#confirm").click(function() {
				var flag2 = true;
				//************数据校验****************	//
				var flag = true;
				$("#myForm").find("[reg1],[desc]").each(function(){
					var a=$(this);
					var reg = new RegExp(a.attr("reg1"));
					var objValue = a.val();
					if(!reg.test(objValue)){
						var tip = a.attr("desc").toString();
					  		$.toptip(tip, 'warning');
					  		a.focus();//获取焦点
					  		flag = false;
							return false;
					}
				});
				var leagueName = $("#leagues").val();
				if($("#type").val() == 3 && leagueName.trim().length == 0){
					$.toptip("联赛名称不能为空", 'warning');
					flag2 = false;
				}
				var flag1 = checkTime();
				//************数据校验****************	//
				if(flag && flag1 && flag2){
					$.modal({	
						title : "确认提交",
						text : "",
						buttons : [
								{text : "取消",onClick : function() {}},
								/*{text : "暂存",onClick : function() {
									//点击确认后的回调函数
									var $isSign =  $("#isSign");
									var $signTime = $("#signTime");
									if ($isSign.val() == "") {
										//签到类型默认设置为需签到
										$isSign.val(1);
									}
									if($isSign.val() == 1){
										if($signTime.val()==""){
											//开始签到默认设置为提前半小时
											$signTime.val(0);
										}
									}
									if($("#isRelease").val() != 1){
										$("#isRelease").val(0);
									}
									
									var options ={
							        	success:function(data) {
							        		var json = $.parseJSON(data);
							        		if(json.data){
							        			//跳转不增加历史记录的页面
							        			fnUrlReplace("showRaceList?teamId="+teamId);
											} else {
												$.toast("保存失败",'cancel');
												$('#confirm').bind("click"); 
											}
							        	}
							        }
							        $("#myForm").ajaxSubmit(options);
							        //一旦提交,解除click绑定  
							        $('#confirm').unbind("click"); 
									}},*/
								{text : "立即发布",onClick : function() {
										//发布事件
										var $isSign =  $("#isSign");
										var $signTime = $("#signTime");
										var lid = $("#leagues").attr("data-values");
										if ($isSign.val() == "") {
											//签到类型默认设置为需签到
											$isSign.val(1);
										}
										if($isSign.val() == 1){
											if($signTime.val()==""){
												//开始签到默认设置为提前半小时
												$signTime.val(0);
											}
										}
										$("#isRelease").val(1);
										var options ={
								        	success:function(data) {
								        		var json = $.parseJSON(data);
								        		if(json.data){
								        			//跳转不增加历史记录的页面
								        			fnUrlReplace("showRaceList?teamId="+teamId);
												} else {
													$.toast("保存失败",'cancel');
													$('#confirm').bind("click"); 
												}
								        	}
								        }
								        $("#myForm").ajaxSubmit(options);
								        //一旦提交,解除click绑定  
		  								$('#confirm').unbind("click"); 
									}
								},]
							});
					}
				});
		</script>
</body>
</html>