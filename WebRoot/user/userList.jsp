<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<%@ taglib prefix="se" uri="/WEB-INF/security.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<!--[if lt IE 9]>
<script type="text/javascript" src="../App_Themes/H-ui/lib/html5.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/lib/respond.min.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/lib/icheck/icheck.css" />
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="../App_Themes/H-ui/static/h-ui.admin/css/style.css" />
<script type="text/javascript" src="../App_Themes/JS/ningXun-1.8.mini.js"></script>
<title>用户管理</title>
</head>
<body>
	<nav class="breadcrumb">用户管理</nav>
	<div class="page-container">

		<s:form id="myForm" action="showUserList" method="post" theme="simple" namespace="/user">
			<div class="text-c">
				<input type="text" name="userNameCX" value='${userNameCX}' id="userNameCX" placeholder="用户名" class="input-text" />
				<input type="text" name="realNameCX" value='${realNameCX}' id="realNameCX" placeholder="真实姓名" class="input-text" />
			<%--	单位：
				<s:select name="firstIdCX" id="firstIdId" list="firstLevelList" onChange="zoneFirst(this.value);" listKey="id" listValue="orgName" headerKey="" headerValue="-请选择-">
				</s:select> 
				<s:select name="secondIdCX" id="secondId" list="secondLevelList" listKey="id" onChange="zoneSecond(this.value);" listValue="orgName" headerKey="" headerValue="-请选择-">
				</s:select>
				<s:select name="thirdIdCX" id="thirdId" onChange="zoneThird(this.value);" list="thirdLevelList" listKey="id" listValue="orgName" headerKey="" headerValue="-请选择-">
				</s:select>
				<s:select name="fourthIdCX" id="fourthId" list="fourthLevelList" listKey="id" listValue="orgName" headerKey="" headerValue="-请选择-">
				</s:select>
			--%>
				<button class="btn btn-success" type="button" onclick="javascript:void(document.myForm.submit())">
					<i class="Hui-iconfont">&#xe665;</i> 查询
				</button>
			</div>

		</s:form>

		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l">
				<a class="btn btn-primary radius"  onclick="javascript:openLayer('用户维护', '<s:url action="showManageAdd"><s:param name="id" value="id" /></s:url>');" >
						<i class="Hui-iconfont">&#xe600;</i> 增加
					</a>
			</span>
			<span class="r">
				共有数据：<strong><s:property value="resultRows" /> </strong> 条
			</span>
		</div>


		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort">
				<tbody>
					<tr>
						<th width="50">序号</th>
						<th width="150">用户名</th>
						<th width="150">真实姓名</th>
						<th width="100">手机号</th>
						<th width="150">电子邮箱</th>
						<th>所在单位</th>
						<th width="300">操作</th>
					</tr>
					<s:if test="userList!=null && userList.size()>0">
						<s:iterator value="userList" status="td">
							<tr>
								<td>
									<s:property value="#td.index+1" />
								</td>
								<td>
									<s:property value="userName" />
								</td>
								<s:if test="realName.length()>=20">
									<td>
										<s:property value="realName.substring(0,20)" />
										...
									</td>
								</s:if>
								<s:else>
									<td>
										<s:property value="realName" />
									</td>
								</s:else>
								<td>
									<s:property value="mobilNo" />
								</td>
								<s:if test="email.length()>=50">
									<td>
										<s:property value="email.substring(0,50)" />
										...
									</td>
								</s:if>
								<s:else>
									<td>
										<s:property value="email" />
									</td>
								</s:else>
								<s:if test="companyLevelName.length()>=25">
									<td>
										<s:property value="companyLevelName.substring(0,25)" />
										...
									</td>
								</s:if>
								<s:else>
									<td>
										<s:property value="companyLevelName" />
									</td>
								</s:else>


								<td>
										<i class="Hui-iconfont">&#xe611;</i>									
										<a data-title="分配角色" _href="user/allotRoles?id=<s:property value="id"/>"  onclick="Hui_admin_tab(this)" href="javascript:;">分配角色</a>
										<a class="compile" onclick="javascript:openLayer('管理员维护', '<s:url action="showManageAdd"><s:param name="id" value="id" /></s:url>');" >编辑</a>
										<a class="strike" href="deleteManageUser?id=<s:property value="id"/>" onClick="return confirm('确定删除该管理员?')">删除</a>
										<i class="Hui-iconfont">&#xe605;</i>
										<a href="resetPassword?id=<s:property value="id"/>" onClick="return confirm('确定重置密码?')">密码重置</a>
										<i class="Hui-iconfont">&#xe665;</i>
										<a data-title="管理员详情" _href="user/seeUserDetails?id=<s:property value="id"/>" onclick="Hui_admin_tab(this)" href="javascript:;">查看</a>
								</td>
							</tr>
						</s:iterator>
					</s:if>
					<s:else>
						<tr>
							<td colspan="7" style="text-align: center;">暂无数据</td>
						</tr>
					</s:else>
			</table>

			<!-- 分页部分 -->
			<div class="pagination">
				<s:if test="userList!=null && userList.size()>0">
					<font>当前<s:property value="pageNo" />/<s:property value="pageCount" />页
					</font>
					<pg:pages pageCount="pageCount" pageNo="pageNo" theme="text">
						<!-- 参数传递 -->
						<s:param name="userNameCX" value="%{userNameCX}" />
						<s:param name="realNameCX" value="%{realNameCX}" />
						<s:param name="emailCX" value="%{emailCX}" />
						<s:param name="firstIdCX" value="%{firstIdCX}" />
						<s:param name="secondCX" value="%{secondCX}" />
						<s:param name="thirdIdCX" value="%{thirdIdCX}" />
						<s:param name="fourthIdCX" value="%{fourthIdCX}" />
					</pg:pages>
				</s:if>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="../App_Themes/H-ui/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/static/h-ui/js/H-ui.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/static/h-ui.admin/js/H-ui.admin.js"></script>
<script type="text/javascript" src="../App_Themes/H-ui/static/h-ui.admin/js/wo.js"></script>
<script type="text/javascript" src="../App_Themes/JS/layerShow.js"></script>
<script type="text/javascript" language="javascript">
	function zone(zoneId, selectValue) {
		var selectValueC = document.getElementById("fourthId");
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
		var selectValue = document.getElementById("secondId");
		zone(zoneId, selectValue);
		var selectValueC = document.getElementById("thirdId");
		selectValueC.options.length = 0;
		var varItemC = new Option("-请选择-", "");
		selectValueC.options.add(varItemC);
	}
	function zoneSecond(zoneId) {
		var selectValue = document.getElementById("thirdId");
		zone(zoneId, selectValue);
	}
	function zoneThird(zoneId) {
		var selectValue = document.getElementById("fourthId");
		zone(zoneId, selectValue);
	}
</script>

</html>