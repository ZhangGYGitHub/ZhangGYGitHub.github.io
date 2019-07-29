<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>
	<head>
		<title>申请加入裁判组</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
			 		<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/common/img/scoreimg.jpg">
			</div>
			<div class="weui-media-box__bd">
				<div>
					<div class="divcssleft">联赛名称</div>
					<div class="divcssright">${obj[1]}</div>
				</div>
				<div>
					<div class="divcssleft">创建人</div>
					<div class="divcssright">
						<s:if test="#obj[2].length()>10">  
				    		<s:property value="#obj[2].substring(0,10)"/>...  
					 	</s:if>  
					    <s:else>  
					   		${obj[2]}
					    </s:else>
					</div>
				</div>
				<div>
					<div class="divcssleft">创建时间</div>
					<div class="divcssright">${obj[3].toString().substring(0,10)}</div>
				</div>
			</div>
		</a>
		<div class="weui-cells weui-cells_form">
				<div class="weui-cell__bd">
					<div class="weui-cell">
					   <div class="weui-cell__hd">
							<label class="weui-label">联赛简介</label>
					    </div>
					</div>
					<div class="weui-cell" >
						<div class="weui-cell__bd" style="margin:0.1em 0.2em">
					      <s:if test="#obj[4] == null || #obj[4] == ''">  
							 	暂无联赛简介
						   </s:if>  
						  <s:else>  
							   ${obj[4]}
						 </s:else>
					</div>
				</div>
			</div>
		</div>
		<div class="weui-msg__opr-area">
			<p class="weui-btn-area">
				<s:iterator>
					<a href="javascript:;" id="apply" class="weui-btn weui-btn_primary">申请加入</a>
				</s:iterator>
			</p>
		</div>
		<script type="text/javascript">
			//申请
			lid = ${league.id};
			$("#apply").click(function(){
				$.confirm("是否申请加入该联赛裁判组?", function() {
			  		//点击确认后的回调函数
			  		$.post("${pageContext.request.contextPath}/apply/applyReferee",{"lid":lid},function(data){
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
			  			} /*else if (json.data == 2){
			  				//2代表是队长	
			  				$.toast("请不要申请自己的球队！","text");
			  				fnUrlReplace("${pageContext.request.contextPath}/home");
			  			}*/
			  			 else if (json.data == 3){
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