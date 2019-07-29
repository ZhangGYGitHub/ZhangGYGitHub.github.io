<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<!--[if lt IE 9]>
<script type="text/javascript" src="../App_Themes/H-ui/lib/html5.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/lib/respond.min.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css"
	href="../App_Themes/H-ui/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="../App_Themes/H-ui/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css"
	href="../App_Themes/H-ui/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="../App_Themes/H-ui/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css"
	href="../App_Themes/H-ui/static/h-ui.admin/skin/default/skin.css"
	id="skin" />
<link rel="stylesheet" type="text/css"
	href="../App_Themes/H-ui/static/h-ui.admin/css/style.css" />


<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->

<title>管理员维护</title>
</head>


<body>
	<div class="page-container">
	<form class="form form-horizontal" id="myForm" action="saveManageUser"
		method="post">
		<s:hidden name="userinfo.id" />

		<div class="row">
			<label><font>*</font>用户名：</label> <span> 
			<s:if test="userinfo.id != null">
				<s:textfield
					name="userinfo.userName" id="userName" maxlength="28" readonly="true"/>
			</s:if>
			<s:else>
				<s:textfield
					name="userinfo.userName" id="userName" maxlength="28" />
			</s:else>			
			<div id="div-userName">
				<s:fielderror cssStyle="color:red;">
					<s:param>userName</s:param>
				</s:fielderror>
			</div> </span>

		</div>

		<div class="row">
			<label><font>*</font>真实姓名：</label> <span> <s:textfield
					name="userinfo.realName" id="realName" maxlength="22" />
				<div id="div-realName">
					<s:fielderror cssStyle="color:red;">
						<s:param>realName</s:param>
					</s:fielderror>
				</div> </span>

		</div>

		<div class="row">
			<label><font>*</font>电子邮箱：</label> <span> <s:textfield
					id="email" name="userinfo.email" maxlength="80"></s:textfield>
				<div id="div-email">
					<s:fielderror cssStyle="color:red;">
						<s:param>email</s:param>
					</s:fielderror>
				</div> </span>

		</div>

		<div class="row">
			<label><font>*</font>手机号码：</label> <span> <s:textfield
					id="mobilNo" name="userinfo.mobilNo" maxlength="15"></s:textfield>
				<div id="div-mobilNo">
					<s:fielderror cssStyle="color:red;">
						<s:param>mobilNo</s:param>
					</s:fielderror>
				</div> </span>

		</div>

		<div class="row">
			<label><font>*</font>单位：</label> <span> 
			<s:select
					name="userinfo.companyLevel1" id="companyLevel1" list="firstLevelList" 
					listKey="id" listValue="orgName">
			</s:select><br>
				<br> 
			<s:select name="userinfo.companyLevel2" id="companyLevel2"
					list="secondLevelList" listKey="id" onChange="zoneSecond(this.value);"
					listValue="orgName" headerKey="" headerValue="-请选择-">
			</s:select><br>
				<br> 
			<s:select name="userinfo.companyLevel3" id="companyLevel3"
					onChange="zoneThird(this.value);" list="thirdLevelList" listKey="id"
					listValue="orgName" headerKey="" headerValue="-请选择-">
			</s:select><br>
			<br> 
			<s:select name="userinfo.companyLevel4" id="companyLevel4"
					list="fourthLevelList" listKey="id"
					listValue="orgName" headerKey="" headerValue="-请选择-">
			</s:select>

				<div id="div-address">
					<s:fielderror cssStyle="color:red;">
						<s:param>address</s:param>
					</s:fielderror>
				</div> </span>

		</div>

		<div class="row">
			<label>性别：</label> <span>
				<s:if test="userinfo == null">
						<s:radio name="userinfo.sex"
										list="#{'0':'男','1':'女'}" listKey="key"	listValue="value" value="0" />
				</s:if>
				<s:else>
						<s:radio name="userinfo.sex"
										list="#{'0':'男','1':'女'}" listKey="key"	listValue="value" />
				</s:else>
				<div id="div-sex">
					<s:fielderror cssStyle="color:red;">
						<s:param>sex</s:param>
					</s:fielderror>
				</div> </span>
		</div>

		<div class="row">
			<label>固定电话：</label> <span> <s:textfield id="phone"
					name="userinfo.phone" maxlength="28"></s:textfield>
				<div id="div-phone">
					<s:fielderror cssStyle="color:red;">
						<s:param>phone</s:param>
					</s:fielderror>
				</div> </span>

		</div>

		<div class="row">
			<label>联系地址：</label> <span> <s:textfield id="address"
					name="userinfo.address" maxlength="48"></s:textfield>
				<div id="div-address">
					<s:fielderror cssStyle="color:red;">
						<s:param>address</s:param>
					</s:fielderror>
				</div> </span>

		</div>

		<div class="row">
			<label>账号未锁定：</label> <span> <s:checkbox
					name="accountNonLocked" cssStyle="width:18px;"></s:checkbox> </span>
		</div>
		<div class="row">
			<label class="kong"></label> <span>
				<button onClick="submitMe();" class='submit' type="button"
					id="saveinfo">保存</button>

				<button onClick="javascript:closeSelf();" type="button">返回</button>
			</span>
		</div>
		<s:token/>
	</form>
</div>
</body>

<script type="text/javascript"
	src="../App_Themes/H-ui/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="../App_Themes/H-ui/lib/layer/2.1/layer.js"></script>
<script type="text/javascript"
	src="../App_Themes/H-ui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="../App_Themes/H-ui/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript"
	src="../App_Themes/H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript"
	src="../App_Themes/H-ui/static/h-ui.admin/js/wo.js"></script>
<script type="text/javascript"
	src="../App_Themes/JS/ningXun-1.8.mini.js"></script>
<script type="text/javascript" src="../App_Themes/JS/layerShow.js"></script>

<script type="text/javascript">
function submitMe() { //提交表单
   if (check()) {
        document.getElementById('myForm').submit();
        document.getElementById('saveinfo').disabled = true;        
	}
}

function check() {
	
    var isOk = true;
    if (!submitCheck('字符', 'userName', 'div-userName', '用户名', 28, true)) {
        isOk = false;
    }
    if (!submitCheck('字符', 'realName', 'div-realName', '真实名', 22, true)) {
        isOk = false;
    }
    if (!submitCheck('邮箱', 'email', 'div-email', '电子邮箱', 80, true)) {
        isOk = false;
    }
    if (!submitCheck('手机', 'mobilNo', 'div-mobilNo', '手机号码', 11, true)) {
        isOk = false;
    }
    if (!submitCheck('固话', 'phone', 'div-phone', '固定电话', 28, false)) {
        isOk = false;
    }
    if (!submitCheck('字符', 'address', 'div-address', '联系地址', 48, false)) {
        isOk = false;
    }
	
    if (isOk) {
        return true;
    } else {
        return false;
    }
}



$().ready(function() {

    focusCheck('字符', 'userName', 'div-userName', '用户名', 28, true);
    focusCheck('字符', 'realName', 'div-realName', '真实名称', 22, true);
    focusCheck('邮箱', 'email', 'div-email', '电子邮箱', 80, true);
    focusCheck('手机', 'mobilNo', 'div-mobilNo', '手机号码', 11, true);
    focusCheck('字符', 'phone', 'div-phone', '固定电话', 28, false);
    focusCheck('字符', 'address', 'div-address', '联系地址', 48, false);
    
});

function zone(zoneId, selectValue) {
	var selectValueC = document.getElementById("companyLevel4");
	selectValueC.options.length = 0;
	var varItemC = new Option("-请选择-", "");
	selectValueC.options.add(varItemC);
	if (zoneId != "") {

		$.post("showManagerZoneList", {
			"zoneId" : zoneId
		}, function(data) {
			if (data != "") {
				var temp = new Array();
				temp = data.split(",");
				selectValue.options.length = 0;
				var varItem = new Option("-请选择-", "");
				selectValue.options.add(varItem);
				for ( var i = 0; i < temp.length / 2; i++) {

					var varItem = new Option(temp[2 * i + 1], temp[2 * i]);
					selectValue.options.add(varItem);
				}
			} else {
				selectValue.options.length = 0;
				var varItem = new Option("-请选择-", "");
				selectValue.options.add(varItem);
			}
		});
	} else {
		selectValue.options.length = 0;
		var varItem = new Option("-请选择-", "");
		selectValue.options.add(varItem);

	}
}

function zoneFirst(zoneId) {
	var selectValue = document.getElementById("companyLevel2");
	zone(zoneId, selectValue);
	var selectValueC = document.getElementById("companyLevel3");
	selectValueC.options.length = 0;
	var varItemC = new Option("-请选择-", "");
	selectValueC.options.add(varItemC);
}
function zoneSecond(zoneId) {
	var selectValue = document.getElementById("companyLevel3");
	zone(zoneId, selectValue);
}
function zoneThird(zoneId) {
	var selectValue = document.getElementById("companyLevel4");
	zone(zoneId, selectValue);
}
</script>

</html>
