<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<%@ taglib prefix="se" uri="/WEB-INF/security.tld"%>
<!DOCTYPE html>
<html>
<head>
	
	<s:if test="id == null">
		<title>创建联赛</title>
	</s:if>
	<s:else>
		<title>编辑联赛</title>
	</s:else>
	<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
	<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
</head>

	<body>
		<form action="leagueSave" id="mainForm" method="post">
			<s:hidden name="league.id" />
			<s:hidden name="league.createTime" />
			<s:hidden name="league.creator" />
			<s:hidden name="league.delState" />
			<s:hidden name="league.teamNum" />

			<div class="weui-cells weui-cells_form">
				<div class="weui-cell">
					<div class="weui-cell__hd"><label class="weui-label">联赛名称</label></div>
					<div class="weui-cell__bd">
						<input id="LeagueName" name="league.name" value="<s:property value='league.name'/>" class="weui-input my-valide" type="text" reg1="^.{2,30}$" desc="联赛名称2-30个任意字符，不能为空" placeholder="请输入名称" tip="已有同名联赛">
					</div>
				</div>
				<div class="weui-cell weui-cell_switch">
					<div class="weui-cell__hd"><label class="weui-label">积分方式</label></div>
				    <div class="weui-cell__bd">一般(3/1/0)</div>
				    <div class="weui-cell__ft">
				      <input id="scoreTypeValue" type="text" name="league.scoreType" hidden="hidden" value="0"/>
				    </div>
  				</div>
				<div id="score_cal" hidden="hidden" style="margin:0 0.5em">
					<div class="weui-cell">
						<div class="weui-cell__hd"><label class="weui-label">胜</label></div>
						<div class="weui-cell__bd">
							<input id="win" class="weui-input" name="league.winScore" value="3" type="number" placeholder="请输入胜分" reg1="^(\d{1,3})|(-\d{1,3})$" desc="请输入合适的分数">
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd"><label class="weui-label">平</label></div>
						<div class="weui-cell__bd">
							<input id="draw" class="weui-input" name="league.loseScore" value="1" type="number" placeholder="请输入负分" reg1="^(\d{1,3})|(-\d{1,3})$" desc="请输入合适的分数">
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd"><label class="weui-label">负</label></div>
						<div class="weui-cell__bd">
							<input id="lose" class="weui-input" name="league.drawScore" value="0" type="number" placeholder="请输入平分" reg1="^(\d{1,3})|(-\d{1,3})$" desc="请输入合适的分数">
						</div>
					</div>
				</div>
				<script type="text/javascript">
					//积分类型选择
					$(".weui-cell_switch").click(function(){
						var type = $(".weui-cell_switch .weui-cell__bd");
						var score = $("#scoreTypeValue");
						if(score.val() == 0){
							type.text("自定义");
							$("#score_cal").show();
							$(this).find("#scoreTypeValue").val(1);
						}else{
							type.text("一般(3/1/0)");
							$("#win").val(3);
							$("#draw").val(1);
							$("#lose").val(0);
							$("#score_cal").hide();
							$(this).find("#scoreTypeValue").val(0);
						}
					});	
				</script>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">上传附件</label>
				</div>
				<div class="weui-cell__bd">
					<input name="upload" id="file" value="" type="file">
				</div>
			</div>
			<div class="weui-cell">
			  <div class="weui-cell__bd">
			    <textarea class="weui-textarea" name="league.introduction" id="introduce" value="<s:property value='league.introduction'/>" placeholder="联赛简介" rows="3"></textarea>
			    <div class="weui-textarea-counter"><span id="iNum">0</span>/50</div>
			  </div>
			</div>
			<script type="text/javascript">
				$("#introduce").keyup(function(){
					var iNum = $("#introduce").val().length;
					if(iNum <= 50){
						$("#iNum").text(iNum);
					} else{
						$("#introduce").val($("#introduce").val().substring(0, 50));
					}
				});
			</script>
			<div class="weui-msg__opr-area">
				<p class="weui-btn-area">
					<input type="button" id="baseConfirm" class="weui-btn weui-btn_primary" value="提交" />
				</p>
			</div>
		</div> 
	</form>
		<script type="text/javascript">		
			var isOk = false;
			//联赛同名校验
			$(".my-valide").bind("input propertychange",function(){
				var input = $(this);
				if($.trim(this.value) == "") return;		
				$.post("ajaxValide" + this.id + "?name=" + $.trim(this.value), function(data){
				   	 if(JSON.parse(data).data != null){
				   	 	$.toptip(input.attr("tip"), "warning");
				   	 	input.focus();
				   	 }
				   	 isOk = true;
				});
			});
			$("#baseConfirm").click(function(){
				//************数据校验****************	//
				$("#mainForm").find("[reg1],[desc]").each(function(){
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
				//post保存
			  	if(isOk){
			  		var options ={
			        	success:function(data){
			        		var json = $.parseJSON(data);
			        		if(json.data){
			        			fnUrlReplace("leagueList");
							}else{
								$.toast("保存失败",'cancel');
							}
							$.hideLoading("文件上传中");
						}
			        }
			        $("#mainForm").ajaxSubmit(options);
			        if($("#file").val() != "")
			        	$.showLoading("文件上传中");
			        else
			        	$.showLoading("保存中");
			  	}
			});
		</script>
</body>
</html>