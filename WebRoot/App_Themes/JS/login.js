
if (top.location !== self.location) {
	top.location = self.location;
}
function loginsubmit() {
	if ($("#username").val() == "" || $("#password").val() == "") {
		alert("请输入用户名、密码");
		return;
	} else {
		if ($("#login_hidden").val() == 0) {
			$("#login_hidden").val(1);
			$("#mylogin").submit();
		}
	}
}

