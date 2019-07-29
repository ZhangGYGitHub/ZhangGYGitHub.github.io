<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>分享邀请</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/city-picker.min.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<style>
			.divcssleft{
				width:35%;
				float:left;
				color: #000000;
				text-align:left;
				font-size:15px;
				
			}
			.divcssright{
				width:65%;
				float:right;
				text-align: left;
				color: #000000;
				font-size:15px;
			}
		</style>
	</head>
	<body>
		<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg" >
			<div class="weui-media-box__hd" style="padding:20px auto">
				<s:if test="#obj[5] == null || #obj[5] == ''">  
			 		<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/common/img/default.png">
			 	</s:if>  
			    <s:else>  
			    	<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/image/${obj[5]}">
			    </s:else>
			</div>
			<div class="weui-media-box__bd">
				<div>
					<div class="divcssleft">创建时间</div>
					<div class="divcssright"><s:date name="%{#obj[1]}" format="yyyy-MM-dd" /></div>
				</div>
				<div>
					<div class="divcssleft">球队队长</div>
					<div class="divcssright">
						<s:if test="#obj[2].length()>10">  
				    		<s:property value="#obj[2].substring(0,10)"/>...  
					 	</s:if>  
					    <s:else>  
					   		<s:property value="#obj[2]"/>
					    </s:else>
					</div>
				</div>
				<div>
					<div class="divcssleft">球队宣言</div>
					<div class="divcssright"><s:property value="#obj[3]"/></div>
				</div>
				
			</div>
			
		</a>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell__bd">
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">球队简介</label>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<s:if test="#obj[4] == null || #obj[4] == ''">  
					 		暂无球队简介
					 	</s:if>  
					    <s:else>  
					    	<s:property value="#obj[4]"/>
					    </s:else>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-msg__opr-area">
			<p class="weui-btn-area">
				<s:if test="#position.positionName == \"队长\"">
					<!-- 队长邀请，直接加入球队 -->
					<a href="javascript:;" id="confirm" class="weui-btn weui-btn_primary">确认邀请</a>
				</s:if>
				<s:else>
					<a href="javascript:;" id="apply" class="weui-btn weui-btn_primary">申请加入</a>
				</s:else>
			</p>
		</div>
		<script type="text/javascript">
			//申请
			var tid = '${obj[0]}';
			$("#confirm").click(function(){
				fnUrlReplace("${pageContext.request.contextPath}/team/confirmJoin?teaminfo.id="+tid);
			});
			$("#apply").click(function(){
				$.confirm("是否申请加入该球队?", function() {
			  		//点击确认后的回调函数
			  		$.post("${pageContext.request.contextPath}/apply/applyTeam",{"tid":tid},function(data){
			  			var json = JSON.parse(data);
			  			if(json.data == 0){
			  				//0代表成功处理
			  				$.toast("申请已提交,请耐心等待...","text");
			  				//申请成功进入系统首页
			  				//跳转不增加历史记录的页面
		        			fnUrlReplace("${pageContext.request.contextPath}/home");
			  			} else if (json.data == 1){
			  				//1代表处理失败
			  				$.toast("申请失败！",'cancel');
			  			} else if (json.data == 2){
			  				//2代表是队长	
			  				$.toast("请不要申请自己的球队！","text");
			  				fnUrlReplace("${pageContext.request.contextPath}/home");
			  			} else if (json.data == 3){
			  				//3代表代处理或者已同意
			  				$.toast("请不要重复申请！","text");
			  				fnUrlReplace("${pageContext.request.contextPath}/home");
			  			} 
			  		});
			  		
			  	}, function() {
			  		//点击取消后的回调函数
			  	});
			});
		</script>
		<script type="text/javascript">
			//防止返回历史记录
		    pushHistory();
		    function pushHistory() {
		        var state = {
		            title: "title",
		            url: "#"    };
		        window.history.pushState(state, "title", "#");
		    };
		    window.onpopstate = function() {
		        wx.closeWindow();
		    };
		</script>
	</body>

</html>