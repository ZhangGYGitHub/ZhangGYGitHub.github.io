<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
	<head>
		<title>我</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="pragma" content="no-cache"> 
		<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"> 
		<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/fonts/font-awesome.min.css" />
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<style>
			.div-name {
				font-weight:bold;
				color: #ffffff;
				font-size:20px;
			}
			.divfont {
				color: #ffffff;
				font-size:15px;
			}
			/* 控制a链接不变色 */
			.weui-media-box2{padding:15px;position:relative}
			.weui-media-box2:before{content:" ";position:absolute;left:0;top:0;right:0;height:1px;border-top:1px solid #e5e5e5;color:#e5e5e5;-webkit-transform-origin:0 0;transform-origin:0 0;-webkit-transform:scaleY(.5);transform:scaleY(.5);left:15px}
			.weui-media-box2:first-child:before{display:none}
			a.weui-media-box2{color:#000;-webkit-tap-highlight-color:rgba(0,0,0,0)}
		
		</style>
	</head>
<body>
	<div class="weui-tab">
		<div style="padding-bottom: 50px;" class="weui-tab__bd">
			<div  id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
				<div style="background:url(${pageContext.request.contextPath}/common/img/bg.jpg);height:170px;">
				<a class="weui-media-box2 weui-media-box_appmsg">
					<div class="weui-media-box__hd" style="padding:20px auto">
						<img id="img-1" style="border-radius:50% " height="70px" width="70px" 
						class="weui-media-box__thumb" onerror="this.src='${pageContext.request.contextPath}/common/img/default.png"
						 src="
						 	<s:if test="wxuser.headPortrait == null || wxuser.headPortrait == ''">
						 		<s:property value='wxuser.headPortraitNew'/>
						 	</s:if>
						 	<s:else>
						 		${pageContext.request.contextPath}/image/<s:property value='wxuser.headPortraitNew'/>
						 	</s:else>
						 " alt="">
					</div>
					<div class="weui-media-box__bd" id="to_edit">
						<div style="clear: both;">
							<div class="div-name">
								<s:property value='wxuser.nickname'/>
							</div>
						</div>
						<div style="clear: both;">
							<div class="divfont">
								<s:property value="locationString"/>
							</div>
						</div>
						<div style="clear: both;">
							<div class="divfont">
								<s:if test="wxuser.habitfoot == 1">
									左脚
								</s:if>
								<s:else>
									右脚
								</s:else>
							</div>
						</div>
						<div style="clear: both;">
							<div class="divfont">
								<s:if test="wxuser.perAutograph == null || wxuser.perAutograph == ''">
									暂无个性签名
								</s:if>
								<s:else>
									<s:property value="wxuser.perAutograph"/>
								</s:else>
							</div>
						</div>
					</div>
				</a>
				<div class="weui-flex">
					<div class="weui-flex__item" style="text-align: center;color: #09BB07;">
						<i class="fa fa-thumbs-o-up" style="width: 20px;height: 20px;"></i><s:property value="wxuser.likeNum"/>
					</div>
					<div class="weui-flex__item" style="text-align: center;" id="my_apply">
						<img src="${pageContext.request.contextPath}/common/img/my_apply.png" width="20px;" height="20px;"/>
					</div>
					<div class="weui-flex__item" style="text-align: center;" id="my_fenxiang">
						<img src="${pageContext.request.contextPath}/common/img/mingpian.png" width="20px;" height="20px;"/>
					</div>
				</div>
				</div>
				<div class="weui-grids">
					<a href="${pageContext.request.contextPath}/team/teaminfoAdd" class="weui-grid">
						<div class="weui-grid__icon">
				      		<img src="${pageContext.request.contextPath}/common/img/createTeam.png" alt="">
				    	</div>
				    	<p class="weui-grid__label">
				      		创建球队
				    	</p>
				  	</a>
				  	<a href="${pageContext.request.contextPath}/team/toQueryTeam" class="weui-grid">
				    	<div class="weui-grid__icon">
				      		<img src="${pageContext.request.contextPath}/common/img/join.png" alt="">
				    	</div>
				    	<p class="weui-grid__label">
				      		加入球队
				    	</p>
				  	</a>
				  	<a href="${pageContext.request.contextPath}/league/leagueList" class="weui-grid">
				    	<div class="weui-grid__icon">
				      		<img src="${pageContext.request.contextPath}/common/img/liansai.png" alt="">
				    	</div>
				    	<p class="weui-grid__label">
				      		我的联赛
				    	</p>
				  	</a>
				</div>
				<div class="weui-panel weui-panel_access">
					<%-- <div style="background-color: #EFEFF4" align="center" id="my_create">
						<h2 class="weui-panel__hd">
							<font color="#09BB07">我创建的球队(<s:property value="#createTeams.size()"/>)</font>
						</h2>
					</div> --%>
					<div align="center" id="my_create">
						<h2 class="weui-panel__hd">
							<font color="#09BB07" size="3px">我创建的球队(<s:property value="#createTeams.size()"/>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
							<img id="img1" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px" >
						</h2>
					</div>
					<div class="weui-panel__bd" id="my_create_team">
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
					<%-- <div style="background-color: #EFEFF4" align="center" id="my_manage">
						<h2 class="weui-panel__hd">
							<font color="#09BB07">我管理的球队(<s:property value="#manageTeams.size()"/>)</font>
						</h2>
					</div> --%>
					<div align="center" id="my_manage">
						<h2 class="weui-panel__hd">
							<font color="#09BB07" size="3px">我管理的球队(<s:property value="#manageTeams.size()"/>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
							<img id="img2" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px" >
						</h2>
					</div>
					<div class="weui-panel__bd" id="my_manage_team">
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
					<%-- <div style="background-color: #EFEFF4" align="center" id="my_join">
						<h2 class="weui-panel__hd">
							<font color="#09BB07">我加入的球队(<s:property value="#joinTeams.size()"/>)</font>
						</h2>
					</div> --%>
					<div align="center" id="my_join">
						<h2 class="weui-panel__hd">
							<font color="#09BB07" size="3px">我加入的球队(<s:property value="#joinTeams.size()"/>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
							<img id="img3" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px" >
						</h2>
					</div>
					<div class="weui-panel__bd" id="my_join_team">
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
					</a>
	    		</div>
	  		</div>
			<div class="weui-tabbar">
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
						<img src="${pageContext.request.contextPath}/common/img/manage.png" alt="">
					</div>
					<p class="weui-tabbar__label">通知</p>
				</a>
				<a href="javascript:void(0)" class="weui-tabbar__item">
					<div class="weui-tabbar__icon">
						<img src="${pageContext.request.contextPath}/common/img/my_select.png" alt="">
					</div>
					<p class="weui-tabbar__label">我</p>
				</a>
			</div>
		</div>
	</div>
	<div class="weui-gallery" style="display: none" id="show-img">
		<span class="weui-gallery__img" style="background-image: url(
			<s:if test="wxuser.headPortrait == null || wxuser.headPortrait == ''">
				<s:property value='wxuser.headPortraitNew'/>
			</s:if>
			<s:elseif test="wxuser.headPortraitNew == null || wxuser.headPortraitNew == ''">
				${pageContext.request.contextPath}/common/img/default.png
			</s:elseif>
			<s:else>
				${pageContext.request.contextPath}/image/<s:property value='wxuser.headPortraitNew'/>
			</s:else>
			);"></span>
	</div>
	<script type="text/javascript">
		/* $(function(){
			$("#my_create_team").hide();
			$("#my_manage_team").hide();
			$("#my_join_team").hide();
		}); */
		$("#img-1").click(function(){
			$("#show-img").show();
		});
		$("#show-img").click(function(){
			$("#show-img").hide();
		});
		$("#to_edit").click(function(){
			window.location.href = "${pageContext.request.contextPath}/wxuser/userAdd";
		});
		$("#my_apply").click(function(){
			window.location.href = "${pageContext.request.contextPath}/apply/myApply";
		});
		$("#my_fenxiang").click(function(){
			window.location.href = "${pageContext.request.contextPath}/personalcard/selectTheme.jsp";
		});
		$("#my_create").click(function(){
			$("#my_create_team").toggle();
			if($("#img1").attr('value')==2){
				$("#img1").attr('src','${pageContext.request.contextPath}/common/img/right.png');
				$("#img1").attr('value','1');
			}else{
				$("#img1").attr('src','${pageContext.request.contextPath}/common/img/bottom.png');
				$("#img1").attr('value','2');
			}
		});
		$("#my_manage").click(function(){
			$("#my_manage_team").toggle();
			if($("#img2").attr('value')==2){
				$("#img2").attr('src','${pageContext.request.contextPath}/common/img/right.png');
				$("#img2").attr('value','1');
			}else{
				$("#img2").attr('src','${pageContext.request.contextPath}/common/img/bottom.png');
				$("#img2").attr('value','2');
			}
		});
		$("#my_join").click(function(){
			$("#my_join_team").toggle();
			if($("#img3").attr('value')==2){
				$("#img3").attr('src','${pageContext.request.contextPath}/common/img/right.png');
				$("#img3").attr('value','1');
			}else{
				$("#img3").attr('src','${pageContext.request.contextPath}/common/img/bottom.png');
				$("#img3").attr('value','2');
			}
		});
	</script>
	
</body>
</html>