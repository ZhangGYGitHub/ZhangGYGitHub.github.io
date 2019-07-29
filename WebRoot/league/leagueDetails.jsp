 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<%@ taglib prefix="se" uri="/WEB-INF/security.tld"%>
<!DOCTYPE html>
<html>

	<head>
		<title>联赛详情</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	</head>

	<body>
		<div class="weui-cells weui-cells_form">
			<div class="weui-cell">
				<div class="weui-cell__hd"><label class="weui-label">联赛名称</label></div>
				<div class="weui-cell__bd">
					${league.leagueName}
				</div>
			</div>
			<div class="weui-cell" >
				<div class="weui-cell__hd">
					<label class="weui-label">赛程状态</label>
				</div>
				<div class="weui-cell__bd">
					<s:if test="league.isOpen == 0">开启</s:if>
					<s:else>关闭</s:else>
				</div>
			</div>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">描述</label>
				</div>
				<div class="weui-cell__bd">
					${league.description}
				</div>
			</div>
			<s:if test="flag == 1">
				<div class="weui-cell">
					<div class="weui-cell__hd">
						<label class="weui-label">创建人</label>
					</div>
					<div class="weui-cell__bd">
						${leagueObj[0]}
					</div>
				</div>
			</s:if>
		</div>
		<s:if test="flag == 0">
		<div class="button_sp_area" align="center">
			<button id="btn1" onclick="edit(${league.id})" class="weui-btn weui-btn_mini weui-btn_primary" style="margin-left: 25px">编辑</button>
			<button onclick="del(${league.id})" class="weui-btn weui-btn_mini weui-btn_warn">删除</button>
			<button onclick="sharepop();"style="margin-right:15%" class="weui-btn weui-btn_mini weui-btn_plain-primary">分享邀请</button>
		</div>
		
		<div id="full" class='weui-popup__container' >
			<div class="weui-popup__overlay"></div>
	      	<div class="weui-popup__modal"  style="background-color: #4D4D4D">
	      		<div>
	      			<img id="shareImg" src="${pageContext.request.contextPath}/common/img/share.jpg" width="100%">
	      			<input type="hidden" value="">
	      		</div>
	      	</div>
	    </div>
		
		<div class="weui-panel weui-panel_access">
			<div class="weui-panel__bd">
				<s:if test="manageTeamList != null && manageTeamList.size() > 0 || createTeamList != null && createTeamList.size() > 0">
					<div class="weui-cell" >
						<div class="weui-cell__hd">
							<label>添加我的球队到此联赛</label>
						</div>
					</div>
				</s:if>
					<s:if test="createTeamList != null && createTeamList.size()>0">
		    			<s:iterator value="createTeamList" status="status" var="createTeam">
						   	<div id="div_${createTeam[0]}" class="weui-media-box weui-media-box_appmsg">
								<div class="weui-media-box__hd">
									<s:if test="#createTeam[3] == null || #createTeam[3] == ''">  
								 		<img id="img_${createTeam[0]}" style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/common/img/default.png">
								 	</s:if>  
								    <s:else>  
								    	<img id="img_${createTeam[0]}" style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/image/${createTeam[3]}">
								    </s:else>
								</div>
								<div class="weui-media-box__bd" onclick="showDetail(<s:property value="#createTeam[0]"/>)">
									<h4 id="teamName_${createTeam[0]}" class="weui-media-box__title">
									<s:if test="#createTeam[1].length()>10">  
								    	<s:property value="#createTeam[1].substring(0,10)"/>...  
								 	</s:if>  
								    <s:else> 
								    	<s:property value="#createTeam[1]"/> 
								    </s:else>
									</h4>
									<p id="dz_${createTeam[0]}" class="weui-media-box__desc">队长:<s:property value="#createTeam[2]"/></p>
									<p class="weui-media-box__desc">宣言:
										<s:if test="#createTeam[4] != null && #createTeam[4] != '' ">  
									    	<s:property value="#createTeam[4]"/> 
									 	</s:if>  
									    <s:else> 
									    	暂无
									    </s:else>
									</p>
								</div>
								<div id="team_<s:property value="#createTeam[0]"/>">
									<button class="weui-btn weui-btn_mini weui-btn_primary" onclick="apply(<s:property value="#createTeam[0]"/>);" >加入</button>
								</div>
							</div>
					    </s:iterator>
					</s:if>
					<s:if test="manageTeamList != null && manageTeamList.size()>0">
		    			<s:iterator value="manageTeamList" status="status" var="manageTeam">
					   			<div id="div_${manageTeam[0]}" class="weui-media-box weui-media-box_appmsg">
									<div class="weui-media-box__hd">
										<s:if test="#manageTeam[3] == null || #manageTeam[3] == ''">  
									 		<img id="img_${manageTeam[0]}" style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/common/img/default.png">
									 	</s:if>  
									    <s:else>  
									    	<img id="img_${manageTeam[0]}" style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/image/${manageTeam[3]}">
									    </s:else>
									</div>
									<div class="weui-media-box__bd" onclick="showDetail(<s:property value="#manageTeam[0]"/>)" >
										<h4 id="teamName_${manageTeam[0]}" class="weui-media-box__title">
											<s:if test="#manageTeam[1].length()>10">  
									    		<s:property value="#manageTeam[1].substring(0,10)"/>...  
										 	</s:if>  
										    <s:else>  
										   		<s:property value="#manageTeam[1]"/>
										    </s:else>
										</h4>
										<p id="dz_${manageTeam[0]}" class="weui-media-box__desc">队长:<s:property value="#manageTeam[2]"/></p>
										<p class="weui-media-box__desc">宣言:
											<s:if test="#manageTeam[4] != null && #manageTeam[4] != '' ">  
										    	<s:property value="#manageTeam[4]"/> 
										 	</s:if>  
										    <s:else> 
										    	暂无
										    </s:else>
										</p>
									</div>
									<div id="team_<s:property value="#manageTeam[0]"/>">
										<button class="weui-btn weui-btn_mini weui-btn_primary" onclick="apply(<s:property value="#manageTeam[0]"/>);" >加入</button>
									</div>
								</div>
					    </s:iterator>
					</s:if>
				</div>
			</div>
	</s:if>
		<div class="weui-panel weui-panel_access">
			<div class="weui-panel__bd">
				<div id="handleDiv" class="weui-cell" >
					<div class="weui-cell__hd">
						<label>已加入球队列表</label>
					</div>
				</div>
				<s:if test="teamList !=null && teamList.size()>0">
					<s:iterator value="teamList" status="status" var="obj">
						<div id="teamList_${obj[3]}" class="weui-media-box weui-media-box_appmsg">
							<div class="weui-media-box__hd" >
									<s:if test="#obj[1] == null || #obj[1] == ''">  
								 		<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/common/img/default.png">
								 	</s:if>  
								    <s:else>  
								    	<img style="border-radius:50%" height="60px" width="60px"  src="${pageContext.request.contextPath}/image/${obj[1]}">
								    </s:else>
							</div>
							<div class="weui-media-box__bd" onclick="showDetail(${obj[3]})" >
								<h4 class="weui-media-box__title"><s:property value="#obj[0]"/></h4>
								<p class="weui-media-box__desc">队长:<s:property value="#obj[2]"/></p>
							</div>
							<s:if test="flag == 0">
							<div class="weui-cell__hd" >
								<button class="weui-btn weui-btn_mini weui-btn_warn" onclick="delout(${obj[3]});">移除</button>
							</div>
							</s:if>
						</div>
					</s:iterator>
				</s:if>
			</div>
		</div>
		<script type="text/javascript">
		lid = ${league.id};
		
		function sharepop(){
			$("#full").popup();
		}
		
		$("#shareImg").click(function(){
			$.closePopup();
		});
		
		// 微信分享配置
		wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，
		    appId: '${appId}', // 必填，公众号的唯一标识
		    timestamp: '${timestamp}', // 必填，生成签名的时间戳
		    nonceStr: '${nonceStr}', // 必填，生成签名的随机串
		    signature: '${signature}',// 必填，签名
		    jsApiList: ['onMenuShareAppMessage','onMenuShareTimeline'] // 必填，需要使用的JS接口列表
		});
		wx.ready(function(){
			var protocol = window.location.protocol;//协议
			var host = window.location.host;//域名
			var leagueName = '${league.leagueName}';
			var lid = ${league.id};
		    // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
		    wx.onMenuShareTimeline({
		        title: leagueName+'诚邀您加入联赛,一起畅游足球世界！', // 分享标题
		        link:protocol+"//"+host+"/rongqiu/league/shareUrl?lid="+lid,//分享链接,该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		        imgUrl: '' // 分享图标
		    });
		    // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
		    wx.onMenuShareAppMessage({
		        title: leagueName+'诚邀您加入联赛,一起畅游足球世界！', // 分享标题
		        link:protocol+"//"+host+"/rongqiu/league/shareUrl?lid="+lid,//分享链接,该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		        imgUrl: ''// 分享图标
		    });
		});
		wx.error(function (res) {
		    //alert("error");
		});
		
		//查看球队详情
		function showDetail(id) {
				window.location.href="${pageContext.request.contextPath}/team/teaminfoDetail?teaminfo.id="+id;
			}
		//编辑
		function edit(id){
			window.location.href ="${pageContext.request.contextPath}/league/leagueAdd?id="+id;
		}
		//删除
		function del(id){
			$.confirm("确认删除？","消息确认框", function() {
				//跳转不增加历史记录的页面
       			fnUrlReplace("${pageContext.request.contextPath}/league/leagueDelete?id="+id);
			}, function() {
			  	//点击取消后的回调函数
		 	});
		}
		
		//申请
		function apply(tid){
			$.confirm("是否申请加入该联赛?", function() {
		  		//点击确认后的回调函数
		  		$.post("${pageContext.request.contextPath}/league/applyLeague",{"teamId":tid,"lid":lid},function(data){
		  			var json = JSON.parse(data);
		  			if(json.data == 0){
		  				//0代表成功处理
		  				//$.toast("加入成功！","text");
		  				//动态拼接
		  				var currentDiv = $("#div_"+tid);//当前处理div
						var firstDiv = $("#handleDiv");
						var img = $("#img_"+tid)[0].src;//头像
						var teamName = $("#teamName_"+tid).text();//球队名称
						var dz = $("#dz_"+tid).text();//队长
						var $div = '<div id="teamList_'+tid+'" class="weui-media-box weui-media-box_appmsg">'
							+'<div class="weui-media-box__hd" >'
							+'<img style="border-radius:50%" height="60px" width="60px"  src="'+img+'">'
							+'</div>'
							+'<div class="weui-media-box__bd" onclick="showDetail('+tid+')" >'
							+'<h4 class="weui-media-box__title">'+teamName+'</h4>'
							+'<p class="weui-media-box__desc">'+dz+'</p>'
							+'</div>'
							+'<div class="weui-cell__hd" >'
							+'<button class="weui-btn weui-btn_mini weui-btn_warn" onclick="delout('+tid+');">移除</button>'
							+'</div>'
							+'</div>';
						firstDiv.after($div);//添加到div中
						//移除当前div
						currentDiv.remove();
		  			} else if (json.data == 1){
		  				//1代表处理失败
		  				$.toast("申请失败！",'cancel');
		  			}else if (json.data == 2){
		  				//2代表代处理或者已同意
		  				$.toast("请不要重复申请！","text");
		  			}else if (json.data == 3){
		  				//3代表联赛已关闭
		  				$.toast("很遗憾！联赛已经关闭了！","text");
		  			}
		  		});
		  		
		  	}, function() {
		  		//点击取消后的回调函数
		  	}); 
		};
		
		//移除队伍
		function delout(tid){
			$.confirm("确认移除该球队?", function() {
		  		//点击确认后的回调函数
		  		$.post("${pageContext.request.contextPath}/league/leagueDelOut",{"teamId":tid,"lid":lid},function(data){
		  			var json = JSON.parse(data);
		  			if(json.data == 0){
		  				//0代表成功处理
		  				//$.toast("移除成功！","text");
		  				$("#teamList_"+tid).remove();
		  				
		  			} else {
		  				//1代表处理失败
		  				$.toast("移除失败！",'cancel');
		  			}
		  		});
		  		
		  	}, function() {
		  		//点击取消后的回调函数
		  	});
		}
		</script>
	</body>

</html>