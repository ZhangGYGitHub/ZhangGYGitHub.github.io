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
				var tid = ${tid};
				var position = ${position.id};
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
					  $.post("${pageContext.request.contextPath}/notice/ajaxLoadMoreNoticeList",{"pageNo":page,"tid":tid},function(data){
						  //请求到数据
						  var json = JSON.parse(data);
						  var list = json.data;
						  var html='';
						  if(list != null && list.length >0 ){
							  for(var i = 0;i<list.length;i++){
								 if(list[i].createTime != null && list[i].createTime != ''){
									 //2017-07-27 16:19:17.0
									 var createTime = list[i].createTime;
									 list[i].createTime = createTime.substring(0,createTime.length-5);
								 }
								 if(position != 4){
								 	html = '<div class="weui-panel__ft" style="clear:both" id="edit'+list[i].id+'">'
							  					+'<div class="button_sp_area">'
							  					+'<div align="center">'
							  					+'<button onclick="deleteNotice('+list[i].id+','+tid+')" class="weui-btn weui-btn_mini weui-btn_plain-primary">删除通知</button>'
							  					+'</div>'
							  					+'</div>'
							  					+'</div>';
								 }
								 if(list[i].type == 1){
									 $div.before('<div class="weui-media-box weui-media-box_text" id="del'+list[i].id+'">'
								  			+'<h4 class="weui-media-box__title">'+list[i].title
								  			+'</h4>'
								  			+'<a href="${pageContext.request.contextPath}/notice/showNoticeDetail?id='+list[i].id+'">'
								  					+'<p class="weui-media-box__desc">'+list[i].createTime
								  					+'</p>'
								  					+'<p class="weui-media-box__desc">'+list[i].position+':'+list[i].nickname
								  					+'</p>'
								  					+'<p class="weui-media-box__desc">'+list[i].content
								  					+'</p>'
								  					+'</a><br />'
								  					+html
								  					+'</div>');
								  }else{
								  	$div.before('<div class="weui-media-box weui-media-box_text" id="del'+list[i].id+'">'
								  			+'<h4 class="weui-media-box__title">'+list[i].title
								  			+'</h4>'
								  			+'<a href="${pageContext.request.contextPath}/notice/showNoticeDetail?id='+list[i].id+'">'
								  					+'<p class="weui-media-box__desc">'+list[i].createTime
								  					+'</p>'
								  					+'<p class="weui-media-box__desc">'+list[i].position+':'+list[i].nickname
								  					+'</p>'
								  					+'<p class="weui-media-box__desc">'+list[i].content
								  					+'</p>'
								  					+'</a><br />'
								  					+'<div class="weui-panel__ft" style="clear:both" id="edit'+list[i].id+'">'
								  					+'<div class="button_sp_area">'
								  					+'<div align="center">'
								  					+'<button onclick="send('+list[i].id+','+tid+')" class="weui-btn weui-btn_mini weui-btn_plain-primary">发布通知</button> '
								  					+'<button onclick="edit('+list[i].id+','+tid+')" class="weui-btn weui-btn_mini weui-btn_plain-primary">编辑通知</button> '
								  					+'<button onclick="deleteNotice('+list[i].id+','+tid+')" class="weui-btn weui-btn_mini weui-btn_plain-primary">删除通知</button>'
								  					+'</div>'
								  					+'</div>'
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
				<div class="weui-panel weui-panel_access">
				<!-- 添加通知按钮 -->
				<s:if test="tid != null && #position.id != 4">
					<div class="weui-msg__opr-area">
						<p class="weui-btn-area" >
							<a href="${pageContext.request.contextPath}/notice/addNotice.action?tid=${tid}" class="weui-btn weui-btn_primary">创建通知</a>
						</p>
					</div>
				</s:if>
				<!-- 迭代列表 -->
					<s:if test="list != null && list.size()>0">
						<s:iterator value="list" status="status" var="nt">
							<s:if test="#position.id == 4 && #nt[6] == 0">
								
							</s:if>
							<s:else>
								<div class="weui-media-box weui-media-box_text"  id="del${nt[0]}">
									<h4 class="weui-media-box__title">
										<s:property value="%{#nt[1]}"/>
									</h4>
									<a href="${pageContext.request.contextPath}/notice/showNoticeDetail.action?id=<s:property value='%{#nt[0]}'/>" >
										<p class="weui-media-box__desc">
											<s:date name="%{#nt[3]}" format="yyyy-MM-dd HH:mm" />
										</p>
										<p class="weui-media-box__desc">
											<s:property value="%{#nt[5]}"/>:<s:property value="%{#nt[4]}"/>
										</p>
										<p class="weui-media-box__desc">
											<s:property value="%{#nt[2]}"/>
										</p>
									</a>
									<br />
									<s:if test="#position.id != 4 && (#nt[6] == 0 || #nt[6] == null)">
										<div class="weui-panel__ft" style="clear:both" id="edit${nt[0]}">
											<div class="button_sp_area" >
												<div align="center">
													<button onclick="send(${nt[0]},${tid})" class="weui-btn weui-btn_mini weui-btn_plain-primary">发布通知</button>
													<button onclick="edit(${nt[0]},${tid})" class="weui-btn weui-btn_mini weui-btn_plain-primary">编辑通知</button>
													<button onclick="deleteNotice(${nt[0]},${tid})" class="weui-btn weui-btn_mini weui-btn_plain-primary">删除通知</button>
												</div>
											</div>
											<br/>
										</div>
									</s:if>
									<s:elseif test="#position.id != 4">
										<div class="weui-panel__ft" style="clear:both">
											<div class="button_sp_area" >
												<div align="center">
													<button onclick="deleteNotice(${nt[0]},${tid})" class="weui-btn weui-btn_mini weui-btn_plain-primary">删除通知</button>
												</div>
											</div>
										</div>
									</s:elseif > 
								</div>   
							</s:else>
						</s:iterator>
					</s:if>
					<s:else>
						<div class="weui-media-box" style="text-align: center;">
							暂无通知
						</div>
					</s:else>
					<div id="loadDiv" style="display: none;" class="weui-loadmore">
					  <i class="weui-loading"></i>
					  <span class="weui-loadmore__tips">正在加载</span>
					</div>
				</div>
				<!-- 迭代列表 -->
			</div>
		</div>	
	</body>
	<script type="text/javascript">
		function edit(nid,tid){
			window.location.href = "${pageContext.request.contextPath}/notice/editNotice.action?id="+nid+"&tid="+tid
		}
		function send(nid,tid){
			$.ajax({
				url:'notice/sendNotice.action?id='+nid+'&tid='+tid,
				type:'psot',  
	            dataType:'json', 
			    success: function (data) {
			    	if(data.data == true){
			    		$("#edit"+nid).empty();
			    		$("#edit"+nid).append('<div align="center"><button onclick="deleteNotice(${nt[0]},${tid})" class="weui-btn weui-btn_mini weui-btn_plain-primary">删除通知</button></div>');
			    	}else if (data.data == false) {
						$.toast("发布消息失败，请刷新页面重试", "cancel");
					}
			    },
			    error:function(){
			    	$.toast("发布消息失败，请刷新页面重试", "cancel");
			    }
			});
		}
		function deleteNotice(nid,tid){
			$.ajax({
				url:'notice/deleteNotice.action?id='+nid+'&tid='+tid,
				type:'psot',  
	            dataType:'json', 
			    success: function (data) {
			    	
			    	if(data.data == true){
			    		$("#del"+nid).remove();
			    		
			    	}else if (flag.data == false) {
						$.toast("删除公告失败，请刷新页面重试", "cancel");
					}
			    },
			    error:function(){
			    	$.toast("删除公告失败，请刷新页面重试", "cancel");
			    }
			});
		}
	</script>
</html>