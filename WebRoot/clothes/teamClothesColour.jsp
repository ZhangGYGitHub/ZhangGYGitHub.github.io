<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>
	<head>
		<title>队服颜色</title>
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
				<div class="weui-msg__opr-area">
					<p class="weui-btn-area">
						<a id="addColour" class="weui-btn weui-btn_primary">添加队服颜色</a>
					</p>
				</div>
				
				<s:if test="#list != null && #list.size()>0">
	    			<s:iterator value="#list" status="status" var="c">
				  		<div id="div_${c.id}" class="weui-media-box weui-media-box_appmsg">
							<div class="weui-media-box__hd" style="padding:10px auto;width: 35%">
								<label>
									<s:if test="#c.clothesColour.length()>5">  
								    	<s:property value="#c.clothesColour.substring(0,5)"/>...  
								 	</s:if>  
								    <s:else>  
								   		${c.clothesColour}
								    </s:else>
								</label>
							</div>
							<div class="weui-media-box__bd">
							</div>
							<div class="weui-cell__hd" >
							    <button onclick="del(${c.id});" class="weui-btn weui-btn_mini weui-btn_warn">删除</button>
							</div>
						</div>
				    </s:iterator>
				</s:if>

			</div>
		</div>	
	</div>	
	<script>
		var tid = "${clothes.tid}";
		$("#addColour").click(function(){
			$.prompt("请输入队服颜色", function(text) {
			  //点击确认后的回调函数
			  //text 是用户输入的内容
				if(text.trim().length > 10){
				  $.toptip("请不要超过10个字符", 'warning');
				  return;
				 }
				$.post("${pageContext.request.contextPath}/clothes/clothesSave",{"clothes.clothesColour":text.trim(),"clothes.tid":tid},function(data){
					var json = $.parseJSON(data);
					var colourId = json.data;
	        		if(colourId != null) {
	        			if(text.trim().length > 5){
	        				text = text.trim().substring(0,5)+"...";
	    			  	}
	        			 //追加一个div  id为#tab1的div下的第二个div的前面
	    				var firstDiv = $("#tab1").find("div:eq(1)");
	        			var $div = '<div id="div_'+colourId+'" class="weui-media-box weui-media-box_appmsg">'
							+'<div class="weui-media-box__hd" style="padding:10px auto;width: 35%">'
							+'<label>'+text+'</label>'
							+'</div>'
							+'<div class="weui-media-box__bd">'
							+'</div>'
							+'<div class="weui-cell__hd" >'
							+'<button onclick="del('+colourId+');" class="weui-btn weui-btn_mini weui-btn_warn">删除</button>'
							+'</div>'
							+'</div>'; 
	        			 if(firstDiv.length>0){
	        				//存在该div则添加到最前面 
	    					firstDiv.before($div);
	        			 } else {
	        				//不存在该div则追加
	    					$("#tab1").append($div);
	        			 }
	        		} else {
	        			$.toast("添加失败",'cancel');
	        		}
				});
			});
		});	
	
		function del(colourId){
			$.confirm("确认删除？", function() {
			  	//点击确认后的回调函数
			 	$.post("${pageContext.request.contextPath}/clothes/clothesDelete",{"clothes.id":colourId},function(data){
					var json = $.parseJSON(data);
	        		if(json.data) {
	        			//移除该div
					  	$("#div_"+colourId).remove(); 
	        		} else {
	        			$.toast("删除失败",'cancel');
	        		}
				});
			}, function() {
			  	//点击取消后的回调函数
		  	});
		}
		}
	</script>
</body>
</html>