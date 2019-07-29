<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>比分审核</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/city-picker.min.js" charset="utf-8"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/swiper.min.js"></script>
	<style>
		.my-btn{
			background:#1C86EE;
			color:white !important;
			-webkit-tap-highlight-color:rgba(6, 133, 252, 0.575);
		}
		.weui-toast {
			top:50%;
			left:65%;
		}
	</style>
	</head>
	<body>
		<div id="empty">
			<span id="lmid" hidden="hidden">${lmid}</span>
			<span id="submitter" hidden="hidden">${uid}</span>
			<div class="weui-cells weui-cells_form">
			  <div class="weui-cell">
			  	<h3 style="color:#09BB07">${t1}</h3>
			  </div>
			  <!-- 0名称 1进球数 2失球数 -->
			  <s:if test="oneExamine != null && oneExamine.size()>0">
	    			<s:iterator value="oneExamine" status="status" var="list">
	    				<div class="weui-cell my-item">
						    <div class="weui-cell__bd">
							    <s:if test="#list[0] == null">
							    	 <input class="weui-input" type="text" readonly value="无">
							    </s:if>
							    <s:else>
							    	<input class="weui-input" type="text" readonly value="${list[0]}">
							    </s:else>
						    </div>
						    <div class="weui-cell__bd">
						      <input class="weui-input" type="text" readonly value="${list[1]}">
						    </div>
						  </div>
	    			</s:iterator>
			   </s:if>
			 </div>
			 <div class="weui-cells weui-cells_form">
			  <div class="weui-cell">
			  	<h3 style="color:#09BB07">${t2}</h3>
			  </div>
			  <!-- 0名称 1进球数  -->
			  <s:if test="twoExamine != null && twoExamine.size()>0">
	    			<s:iterator value="twoExamine" status="status" var="list">
	    				<div class="weui-cell my-item">
						    <div class="weui-cell__bd">
						      <s:if test="#list[0] == null">
							    	 <input class="weui-input" type="text" readonly value="无">
							    </s:if>
							    <s:else>
							    	<input class="weui-input" type="text" readonly value="${list[0]}">
							    </s:else>
						    </div>
						    <div class="weui-cell__bd">
						      <input class="weui-input" type="text" readonly value="${list[1]}">
						    </div>
						  </div>
	    			</s:iterator>
			   </s:if>
			 </div>
			 <div class="weui-cells weui-cells_form">
			  <div class="weui-cell">
			  	<h3 style="color:red">红牌</h3>
			  </div>
			  <!-- 0名称 1牌数  -->
			  <s:if test="redList != null && redList.size()>0">
	    			<s:iterator value="redList" status="status" var="list">
	    				<div class="weui-cell my-item">
						    <div class="weui-cell__bd">
						      <s:if test="#list[0] == ''">
							    	 <input class="weui-input" type="text" readonly value="无">
							    </s:if>
							    <s:else>
							    	<input class="weui-input" type="text" readonly value="${list[0]}">
							    </s:else>
						    </div>
						    <div class="weui-cell__bd">
						      <input class="weui-input" type="text" readonly value="${list[1]}">
						    </div>
						  </div>
	    			</s:iterator>
			   </s:if>
			   <s:else>
			  		<div class="weui-cell my-item">
			   			<div class="weui-cell__bd">
						   <input class="weui-input" type="text" readonly value="无">
						 </div>
					</div>
			   </s:else>
			 </div>
			 <div class="weui-cells weui-cells_form">
			  <div class="weui-cell">
			  	<h3 style="color:orange">黄牌</h3>
			  </div>
			  <!-- 0名称 1牌数  -->
			  <s:if test="yellowList != null && yellowList.size()>0">
	    			<s:iterator value="yellowList" status="status" var="list">
	    				<div class="weui-cell my-item">
						    <div class="weui-cell__bd">
						      <s:if test="#list[0] == ''">
							    	 <input class="weui-input" type="text" readonly value="无">
							    </s:if>
							    <s:else>
							    	<input class="weui-input" type="text" readonly value="${list[0]}">
							    </s:else>
						    </div>
						    <div class="weui-cell__bd">
						      <input class="weui-input" type="text" readonly value="${list[1]}">
						    </div>
						  </div>
	    			</s:iterator>
			   </s:if>
			   <s:else>
			  		<div class="weui-cell my-item">
			   			<div class="weui-cell__bd">
						   <input class="weui-input" type="text" readonly value="无">
						 </div>
					</div>
			   </s:else>
			 </div>
			 <div class="weui-cell">
				 <div class="weui-cell__bd">
				   <textarea id="reason" class="weui-textarea" value="" placeholder="拒绝理由" rows="3"></textarea>
				   <div class="weui-textarea-counter"><span id="iNum">0</span>/30</div>
				 </div>
			 </div>
			<script type="text/javascript">
				var reason = $("#reason").parent().parent();
				reason.hide();
				$("#reason").keyup(function(){
					var iNum = $("#reason").val().length;
					if(iNum <= 30){
						$("#iNum").text(iNum);
					} else{
						$("#reason").val($("#reason").val().substring(0, 30));
					}
				});
			</script>
			 <div style="height:3em"></div>
			 <s:if test="isExamine == 0">
			 	<btton id="save" href="javascript:;" class="weui-btn weui-btn_primary" style="margin:10px">提交</btton>
			 	<btton id="no" href="javascript:;" class="weui-btn weui-btn_warn" style="margin:10px">拒绝</btton>
			 	<script>
			 	var isOk = true;
			 		$("#save").click(function(){
			 			$.confirm("您确定要提交吗?", "确认提交?", function() {
				 			if(isOk){
				 				isOk = false;
				 				$.showLoading("提交中");
				 				$.post("scoreExamine?isSubmit=0&lmid="+$("#lmid").text() + "&submitter="+$("#submitter").text(), function(data){
					 				isOk = true;
						 			if(JSON.parse(data).data != null){
						 				$.hideLoading();
						 				$.toast("已提交");
						 				fnUrlReplace("leagueList");
						 			}else{
										$.toast("提交失败",'cancel');
									}
				 			});
				 			}
				        }, function() {
				          //取消操作
				        });
			 		});
			 		
			 		$("#no").click(function(){
			 			if(reason.is(":hidden")){
			 				reason.show();
			 				$.toptip("若拒绝，请填写拒绝理由","success");
			 				$("#reason").focus();
			 			  	return;
			 			}
			 			if($("#reason").val() == ""){
			 			  	$.toptip("拒绝理由不能为空","warning");
			 			  	$("#reason").focus();
			 			  	return;
			 			  }
			 			$.confirm("您确定要拒绝吗?", "确认拒绝?", function() {  
				          $.post("refuseExamine?lmid="+$("#lmid").text() + "&submitter="+$("#submitter").text(), {reason:$("#reason").val()}, function(data){
				 			if(JSON.parse(data).data != null){
				 				$.toast("已拒绝");
				 				fnUrlReplace("leagueList");
				 			}else{
								$.toast("提交失败",'cancel');
							}
				 		});
				        }, function() {
				          //取消操作
				        });
			 		});
			 	
			 	</script>
			 </s:if>
			</div>
			<!-- 当已被审核后显示 -->
			<s:if test="oneExamine.size() == 0 && twoExamine.size() == 0">
				<script>
					$("#empty").remove();
					$("title").text("链接失效");
					$("body").append('<div style="text-align:center;margin:2em auto;color:gray";>该链接已失效</div>'+
										'<p style="text-align:center">可能原因:其他裁判已经审核完成<p>');
				</script>
			</s:if> 
	</body>
</html>