<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<title>签到</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<script type="text/javascript">
		
			$(function(){
				var page = 2;//第一次加载更多肯定是加载第二页
				$(document.body).infinite(0);//滑到最底部才加载
				//加载的div
				var $div = $("#loadDiv");//加载的div
				var loading = false;  //状态标记
				var falg = true;//还有数据的标志
				$(document.body).infinite().on("infinite", function() {
				 $div.css("display","block");//显示加载div
				  if(loading) return;
				  loading = true;
				  if(falg){
					//请求数据
					  $.post("${pageContext.request.contextPath}/sign/ajaxLoadMoreTRList",{"pageNo":page},function(data){
						  //请求到数据
						  var json = JSON.parse(data);
						  var list = json.data;
						  if(list != null && list.length >0 ){
							  for(var i = 0;i<list.length;i++){
								 if(list[i].createTime != null && list[i].createTime != ''){
									 //2017-07-27 16:19:17.0
									 var startTime = list[i].startTime;
									 list[i].startTime = startTime.substring(0,createTime.length-6);
								 }
								 var opponent;
								 if(list[i].t2 == null || list[i].t2 == ''){
								 	opponent = list[i].opponent;
								 }else{
								 	opponent = list[i].t2;
								 } 
								 var img;
								 if(list[i].isAttendance == 1 || list[i].isAttendance == 2){
								 	img ='<img class="weui-media-box__thumb" onclick="siSign()" src="${pageContext.request.contextPath}/common/img/sign1.png"  alt="" />';
								 }else if (list[i].isAttendance == 0 || list[i].isAttendance == null || list[i].isAttendance == -1){
								 	if(list[i].raceortrain == 2){
								 		img ='<img class="weui-media-box__thumb" onclick="Rsign('+list[i].sid+')"  src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />';
								 	}else if(list[i].raceortrain == 1){
								 		img ='<img class="weui-media-box__thumb" onclick="Tsign('+list[i].sid+')"  src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />';
								 	}
								 }else{
								 	img='<img class="weui-media-box__thumb" onclick="noSign()" src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />';
								 }
								 if(list[i].raceortrain == 2){
									 $div.before('<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">'
								  			+'<div onclick="showDetail('+list[i].rtid+',2);" class="weui-media-box__bd">'
								  			+'<h4>比赛：'+list[i].rtName+'</h4>'
								  			+'<h5 style="font-weight:normal;">对手:'
								  			+opponent
								  			+'</h5>'
						  					+'<h5 style="font-weight:normal;">开始时间：'
						  					+list[i].startTime
						  					+'</h5>'
						  					+'</div>'
						  					+'<div class="weui-media-box__hd" id="r'+list[i].sid+'">'
						  					+img
						  					+'</div>'
						  					+'</a>'
						  					+'<div class="weui-form-preview__ft">'
						  					+'<div class="weui-form-preview__btn weui-form-preview__btn_primary" onclick="nameList('+list[i].rtid+','+list[i].position+')">查看签到名单</div>'
						  					+'</div>');
								  }else{
								  	$div.before('<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">'
								  			+'<div onclick="showDetail('+list[i].rtid+',1);" class="weui-media-box__bd">'
								  			+'<h4>训练：'+list[i].rtName+'</h4>'
								  			+'<h5 style="font-weight:normal;">训练地点:'
								  			+opponent
								  			+'</h5>'
						  					+'<h5 style="font-weight:normal;">开始时间：'
						  					+list[i].startTime
						  					+'</h5>'
						  					+'</div>'
						  					+'<div class="weui-media-box__hd" id="r'+list[i].sid+'">'
						  					+img
						  					+'</div>'
						  					+'</a>'
						  					+'<div class="weui-form-preview__ft">'
						  					+'<div class="weui-form-preview__btn weui-form-preview__btn_primary" onclick="nameTList('+list[i].rtid+','+list[i].position+')">查看签到名单</div>'
						  					+'</div>');
								  }
							  }
							  page++;//页数++
							  falg = true;//还有数据
						  } else {
							  //没有更多数据
							  $(document.body).destroyInfinite();
							  falg = false;//没有更多数据了
						  }
						  loading = false;
						  $div.css("display","none");//隐藏加载div
					  });
				  } else{
					  $(document.body).destroyInfinite();
					  $div.css("display","none");//隐藏加载div
				  }
				});
			});
		</script>
	</head>
	<body>
		<div class="weui-tab">
			<div style="padding-bottom: 50px;" class="weui-tab__bd">
					
				<div class="weui-panel weui-panel_access">
					<div class="weui-panel__bd">
						<s:if test="list != null && list.size() > 0">
							<s:iterator value="list" status="status" var="l">
								<s:if test="#l[9]==2">
									<a href="javascript:void(0);" id ="time${l[6]}" class="weui-media-box weui-media-box_appmsg">									
										<div onclick="showDetail(${l[0]},2);" class="weui-media-box__bd">
											<h4>比赛：${l[1]}</h4>
											<h5 style="font-weight:normal;">对手:
												<s:if test="#l[5]==null || #l[5]==''">
													${l[4]}
												</s:if>
												<s:else>
													${l[5]}
												</s:else>
											</h5>
											<h5 style="font-weight:normal;">
												开始时间：<s:date name="%{#l[2]}" format="yyyy-MM-dd HH:mm" />
											</h5>
										</div>
										<div class="weui-media-box__hd" id="r${l[6]}">
											<s:if test="#l[7] == 0 || #l[7] == null || #l[7] == -1">
												<img class="weui-media-box__thumb" onclick="Rsign(${l[6]})"  src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />
											</s:if>
											<s:elseif test="#l[7] == 3">
												<img class="weui-media-box__thumb" onclick="noSign()" src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />
											</s:elseif>
											<s:elseif test="#l[7] == 1 ||#l[7] == 2">
												<img class="weui-media-box__thumb" onclick="siSign()" src="${pageContext.request.contextPath}/common/img/sign1.png"  alt="" />
											</s:elseif>
										</div>
									</a>
									<div  class="weui-form-preview__ft">
						            	<div class="weui-form-preview__btn weui-form-preview__btn_primary" onclick="nameList(${l[0]},${l[8]})">查看签到名单</div>
						          	</div> 
					          	</s:if>
					          	<s:else>
					          		<a href="javascript:void(0);" id ="time${l[6]}" class="weui-media-box weui-media-box_appmsg">									
										<div onclick="showDetail(${l[0]},1);" class="weui-media-box__bd">
											<h4>训练：${l[1]}</h4>
											<h5 style="font-weight:normal;">训练地点:
												${l[4]}
											</h5>
											<h5 style="font-weight:normal;">
												开始时间：<s:date name="%{#l[2]}" format="yyyy-MM-dd HH:mm" />
											</h5>
										</div>
										<div class="weui-media-box__hd" id="r${l[6]}">
											<s:if test="#l[7] == 0 || #l[7] == null || #l[7] == -1">
												<img class="weui-media-box__thumb" onclick="Tsign(${l[6]})"  src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />
											</s:if>
											<s:elseif test="#l[7] == 3">
												<img class="weui-media-box__thumb" onclick="noSign()" src="${pageContext.request.contextPath}/common/img/sign.png"  alt="" />
											</s:elseif>
											<s:elseif test="#l[7] == 1 ||#l[7] == 2">
												<img class="weui-media-box__thumb" onclick="siSign()" src="${pageContext.request.contextPath}/common/img/sign1.png"  alt="" />
											</s:elseif>
										</div>
									</a>
									<div class="weui-form-preview__ft">
						            	<div class="weui-form-preview__btn weui-form-preview__btn_primary" onclick="nameTList(${l[0]},${l[8]})">查看签到名单</div>
						          	</div> 
					          	</s:else>
							</s:iterator>
							<div id="loadDiv" style="display: none;" class="weui-loadmore">
								 <i class="weui-loading"></i>
								 <span class="weui-loadmore__tips">正在加载</span>
							</div>
						</s:if>
						<s:else>
							<div class="weui-panel weui-panel_access">
					<div style="background-color: #F7F7F7" align="center">
						<h2 class="weui-panel__hd"><font color="#09BB07">暂无签到</font></h2>
					</div>
				</div>
						</s:else>
					</div>
					
				</div>
				
				<div style="height: 70px;"></div>
			
				
				<div class="weui-tabbar" style="position:fixed;bottom: 0px;">
					<a href="${pageContext.request.contextPath}/home" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/home.png" alt="">
						</div>
						<p class="weui-tabbar__label">动态</p>
					</a>
					<a href="javascript:void(0)" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/qiandao_select.png" alt="">
						</div>
						<p class="weui-tabbar__label">签到</p>
					</a>
					<a href="${pageContext.request.contextPath}/notice/findNoticeList" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/manage.png" alt="">
						</div>
						<p class="weui-tabbar__label">通知</p>
					</a>
					<a href="${pageContext.request.contextPath}/wxuser/userDetail" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/my.png" alt="">
						</div>
						<p class="weui-tabbar__label">我</p>
					</a>
				</div>
				
			</div>	
		</div>	
		<script type="text/javascript">
			/*************ajax比赛签到*******************/
			function Rsign(id){
				$.showLoading("正在签到，请稍后...");
				$.ajax({
					url:'sign/ajaxSign.action?sid='+id,
					type:'post',  
				    success: function (data) {
				    	
				    	var flag = $.parseJSON(data);
				    	if(flag.data == 1){
					    	$.toast("签到成功");
					    	$("#r"+id).empty();
					    	$("#r"+id).append("<img class='weui-media-box__thumb' onclick='siSign()' src='${pageContext.request.contextPath}/common/img/sign1.png'  alt=' ' />");
				    	}else if(flag.data == 2){
				    		$.toast("签到失败，已超过签到时间", "forbidden");
				    	}else if (flag.data == 3) {
				    		$.toast("签到还未开始", "cancel");
						}else {
							$.toast("您已签到，请刷新页面", "cancel");
						}
				    }
				});
				setTimeout(function() {
							  $.hideLoading();
						}, 1)//1毫秒后直接消失
			}
			/*************ajax训练签到**************/
			function Tsign(id){
				$.ajax({
					url:'sign/ajaxTSign.action?sid='+id,
					type:'post',  
				    success: function (data) {
				    	//alert(data);
				    	var flag = $.parseJSON(data);
				    	if(flag.data == 1 ){
					    	$.toast("签到成功");
					    	$("#r"+id).empty();
					    	$("#r"+id).append("<img class='weui-media-box__thumb' onclick='siSign()' src='${pageContext.request.contextPath}/common/img/sign1.png'  alt=' ' />");
				    	}else if(flag.data == 2){
				    		$.toast("签到失败，已超过签到时间", "forbidden");
				    	}else if (flag.data == 3) {
				    		$.toast("签到还未开始", "cancel");
						}else {
							$.toast("您已签到，请刷新页面", "cancel");
						}
				    }
				});
			}
			
			function siSign(){
				$.toast("您已经签到，无需重复签到", "cancel");
			}
			
			function noSign(){
				 $.toast("sorry,队长判定您未到场", "forbidden");
			}
			
			function nameList(rid,type){
				window.location.href = "${pageContext.request.contextPath}/sign/findRSignList.action?rid="+rid+"&type="+type
			}
			
			function nameTList(trid,type){
				window.location.href = "${pageContext.request.contextPath}/sign/findTSignList.action?trid="+trid+"&type="+type
			}
			
			function leaveList(rid,type){
				window.location.href = "${pageContext.request.contextPath}/sign/showLeaveList.action?rid="+rid+"&type="+type
			}
			function showDetail(rtid,type){
				if (type==2) {
					window.location.href = "${pageContext.request.contextPath}/race/showMesDetails.action?id="+rtid
				}else{
					window.location.href = "${pageContext.request.contextPath}/train/showMesDetails.action?id="+rtid;
				}
			}
			
		</script>
	</body>
</html>