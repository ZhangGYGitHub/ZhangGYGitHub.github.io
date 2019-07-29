<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!doctype html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<title>错误页面</title>
		<style type="text/css">
body {
	padding-right: 0px;
	padding-left: 35px;
	padding-bottom: 0px;
	margin: 0px;
	font: 12px arial, helvetica, sans-serif;
	color: #333;
	padding-top: 35px
}

a {
	font-size: 9pt;
	color: #842b00;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: none
}

a:hover {
	font-size: 9pt;
	color: #0188d2;
	line-height: 16pt;
	font-family: "tahoma", "宋体";
	text-decoration: underline
}
.hidehr {
	display: none
}

.img404 {
	padding-right: 0px;
	padding-left: 0px;
	background: url(../errors/images/002.gif) no-repeat left top;
	float: left;
	padding-bottom: 0px;
	margin: 0px;
	width: 80px;
	padding-top: 0px;
	position: relative;
	height: 90px
}

h2 {
	padding-right: 0px;
	padding-left: 0px;
	font-size: 16px;
	float: left;
	padding-bottom: 25px;
	margin: 0px;
	width: 80%;
	line-height: 0;
	padding-top: 25px;
	border-bottom: #ccc 1px solid;
	position: relative
}

h3.wearesorry {
	padding-right: 0px;
	padding-left: 0px;
	font-weight: normal;
	font-size: 10px;
	left: 117px;
	padding-bottom: 0px;
	margin: 0px;
	color: #ccc;
	line-height: 10px;
	padding-top: 0px;
	position: absolute;
	top: 70px
}

.content {
	clear: both;
	padding-right: 0px;
	padding-left: 0px;
	font-size: 13px;
	left: 80px;
	float: left;
	padding-bottom: 0px;
	margin: 0px;
	width: 80%;
	line-height: 19px;
	padding-top: 0px;
	position: relative;
	top: -30px
}

.content ul {
	padding-right: 35px;
	padding-left: 35px;
	padding-bottom: 20px;
	margin: 0px;
	padding-top: 10px
}

.show12 ul {
	padding-right: 0px;
	padding-left: 0px;
	padding-bottom: 0px;
	margin: 0px 0px 0px 20px;
	padding-top: 0px;
	list-style-type: none
}

.show14 ul li {
	margin-bottom: 5px
}
</style>


	</head>

	<body>

		<div class=img404>
		</div>
		<h2> 
			抱歉，文件上传出现了问题 
		</h2>
		<h3 class=wearesorry>
			we're sorry ,file upload error...
		</h3>
		<div class=content>
			<font color="#666666">请尝试以下操作：</font>
			<ul>
				<li>
					<font color="#666666">确保您上传的不是0字节的文件! </font>
				</li>
				<li>
					<font color="#666666">上传文件的大小是否超过了最大限制! </font>
				</li>
				<li>
					<font color="#666666">上传文件的格式是否被允许! </font>
				</li>
				<!-- <li>
					<font color="#0000ff"><a href="javascript:history.back(-1)">返回上一页</a></font>
					<font color="#666666">尝试其他操作。 </font>
				</li> -->
				
					<s:actionmessage />
					<s:actionerror />
					<s:fielderror></s:fielderror>

			</ul>
		</div>
	</body>
</html>
