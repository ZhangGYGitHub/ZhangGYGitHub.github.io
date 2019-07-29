<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<s:if test="teaminfo.id != null">
			<title>编辑球队</title>
		</s:if>
		<s:else>
			<title>创建球队</title>
		</s:else>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/city-picker.min.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
	</head>
	<body>
		<div class="weui-cells weui-cells_form">
			<form id="myForm" action="${pageContext.request.contextPath}/team/teaminfoSave" method="post"> 
				<s:hidden name="teaminfo.id"/>
				<s:hidden name="teaminfo.captainId"/>
				<s:hidden name="teaminfo.createTime"/>
				<s:hidden name="teaminfo.createUser"/>
				<s:hidden name="teaminfo.modifyTime"/>
				<s:hidden name="teaminfo.modifyUser"/>
				<input name="teaminfo.isOpen" id="isOpen" value="${teaminfo.isOpen}" type="hidden">
				<input name="teaminfo.province" id="province" value="${teaminfo.province}" type="hidden">
				<input name="teaminfo.city" id="city" value="${teaminfo.city}" type="hidden">
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">球队名称</label>
					</div>
					<div class="weui-cell__bd">
						<input id="name" class="weui-input" name="teaminfo.name" reg1="^.{2,16}$" desc="球队名称2~16位任意字符" value="${teaminfo.name}" maxlength="16" type="text" placeholder="2~16位任意字符">
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">球队宣言</label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input" name="teaminfo.teamDeclaration" reg1="^.{2,12}$" desc="球队宣言2~12位任意字符" value="${teaminfo.teamDeclaration}" maxlength="12" type="text" placeholder="2~12位任意字符">
					</div>
				</div>
				<div class="weui-cell weui-cell_switch">
				<div class="weui-cell__bd">是否公开</div>
				<div class="weui-cell__ft">
					//公开：需要申请加入    不公开：只能分享加入
					<input class="weui-switch" type="checkbox" id="isPublic" checked="checked">
				</div>
			</div>
			<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">球队简介</label>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea class="weui-textarea" name="teaminfo.introduce" id="introduce" reg1="^.{0,200}$" desc="球队简介200个任意字符以内" placeholder="请填写球队介绍,不超过200字" maxlength="200" rows="3">${teaminfo.introduce}</textarea>
						<div class="weui-textarea-counter"><span id="iNum">0</span>/200</div>
					</div>
				</div>
				
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">所在地址</label>
					</div>
					<div class="weui-cell__bd">
						<input type="text" id='city-picker' style="border: hidden; font-size: inherit;" />
					</div>
				</div>
				<script>
					$("#city-picker").cityPicker({
						showDistrict: false
					});
				</script>
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">赞助商</label>
					</div>
					<div class="weui-cell__bd">
						<input class="weui-input" name="teaminfo.sponsor" value="${teaminfo.sponsor}" reg1="^.{0,50}$" desc="赞助商50个任意字符以内" maxlength="50"  type="text" placeholder="50个任意字符以内">
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">规章制度</label>
					</div>
				</div>
				<div class="weui-cell">
					<div class="weui-cell__bd">
						<textarea class="weui-textarea" id="regulations" name="teaminfo.regulations" reg1="^.{0,500}$" desc="规章制度500个任意字符以内" placeholder="请填写规章制度,不超过500字" maxlength="500" rows="3">${teaminfo.regulations}</textarea>
						<div class="weui-textarea-counter"><span id="rNum">0</span>/500</div>
					</div>
				</div>
				<input id="newName" name="teaminfo.iconNewName" value="${teaminfo.iconNewName}"  type="hidden">
				<input id="oldName" name="teaminfo.iconOldName" value="${teaminfo.iconOldName}"  type="hidden">
			</form>
			<form action="${pageContext.request.contextPath}/team/ajaxUploadPic" id="fileForm" method="post" enctype="multipart/form-data">
				<div class="weui-cells weui-cells_form">
					<div class="weui-cell">
						<div class="weui-cell__bd">
							<div class="weui-uploader">
								<div class="weui-uploader__hd">
									<p class="weui-uploader__title">队徽上传</p>
									<div id="num" class="weui-uploader__info">0/1</div>
								</div>
								<div class="weui-uploader__bd" id="boxDiv">
									<div class="weui-uploader__input-box">
										<input id="uploaderInput" class="weui-uploader__input" name="upload" type="file" accept="image/*">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<script type="text/javascript">
				$("#uploaderInput").change(function(){
					$.showLoading("正在上传中...");
					 var options ={
							success:function(data) {
								var json = $.parseJSON(data);
								var obj = json.data;
								//{"success":true,"message":"","data":{"newName":"1.jpg","oldName":"8c659a30-38de-4e8e-a820-a75d214dc8c6.jpg","success":true}}
								if(obj.success){
									$("#newName").val(obj.newName);
									$("#oldName").val(obj.oldName);
									//先移除第一个子元素
									$("#boxDiv img").remove();
									//再添加到最前面
									$("#boxDiv").prepend("<img class='weui-uploader__file' src='${pageContext.request.contextPath}/image/"+obj.newName+"'>");
									$("#num").empty();
									$("#num").append("1/1");
								} else {
									$.toast("上传失败",'cancel');
								}
								setTimeout(function() {
									  $.hideLoading();
								 }, 1)//1毫秒后直接消失
							}
						}
						$("#fileForm").ajaxSubmit(options);
				 });
			</script>
			<div class="weui-msg__opr-area">
					<p class="weui-btn-area">
						<a href="javascript:;" id="confirm" class="weui-btn weui-btn_primary">提交</a>
					</p>
				</div>
			</div>
	<script type="text/javascript">
		$(function(){
			//页面加载
			//回显地址
			if($("#province").val() == '' && $("#city").val() == ''){
				//添加  默认地址
				$("#city-picker").val("河北省 石家庄市");
			} else {
				//修改
				$("#city-picker").val($("#province").val()+" "+ $("#city").val());
			}
			//回显是否公开
			if($("#isOpen").val() == 1){
				//只能分享
				$("#isPublic").removeAttr("checked");
			} else {
				$("#isPublic").attr("checked","checked");
			}
			//回显计数
			$("#iNum").text($("#introduce").val().length);
			$("#rNum").text($("#regulations").val().length);
			//回显队徽和计数
			var newName = $("#newName").val();
			if(newName != ''){
				//追加到开头
				$("#boxDiv").prepend("<img class='weui-uploader__file' src='${pageContext.request.contextPath}/image/"+newName+"'>");
				$("#num").empty();
				$("#num").append("1/1");
			}
			
		});
	</script>
	<script type="text/javascript">
		var flag = true;
		//校验同一个人不能创建相同的球队
		var id = '${teaminfo.id}';//为了区分是编辑还是添加
		$("#name").blur(function(){
			flag = false;
			var name = $("#name").val();
			if(name.length >=2 && name.length <= 16){
				$.post("${pageContext.request.contextPath}/team/checkTeamName",{"teaminfo.name":name,"teaminfo.id":id},function(data){
					var json = JSON.parse(data);
					if(json.data){
						$.toast("您已经创建了同名的球队,请不要重复创建","text");
						$("#name").focus();//获取焦点
						flag = false;
						return;
					} else {
						flag = true;
					}
				});
			} else {
				$.toptip("球队名称2~16位任意字符", 'warning');
				$("#name").focus();//获取焦点
				flag = false;
			}
			
		});
		
		$("#confirm").click(function(){
			//************数据校验****************	//
			var isOk = true;
			$("#myForm").find("[reg1],[desc]").each(function(){
			var a=$(this);
			var reg = new RegExp(a.attr("reg1"));
			var objValue = a.val();
			if(!reg.test(objValue)){
					var tip = a.attr("desc").toString();
				  		$.toptip(tip, 'warning');
				  		a.focus();//获取焦点
				  		isOk = false;
						return;
					}
				});
			//************数据校验****************	//
		  	//点击确认后的回调函数
		  	//是否公开
		  	if($("#isPublic").is(":checked")) {
				$("#isOpen").val(0);//需要申请加入
			} else {
				$("#isOpen").val(1);//只能分享加入
			}
		  	var location = $("#city-picker").val();
		  	var arr = location.split(' ');
		  	$("#province").val(arr[0]);//省份
		  	$("#city").val(arr[1]);//城市
		  	if(flag && isOk){
		  		var options ={
		        	success:function(data) {
		        		var json = $.parseJSON(data);
		        		if(json.data){
		        			//跳转不增加历史记录的页面
		        			fnUrlReplace("${pageContext.request.contextPath}/wxuser/userDetail");
						} else {
							$.toast("保存失败",'cancel');
							$('#confirm').bind("click"); 
						}
		        	}
		        }
		        $("#myForm").ajaxSubmit(options);
 		  		//一旦提交,解除click绑定  
		  		$('#confirm').unbind("click"); 
		  	}
		  	
		});
		
		$("#introduce").keyup(function(){
			var iNum = $("#introduce").val().length;
			if(iNum <= 200){
				$("#iNum").text(iNum);
			} else{
				$("#introduce").val($("#introduce").val().substring(0, 200));
			}
		});
		$("#regulations").keyup(function(){
			var rNum = $("#regulations").val().length;
			if(rNum <= 500){
				$("#rNum").text(rNum);
			} else{
				$("#regulations").val($("#regulations").val().substring(0, 500));
			}
		});
	
	</script>
	
	</body>

</html>