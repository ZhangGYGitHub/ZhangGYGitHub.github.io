<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>
			<s:if test="myCard[16] != null && myCard[16] != ''">
				<s:property value="myCard[16]"/>的名片
			</s:if>
			<s:else>
				<s:property value="myCard[0]"/>的名片
			</s:else>
			
		</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/fonts/font-awesome.min.css" />
		<style>
			.img_radius {
				margin-top: 10px;
				width: 70px;
				height: 70px;
				border-radius: 50%;
				/*padding-right: -10px;*/
			}
			
			.div_bg {
				background: linear-gradient(#7EA2D7, #12CF5C);
				width: 100%;
				height: 150px;
				color: #F1F1F1;
			}
			
			.img_sex {
				width: 20px;
				height: 20px;
			}
			
			.img_dianzan {
				float: right;
				padding-top: 10px;
				padding-right: 10px;
				width: 20px;
				height: 20px;
			}
			
			.div_dianzan {
				float: right;
				border: 1px solid #FFFFFF;
				border-radius: 20px;
				width: 70px;
				height: 25px;
			}
			.div_progress {
				margin-left: 15px;
				margin-right: 15px;
			}
		</style>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.min.js"></script>
	</head>
	<body>
		<div align="center" class="div_bg">
			<img id="img-1" onerror="this.src='${pageContext.request.contextPath}/common/img/default.png'"
				src="
					<s:if test="myCard[1] == null || myCard[1] == ''">
						<s:property value="myCard[2]"/>
					</s:if>
					<s:else>
						${pageContext.request.contextPath}/image/<s:property value="myCard[2]"/>
					</s:else>
					" 
					class="img_radius">
			<div>
				<label><!-- 暴走精灵 -->
					<s:if test="myCard[16] != null && myCard[16] != ''">
						<s:property value="myCard[16]"/>
					</s:if>
					<s:else>
						<s:property value="myCard[0]"/>
					</s:else>
				</label>
				<label>
					<s:if test="myCard[3] == 1">
						<img src="${pageContext.request.contextPath}/common/img/nan.png" 
					class="img_sex" />
					</s:if>
					<s:else>
						<img src="${pageContext.request.contextPath}/common/img/nv.png" 
					class="img_sex" />
					</s:else>
				</label>
			</div>
			<div class="div_dianzan" id="dianzan">
				<i class="fa fa-thumbs-o-up"></i><!-- 1300 -->
				<font id="dianzanshu">
					<s:property value="myCard[4]"/>
				</font>
			</div>
		</div>
		<div class="weui-cells weui-cells_form">
			<br />
			<div class="weui-progress div_progress">
				赴约率&nbsp;&nbsp;
				<s:if test="myCard[6] != null ">
					<div class="weui-progress__bar">
						<div class="weui-progress__inner-bar js_progress" style="width: <s:property value="myCard[6]"/>%;"></div>
					</div>&nbsp;&nbsp;<!-- 80% --><s:property value="myCard[6]"/>%
				</s:if>
				<s:else>
					<div class="weui-progress__bar">
						<div class="weui-progress__inner-bar js_progress" style="width: 100%;"></div>
					</div>&nbsp;&nbsp;100%
				</s:else>
			</div>
			<div style="margin: 10px;"></div>
			<div class="weui-progress div_progress">
				鸽子率&nbsp;&nbsp;
				<s:if test="myCard[5] != null  ">
					<div class="weui-progress__bar">
						<div class="weui-progress__inner-bar js_progress" style="width: <s:property value="myCard[5]"/>%;"></div>
					</div>&nbsp;&nbsp;<!-- 20% --><s:property value="myCard[5]"/>%
				</s:if>
				<s:else>
					<div class="weui-progress__bar">
						<div class="weui-progress__inner-bar js_progress" style="width: 0%;"></div>
					</div>&nbsp;&nbsp;0%
				</s:else>
			</div>
		
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">身高</label>
				</div>
				<div class="weui-cell__hd" align="right">
					<s:if test="myCard[7] != null">
						<!-- 185 --><s:property value="myCard[7]"/>cm
					</s:if>
					<s:else>
						未填写
					</s:else>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">体重</label>
				</div>
				<div class="weui-cell__hd" align="right">
					<s:if test="myCard[8] != null">
						<s:property value="myCard[8]"/>kg
					</s:if>
					<s:else>
						未填写
					</s:else>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">惯用脚</label>
				</div>
				<div class="weui-cell__hd" align="right">
					<!-- 中脚 -->
					<s:if test="myCard[9] == 1">
						左脚
					</s:if>
					<s:else>
						右脚
					</s:else>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">位置</label>
				</div>
				<div class="weui-cell__hd" align="right">
					<s:if test="myCard[10] == null">
						暂无踢球位置信息
					</s:if>
					<s:else>
						<s:property value="myCard[10]"/>
					</s:else>
					<!-- 前锋，中锋 -->
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">手机号</label>
				</div>
				<div class="weui-cell__hd" align="right">
					<s:if test="myCard[11] != null">
						<a href="tel:<s:property value="myCard[11]"/>">
							<s:property value="myCard[11]"/>
						</a>
						<!-- 13777777777 -->
					</s:if>
					<s:else>
						未填写
					</s:else>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">生日</label>
				</div>
				<div class="weui-cell__hd" align="right">
					<s:if test="myCard[12] != null">
						<s:date name="myCard[12]" format="yyyy年MM月dd日"/>
						<!-- 1970年1月1日 -->
					</s:if>
					<s:else>
						未填写
					</s:else>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">个性签名</label>
				</div>
				<div class="weui-cell__hd" align="right">
					<s:if test="myCard[13] != null">
						<!-- 走自己的路 -->
						<s:if test="myCard[13].length()>12">  
				    		<s:property value="myCard[13].substring(0,12)"/>...  
					 	</s:if>  
					    <s:else>  
					   		<s:property value="myCard[13]"/>
					    </s:else>
					</s:if>
					<s:else>
						未填写
					</s:else>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">个人简介</label>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__bd">
					<s:if test="myCard[14] != null">
						<s:property value="myCard[14]"/>
						<!-- 仰天大脚射门去，我辈岂是国足脚！！ -->
					</s:if>
					<s:else>
						<font color="#888888">该用户很懒，没有填写自己的简介...</font>
					</s:else>
				</div>
			</div>
			<%-- <div class="weui-msg__opr-area">
				<p class="weui-btn-area">
					<a href="${pageContext.request.contextPath}/personalcard/selectTheme.jsp">
						<input type="button" class="weui-btn weui-btn_primary" value="生成我的名片" />
					</a>
				</p>
			</div> --%>
		</div>
		<div class="weui-gallery" style="display: block" id="show-img">
			<span class="weui-gallery__img" style="background-image: url(
				<s:if test="myCard[1] == null || myCard[1] == ''">
					<s:property value="myCard[2]"/>
				</s:if>
				<s:elseif test="myCard[2] == null || myCard[2] == ''">
					${pageContext.request.contextPath}/common/img/default.png
				</s:elseif>
				<s:else>
					${pageContext.request.contextPath}/image/<s:property value="myCard[2]"/>
				</s:else>
				);"></span>
		</div>
		<script type="text/javascript">
			$(function(){
				$("#show-img").hide();
			});
			$("#img-1").click(function(){
				$("#show-img").show();
			});
			$("#show-img").click(function(){
				$("#show-img").hide();
			});
			$("#dianzan").click(function(){
				var userId = "${myCard[15]}";
				var loginUserId = "${currentUserId}";
				var num = "${myCard[4]}";
				if(userId == loginUserId) {
					$.toptip('自己不能给自己点赞哦', 'warning');
				} else {
					var fn = arguments.callee,
			        self = this;
			    	$(self).unbind('click', fn);
					$.ajax({
						url: "${pageContext.request.contextPath}/wxuser/dianzan",
						type: "post",
						dataType:"json",
						data:{
	                		'likeNum': num,
	                		'id': userId
	            		},
						success:function(data){
							$("#dianzanshu").empty();
							var dianshu = data.likeNum;
							var $font = document.createTextNode(dianshu);
							$("#dianzanshu").append($font);
						}
					});
				}
				
			});
		</script>
	</body>
</html>