<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<%@ taglib prefix="se" uri="/WEB-INF/security.tld"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
		<s:if test="id == null">
			<title>创建训练</title>
		</s:if>
		<s:else>
			<title>编辑训练</title>
		</s:else>
		
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
	</head>

	<body>
		<s:form action="saveTrain" namespace="/train" id="myForm" method="post" theme="simple" >
			<s:hidden name="train.id" />
			<s:hidden name="train.createTime" />
			<s:hidden name="train.createUser" />
			<s:hidden name="train.modifyTime" />
			<s:hidden name="train.modifyUser" />
			<s:hidden name="train.delState" />
			<input name="train.tid" value="${train.tid}"  type="hidden"/>
			<input id="teamId" name="teamId" value="${teamId}"  type="hidden"/>
			<input name="train.isRelease" value="${train.isRelease}" id="isRelease" type="hidden"/>
			<div class="weui-cells weui-cells_form">
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">训练名称</label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input" type="text" reg1="^.{1,20}$" desc="训练名称20个任意字符以内，不能为空" name="train.trainName" value="<s:property value="train.trainName"/>" placeholder="请输入训练名称">
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">开始时间</label>
					</div>
					<div class="weui-cell__bd">
						<input type="text" id='picker1' name="picker1" value="<s:date name="train.startTime"  format="yyyy-MM-dd HH:mm"/>"  style="border: hidden; font-size: inherit;" />
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">结束时间</label>
					</div>
					<div class="weui-cell__bd">
						<input type="text" id='picker2' name="picker2" value="<s:date name="train.endTime"  format="yyyy-MM-dd HH:mm"/>"  style="border: hidden; font-size: inherit;" />
					</div>
				</div>
			<script>
				$("#picker1").datetimePicker({
					title: '开始时间',
					min: "1970-01-01 00:00",
					max: "2050-12-31 23:59",
					onChange: function(picker, values, displayValues) {
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
						<div class="weui-cell__hd"><label class="weui-label">训练地点</label></div>
						<div class="weui-cell__bd">
							<input class="weui-input" type="text" reg1="^.{1,20}$" desc="训练地点20个任意字符以内，不能为空" name="train.trainPlace" value="<s:property value="train.trainPlace"/>" placeholder="请输入地点">
						</div>
					</div>
					<div class="weui-cell" id="clothcolor">
						<div class="weui-cell__hd">
							<label class="weui-label">服装颜色</label>
						</div>
						<div class="weui-cell__bd" id="colorselect">
							<input class="weui-input" type="text" id="clothescolour" name="train.clothescolour" value="${train.clothescolour}"/>
						</div>
					</div>
					<div class="weui-cell weui-cell_switch">
						<div class="weui-cell__bd">是否签到</div>
						<div class="weui-cell__ft">
							<input type="hidden" id="isSign" name="train.issign" value="${train.issign}"/>
							<input class="weui-switch" type="checkbox" id="signType"   checked="checked">
						</div>
					</div>
					<div id="sType" class="weui-cell" >
						<div class="weui-cell__hd">
							<label class="weui-label">开始签到</label>
						</div>
						<div class="weui-cell__bd">
								<input type="hidden" id="signTime" name="train.signTime" value="${train.signTime}"/>
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
					<div class="weui-cell__hd">
						<label class="weui-label">训练内容</label>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea id="remark" class="weui-textarea" reg1="^.{0,500}$" desc="训练内容500个任意字符以内" name="train.trainContent" placeholder="请填写备注,不超过500字" rows="3"><s:property value="train.trainContent"/></textarea>
						<div class="weui-textarea-counter"><span id="rNum">0</span>/500</div>
					</div>
				</div>
		<script type="text/javascript">
			//备注计数
			$("#remark").keyup(function(){
				var rNum = $("#remark").val().length;
				if(rNum <= 500){
					$("#rNum").text(rNum);
				} else{
					$("#remark").val($("#remark").val().substring(0, 500));
				}
			});
		</script>
			</div>	
				<div class="weui-msg__opr-area">
					<p class="weui-btn-area">
						<!-- <input  id="confirm" class="weui-btn weui-btn_primary" value="提交" /> -->
						<a href="javascript:;" id="confirm" class="weui-btn weui-btn_primary">提交</a>
					</p>
				</div>
		</s:form>
			<script type="text/javascript">
				//加载即回显
				$(function(){
				  	//回显是否签到
				  	if($("#isSign").val() == 1 || $("#isSign").val() == ""){
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
						type:'psot',  
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
										$.toptip("本球队队服颜色为空，请去球队管理页添加队服颜色", 'warning');
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
				$("#confirm").click(function(){
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
								return;
							}
						});
					flag1 = checkTime();
					//************数据校验****************	//
					if(flag && flag1){
			        $.modal({
						title: "确认提交",
						text: "",
						buttons: [
						  { text: "取消", onClick: function(){}},
						  /*{ text: "暂存", onClick: function(){
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
						        			fnUrlReplace("showTrainList?teamId="+teamId);
										} else {
											$.toast("保存失败",'cancel');
											$('#confirm').bind("click"); 
										}
						        	}
						        }
						        $("#myForm").ajaxSubmit(options);
						        //一旦提交,解除click绑定  
		  						$('#confirm').unbind("click"); 
							} },*/
						  { text: "立即发布", onClick: function(){ 
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
							  $("#isRelease").val(1);
								var options ={
						        	success:function(data) {
						        		var json = $.parseJSON(data);
						        		if(json.data){
						        			//跳转不增加历史记录的页面
						        			fnUrlReplace("showTrainList?teamId="+teamId);
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