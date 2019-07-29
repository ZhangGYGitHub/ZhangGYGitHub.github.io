<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>物质</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>
	<body>
		<div class="weui-tab">
			<div class="weui-tab__bd">
				<div  id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
					<s:if test="#position.id != 4">
						<div class="weui-msg__opr-area">
							<p class="weui-btn-area">
								<a href="${pageContext.request.contextPath}/goods/addOrEditGoods.action?tid=${tid}" id="addgoods" class="weui-btn weui-btn_primary">添加物资</a>
							</p>
						</div>
					</s:if>
					<s:if test="list != null && list.size()>0">
						<s:iterator value="list" status="status" var="gl">
							<div class="weui-media-box weui-media-box_appmsg" id="${gl[0]}">
								<div class="weui-media-box__hd" style="padding:5px auto;width: 35%">
									<h4 class="weui-media-box__title">
										<!--物资名称  -->
										${gl[1]}
									</h4>
								</div>
								<div class="weui-media-box__bd" style="width: 40%;">
									<h4 class="weui-media-box__title">
										<!-- 物资数量和计量单位 -->
										${gl[2]}${gl[3]}
									</h4>
										
								</div>
								<div class="weui-cell__hd" style="width:25%;">
									<s:if test="#position.id != 4">
								    	<button onclick="change(${gl[0]},${gl[4]});" class="weui-btn weui-btn_mini weui-btn_default">操作</button>
								    </s:if>
								</div>
							</div>
						</s:iterator>
					</s:if>
					<s:else>
						<div class="weui-media-box" style="text-align: center;">
							暂无物资
						</div>
					</s:else>
					
					<script type="text/javascript">
						/************跳转到编辑页面***********/
						function edit(gid,tid){
							window.location.href = '${pageContext.request.contextPath}/goods/addOrEditGoods.action?gid ='+gid+ '&tid ='+tid ;
						} 
						/************ajax删除物资信息***********/
						function del(gid,tid){
							$.confirm("确认删除？","删除确认框", function() {
							$.ajax({
								url:'${pageContext.request.contextPath}/goods/deleteGoods.action?gid ='+gid+ '&tid ='+tid,
								type:'psot',
								success: function (data) {
									var obj = $.parseJSON(data);
									if(obj.data == true){
										$("#"+gid).remove();
										$.toast("删除成功");
									}else{
										$.toast("删除失败，请刷新页面重试", "cancel");
									}
							    },
							    error:function(){
							    	$.toast("删除失败，请刷新页面重试", "cancel");
							    }
							});
							}, function() {
							  	//点击取消后的回调函数
						  	});
						} 
						function change(gid,tid){
							
							$.actions({
								actions: [{
										text: "编辑",
										onClick: function() {
											edit(gid,tid);
										}
									},
									{
										text: "删除",
										onClick: function() {
											del(gid,tid);
										}
									}]
							});
						}
					</script>		
	
				</div>
			</div>	
		</div>	
	</body>
</html>