<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>比分编辑</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/city-picker.min.js" charset="utf-8"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/swiper.min.js"></script>
   </head>
	<body>
		<div class="weui-tab">
		  <div class="weui-tab__bd">
			 <div id="match_" class="weui-tab__bd-item weui-tab__bd-item--active">
			 	<s:if test="turns != null && turns.size() > 0">
	           		<s:iterator value="turns" status="status" var="list">
		           		<div class="weui-panel weui-panel_access fastclick" turn="${turn}">
							<div align="center" class="titleclick">
								<h2 class="weui-panel__hd"><font color="#09BB07" size="3px">第<s:property value="#status.index+1"/>轮&nbsp;&nbsp;&nbsp;&nbsp;</font>
									<img class="img-bottom" val="2" src="${pageContext.request.contextPath}/common/img/right.png" style="width:15px;hight:15px;margin-bottom: -2px; border:none" >
								</h2>
							</div>
							<div class="weui-panel__bd" hidden="hidden" >
								
							</div>
		           		</div>
	           		</s:iterator>
	          	</s:if>
	          	<div style="height:3em"></div>
			 </div>
			 <script>
           		var turn;
				var onwork = false //防止快速点击
           		$(".titleclick").click(function(){      
  					var img = $(this).parent().find("div h2 .img-bottom");
  					turn = $(this).parent().attr("turn");
  					//下拉框
  					if(img.attr("val") == 2){
  						img.attr("src", "${pageContext.request.contextPath}/common/img/right.png");
           				$(this).parent().find(".weui-panel__bd").hide();
           				img.attr("val", 1);
  					}else{
  						img.attr("src", "${pageContext.request.contextPath}/common/img/bottom.png");
           				$(this).parent().find(".weui-panel__bd").show();
           				img.attr("val", 2);
  					}
  					//post获取比赛
  					if($(this).parent().find(".weui-panel__bd").children().length == 0){
  						if(onwork) return;//防止快速点击
  						onwork = true;//防止快速点击
  						$.showLoading("加载中");
  						$.post("ajaxGetMatch?turn=" + turn , function(data) {
  						//sql查询 0oneWin 1twoWin 2site 3startTime 4endTime 5oneName 6twoName 7onePic 8twoPic 9t1id 10t2id 11lmid 12finish 且有等量的队长,审核判断
   						   var json = JSON.parse(data);
						   var list = json.data;
						   if(list != null && list.length > 0 ){
						   	  var num = list.length/2;
							  for(var i = 0; i < num; i++){	
							  	if(list[i][5] == null) list[i][5] = "未填写";
							  	if(list[i][6] == null) list[i][6] = "未填写";
							  	//是队长
							  	//showType 0自己审核中的  1审核人看  2审核通过的
							  	if(list[i+num][0] == true){
								  		if(list[i+num][1] == true){
								  		//有审核
  										  		$(".weui-panel_access[turn="+turn+"] .weui-panel__bd").append('<a href="${pageContext.request.contextPath}/league/scoreExamine?isSubmit=1&showType=0&lmid='+list[i][11]+'" class="weui-cell weui-cell_access">'+
												 																	'<div style="float:left;width:70%;padding:5px;margin-top:5px;font-weight:bold;color:#333">'+
																													'<div style="padding-left:1em">'+list[i][5]+'</div>'+
																													'<div style="padding-left:1em">'+list[i][6]+'</div>'+
																												'</div>'+
																												'<div style="float:right;width:16%;padding:5px;margin-top:5px">'+
																													'<div>第'+list[i][2]+'场</div>'+
																													'<div style="color:orange">审核中</div>'+
																												'</div>'+
																												'<div style="height:1px;background:#ddd;clear:both"></div>'+
																												'</a>');
								  		}else{
								  		//已过审核
								  			if(list[i][12] == 1){
												$(".weui-panel_access[turn="+turn+"] .weui-panel__bd").append('<a href="${pageContext.request.contextPath}/league/scoreExamine?&isSubmit=1&showType=2&lmid='+list[i][11]+'" class="weui-cell weui-cell_access">'+
							  																	'<div style="float:left;width:70%;padding:5px;margin-top:5px;font-weight:bold;color:#333">'+
																									'<div style="padding-left:1em">'+list[i][5]+'</div>'+
																									'<div style="padding-left:1em">'+list[i][6]+'</div>'+
																								'</div>'+
																								'<div style="float:right;width:16%;padding:5px;margin-top:5px">'+
																									'<div>第'+list[i][2]+'场</div>'+
																									'<div style="color:#09BB07">已编辑</div>'+
																								'</div>'+
																								'<div style="height:1px;background:#ddd;clear:both"></div>'+
																								'</a>');
								  			}else{
								  			//待编辑
								  				$(".weui-panel_access[turn="+turn+"] .weui-panel__bd").append('<a href="${pageContext.request.contextPath}/league/scoreEdit?lmid='+list[i][11]+'&t1='+list[i][5]+'&t2='+list[i][6]+'" class="weui-cell weui-cell_access">'+
											  																	'<div style="float:left;width:70%;padding:5px;margin-top:5px;font-weight:bold;color:#333">'+
																													'<div style="padding-left:1em">'+list[i][5]+'</div>'+
																													'<div style="padding-left:1em">'+list[i][6]+'</div>'+
																												'</div>'+
																												'<div style="float:right;width:16%;padding:5px;margin-top:5px">'+
																													'<div>第'+list[i][2]+'场</div>'+
																													'<div style="color:red">待编辑</div>'+
																												'</div>'+
																												'<div style="height:1px;background:#ddd;clear:both"></div>'+
																												'</a>');
								  			}
								  	}
							  	}else{
								  	//不是队长
								  		$(".weui-panel_access[turn="+turn+"] .weui-panel__bd").append('<a href="javascript:;" class="weui-cell weui-cell_access">'+
							  																	'<div style="float:left;width:70%;padding:5px;margin-top:5px;font-weight:bold;color:#333">'+
																									'<div style="padding-left:1em">'+list[i][5]+'</div>'+
																									'<div style="padding-left:1em">'+list[i][6]+'</div>'+
																								'</div>'+
																								'<div style="float:right;width:16%;padding:5px;margin-top:5px">'+
																									'<div>第'+list[i][2]+'场</div>'+
																									'<div style="color:blue">非队长</div>'+
																								'</div>'+
																								'<div style="height:1px;background:#ddd;clear:both"></div>'+
																								'</a>');
								  	
							  	}
							  	
							  $(".weui-panel_access[turn="+turn+"] div h2 .img-bottom").attr("src", "${pageContext.request.contextPath}/common/img/bottom.png");
							  $(".weui-panel_access[turn="+turn+"] .weui-panel__bd").show();
							}
							onwork = false;//防止快速点击
							}
						});
						$.hideLoading();
  					}
           		});
           	</script>
			 <div id="referee_" class="weui-tab__bd-item">
			 <!-- sql查询 0name 1head -->
			 	<s:if test="leagueRefereeList != null && leagueRefereeList.size() > 0">
	           		<s:iterator value="leagueRefereeList" status="status" var="list">
		           		<div class="weui-media-box weui-media-box_appmsg">
							<div class="weui-media-box__hd" style="width:20%">
								<s:if test="#list[1] != '' ">
									<s:if test="#list[1].indexOf('http') != -1 ">
									    <img class="rank-head" src="${list[1]}" style="border-radius:50%;height:60px;width:60px;"/>
									</s:if>
									<s:else>
										<img class="rank-head" src="${pageContext.request.contextPath}/image/${list[1]}" style="border-radius:50%;height:60px;width:60px;"/>
									</s:else>
								</s:if>
								<s:else>
									<img class="rank-head" src="${pageContext.request.contextPath}/common/img/default.png" style="border-radius:50%;height:60px;width:60px;"/>
								</s:else>	
							</div>
							<div class="weui-media-box__bd">
								<h4 class="weui-media-box__title">${list[0]}</h4>
							</div>
						</div>
	           		</s:iterator>
	          	</s:if>
	          	<div style="height:3em"></div>
			 </div>
		  </div>
		  <div class="weui-tabbar">
            <a class="weui-navbar__item weui-bar__item--on" href="#match_">
                <span class="fa fa-book"></span>赛程
            </a>
            <a class="weui-navbar__item" href="#referee_">
                <span class="fa fa-user"></span>裁判组
            </a>
        </div>
	</div>
	</body>
</html>