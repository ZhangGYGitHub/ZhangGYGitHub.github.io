<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>
	<head>
		<title>我的球队</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	</head>
<body style="background-color: #F7F7F7;">
	<div class="weui-tab">
		<div class="weui-tab__bd">
			<div  id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
				
				<div class="weui-panel weui-panel_access">
					<div style="background-color: #EFEFF4" align="center">
						<h2 class="weui-panel__hd"><font color="#09BB07">我创建的球队</font></h2>
					</div>
					
					<div class="weui-panel__bd">
						<s:if test="#createTeams != null && #createTeams.size()>0">
			    			<s:iterator value="#createTeams" status="status" var="createTeam">
								<a  href="${pageContext.request.contextPath}/team/teaminfoManage?teaminfo.id=${createTeam[0]}" class="weui-cell weui-cell_access">
								   	<div class="weui-media-box weui-media-box_appmsg">
										<div class="weui-media-box__hd">
											<s:if test="#createTeam[3] == null || #createTeam[3] == ''">  
										 		<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/common/img/default.png">
										 	</s:if>  
										    <s:else>  
										    	<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/image/${createTeam[3]}">
										    </s:else>
										</div>
										<div class="weui-media-box__bd">
											<h4 class="weui-media-box__title">
											<s:if test="#createTeam[1].length()>10">  
										    	<s:property value="#createTeam[1].substring(0,10)"/>...  
										 	</s:if>  
										    <s:else> 
										    	<s:property value="#createTeam[1]"/> 
										    </s:else>
											</h4>
											<p class="weui-media-box__desc">队长:<s:property value="#createTeam[2]"/></p>
											<p class="weui-media-box__desc">宣言:
												<s:if test="#createTeam[4] != null && #createTeam[4] != '' ">  
											    	<s:property value="#createTeam[4]"/> 
											 	</s:if>  
											    <s:else> 
											    	暂无
											    </s:else>
											</p>
										</div>
									</div>
								</a>
						    </s:iterator>
						</s:if>
						
					</div>
					
				</div>
				
				<div class="weui-panel weui-panel_access">
					<div style="background-color: #EFEFF4" align="center">
						<h2 class="weui-panel__hd"><font color="#09BB07">我管理的球队</font></h2>
					</div>
					
					<div class="weui-panel__bd">
						<s:if test="#manageTeams != null && #manageTeams.size()>0">
			    			<s:iterator value="#manageTeams" status="status" var="manageTeam">
								<a  href="${pageContext.request.contextPath}/team/teaminfoManage?teaminfo.id=${manageTeam[0]}" class="weui-cell weui-cell_access">
						   			<div class="weui-media-box weui-media-box_appmsg">
										<div class="weui-media-box__hd">
											<s:if test="#manageTeam[3] == null || #manageTeam[3] == ''">  
										 		<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/common/img/default.png">
										 	</s:if>  
										    <s:else>  
										    	<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/image/${manageTeam[3]}">
										    </s:else>
										</div>
										<div class="weui-media-box__bd">
											<h4 class="weui-media-box__title">
												<s:if test="#manageTeam[1].length()>10">  
										    		<s:property value="#manageTeam[1].substring(0,10)"/>...  
											 	</s:if>  
											    <s:else>  
											   		<s:property value="#manageTeam[1]"/>
											    </s:else>
											</h4>
											<p class="weui-media-box__desc">队长:<s:property value="#manageTeam[2]"/></p>
											<p class="weui-media-box__desc">宣言:
												<s:if test="#manageTeam[4] != null && #manageTeam[4] != '' ">  
											    	<s:property value="#manageTeam[4]"/> 
											 	</s:if>  
											    <s:else> 
											    	暂无
											    </s:else>
											</p>
										</div>
									</div>
								</a>
						    </s:iterator>
						</s:if>
						
					</div>
					
				</div>
				
				<div class="weui-panel weui-panel_access">
					<div style="background-color: #EFEFF4" align="center">
						<h2 class="weui-panel__hd"><font color="#09BB07">我加入的球队</font></h2>
					</div>
					
					<div class="weui-panel__bd">
						<s:if test="#joinTeams != null && #joinTeams.size()>0">
			    			<s:iterator value="#joinTeams" status="status" var="joinTeam">
								<a  href="${pageContext.request.contextPath}/team/teaminfoManage?teaminfo.id=${joinTeam[0]}" class="weui-cell weui-cell_access">
						   			<div class="weui-media-box weui-media-box_appmsg">
										<div class="weui-media-box__hd">
											<s:if test="#joinTeam[3] == null || #joinTeam[3] == ''">  
										 		<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/common/img/default.png">
										 	</s:if>  
										    <s:else>  
										    	<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/image/${joinTeam[3]}">
										    </s:else>
										</div>
										<div class="weui-media-box__bd">
											<h4 class="weui-media-box__title">
											<s:if test="#joinTeam[1].length()>10">  
									    		<s:property value="#joinTeam[1].substring(0,10)"/>...  
										 	</s:if>  
										    <s:else>  
										   		<s:property value="#joinTeam[1]"/>
										    </s:else>
											</h4>
											<p class="weui-media-box__desc">队长:<s:property value="#joinTeam[2]"/></p>
											<p class="weui-media-box__desc">宣言:
												<s:if test="#joinTeam[4] != null && #joinTeam[4] != '' ">  
											    	<s:property value="#joinTeam[4]"/> 
											 	</s:if>  
											    <s:else> 
											    	暂无
											    </s:else>
											</p>
										</div>
									</div>
								</a>
						    </s:iterator>
						</s:if>
						
					</div>
					
				</div>
				
				
			</div>

		</div>	
	</div>	
</body>
</html>