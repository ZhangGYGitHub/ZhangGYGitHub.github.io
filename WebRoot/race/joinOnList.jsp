<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
	<head>
		<title>参赛情况</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>
	<body>
		<div class="weui-tab">
			<div class="weui-tab__bd">
				<div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
					<div class="weui-panel__bd" style="margin-bottom:50px;">
					
						<s:if test="race.signType == 1">
							<div align="center" id="signkey">
								<h2 class="weui-panel__hd">
									<font color="#09BB07" size="3px">签到列表(<s:property value="signlist.size()"/>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
									<img id="img2" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px" >
								</h2>
							</div>
							<!-- 迭代签到名单 -->
							<div id="signlist">
								<s:if test="signlist !=null&& signlist.size()>0">
									<s:iterator value="signlist" status="status" var="sl">
										<div class="weui-media-box weui-media-box_appmsg">
											<div class="weui-media-box__hd" style="padding:10px auto">
												<s:if test="(#sl[3] == null || #sl[3] == '')&& (#sl[4] == null || #sl[4] == '')">
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="${pageContext.request.contextPath}/common/img/default.png" alt="" />
												</s:if>
												<s:elseif test="#sl[3] == null || #sl[3] == ''">
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="<s:property value="%{#sl[4]}"/>" alt="" />
												</s:elseif>
												<s:else>
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="${pageContext.request.contextPath}/image/<s:property value="%{#sl[4]}"/>" alt="" />
												</s:else>
											</div>
											<div class="weui-media-box__bd">
												<h4 class="weui-media-box__title">
													<s:if test="#sl[2] == null">
														<s:property value="%{#sl[1]}" />
													</s:if>
													<s:else>
														<s:property value="%{#sl[2]}" />
													</s:else>
												</h4>
											</div>
											<div class="weui-cell__hd">
												<button class="weui-btn weui-btn_mini weui-btn_primary">已签到</button>
											</div>
										</div>
									</s:iterator>
								</s:if>
								<%-- <s:else>
									<div class="weui-media-box" style="text-align: center;">
										暂无签到人员
									</div>
								</s:else> --%>
							</div>
							<!-- 迭代签到名单 -->
						
						</s:if>
					
						<div align="center" id="signupkey">
								<h2 class="weui-panel__hd">
									<font color="#09BB07" size="3px">报名列表(<s:property value="signUpList.size()"/>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
									<img id="img1" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px" >
								</h2>
						</div>
						<!-- 迭代报名人员列表 -->
						<div id="signuplist">
							<s:if test="signUpList != null && signUpList.size() > 0">
								<s:iterator value="signUpList" status="status" var="sl">
									<div class="weui-media-box weui-media-box_appmsg">
										<div class="weui-media-box__hd" style="padding:10px auto">
											<s:if test="(#sl[3] == null || #sl[3] == '') && (#sl[4] == null || #sl[4] == '')">
												<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
													src="${pageContext.request.contextPath}/common/img/default.png" alt="" />
											</s:if>
											<s:elseif test="#sl[3] == null ||#sl[3] == ''">
												<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
													src="<s:property value="%{#sl[4]}"/>" alt="" />
											</s:elseif>
											<s:else>
												<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
													src="${pageContext.request.contextPath}/image/<s:property value="%{#sl[4]}"/>" alt="" />
											</s:else>
										</div>
										<div class="weui-media-box__bd">
											<h4 class="weui-media-box__title">
												<s:if test="#sl[2] == null">
													<s:property value="%{#sl[1]}" />
												</s:if>
												<s:else>
													<s:property value="%{#sl[2]}" />
												</s:else>
											</h4>
										</div>
										<div class="weui-cell__hd">
											<button class="weui-btn weui-btn_mini weui-btn_primary">已报名</button>
										</div>
									</div>
								</s:iterator>
							</s:if>
							<%-- <s:else>
								<div class="weui-media-box" style="text-align: center;">
									暂无报名人员
								</div>
							</s:else> --%>
						</div>
						<!-- 迭代报名人员列表 -->
						
						<div align="center" id="leavekey">
							<h2 class="weui-panel__hd">
								<font color="#09BB07" size="3px">请假列表(<s:property value="#leaveNum"/>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
								<img id="img3" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px" >
							</h2>
						</div>
						<!-- 迭代请假名单 -->
						<div id="leavelist">
							<s:if test="leaveList !=null&& leaveList.size()>0">
								<s:iterator value="leaveList" status="status" var="ll">
									<s:if test="#ll[6] != 1 && #ll[6] != 2">
										<div class="weui-media-box weui-media-box_appmsg">
											<div class="weui-media-box__hd" style="padding:10px auto">
												<s:if test="(#ll[5] == null || #ll[5] == '') &&(#ll[4] == null || #ll[4] == '' )">
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="${pageContext.request.contextPath}/common/img/default.png" alt="" />
												</s:if>
												<s:elseif test="#ll[4] == null || #ll[4] == ''">
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="<s:property value="%{#ll[5]}"/>" alt="" />
												</s:elseif>
												<s:else>
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="${pageContext.request.contextPath}/image/<s:property value="%{#ll[5]}"/>" alt="" />
												</s:else>
											</div>
											<div class="weui-media-box__bd">
												<h4 class="weui-media-box__title">
													<s:if test="#ll[3] == null">
														<s:property value="%{#ll[2]}" />
													</s:if>
													<s:else>
														<s:property value="%{#ll[3]}" />
													</s:else>
												</h4>
												<h5 style="font-weight:normal;">
													请假原因: <s:property value="%{#ll[1]}" />
												</h5>
											</div>
											<div class="weui-cell__hd">
												<button class="weui-btn weui-btn_mini weui-btn_warn">已请假</button>
											</div>
										</div>
									</s:if>
								</s:iterator>
							</s:if>
							<%-- <s:else>
								<div class="weui-media-box" style="text-align: center;">
									暂无请假人员
								</div>
							</s:else> --%>
						</div>
						<!-- 迭代请假名单 -->
						
						<div align="center" id="unSignkey">
							<h2 class="weui-panel__hd">
								<font color="#09BB07" size="3px">未到场列表(<s:property value="#sizeNum"/>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
								<img id="img4" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px" >
							</h2>
						</div>
						<!-- 迭代未到场名单 -->
						<div id=unSignList>
							<s:if test="unSignList !=null&& unSignList.size()>0">
								<s:iterator value="unSignList" status="status" var="usl">
									<s:if test="#usl[4] == null">
										<div class="weui-media-box weui-media-box_appmsg">
											<div class="weui-media-box__hd" style="padding:10px auto">
												<s:if test="(#usl[2] == null || #usl[2] == '') &&(#usl[3] == null || #usl[3] == '' )">
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="${pageContext.request.contextPath}/common/img/default.png" alt="" />
												</s:if>
												<s:elseif test="#usl[2] == null || #usl[2] == ''">
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="<s:property value="%{#usl[3]}"/>" alt="" />
												</s:elseif>
												<s:else>
													<img style="border-radius:50%" height="70px" width="70px" class="weui-media-box__thumb" 
														src="${pageContext.request.contextPath}/image/<s:property value="%{#usl[2]}"/>" alt="" />
												</s:else>
											</div>
											<div class="weui-media-box__bd">
												<h4 class="weui-media-box__title">
													<s:if test="#usl[1] == null">
														<s:property value="%{#usl[0]}" />
													</s:if>
													<s:else>
														<s:property value="%{#usl[1]}" />
													</s:else>
												</h4>
											</div>
											<div class="weui-cell__hd">
												<button class="weui-btn weui-btn_mini weui-btn_warn">未到场</button>
											</div>
										</div>
									</s:if>
								</s:iterator>
							</s:if>
							<%-- <s:else>
								<div class="weui-media-box" style="text-align: center;">
									暂无请假人员
								</div>
							</s:else> --%>
						</div>
						
						
					</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
		/* $(function(){
			$("#signuplist").hide();
			$("#signlist").hide();
			$("#leavelist").hide();
		}); */
		//加载
		$("#signupkey").click(function(){
			$("#signuplist").toggle();
			if($("#img1").attr('value')==2){
				$("#img1").attr('src','${pageContext.request.contextPath}/common/img/right.png');
				$("#img1").attr('value','1');
			}else{
				$("#img1").attr('src','${pageContext.request.contextPath}/common/img/bottom.png');
				$("#img1").attr('value','2');
			}
		});
		$("#signkey").click(function(){
			$("#signlist").toggle();
			if($("#img2").attr('value')==2){
				$("#img2").attr('src','${pageContext.request.contextPath}/common/img/right.png');
				$("#img2").attr('value','1');
			}else{
				$("#img2").attr('src','${pageContext.request.contextPath}/common/img/bottom.png');
				$("#img2").attr('value','2');
			}
		});
		$("#leavekey").click(function(){
			$("#leavelist").toggle();
			if($("#img3").attr('value')==2){
				$("#img3").attr('src','${pageContext.request.contextPath}/common/img/right.png');
				$("#img3").attr('value','1');
			}else{
				$("#img3").attr('src','${pageContext.request.contextPath}/common/img/bottom.png');
				$("#img3").attr('value','2');
			}
		});
		$("#unSignkey").click(function(){
			$("#unSignList").toggle();
			if($("#img4").attr('value')==2){
				$("#img4").attr('src','${pageContext.request.contextPath}/common/img/right.png');
				$("#img4").attr('value','1');
			}else{
				$("#img4").attr('src','${pageContext.request.contextPath}/common/img/bottom.png');
				$("#img4").attr('value','2');
			}
		});
		
		</script>
	</body>
</html>