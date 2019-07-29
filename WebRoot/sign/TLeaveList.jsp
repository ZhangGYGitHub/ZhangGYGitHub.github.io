<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>代替队员请假</title>
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
					<div class="weui-cells weui-cells_checkbox">
						<s:if test="list.size()>0 && list != null">
							<s:iterator value="list" status="status" var="rl">
								<s:if test="#rl[6] != 1">
									<label class="weui-cell weui-check__label">
										<div class="weui-cell__hd" style="width:70%;">
											<s:if test="#rl[3] == null">
												<p>${rl[2]}</p>
											</s:if>
											<s:else>
												<p>${rl[3]}</p>
											</s:else>
										</div>
										<div class="weui-cell__hd" id="${rl[1]}">
											<s:if test="#rl[7] == null">
												<button onclick="changeType(${rl[1]},${rl[5]})" class="weui-btn weui-btn_mini weui-btn_primary">请&nbsp;&nbsp;&nbsp;假</button>
											</s:if>
											<s:else>
												<button class="weui-btn weui-btn_mini weui-btn_primary">已请假</button>
											</s:else>
										</div>
									</label>
								</s:if>
							</s:iterator>
						</s:if>
					  	<s:else>
					  		<label class="weui-cell weui-check__label">
					  			暂无需要请假人员
					  		</label>
					  	</s:else>
					</div>
					
					
					<script type="text/javascript">
						
						
						function changeType(uid,rid){
							var type = ${type};
							$.ajax({
								url:'sign/ajaxLeave.action?uid='+uid+'&rid ='+rid+'&type ='+type,
								type:'post',  
							    success: function (data) {
							    	var json = JSON.parse(data);
							    	if(json.data == true){
							    		$("#"+uid).empty();
							    		$("#"+uid).append("<button class='weui-btn weui-btn_mini weui-btn_primary'>已请假</button>");
							    	}else{
							    		$.toptip("网络异常，请刷新页面重试", 'warning');
							    	}
							    }
							});
						}
					</script>
		
	
	
				</div>
			</div>	
		</div>	
	</body>
</html>