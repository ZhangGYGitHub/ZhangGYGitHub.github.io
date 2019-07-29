<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>
	<head>
		<title>联赛列表</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/fonts/font-awesome.min.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>

	</head>
<body>
	<div class="weui-tab">
		<div class="weui-tab__bd">
		
			<!-- *************************************创建联赛*********************************************** -->
			<div class="weui-msg__opr-area">
				<p class="weui-btn-area">
					<a href="${pageContext.request.contextPath}/league/leagueAdd" class="weui-btn weui-btn_primary">创建联赛</a>
				</p>
			</div>
			
			<!-- *************************************我创建的联赛*********************************************** -->
			<div class="weui-panel weui-panel_access">
				<div align="center" class="titleclick">
					<h2 class="weui-panel__hd"><font color="#09BB07" size="3px">我创建的联赛(<s:property value="myLeagues.size()"/>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
					<img class="img-bottom" value="1" src="${pageContext.request.contextPath}/common/img/right.png" style="width:15px;hight:15px;margin-bottom: -2px" >
					</h2>
				</div>
				
				<div class="weui-panel__bd">
					<s:if test="myLeagues != null && myLeagues.size()>0">
		    			<s:iterator value="myLeagues" status="status" var="list">
							<a href="${pageContext.request.contextPath}/league/leagueEdit?id=${id}" class="weui-cell weui-cell_access">
								<div class="weui-media-box weui-media-box_appmsg" style="padding-top: 0px;padding-bottom: 0px;">
									<div class="weui-cell__hd">
										<div class="weui-media-box__bd">
											<h2>
												<div style="word-wrap:nowrap; overflow: hidden; text-overflow: ellipsis">
													<font color="#09BB07" style="padding-bottom:20px">
														${name}
													</font>
												</div>
											</h2>
											<h4 class="weui-media-box__title">
												球队数:${teamNum}
											</h4>
										</div>
									</div>
								</div>
							</a>
						</s:iterator>
					</s:if>
				</div>
			</div>
			
			<!-- *************************************我加入的联赛*********************************************** -->
			<div class="weui-panel weui-panel_access">
				<div align="center" class="titleclick">
					<h2 class="weui-panel__hd"><font color="#09BB07" size="3px">我加入的联赛(<s:property value="joinLeagues.size()"/>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
							<img class="img-bottom" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px" >
					</h2>
				</div>
				
				<div id="join_leagues" class="weui-panel__bd">
					<s:if test="joinLeagues != null && joinLeagues.size()>0">
		    			<s:iterator value="joinLeagues" status="status" var="list">
							<a href="${pageContext.request.contextPath}/league/showBillboard?id=${list.id}" class="weui-cell weui-cell_access">
								<div class="weui-media-box weui-media-box_appmsg" style="padding-top: 0px;padding-bottom: 0px;">
									<div class="weui-cell__hd">
										<div class="weui-media-box__bd">
											<h2>
												<div style="word-wrap:nowrap; overflow: hidden; text-overflow: ellipsis">
													<font color="#09BB07" style="padding-bottom:20px">
														${name}
													</font>
												</div>
											</h2>
											<h4 class="weui-media-box__title">
												球队数:${teamNum}
											</h4>
											<h4 class="weui-media-box__title" style="margin-bottom:0">简&nbsp;&nbsp;介:
												<s:if test="#list.introduction != null && !#list.introduction.equals('')">
									            	${introduction}
									            </s:if>
									            <s:else>
									            	暂无简介
									            </s:else>
											</h4>							            
										</div>
							         </div>	
							     </div>
							</a>
					    </s:iterator>
					</s:if>
				</div>
			</div>
			
			<!-- *************************************比分编辑*********************************************** -->
			<div class="weui-panel weui-panel_access">
				<div align="center" class="titleclick">
					<h2 class="weui-panel__hd"><font color="#09BB07" size="3px">比分编辑(<s:property value="myReferee.size()"/>)&nbsp;&nbsp;&nbsp;&nbsp;</font>
					<img class="img-bottom" value="1" src="${pageContext.request.contextPath}/common/img/right.png" style="width:15px;hight:15px;margin-bottom: -2px" >
					</h2>
				</div>
				
				<div class="weui-panel__bd">
				<!-- sql查询得到列表leagueScore:0lid 1联赛name 2裁判num -->
					<s:if test="leagueScore != null && leagueScore.size()>0">
		    			<s:iterator value="leagueScore" status="status" var="list">
							<a href="${pageContext.request.contextPath}/league/leagueScore?id=${list[0]}&flag=0" class="weui-cell weui-cell_access">
								<div class="weui-media-box weui-media-box_appmsg" style="padding-top: 0px;padding-bottom: 0px;">
									<div class="weui-cell__hd">
										<div class="weui-media-box__bd">
											<h2>
												<div style="word-wrap:nowrap; overflow: hidden; text-overflow: ellipsis">
													<font color="#09BB07" style="padding-bottom:20px">
														${list[1]}
													</font>
												</div>
											</h2>
											<h4 class="weui-media-box__title">
												裁判数:${list[2]}
											</h4>
										</div>
									</div>
								</div>
							</a>
						</s:iterator>
					</s:if>
				</div>
			</div>
		
		</div>	
	</div>	
	<script type="text/javascript">
		//下拉展示
		$(".weui-panel__bd").hide();
		$("#join_leagues").show();
		$(".titleclick").click(function(){
			var img = $(this).children("h2").children("img");
		 	$(this).next().toggle();
			if(img.attr('value') == 2){
				img.attr('src','${pageContext.request.contextPath}/common/img/right.png');
				img.attr('value','1');
			}else{
				img.attr('src','${pageContext.request.contextPath}/common/img/bottom.png');
				img.attr('value','2');
			}
		});
	</script>
</body>
</html>