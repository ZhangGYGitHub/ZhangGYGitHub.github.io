<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>

	<head>
		<title>加入球队</title>
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
					  $.post("${pageContext.request.contextPath}/team/ajaxLoadMoreList",{"pageNo":page,"teamName":$("#searchInput").val()},function(data){
						  //请求到数据
						  var json = JSON.parse(data);
						  var list = json.data;
						  if(list != null && list.length >0 ){
							  for(var i = 0;i<list.length;i++){
								 if(list[i].iconNewName == null || list[i].iconNewName == ''){
									 list[i].iconNewName = "common/img/default.png";
								 } else {
									 list[i].iconNewName = "image/"+list[i].iconNewName;
								 }
							 	 $div.before('<div  class="weui-media-box weui-media-box_appmsg">'
							 	 			+'<div class="weui-media-box__hd" style="padding:10px auto">'
							 	 			+'<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/'+list[i].iconNewName+'">'
							 	 			+'</div>'
							 	 			+'<div onclick="showDetail('+list[i].id+')" class="weui-media-box__bd">'
							 	 			+'<h4 class="weui-media-box__title">'+list[i].name+'</h4>'
							 	 			+'<p class="weui-media-box__desc">队长:'+list[i].nickname+'</p>'
							 	 			+'</div>'
							 	 			+'<div class="weui-media-box__hd" style="margin:10px auto">'
							 	 			+'<img onclick="apply('+list[i].id+')" style="margin-top:11px;border-radius:50%;height:40px;width:40px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/resizeApi.png" />'
							 	 			+'</div>'
							 	 			+'</div>');
							  }
							  page++;//页数++
							  falg = true;//还有数据
						  } else {
							  //没有更多数据
							  $(document.body).destroyInfinite();
							  //$.toptip("没有更多数据了", 'warning');
							  falg = false;//没有更多数据了
						  }
						  loading = false;
						  $div.css("display","none");//隐藏加载div
					  });
				  } else{
					  $(document.body).destroyInfinite();
					  $div.css("display","none");//隐藏加载div
					  //$.toptip("没有更多数据了", 'warning');
				  }
				});
				
				//表单提交
				$("form").submit(function(e){
					//阻止表单提交
				    e.preventDefault();
				    var content = $("#searchInput").val();
					if(content.trim().length == 0){
						 $.toptip("搜索内容不能为空", 'warning');
						 return;
					}
					//异步提交
				    var options ={
				        	success:function(data) {
				        		//移除class=weui-media-box_appmsg的div
				        		$(".weui-media-box_appmsg").remove(); 
				        		//暂无数据的div
				        		$("#no_div").remove();
				        		var json = $.parseJSON(data);
				        		var list = json.data;
				        		if(list != null && list.length >0 ){
									  for(var i = 0;i<list.length;i++){
										 if(list[i].iconNewName == null || list[i].iconNewName == ''){
											 list[i].iconNewName = "common/img/default.png";
										 } else {
											 list[i].iconNewName = "image/"+list[i].iconNewName;
										 }
									 	 $div.before('<div  class="weui-media-box weui-media-box_appmsg">'
									 	 			+'<div class="weui-media-box__hd" style="padding:10px auto">'
									 	 			+'<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/'+list[i].iconNewName+'">'
									 	 			+'</div>'
									 	 			+'<div onclick="showDetail('+list[i].id+')" class="weui-media-box__bd">'
									 	 			+'<h4 class="weui-media-box__title">'+list[i].name+'</h4>'
									 	 			+'<p class="weui-media-box__desc">队长:'+list[i].nickname+'</p>'
									 	 			+'</div>'
									 	 			+'<div class="weui-media-box__hd" style="margin:10px auto">'
									 	 			+'<img onclick="apply('+list[i].id+')" style="margin-top:11px;border-radius:50%;height:40px;width:40px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/resizeApi.png" />'
									 	 			+'</div>'
									 	 			+'</div>');
									  }
								  } else {
									  $div.before('<div id="no_div" align="center">暂无搜索结果</div">');  
								  }
				        	}
				        }
				        $("#myForm").ajaxSubmit(options);
				});
				
				
			});
		</script>
		<div class="weui-search-bar" id="searchBar">
			<form id="myForm" action="${pageContext.request.contextPath}/team/ajaxLoadMoreList" class="weui-search-bar__form">
				<div class="weui-search-bar__box">
					<i class="weui-icon-search"></i>
					<input type="search" name="teamName" class="weui-search-bar__input" id="searchInput" placeholder="搜索">
					<a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
				</div>
				<label class="weui-search-bar__label" id="searchText" style="transform-origin: 0px 0px 0px; opacity: 1; transform: scale(1, 1);">
          <i class="weui-icon-search"></i>
          <span>搜索</span>
        </label>
			</form>
			<a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
		</div>
			<s:if test="#list != null && #list.size()>0">
    			<s:iterator value="#list" status="status" var="obj">
			   		<div  class="weui-media-box weui-media-box_appmsg">
						<div class="weui-media-box__hd" style="padding:10px auto">
								<s:if test="#obj[3] == null || #obj[3] == ''">  
							 		<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/common/img/default.png">
							 	</s:if>  
							    <s:else>  
							    	<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/image/${obj[3]}">
							    </s:else>
						</div>
						<div onclick="showDetail(${obj[0]})" class="weui-media-box__bd">
							<h4 class="weui-media-box__title"><s:property value="#obj[1]"/></h4>
							<p class="weui-media-box__desc">队长:<s:property value="#obj[2]"/></p>
						</div>
						<div class="weui-media-box__hd" style="margin:10px auto">
							<img onclick="apply(${obj[0]})" style="margin-top:11px;border-radius:50%;height:40px;width:40px" class="weui-media-box__thumb" src="${pageContext.request.contextPath}/common/img/resizeApi.png" />
						</div>
					</div>
			    </s:iterator>
			</s:if>
			<div id="loadDiv" style="display: none;" class="weui-loadmore">
			  <i class="weui-loading"></i>
			  <span class="weui-loadmore__tips">正在加载</span>
			</div>
		<script type="text/javascript">
			function apply(tid) {
				$.confirm("是否申请加入球队?", function() {
			  		//点击确认后的回调函数
			  		$.post("${pageContext.request.contextPath}/apply/applyTeam",{"tid":tid},function(data){
			  			var json = JSON.parse(data);
			  			if(json.data == 0){
			  				//0代表成功处理
			  				$.toast("申请已提交,请耐心等待...","text");
			  			} else if (json.data == 1){
			  				//1代表处理失败
			  				$.toast("申请失败！",'cancel');
			  			} else if (json.data == 2){
			  				//2代表是队长	
			  				$.toast("请不要申请自己的球队！","text");
			  			} else if (json.data == 3){
			  				//3代表代处理或者已同意
			  				$.toast("请不要重复申请！","text");
			  			} 
			  		});
			  		
			  	}, function() {
			  		//点击取消后的回调函数
			  	});
			}
			function showDetail(id) {
				window.location.href="${pageContext.request.contextPath}/team/teaminfoDetail?teaminfo.id="+id;
			}
		</script>
	</body>
</html>