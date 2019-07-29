<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>快捷注册</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/common/fonts/jquery.mobile-1.4.5.min.css" />
<style>
.img_head {
	width: 70px;
	height: 70px;
	border-radius: 50%;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>

<body>
	<form action="${pageContext.request.contextPath}/wxuser/userSave" method="post" id="myForm" onsubmit="return checkForm();">
		<input type="hidden" name="wxuser.openId" value="<s:property value='wxuser.openId'/>">
		<div align="center" style="margin-top: 20px;">
			<img id="img-1" src="<s:if test="wxuser.headPortraitNew == null || wxuser.headPortraitNew == ''">
							${pageContext.request.contextPath}/common/default.png
						</s:if>
						<s:else>
							<s:property value="wxuser.headPortraitNew"/>
						</s:else>"
				class="img_head" />
		</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">昵称</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" type="text" name="wxuser.nickname"
						value="<s:property value='wxuser.nickname'/>"
						placeholder="请输入昵称" id="nickname" maxlength="15">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">手机号</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" type="tel" name="wxuser.phone"
						placeholder="输入您的手机号" value="" pattern="[0-9]*" id="phone" maxlength="11">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">性别</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="sexButton" name="sexButton"
						type="button" style="text-align: left;">
					<input class="weui-input" id="sex" name="sex"
						type="hidden" style="text-align: left;">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">常踢位置</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="locationView"
						type="text" value="中锋">
					<input id="location" name="location" type="hidden" >
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">习惯用脚</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" name="habitfootButton" id="habitfootButton"
						type="button" value="" style="text-align: left;">
					<input class="weui-input" name="habitfoot" id="habitfoot"
						type="hidden" value="" style="text-align: left;">
				</div>
			</div>
		</div>
		<div class="weui-msg__opr-area">
			<p class="weui-btn-area">
				<input type="submit" id="confirm" value="快捷注册"
					class="weui-btn weui-btn_primary" />
			</p>
		</div>
		<div class="weui-msg__opr-area" align="center">
			<p class="weui-btn-area">
				用户注册即代表同意<a href="${pageContext.request.contextPath}/tiaokuan.html">《踢球么服务协议》</a>
			</p>
		</div>
	</form>
	<div class="weui-gallery" style="display: none" id="show-img">
		<span class="weui-gallery__img" style="background-image: url(
			<s:if test="wxuser.headPortraitNew == null || wxuser.headPortraitNew == ''">
				${pageContext.request.contextPath}/common/default.png
			</s:if>
			<s:else>
				<s:property value="wxuser.headPortraitNew"/>
			</s:else>
			);"></span>
	</div>
</body>
<script>
	$(function() {
	
    	$("#sexButton").val("男");
		$("#sex").val("男");
		$("#habitfootButton").val("右脚");
		$("#location").val("中锋");
		$("#locationView").select({
			title : "选择常踢位置",
			multi: true,
			max:3,
			items : ${locations}
		});
  	});
  	/*图片显示或者隐藏*/
  	$("#img-1").click(function(){
		$("#show-img").show();
	});
	$("#show-img").click(function(){
		$("#show-img").hide();
	});
  	
  	$("#sexButton").click(function(){
  		$.actions({
		  actions: [{
		    text: "男",
		    onClick: function() {
		      $("#sexButton").val("男");
		      $("#sex").val("男");
		    }
		  },{
		    text: "女",
		    onClick: function() {
		      $("#sexButton").val("女");
		      $("#sex").val("女");
		    }
		  }]
		});
  	});
  	$("#habitfootButton").click(function(){
  		$.actions({
		  actions: [{
		    text: "左脚",
		    onClick: function() {
		      $("#habitfootButton").val("左脚");
		      $("#habitfoot").val("左脚");
		    }
		  },{
		    text: "右脚",
		    onClick: function() {
		      $("#habitfootButton").val("右脚");
		      $("#habitfoot").val("右脚");
		    }
		  }]
		});
  	});
  	
  	$("#locationView").change(function(){
		$("#location").removeAttr("value");
		var locationView = $("#locationView").val();
		$("#location").val(locationView);
		if ($("#location").val().trim() == null || $("#location").val().trim() == '') {
			$.toptip('请至少选择一个常踢位置', 'warning');
		} else {
			// 用统计,的数量+1来表示选择的数量
			var str = locationView;
			var c = ","; // 要计算的字符
			var regex = new RegExp(c, 'g'); // 使用g表示整个字符串都要匹配
			var result = str.match(regex);
			var count = !result ? 0 : result.length;
			count = count + 1;
			if (count > 3) {
				$.toptip('最多只能选择3个常球位置', 'warning');
			}
		}
	});
	
	/***********焦点校验************/
	$("#nickname").blur(function() {
		if ($("#nickname").val().trim() == null || $("#nickname").val().trim() == '') {
			$.toptip('亲，昵称不能为空哦', 'warning');
		}
	});
	$("#phone").blur(function() {
		if ($("#phone").val().trim() == null || $("#phone").val().trim() == '') {
			$.toptip('亲，手机号不能为空哦', 'warning');
		} else {
			var reg = /^1[3|4|5|7|8][0-9]{9}$/;
			if (!reg.test($("#phone").val())) {
				$.toptip('亲，手机号不正确哦', 'warning');
			}
		}
	});
	/**********表单校验***********/
	function checkForm(){
		var flag = true;
		if ($("#nickname").val().trim() == null || $("#nickname").val().trim() == '') {
			$.toptip('亲，昵称不能为空哦', 'warning');
			flag = false;
		}
		if ($("#phone").val().trim() == null || $("#phone").val().trim() == '') {
			$.toptip('亲，手机号不能为空哦', 'warning');
			flag = false;
		} else {
			var reg = /^1[3|4|5|7|8][0-9]{9}$/;
			if (!reg.test($("#phone").val())) {
				$.toptip('亲，手机号不正确哦', 'warning');
				flag = false;
			}
		}
		if ($("#location").val().trim() == null || $("#location").val().trim() == '') {
			$.toptip('亲，请选择常踢位置哦', 'warning');
			flag = false;
		} else {
			// 用统计,的数量+1来表示选择的数量
			var str = $("#location").val();
			var c = ","; // 要计算的字符
			var regex = new RegExp(c, 'g'); // 使用g表示整个字符串都要匹配
			var result = str.match(regex);
			var count = !result ? 0 : result.length;
			count = count + 1;
			if (count > 3) {
				$.toptip('亲，常踢位置不要超过三个哦', 'warning');
				flag = false;
			}
		}
		return flag;
 	}
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
</html>