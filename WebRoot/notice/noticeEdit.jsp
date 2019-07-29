<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
		<s:if test="notice.id == null">
			<title>创建通知</title>
		</s:if>
		<s:else>
			<title>编辑通知</title>
		</s:else>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/swiper.min.js"></script>
	</head>
	<body>
		<div class="weui-tab">
			<div class="weui-tab__bd">
				<div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
					<input type="hidden" value="${uid}" id="uid" />
					<form  action="${pageContext.request.contextPath}/notice/saveNotice.action" id="submitForm" method="post" enctype="multipart/form-data">
						<input name="notice.id" value="${notice.id}" type="hidden"/>
						<input name="notice.picNewName" value="${notice.picNewName}" type="hidden" id="picNewName"/>
						<input name="notice.picOldName" value="${notice.picOldName}" type="hidden" id="picOldName"/>
						<input name="notice.attachmentNewName" value="${notice.attachmentNewName}" type="hidden"/>
						<input name="notice.attachmentOldName" value="${notice.attachmentOldName}" type="hidden"/>
						<input name="notice.createTime" value="${notice.createTime}" type="hidden"/>
						<input name="notice.createUser" value="${notice.createUser}" type="hidden"/>
						<input name="notice.noticeType" value="${notice.noticeType}" type="hidden"/>
						<input name="type" type="hidden" id="type"/>
						<input name="tid" value="${tid}" type="hidden" id="tid"/>
						<div class="weui-cells weui-cells_form">
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">通知标题</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" maxlength="20" id="title" name="notice.title" value="<s:property value="notice.title"/>" type="text" placeholder="输入一个通知标题吧">
								</div>
							</div>
							
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">通知内容</label>
								</div>
							</div>
							
							<div class="weui-cell">
								<div class="weui-cell__bd">
									<textarea class="weui-textarea" maxlength="200" id="introduce" name="notice.content" placeholder="请填写通知内容,不超过200字" rows="3"><s:property value="notice.content"/></textarea>
									<div class="weui-textarea-counter" ><span id="rNum">0</span>/200</div>
								</div>
							</div>
							
							<!-- <div id="ntype" class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">通知类型</label>
								</div>
								<div id="" class="weui-cell__hd">
									<input class="weui-input" name="noticeType" id="ntypeValue" type="text" value="">
								</div>
							</div> -->
					
							<!-- <div id="nteam" style="display: none" class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">通知队伍</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" id='picker' type="text">
								</div>
							</div> -->
					
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">上传附件</label>
								</div>
								<div class="weui-cell__bd">
									<input name="upload" id="file" value="" type="file">
								</div>
							</div>
							
							<s:if test="notice.attachmentOldName != null">
								<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">已有附件：</label>
								</div>
								<div class="weui-cell__bd">
									${notice.attachmentOldName}
								</div>
							</div>
							</s:if>
						</div>
					</form>
					<form action="${pageContext.request.contextPath}/notice/ajaxFile.action" id="fileuplaod" method="post" enctype="multipart/form-data">
						<div class="weui-cell">
							<div class="weui-cell__bd">
								<div class="weui-uploader">
									<div class="weui-uploader__hd">
										<p class="weui-uploader__title">附加图片</p>
										<s:if test="notice.picNewName != ''&&notice.picNewName != null">
											<div class="weui-uploader__info" id="picNum">1/1</div>
										</s:if>
										<s:else>
											<div class="weui-uploader__info" id="picNum">0/1</div>
										</s:else>
									</div>
									
									<div class="weui-uploader__bd" >
										<div id="ineer">
											<s:if test="notice.picNewName != ''&&notice.picNewName != null">
												<img class='weui-uploader__file' src='${pageContext.request.contextPath}/image/min_${notice.picNewName}' />
											</s:if>
										</div>
										<div class="weui-uploader__input-box">
											<input id="uploaderInput" class="weui-uploader__input" name="upload" type="file" accept="image/*" multiple="multiple" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
					<div class="weui-msg__opr-area">
						<p class="weui-btn-area">
							<a href="javascript:;" id="confirm" class="weui-btn weui-btn_primary">提交</a>
						</p>
					</div>
					<script type="text/javascript">
						/***********通知类型js************/
						$("#ntypeValue").select({
							title: "选择类型",
							items: [{
									title: "本队成员",
									value: 1
								},
								{
									title: "全体成员",
									value: 2
								}
							]
						});
					</script>
					<!-- <script type="text/javascript">
					/****************选择通知队伍js***********************/
					$("#ntype").change(function(){
						var val = $("#ntypeValue").val();
						if(val=="队伍成员"){
							$("#nteam").show();
							var uid = $("#uid").val();
							$.ajax({
								url:'notice/teamAjax.action?uid='+uid,
								type:'psot',  
					            dataType:'json', 
							    success: function (data) {
							    	//alert(data);
					            	$("#picker").select({
										title: "选择队伍",
										items: data.data.array,
									});
							    }
							});
						}else{
			    			$("#nteam").hide();
						}
					});
					</script> -->
					<script type="text/javascript">
					
					
					</script>
					<script type="text/javascript">
						/***************文字计数********************/
						$(document).ready(function(){ 
							$("#rNum").text($("#introduce").val().length);
						});
						$("#introduce").keyup(function(){
							$("#rNum").text($("#introduce").val().length);
						});
						/***************ajax图片异步上传********************/
						$("#uploaderInput").change(function(){
						         var options ={
					        		beforeSubmit: function(){  
					        			$("#ineer").empty();
					        			$.showLoading("正在上传中...");
					        	    },  
						        	success:function(data) {
						        		$.hideLoading();
						        		
						        		var myObject = $.parseJSON(data);
						        		//alert(data);
						        		if(myObject.data != null){
							        		$("#picNewName").val(myObject.data.newName);
							        		$("#picOldName").val(myObject.data.oldName);
							        		$("#ineer").append("<img id='pb1' class='weui-uploader__file' src='${pageContext.request.contextPath}/image/min_"+myObject.data.newName+"'>");
							        		$("#picNum").empty();
							        		$("#picNum").append("1/1");
						        		}else{
						        			$.toast("因网络原因，图片上传失败，请刷新页面重试", "cancel");
						        		}
						        	}
						        }
						        $("#fileuplaod").ajaxSubmit(options); 
						        
						});
						/****************点击图片放大***************/
						 $("#ineer").click(function() {
						 //alert($("#picNewName").val());
					        	pb1.open();
					      });
					      var pb1 = $.photoBrowser({
					        items: [
					          "../image/max_"+$("#picNewName").val()
					        ]
					      });
						/*************前台校验********************/
						var click = 1;
						$("#confirm").click(function(){
							var flag = true;
						  	if($("#title").val().length>20){
						  		flag = false;
						  		$.toptip('亲，您填写的标题不能超过20个字', 'warning');
						  	}
						  	if($("#title").val().length == 0&&flag == true){
						  		flag = false;
						  		$.toptip('亲，您还未填写通知标题', 'warning');
						  	}
						  	if($("#introduce").val().length == 0&&flag == true){
						  		flag = false;
						  		$.toptip('亲，您还未填写通知内容', 'warning');
						  	}
						  	if($("#title").val().length>200){
						  		flag = false;
						  		$.toptip('亲，您填写的通知内容不能超过200个字', 'warning');
						  	}
						  	/* if($("#ntypeValue").val().length == 0&&flag == true){
						  		flag = false;
						  		$.toptip('亲，您还未选择通知类型', 'warning');
						  	} */
						  /* 	if($("#ntypeValue").val() == "队伍成员"&&flag == true){
						  		if($("#picker").val().length == 0){
						  			flag = false;
							  		$.toptip('亲，您还未选择通知队伍', 'warning');
							  	}
						  	} */
						  	/* if($("#picker").attr("data-values") != null){
						  		$("#tid").val($("#picker").attr("data-values"));
						  	} */
							if( flag == true && click == 1){
								var url = "findTeamNList?tid="+${tid};
								click =2;
								$.modal({	
									title : "确认消息",
									text : "请选择您要进行的下一步操作",
									buttons : [
											{text : "取消",onClick : function() {}},
											{text : "暂存",onClick : function() {
												$("#type").val(0);
												var options ={
										        		beforeSubmit: function(){  
										        			
										        	    },  
											        	success:function(data) {
											        		var flag = $.parseJSON(data);
											        		//alert(flag.data);
											        		if(flag.data == true){
												        		flag = false;
												        		
												        		fnUrlReplace(url);
											        		}else{
											        			$.toast("暂存失败，请刷新页面重试", "cancel");
											        		}
											        		//window.location.href = "${pageContext.request.contextPath}/goods/findGList.action?tid="+${tid}
											        	},
											        	error:function(){
											        		$.toast("暂存失败，请刷新页面重试", "cancel");
											        	}
											        }
											    $("#submitForm").ajaxSubmit(options); 
												//$("#submitForm").submit();
											}
											},
											{text : "发布",onClick : function() {
												$("#type").val(1);
												var options ={
										        		beforeSubmit: function(){  
										        			
										        	    },  
											        	success:function(data) {
											        		var flag = $.parseJSON(data);
											        		if(flag.data == true){
												        		flag = false;
												        		
												        		fnUrlReplace(url);
											        		}else{
											        			$.toast("发布失败，请刷新页面重试", "cancel");
											        		}
											        		//window.location.href = "${pageContext.request.contextPath}/goods/findGList.action?tid="+${tid}
											        	},
											        	error:function(){
											        		$.toast("发布失败，请刷新页面重试", "cancel");
											        	}
											        }
											    $("#submitForm").ajaxSubmit(options); 
												//$("#submitForm").submit();
											}
											},]
										});
							}
						});
					</script>
				</div>
			</div>	
		</div>	
	</body>
</html>