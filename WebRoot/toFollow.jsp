<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<title>二维码关注</title>
	</head>
	<body>
		<div class="weui-msg__opr-area">
			<img width="100%" alt="" src="${pageContext.request.contextPath}/common/img/code.jpg" />
		</div>
		<div align="center">
			请长按图片识别二维码关注
		</div>
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