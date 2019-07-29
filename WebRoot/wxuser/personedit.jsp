<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<title>修改个人资料</title>
<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
<style type="text/css">
	.img_box {
		width: 90px;
		height: 90px;
	}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
</head>
<body>
	<form method="post" action="${pageContext.request.contextPath}/wxuser/userSave" id="myForm" enctype="multipart/form-data">
		<s:hidden name="wxuser.id"/>
		<s:hidden name="wxuser.openId"/>
		<s:hidden name="wxuser.unionId"/>
		<s:hidden name="wxuser.headPortrait"/>
		<s:hidden name="wxuser.headPortraitNew"/>
		<s:hidden name="wxuser.habitfoot"/>
		<s:hidden name="wxuser.likeNum"/>
		<s:hidden name="wxuser.createTime"/>
		<s:hidden name="wxuser.createUser"/>
		<s:hidden name="wxuser.isBind"/>
		<s:hidden name="wxuser.delState"/>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">昵称</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" name="wxuser.nickname" id="nickname" type="text" maxlength="15"
						style="border: hidden;" placeholder="请输入昵称" value="<s:property value='wxuser.nickname'/>">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">头像</label>
				</div>
				<div class="weui-cell__bd weui-uploader__bd">
					<div class="img_box">
						<img id="img0" onerror="${pageContext.request.contextPath}/common/img/default.png" 
						src="
							<s:if test="wxuser.headPortrait == null || wxuser.headPortrait == ''">
								<s:property value='wxuser.headPortraitNew'/>
							</s:if>
							<s:else>
								${pageContext.request.contextPath}/image/<s:property value='wxuser.headPortraitNew'/>
							</s:else>
							" class="img_box" />
						<input id="uploaderInput" name="upload" class="weui-uploader__input" type="file" accept="image/*"/>
					</div>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">手机号</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" name="wxuser.phone" type="tel" id="phone"
						pattern="[0-9]*" maxlength="11" placeholder="请输入手机号" value="<s:property value='wxuser.phone'/>">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label for="date" class="weui-label">生日</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" name="birthday" id="date"
						type="text" value="<s:date name='wxuser.birthday' format="yyyy-MM-dd" />">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">性别</label>
				</div>
				<div class="weui-cell__bd" id="show-actions-bg">
					<input class="weui-input" id="sexButton" name="sexButton"
						type="button" value="" style="text-align: left;">
					<input class="weui-input" id="sex" name="sex"
						type="hidden" value="" style="text-align: left;">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">身高</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" name="wxuser.height" type="number" id="height"
						pattern="[0-9]*" maxlength="3" value="<s:property value='wxuser.height'/>" style="border: hidden;" placeholder="请输入身高">
				</div>
				cm
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">体重</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" name="wxuser.weight" type="number" id="weight"
						pattern="[0-9]*" maxlength="3" value="<s:property value='wxuser.weight'/>" style="border: hidden;" placeholder="请输入体重">
				</div>
				kg
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
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">个性签名</label>
				</div>
				<div class="weui-cell__bd">
					<input class="weui-input" id="perAutograph" name="wxuser.perAutograph" type="text" maxlength="20"
						style="border: hidden;" value="<s:property value='wxuser.perAutograph'/>"
						placeholder="请输入个性签名">
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">个人简介</label>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<textarea class="weui-textarea" name="wxuser.synopsis" id="synopsis"
						placeholder="请填写个人简介,不超过200字" maxlength="200" rows="3"><s:property value='wxuser.synopsis'/></textarea>
					<div class="weui-textarea-counter">
						<span id="iNum">0</span>/200
					</div>
				</div>
			</div>
		</div>
		<div class="weui-msg__opr-area">
			<p class="weui-btn-area">
				<input id="confirm" type="button"
					class="weui-btn weui-btn_primary" onclick="checkForm();" value="确认">
			</p>
		</div>
	</form>
	<div id="time-container"></div>
	<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	<script>
		$(function() {
			FastClick.attach(document.body);
			$("#iNum").text($("#synopsis").val().length);
		});
		var usex = "${wxuser.sex}";
		if(usex == 1) {
			$("#sex").val("男");
			$("#sexButton").val("男");
		} else {
			$("#sex").val("女");
			$("#sexButton").val("女");
		}
		$("#sexButton").click(function(){
			$.actions({
			  actions: [{
			    text: "男",
			    onClick: function() {
			      $("#sex").val("男");
			      $("#sexButton").val("男");
			    }
			  },{
			    text: "女",
			    onClick: function() {
			      $("#sex").val("女");
			      $("#sexButton").val("女");
			    }
			  }]
			});
		});
		var ulocation = "${myLocation}";
		$("#location").val(ulocation);
		$("#locationView").val(ulocation);
		$("#locationView").select({
			title : "选择常踢位置",
			multi: true,
			max:3,
			items : ${locationTotal}
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
					$.toptip('亲，常踢位置不要超过三个哦', 'warning');
				}
			}
			
		});
		var uhabitfoot = "${wxuser.habitfoot}";
		if(uhabitfoot == 1) {
			$("#habitfoot").val("左脚");
			$("#habitfootButton").val("左脚");
		} else if (uhabitfoot == 2) {
			$("#habitfoot").val("右脚");
			$("#habitfootButton").val("右脚");
		}
		$("#habitfootButton").click(function(){
			$.actions({
			  actions: [{
			    text: "左脚",
			    onClick: function() {
			      $("#habitfoot").val("左脚");
			      $("#habitfootButton").val("左脚");
			    }
			  },{
			    text: "右脚",
			    onClick: function() {
			      $("#habitfoot").val("右脚");
			      $("#habitfootButton").val("右脚");
			    }
			  }]
			});
		});
		$("#date").calendar({
			onChange : function(p, values, displayValues) {
				
			}
		});
		/* $("#confirm").click(function() {
			$.confirm("确认修改信息？", "消息确认框", function() {
				//点击确认后的回调函数
				$("#myForm").submit();
				// $.toast("操作成功");
			}, function() {
				//点击取消后的回调函数
			});
		}); */
		/******************文本框计数********************/
		$("#synopsis").keyup(function(){
			var iNum = $("#synopsis").val().length;
			if(iNum <= 200){
				$("#iNum").text(iNum);
			} else{
				$("#synopsis").val($("#synopsis").val().substring(0, 200));
			}
		});
		/*********************图片预览********************/
		$("#uploaderInput").change(function(){
			var objUrl = getObjectURL(this.files[0]) ;
			if (objUrl) {
				$("#img0").removeAttr("src");
				$("#img0").attr("src", objUrl) ;
			}
		}) ;
		function getObjectURL(file) {
			var url = null ; 
			if (window.createObjectURL!=undefined) { // basic
				url = window.createObjectURL(file) ;
			} else if (window.URL!=undefined) { // mozilla(firefox)
				url = window.URL.createObjectURL(file) ;
			} else if (window.webkitURL!=undefined) { // webkit or chrome
				url = window.webkitURL.createObjectURL(file) ;
			}
			return url ;
		}
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
		$("#height").blur(function() {
			if ($("#height").val().trim() == null || $("#height").val().trim() == '') {
				$.toptip('亲，身高不能为空哦', 'warning');
			} else if (parseInt($("#height").val().trim()) > 200 || parseInt($("#height").val().trim()) < 100) {
				$.toptip('亲，您的身高有些不合理哦', 'warning');
			}
		});
		$("#weight").blur(function() {
			if ($("#weight").val().trim() == null || $("#weight").val().trim() == '') {
				$.toptip('亲，您的体重不能为空哦', 'warning');
			} else if (parseInt($("#weight").val().trim()) > 150 || parseInt($("#weight").val().trim()) < 40) {
				$.toptip('亲，您的体重有些不合理哦', 'warning');
			}
		});
		$("#perAutograph").blur(function() {
			if ($("#perAutograph").val().trim().length > 25) {
				$.toptip('亲，换个短点的个性签名吧', 'warning');
			}
		});
		$("#synopsis").blur(function() {
			if ($("#synopsis").val().trim().length > 200) {
				$.toptip('亲，您的个人简介过长啦', 'warning');
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
			if ($("#height").val().trim() == null || $("#height").val().trim() == '') {
				$.toptip('亲，您的身高有些不合理哦', 'warning');
				flag = false;
			} else if (parseInt($("#height").val().trim()) > 200 || parseInt($("#height").val().trim()) < 100) {
				$.toptip('亲，您的身高有些不合理哦', 'warning');
				flag = false;
			}
			if ($("#weight").val().trim() == null || $("#weight").val().trim() == '') {
				$.toptip('亲，您的体重有些不合理哦', 'warning');
				flag = false;
			} else if (parseInt($("#weight").val().trim()) > 150 || parseInt($("#weight").val().trim()) < 40) {
				$.toptip('亲，您的体重有些不合理哦', 'warning');
				flag = false;
			}
			if ($("#perAutograph").val().trim().length > 25) {
				$.toptip('亲，换个短点的个性签名吧', 'warning');
				flag = false;
			}
			if ($("#synopsis").val().trim().length > 200) {
				$.toptip('亲，您的个人简介过长啦', 'warning');
				flag = false;
			}
			if ($("#location").val().trim() == null || $("#location").val().trim() == '') {
				$.toptip('请至少选择一个常踢位置', 'warning');
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
			
			if (flag) {
				$("#myForm").submit();
			}
		}
	</script>
</body>

</html>