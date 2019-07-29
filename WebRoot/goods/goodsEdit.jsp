<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<s:if test="id == null">
			<title>添加物资</title>
		</s:if>
		<s:else>
			<title>编辑物资</title>
		</s:else>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>
	<body>
		<div class="weui-tab">
			<div class="weui-tab__bd">
				<div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
					<form id="submitForm" action="${pageContext.request.contextPath}/goods/saveGoods.action?tid =${tid}" method="post">
						<input value="${goods.id}" type="hidden" name="goods.id" />
						<input value="${goods.unit}" id="myunit" type="hidden" name="goods.unit" />
						<div class="weui-cells weui-cells_form">
							
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<label class="weui-label">物资名称</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" maxlength="10" id="goodsName" name="goods.goodsName" value="<s:property value="goods.goodsName"/>" type="text" placeholder="请输入名称">
								</div>
							</div>
							
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<label class="weui-label">物资数量</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" id="number"  maxlength="5"
									 	name="goods.number" value="<s:property value="goods.number"/>" onkeyup="this.value=this.value.replace(/[^\d]/g,'') " onafterpaste="this.value=this.value.replace(/[^\d]/g,'') " type="text" placeholder="请输入数量">
								</div>
							</div>
							
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<label class="weui-label">计量单位</label>
								</div>
								<div class="weui-cell__bd">
									<s:if test="goods.unit == null || goods.unit == ''">
										<div class="weui-input" onclick="change()" id="unit"><p style="color:#B7B7B7;">请输入计量单位</p></div>
									</s:if>
									<s:else>
										<div class="weui-input" onclick="change()" id="unit"><s:property value="goods.unit"/></div>
									</s:else>
								</div>
							</div>
							
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">备注</label>
								</div>
							</div>
							
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<textarea class="weui-textarea" maxlength="200" id="remark" name="goods.remark"  placeholder="请填写备注,不超过200字" rows="3"><s:property value="goods.remark"/></textarea>
									<div class="weui-textarea-counter"><span id="rNum">0</span>/200</div>
								</div>
							</div>
							
						</div>
						<div class="weui-msg__opr-area">
							<p class="weui-btn-area">
								<input type="button" id="confirm" class="weui-btn weui-btn_primary" value="保存" />
							</p>
						</div>
	
					</form>
				</div>
			</div>	
		</div>	
		<script type="text/javascript">
			/***************文字计数********************/
			$(document).ready(function(){ 
				$("#rNum").text($("#remark").val().length);
			});
			$("#remark").keyup(function(){
				$("#rNum").text($("#remark").val().length);
			});
			/**************计量单位选择******************/
			function change(){
				$.actions({
				  actions: [{
				    text: "个",
				    onClick: function() {
				      $("#myunit").val("个");
				      $("#unit").empty();
				      $("#unit").append("个");
				    }
				  },{
				    text: "支",
				    onClick: function() {
				      $("#myunit").val("支");
				      $("#unit").empty();
				      $("#unit").append("支");
				    }
				  },{
				    text: "kg",
				    onClick: function() {
				      $("#myunit").val("kg");
				      $("#unit").empty();
				      $("#unit").append("kg");
				    }
				  },{
				    text: "件",
				    onClick: function() {
				      $("#myunit").val("件");
				      $("#unit").empty();
				      $("#unit").append("件");
				    }
				  },{
				    text: "根",
				    onClick: function() {
				      $("#myunit").val("根");
				      $("#unit").empty();
				      $("#unit").append("根");
				    }
				  },{
				    text: "m",
				    onClick: function() {
				      $("#myunit").val("m");
				      $("#unit").empty();
				      $("#unit").append("m");
				    }
				  },{
				    text: "kg",
				    onClick: function() {
				      $("#myunit").val("kg");
				      $("#unit").empty();
				      $("#unit").append("kg");
				    }
				  }]
				});
			}
			/*************前台校验********************/
			var click = 1;
			$("#confirm").click(function(){
				var flag = true;
			  	if($("#goodsName").val().length>10){
			  		flag = false;
			  		$.toptip('亲，您填写的物资名称不能超过10个字', 'warning');
			  	}
			  	if($("#goodsName").val().length == 0&&flag == true){
			  		flag = false;
			  		$.toptip('亲，您还未填写物资名称', 'warning');
			  	}
			  	if($("#number").val().length == 0&&flag == true){
			  		flag = false;
			  		$.toptip('亲，您还未填物资数量', 'warning');
			  	}
			  	if($("#number").val().length > 5&&flag == true){
			  		flag = false;
			  		$.toptip('亲，您填写的物资数量过多，超过五位', 'warning');
			  	}
			  	if($("#myunit").val().length == 0&&flag == true){
			  		flag = false;
			  		$.toptip('亲，您还未填写计量单位', 'warning');
			  	}
			  	if($("#myunit").val().length> 5&&flag == true){
			  		flag = false;
			  		$.toptip('亲，您填写的计量单位不能超过5个字符', 'warning');
			  	}
				if(flag == true && click == 1){
					click = 2;
					var options ={
		        		beforeSubmit: function(){  
		        			
		        	    },  
			        	success:function(data) {
			        		var flag = $.parseJSON(data);
			        		if(flag.data == true){
				        		flag = false;
				        		var url ="findGList?tid="+${tid};
				        		fnUrlReplace(url);
			        		}else{
			        			$.toast("保存失败，请刷新页面重试", "cancel");
			        		}
			        		//window.location.href = "${pageContext.request.contextPath}/goods/findGList.action?tid="+${tid}
			        	},
		        	    error: function () {
		        	    	$.toast("保存失败，请刷新页面重试", "cancel");
	                    }
			        }
			        $("#submitForm").ajaxSubmit(options); 
				  
				}
			});
		
		</script>
	</body>
</html>