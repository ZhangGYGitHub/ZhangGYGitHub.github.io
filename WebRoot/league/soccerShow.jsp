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
	<div class="weui-cells weui-cells_form">
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">昵称</label>
			</div>
			<div class="weui-cell__bd">
				${u.nickname}
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
							${pageContext.request.contextPath}/image/<s:property value='u.headPortraitNew'/>
						</s:else>
						" class="img_box" />
				</div>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label for="date" class="weui-label">生日</label>
			</div>
			<div class="weui-cell__bd">
				<s:date name='u.birthday' format="yyyy-MM-dd" />
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">性别</label>
			</div>
			<div class="weui-cell__bd" id="show-actions-bg">
				<s:if test="u.sex == 1">
					男
				</s:if>
				<s:else>
					女
				</s:else>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">身高</label>
			</div>
			<div class="weui-cell__bd">
				${u.height}
			</div>
			cm
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">体重</label>
			</div>
			<div class="weui-cell__bd">
				${u.weight}
			</div>
			kg
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">红牌数</label>
			</div>
			<div class="weui-cell__bd">
				<s:if test="redNum == null">
					0
				</s:if>
				<s:else>
					${redNum}
				</s:else>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">黄牌数</label>
			</div>
			<div class="weui-cell__bd">
				<s:if test="yellowNum == null">
					0
				</s:if>
				<s:else>
					${yellowNum}
				</s:else>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">常踢位置</label>
			</div>
			<div class="weui-cell__bd">
				${myLocation}
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">习惯用脚</label>
			</div>
			<div class="weui-cell__bd">
				<s:if test="u.habitfoot == 1">
					左脚
				</s:if>
				<s:else>
					右脚
				</s:else>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">个性签名</label>
			</div>
			<div class="weui-cell__bd">
				<s:if test="u.getPerAutograph().length() == 0">
					暂无签名
				</s:if>
				<s:else>
					${u.perAutograph}
				</s:else>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__hd">
				<label class="weui-label">个人简介</label>
			</div>
		</div>
		<div class="weui-cell">
			<div class="weui-cell__bd">
				<s:if test="u.getSynopsis().length() == 0">
					&nbsp;&nbsp;&nbsp;暂无简介
				</s:if>
				<s:else>
					${u.synopsis}
				</s:else>
			</div>
		</div>
	</div>
	<div class="weui-msg__opr-area">
		<p class="weui-btn-area">
			<input id="confirm" type="button"
				class="weui-btn weui-btn_primary" onclick="history.back(-1)" value="返回">
		</p>
	</div>
	<div id="time-container"></div>
	<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
</body>

</html>