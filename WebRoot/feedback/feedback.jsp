<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
	<head>
		<title>评价</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/xing/jb51.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/xing/jb51.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
		<style>
		.my-lable{
			display:inline-block;
			float:left;
			border-radius:7px;
			text-align:center;
			padding:2px;
			margin:0.5em;
			color:white;
			font-weight:bold;
			text-shadow: 1px 1px 1px gray;
			padding:10px 1em 10px 1em;
		}
		.my-line{
			height:2px;
			background:#ddd;
			margin-top:1em;
		}
		.placeholder{
			height:3em;
		}
		</style>
	</head>
<body>
	<div class="weui-tab"  id="text">
		<div class="weui-tab__bd">
			<form action="${pageContext.request.contextPath}/feedback/feedBackSave" method="post" id="myForm">
				<input type="hidden" name="tid" value="<s:property value='tid'/>">
				<input type="hidden" name="feedBack.trId" value="<s:property value='trId'/>">
				<input type="hidden" name="feedBack.type" value="<s:property value='type'/>">
				<input type="hidden" name="score" id="score" value="0">
				<div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
					<div class="weui-cells weui-cells_form" id="div-form">
						<s:if test="score == null || score == ''">
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">评价分数</label>
								</div>
								<div id="xzw_starSys" 
									style="font:normal 12px/2em '微软雅黑';padding:0;margin:0; padding-left: 15px;">
								<div id="xzw_starBox">
									<ul class="star" id="star">
										<li onclick="chooseNum(1)">
											<a href="javascript:void(0)" 
											title="1" class="one-star">1
											</a>
										</li>
										<li onclick="chooseNum(2)">
											<a href="javascript:void(0)" 
											title="2" class="two-stars">2
											</a>
										</li>
										<li>
											<a href="javascript:void(0)" onclick="chooseNum(3)" 
											title="3" class="three-stars">3
											</a>
										</li>
										<li>
											<a href="javascript:void(0)" onclick="chooseNum(4)" 
											title="4" class="four-stars">4
											</a>
										</li>
										<li>
											<a href="javascript:void(0)" onclick="chooseNum(5)" 
											title="5" class="five-stars">5
											</a>
										</li>
									</ul>
									<div class="current-rating" id="showb"></div>
								</div>
								</div>
							</div>
						</s:if>
						<s:else>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">追评</label>
								</div>
							</div>
						</s:else>
						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">
									<s:if test="score == null || score == ''">
										评价
									</s:if>
									<s:else>
										追评
									</s:else>
									内容
								</label>
							</div>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__bd">
								<textarea class="weui-textarea" name="feedBack.comment" id="comment" 
								placeholder="请填写评价,不超过200字" rows="3" maxlength="200"></textarea>
								<div class="weui-textarea-counter">
									<span id="iNum">0</span>/200
								</div>
							</div>
						</div>
					</div>
					<%--评论提示 --%>
					<div class="weui-cell">
						<div class="weui-cell__hd" style="border-bottom:1px solid #ddd;width:100%">
							<label class="weui-label" >小标签</label>
						</div>
					</div>
					<div style="margin-top:0;margin-left:1.5em;">
						<s:iterator value="lables" status="status" var="lable">
							<s:if test="(#status.index+1)%2 != 0 && (#status.index+1)%3 != 0">
								<div class="my-lable" style="background:url(../common/img/lable1.png);background-size:100% 100%;">${lable.content}</div>
							</s:if>
							<s:elseif test="(#status.index+1)%2 == 0">
								<div class="my-lable" style="background:url(../common/img/lable2.png);background-size:100% 100%;">${lable.content}</div>
							</s:elseif>
							<s:else>
								<div class="my-lable" style="background:url(../common/img/lable3.png);background-size:100% 100%;">${lable.content}</div>
							</s:else>
						</s:iterator>
						<div style="clear:both"></div>
					</div>
					<script>
						//添加标签
						$(".my-lable").click(function(){
							var area = $(".weui-textarea");
							var num = $("#iNum");
							area.val(area.val() + $(this).text() + " ");
							num.text(parseInt(num.text()) + $(this).html().length + 1);
						});
					</script>
					<div class="my-line"></div>
					<div class="weui-msg__opr-area">
						<div class="placeholder"></div>
						<p class="weui-btn-area">
							<button type="button" onclick="checkForm();" id="confirm" class="weui-btn weui-btn_primary">提交</button>
							<!-- <input type="submit" id="confirm" class="weui-btn weui-btn_primary" value="提交" /> -->
						</p>
					</div>

					
			
			<script>
						 /*$("#confirm").click(function() {
							$.confirm("确认提交？", "消息确认框", function() {
								var flag = true;
								if ($("#comment").val().trim() == null || $("#comment").val().trim() == '') {
									$.toptip('亲，加点评论吧', 'warning');
									flag = false;
								} else {
									if ($("#comment").val().length > 200) {
										$.toptip('亲，您的评论过长了哦', 'warning');
										flag = false;
									}
								}
								if ($("#score").val().trim() == "0") {
									
									if ($("#div-form").children(":first").text().trim() == "追评") {
										flag = true;
									} else {
										$.toptip('亲，您还没有评价分数哦', 'warning');
										flag = false;
									}
								}
								return flag;
								//点击确认后的回调函数
								if (flag) {
									var options ={
							        	success:function(data) {
							        		var json = $.parseJSON(data);
							        		if(json.data){
							        			//跳转不增加历史记录的页面
							        			fnUrlReplace("${pageContext.request.contextPath}/home");
											} else {
												$.toast("保存失败",'cancel');
											}
							        	}
							        }
							        $("#myForm").ajaxSubmit(options);
								}
								// $.toast("操作成功");
							}, function() {
								//点击取消后的回调函数
								
							});
						}); */
						function chooseNum(num) {
							$("#score").removeAttr("value");
							$("#score").attr("value", num);
						}
						/******************文本框计数********************/
						$("#comment").keyup(function(){
							var iNum = $("#comment").val().length;
							if(iNum <= 200){
								$("#iNum").text(iNum);
							} else{
								$("#comment").val($("#comment").val().substring(0, 200));
							}
						});
						/****************焦点检验**********************/
						$("#comment").blur(function() {
							setTimeout(function(){
							if ($("#comment").val().trim() == null || $("#comment").val().trim() == '') {
								$.toptip('亲，加点评价吧', 'warning');
							} else {
								if ($("#comment").val().length > 200) {
									$.toptip('亲，评价过长了哦', 'warning');
								}
							}
							}, 100);
						});
						/****************表单检验**********************/
						function checkForm(){
							var flag = true;
							if ($("#score").val().trim() == "0") {
								if ($("#div-form").children(":first").text().trim() == "追评") {
									flag = true;
								} else {
									$.toptip('亲，您还没有评价分数哦', 'warning');
									flag = false;
								}
							}
							if ($("#comment").val().trim() == null || $("#comment").val().trim() == '') {
								$.toptip('亲，加点评论吧', 'warning');
								flag = false;
							} else {
								if ($("#comment").val().length > 200) {
									$.toptip('亲，您的评论过长了哦', 'warning');
									flag = false;
								}
							}
							if (flag) {
								//跳转不增加历史记录的页面
				        		fnUrlReplace("${pageContext.request.contextPath}/home");
				        		$("#myForm").submit();
				        		$("#confirm").attr("disabled", "disabled");
							}
						}
					</script>
				</div>
			</form>
		</div>	
	</div>	
</body>
</html>