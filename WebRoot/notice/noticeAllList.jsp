<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>

	<head>
		<title>通知</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>
	<body>
		<script type="text/javascript">
		
			$(function(){
				var uid = ${uid};
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
					  $.post("${pageContext.request.contextPath}/notice/ajaxLoadMoreUNoticeList",{"pageNo":page,"uid":uid},function(data){
						  //请求到数据
						  var json = JSON.parse(data);
						  var list = json.data;
						  if(list != null && list.length >0 ){
							  for(var i = 0;i<list.length;i++){
								 if(list[i].createTime != null && list[i].createTime != ''){
									 //2017-07-27 16:19:17.0
									 var createTime = list[i].createTime;
									 list[i].createTime = createTime.substring(0,createTime.length-5);
								 } 
								 if(list[i].iconNewName == null || list[i].iconNewName == ""){
									 $div.before('<div class="weui-media-box weui-media-box_appmsg" onclick="showDetail('+list[i].id+')">'
								  			+'<div class="weui-media-box__hd" style="padding:10px auto">'
								  			+'<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb"  src="${pageContext.request.contextPath}/common/img/default.png" alt="">'
								  			+'</div>'
								  			+'<div class="weui-media-box__bd">'
						  					+'<h4>'
						  					+list[i].title
						  					+'</h4>'
						  					+'<h5 style="font-weight:normal;">'
						  					+list[i].tName
						  					+'</h5>'
						  					+'<h5 style="font-weight:normal;">发布人：s'
						  					+list[i].nickname
						  					+'</h5>'
						  					+'<h5 style="font-weight:normal;">发布时间'
						  					+list[i].createTime
						  					+'</h5>'
						  					+'</div>'
						  					+'</div>');
								  }else{
								  	$div.before('<div class="weui-media-box weui-media-box_appmsg" onclick="showDetail('+list[i].id+')">'
								  			+'<div class="weui-media-box__hd" style="padding:10px auto">'
								  			+'<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb"  src="${pageContext.request.contextPath}/image/'+list[i].iconNewName+'" alt="">'
								  			+'</div>'
								  			+'<div class="weui-media-box__bd">'
						  					+'<h4>'
						  					+list[i].title
						  					+'</h4>'
						  					+'<h5 style="font-weight:normal;">'
						  					+list[i].tName
						  					+'</h5>'
						  					+'<h5 style="font-weight:normal;">'
						  					+list[i].nickname
						  					+'</h5>'
						  					+'<h5 style="font-weight:normal;">'
						  					+list[i].createTime
						  					+'</h5>'
						  					+'</div>'
						  					+'</div>');
								  }
							  }
							  page++;//页数++
							  falg = true;//还有数据
						  } else {
							  //没有更多数据
							  $(document.body).destroyInfinite();
							  // $.toptip("没有更多数据了", 'warning');
							  falg = false;//没有更多数据了
						  }
						  loading = false;
						  $div.css("display","none");//隐藏加载div
					  });
				  } else{
					  $(document.body).destroyInfinite();
					  $div.css("display","none");//隐藏加载div
					  // $.toptip("没有更多数据了", 'warning');
				  }
				});
			});
		</script>
		<div class="weui-tab">
			<div class="weui-tab__bd">
						<!-- 迭代列表 -->
						<s:if test="list != null && list.size()>0">
							<s:iterator value="list" status="status" var="nt">
								
								<div class="weui-media-box weui-media-box_appmsg" onclick="showDetail(${nt[0]})">
									<div class="weui-media-box__hd" style="padding:10px auto">
										<s:if test="#nt[6] == null || #nt[6] == ''">
											<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
												src="${pageContext.request.contextPath}/common/img/default.png" alt="">
										</s:if>
										<s:else>
											<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
												src="${pageContext.request.contextPath}/image/${nt[6]}">
										</s:else>
									</div>
									<div class="weui-media-box__bd">
										<h4>${nt[1]}</h4>
										<h5 style="font-weight:normal;">队伍:${nt[5]}</h5>
										<h5 style="font-weight:normal;">发布人: ${nt[4]}</h5>
										<h5 style="font-weight:normal;">发布时间: <s:date name="%{#nt[3]}" format="yyyy-MM-dd HH:mm" /></h5>
									</div>
								</div>
															  
							</s:iterator>
						</s:if>
						<s:else>
							<div class="weui-panel weui-panel_access">
								<div style="background-color: #F7F7F7" align="center">
									<h2 class="weui-panel__hd"><font color="#09BB07">暂无通知</font></h2>
								</div>
							</div>
						</s:else>
						<div id="loadDiv" style="display: none;" class="weui-loadmore">
							 <i class="weui-loading"></i>
							 <span class="weui-loadmore__tips">正在加载</span>
						</div>
						<div style="height: 70px;"></div>
				<!-- 迭代列表 -->
				<div class="weui-tabbar" style="position:fixed;bottom: 0px;">
					<a href="${pageContext.request.contextPath}/home" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/home.png" alt="">
						</div>
						<p class="weui-tabbar__label">动态</p>
					</a>
					<a href="${pageContext.request.contextPath}/sign/findAllList" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/qiandao.png" alt="">
						</div>
						<p class="weui-tabbar__label">签到</p>
					</a>
					<a href="${pageContext.request.contextPath}/notice/findNoticeList" class="weui-tabbar__item">
						<div class="weui-tabbar__icon">
							<img src="${pageContext.request.contextPath}/common/img/manage_select.png" alt="">
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
			function showDetail(nid){
				window.location.href = "${pageContext.request.contextPath}/notice/showNoticeDetail.action?id="+nid
			}
		</script>
	</body>
</html>