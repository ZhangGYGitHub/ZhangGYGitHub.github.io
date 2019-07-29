<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>赛情编辑</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/city-picker.min.js" charset="utf-8"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/swiper.min.js"></script>
	<style>
		.my-btn{
			background:#1C86EE;
			color:white !important;
			-webkit-tap-highlight-color:rgba(6, 133, 252, 0.575);
		}
		.weui-toast {
			top:50%;
			left:65%;
		}
	</style>
	</head>
	<body>
		<span id="lmid" hidden="hidden">${lmid}</span>
		<span id="uid" hidden="hidden">${uid}</span>
		<div class="weui-cells weui-cells_form" id="t1-form">
		<input id="teamOne" type="text" data-values="${match.teamOne}" hidden="hidden"/>
		  <div class="weui-cell">
		  	<h3 style="color:#09BB07" id="oneName">${t1}</h3>
		  </div>
		  <div class="weui-cell my-item">
		    <div class="weui-cell__bd">
		      <input class="weui-input my-form" type="text"  placeholder="球员" onclick="addOneName(this)">
		    </div>
		    <div class="weui-cell__bd">
		      <input class="weui-input my-form" type="number" placeholder="进球" onclick="addNum(this)">
		    </div>
		    <div class="weui-cell__hd" hidden="hidden">
				<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini" onclick="del(this)">删除</a>
			</div>
			<div class="weui-cell__hd">
				<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini my-btn" onclick="addOne(this)">新增</a>
			</div>
		  </div>
		 </div>
		 <div class="weui-cells weui-cells_form" id="t2-form">
		 	<input id="teamTwo" type="text" data-values="${match.teamTwo}" hidden="hidden"/>
			  <div class="weui-cell">
			  	<h3 style="color:#09BB07" id="twoName">${t2}</h3>
			  </div>
			  <div class="weui-cell my-item">
			    <div class="weui-cell__bd">
			      <input class="weui-input my-form" readonly type="text"  placeholder="球员" onclick="addTwoName(this)">
			    </div>
			    <div class="weui-cell__bd">
			      <input class="weui-input my-form" readonly type="number" placeholder="进球" onclick="addNum(this)">
			    </div>
			    <div class="weui-cell__hd" hidden="hidden">
				<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini" onclick="del(this)">删除</a>
				</div>
				<div class="weui-cell__hd">
					<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini my-btn" onclick="addTwo(this)">新增</a>
				</div>
			  </div>
		 </div>
		 <div class="weui-cells weui-cells_form" id="red-form">
			  <div class="weui-cell">
			  	<h3 style="color:red">红牌</h3>
			  </div>
			  <div class="weui-cell my-item">
			    <div class="weui-cell__bd">
			      <input class="weui-input my-form" readonly type="text"  placeholder="球员" onclick="addName(this)">
			    </div>
			    <div class="weui-cell__bd">
			      <input class="weui-input my-form" readonly type="number" placeholder="红牌数" onclick="addCardNum(this)">
			    </div>
			    <div class="weui-cell__hd" hidden="hidden">
				<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini" onclick="del(this)">删除</a>
				</div>
				<div class="weui-cell__hd">
					<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini my-btn" onclick="addRed(this)">新增</a>
				</div>
			  </div>
		 </div>
		 <div class="weui-cells weui-cells_form" id="yellow-form">
			  <div class="weui-cell">
			  	<h3 style="color:orange">黄牌</h3>
			  </div>
			  <div class="weui-cell my-item">
			    <div class="weui-cell__bd">
			      <input class="weui-input my-form" readonly type="text"  placeholder="球员" onclick="addName(this)">
			    </div>
			    <div class="weui-cell__bd">
			      <input class="weui-input my-form" readonly type="number" placeholder="黄牌数" onclick="addCardNum(this)">
			    </div>
			    <div class="weui-cell__hd" hidden="hidden">
				<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini" onclick="del(this)">删除</a>
				</div>
				<div class="weui-cell__hd">
					<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini my-btn" onclick="addYellow(this)">新增</a>
				</div>
			  </div>
		 </div>
		 <div style="height:3em"></div>
		 <btton id="save" href="javascript:;" class="weui-btn weui-btn_primary" style="margin:10px">提交</btton>
		 <script type="text/javascript">
		 	
		 	var oneName = $("#oneName").text();
		 	var twoName = $("#twoName").text();
		 	var uid = $("#uid").text();
		 	
		 	//提交审核
		 	var isOk = true;
		 	$("#save").click(function(){
		 		$("body").find(".my-form").each(function(){
		 			isOk = true;
		 			if(this.value == ""){
		 				$.toptip("您尚未填完所有项目", 'warning');
		 				isOk = false;
		 				return false;
		 			}
		 		});
		 		if(isOk){
		 			$.confirm("您确定要提交吗?", "确认提交?", function() {
				 	
			 			var t1 = "";
				 		var t2 = "";
				 		var yellow = "";
				 		var red = "";
			 			isOk = false;
				 			
				 		$.showLoading("提交中");
				 		
				 		$("#t1-form").find("input").each(function(){
				 			t1 += $(this).attr("data-values") + "#";
				 		});
				 		
				 		$("#t2-form").find("input").each(function(){
				 			t2 += $(this).attr("data-values") + "#";
				 		});
				 		
				 		$("#red-form").find("input").each(function(){
				 			red += $(this).attr("data-values") + "#";
				 		});
				 		
				 		$("#yellow-form").find("input").each(function(){
				 			yellow += $(this).attr("data-values") + "#";
				 		});
				 		
				 		$.post("subMatchToExam?lmid=" + $("#lmid").text(), {uid:uid, t1:t1, t2:t2, red:red, yellow:yellow, oneName:oneName, twoName:twoName}, function(data){
				 			isOk = true;
				 			$.hideLoading();
				 			if(JSON.parse(data).data != null){
				 				$.toast("已提交审核");
				 				fnUrlReplace("leagueScore?lmid=" + $("#lmid").val());
				 			}else{
								$.toast("保存失败",'cancel');
							}
				 		});
		        }, function() {
		          //取消操作
		        });
		 		}
		 	
		 	});
		 	
		 	var teamOne = [{title:"无",value:"0"}];
		 	var teamTwo = [{title:"无",value:"0"}];
		 	var allName = [{title:"无",value:"0"}];
		 	//sql 0id 1name
		 	$.post("ajaxFindMemberByTid?tid="+ $("#teamOne").attr("data-values"), function(data){
		 		 var json = JSON.parse(data);
				 var list = json.data;
				 for(var i = 0; i < list.length; i++){
				 	teamOne.push({title:list[i][1],value: list[i][0]});
				 	allName.push({title:list[i][1],value: list[i][0]});
				 }
				//下拉框预加载
		 		$("#t1-form").find(".my-form").click();
		 	});
		 	$.post("ajaxFindMemberByTid?tid="+ $("#teamTwo").attr("data-values"), function(data){
		 		var json = JSON.parse(data);
				 var list = json.data;
				 for(var i = 0; i < list.length; i++){
				 	teamTwo.push({title:list[i][1],value: list[i][0]});
				 	allName.push({title:list[i][1],value: list[i][0]});
				 }
				 //下拉框预加载
		 		$("#t2-form").find(".my-form").click();
		 		$("#yellow-form").find(".my-form").click();
		 		$("#red-form").find(".my-form").click();
		 	});
		 	//选t1名字
		 	function addOneName(name){
		 		$(name).select({
				  title: "选择球员",
				  items: teamOne
				});
				if(teamOne == null)
					alert("无")
		 	}
		 	//选t2名字
		 	function addTwoName(name){
		 		$(name).select({
				  title: "选择球员",
				  items: teamTwo
				});
		 	}
		 	//选t1,t2名字
		 	function addName(name){
		 		$(name).select({
				  title: "选择球员",
				  items: allName
				});
		 	}
		 	//选择进球数
		 	function addNum(num){
		 		$(num).select({
				  title: "数目",
				  items: ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				  		 "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				  		 "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"]
				});
		 	}
		 	//选择吃牌数
		 	function addCardNum(num){
		 		$(num).select({
				  title: "牌数",
				  items: ["0", "1", "2", "3", "4", "5"]
				});
		 	}
		 	//队一添加
		 	function addOne(btn){
		 		$(btn).parent().prev().show();
		 		$(btn).parent().parent().parent().append('<div class="weui-cell my-item">'+
														    '<div class="weui-cell__bd">'+
														      '<input class="weui-input my-form" readonly type="text"  placeholder="球员" onclick="addOneName(this)">'+
														    '</div>'+
														    '<div class="weui-cell__bd">'+
														      '<input class="weui-input my-form" readonly type="number" placeholder="进球" onclick="addNum(this)">'+
														    '</div>'+
														    '<div class="weui-cell__hd">'+
																'<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini" onclick="del(this)">删除</a>'+
															'</div>'+
															'<div class="weui-cell__hd">'+
																'<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini my-btn" onclick="addOne(this)">新增</a>'+
															'</div>'+
														  '</div>');
		 		$(btn).parent().hide();
		 		$(btn).parent().parent().next().find(".my-form").click();
		 	}
		 	//队二添加
		 	function addTwo(btn){
		 		$(btn).parent().prev().show();
		 		$(btn).parent().parent().parent().append('<div class="weui-cell my-item">'+
														    '<div class="weui-cell__bd">'+
														      '<input class="weui-input my-form" readonly type="text"  placeholder="球员" onclick="addTwoName(this)">'+
														    '</div>'+
														    '<div class="weui-cell__bd">'+
														      '<input class="weui-input my-form" readonly type="number" placeholder="进球" onclick="addNum(this)">'+
														    '</div>'+
														    '<div class="weui-cell__hd">'+
																'<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini" onclick="del(this)">删除</a>'+
															'</div>'+
															'<div class="weui-cell__hd">'+
																'<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini my-btn" onclick="addTwo(this)">新增</a>'+
															'</div>'+
														  '</div>');
		 		$(btn).parent().hide();
		 		$(btn).parent().parent().next().find(".my-form").click();
		 	}
		 	//黄牌添加
		 	function addYellow(btn){
		 		$(btn).parent().prev().show();
		 		$(btn).parent().parent().parent().append('<div class="weui-cell my-item">'+
														    '<div class="weui-cell__bd">'+
														      '<input class="weui-input my-form" readonly type="text"  placeholder="球员" onclick="addName(this)">'+
														    '</div>'+
														    '<div class="weui-cell__bd">'+
														      '<input class="weui-input my-form" readonly type="number" placeholder="黄牌数" onclick="addCardNum(this)">'+
														    '</div>'+
														    '<div class="weui-cell__hd">'+
																'<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini" onclick="del(this)">删除</a>'+
															'</div>'+
															'<div class="weui-cell__hd">'+
																'<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini my-btn" onclick="addYellow(this)">新增</a>'+
															'</div>'+
														  '</div>');
		 		$(btn).parent().hide();
		 		$(btn).parent().parent().next().find(".my-form").click();
		 	}
		 	//红牌添加
		 	function addRed(btn){
		 		$(btn).parent().prev().show();
		 		$(btn).parent().parent().parent().append('<div class="weui-cell my-item">'+
														    '<div class="weui-cell__bd">'+
														      '<input class="weui-input my-form" readonly type="text"  placeholder="球员" onclick="addName(this)">'+
														    '</div>'+
														    '<div class="weui-cell__bd">'+
														      '<input class="weui-input my-form" readonly type="number" placeholder="红牌数" onclick="addCardNum(this)">'+
														    '</div>'+
														    '<div class="weui-cell__hd">'+
																'<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini" onclick="del(this)">删除</a>'+
															'</div>'+
															'<div class="weui-cell__hd">'+
																'<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini my-btn" onclick="addRed(this)">新增</a>'+
															'</div>'+
														  '</div>');
		 		$(btn).parent().hide();
		 		$(btn).parent().parent().next().find(".my-form").click();
		 	}
		 	//删除一行
		 	function del(btn){
		 		$.confirm("确定删除", function() {
				  var parent = $(btn).parent().parent().parent();
			 		$(btn).parent().parent().remove()
			 		var items = parent.children(".my-item");
			 		if(items.length == 1){
			 			var del = items.children(".weui-cell__hd:first");
			 			del.hide();
			 			del.next().show();
			 		}else{
			 			var item = parent.find(".my-item:last");
			 			item.children(".weui-cell__hd:last").show();
			 		}
				  }, function() {
				  //点击取消后的回调函数
				  });
		 	}
		 	
		 	
		 </script>
	</body>
</html>