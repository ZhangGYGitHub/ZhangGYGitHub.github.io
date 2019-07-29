<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>

	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title><s:property value="teaminfo.name"/></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/common/js/jweixin-1.0.0.js"></script><!-- 微信分享所需要引的js -->
		
		<style type="text/css">
			.img_fengxian {
				padding-top: 10px;
				width: 30px;
				height: 30px;
			}
			
			.img_touxiang {
				height: 60px;
				width: 60px;
				border-radius: 50%;
				padding: 10px;
			}
		</style>
	<body>
		<div align="center">
			 <s:if test="#position.positionName != \"队员\"">
				<a href="${pageContext.request.contextPath}/team/teaminfoAdd?teaminfo.id=${teaminfo.id}">
					<div id="icon">
							<s:if test="teaminfo.iconNewName == null || teaminfo.iconNewName == ''">  
						 		<img src="${pageContext.request.contextPath}/common/img/default.png" class="img_touxiang">
						 	</s:if>  
						    <s:else>  
						   		 <img src="${pageContext.request.contextPath}/image/${teaminfo.iconNewName}" class="img_touxiang">
						    </s:else>
							<div><font color="#000"><s:property value="teaminfo.name"/></font></div>
					</div>
				</a>
			</s:if>
			<s:else>
				<a href="${pageContext.request.contextPath}/team/teaminfoDetail?teaminfo.id=${teaminfo.id}">
					<div id="icon">
							<s:if test="teaminfo.iconNewName == null || teaminfo.iconNewName == ''">  
						 		<img src="${pageContext.request.contextPath}/common/img/default.png" class="img_touxiang">
						 	</s:if>  
						    <s:else>  
						   		 <img src="${pageContext.request.contextPath}/image/${teaminfo.iconNewName}" class="img_touxiang">
						    </s:else>
							<div><font color="#000"><s:property value="teaminfo.name"/></font></div>
					</div>
				</a>
			</s:else>
		</div>
		<div class="weui-cells weui-cells_form">
			<div class="weui-grids">
				<a href="${pageContext.request.contextPath}/notice/findTeamNList?tid=${teaminfo.id}" class="weui-grid js_grid">
					<div class="weui-grid__icon">
						<img src="${pageContext.request.contextPath}/common/img/gonggao.png" alt="">
					</div>
					<p class="weui-grid__label">
						通知
					</p>
				</a>
				<a href="${pageContext.request.contextPath}/race/showRaceList?teamId=${teaminfo.id}" class="weui-grid js_grid">
					<div class="weui-grid__icon">
						<img src="${pageContext.request.contextPath}/common/img/saicheng.png" alt="">
					</div>
					<p class="weui-grid__label">
						比赛
					</p>
				</a>
				<a href="${pageContext.request.contextPath}/train/showTrainList?teamId=${teaminfo.id}" class="weui-grid js_grid">
					<div class="weui-grid__icon">
						<img src="${pageContext.request.contextPath}/common/img/xunlian.png" alt="">
					</div>
					<p class="weui-grid__label">
						训练
					</p>
				</a>
				<a href="${pageContext.request.contextPath}/goods/findGList?tid=${teaminfo.id}" class="weui-grid js_grid">
					<div class="weui-grid__icon">
						<img src="${pageContext.request.contextPath}/common/img/wuzi.png" alt="">
					</div>
					<p class="weui-grid__label">
						物资
					</p>
				</a>
				<a href="${pageContext.request.contextPath}/team/teaminfoMember?teaminfo.id=${teaminfo.id}" class="weui-grid js_grid">
					<div class="weui-grid__icon">
						<img src="${pageContext.request.contextPath}/common/img/duiyuan.png" alt="">
					</div>
					<p class="weui-grid__label">
						成员
					</p>
				</a>
				<div id="full" class='weui-popup__container' >
					<div class="weui-popup__overlay"></div>
			      	<div class="weui-popup__modal"  style="background-color: #4D4D4D">
			      		<div>
			      			<img id="shareImg" src="${pageContext.request.contextPath}/common/img/share.jpg" width="100%">
			      		</div>
			      	</div>
			    </div>
				<a id="share" href="javascript:;" class="weui-grid js_grid open-popup" data-target="#full">
					<div class="weui-grid__icon">
						<img src="${pageContext.request.contextPath}/common/img/yaoqing.png" alt="">
					</div>
					<p class="weui-grid__label">
						分享
					</p>
				</a>
			</div>
			<s:if test="#position.positionName != \"队员\"">
				<a class="weui-cell weui-cell_access" href="${pageContext.request.contextPath}/apply/applyList?tid=${teaminfo.id}">
					<div class="weui-cell__bd">
						<p>查看申请&nbsp;
						<s:if test="#waitHandleList != null && #waitHandleList.size() > 0">
							<span class="weui-badge" style="position: absolute;"><s:property value="#waitHandleList.size()"/></span>
						</s:if>
						</p>
					</div>
					<div class="weui-cell__ft">
					</div>
				</a>
			</s:if>
			<s:if test="#position.positionName != \"队员\"">
				<a class="weui-cell weui-cell_access" href="${pageContext.request.contextPath}/team/teamPicture?teaminfo.id=${teaminfo.id}">
					<div class="weui-cell__bd">
						<p>全家福</p>
					</div>
					<div class="weui-cell__ft">
					</div>
				</a>
			</s:if>
			<s:if test="#position.positionName != \"队员\"">
				<a class="weui-cell weui-cell_access" href="${pageContext.request.contextPath}/clothes/clothesColourList?clothes.tid=${teaminfo.id}">
					<div class="weui-cell__bd">
						<p>队服颜色</p>
					</div>
					<div class="weui-cell__ft">
					</div>
				</a>
			</s:if>
			<div class="weui-cell">
				<div class="weui-cell__hd">
					<label class="weui-label">踢球位置</label>
				</div>
				<div  class="weui-cell__hd">
					<input class="weui-input" value="${myLocation.playerPosition}" id="playerPosition" type="text">
				</div>
			</div>
			<script type="text/javascript">
				$("#playerPosition").select({
					title: "请选择踢球位置",
					multi: true,
					max:3,
					items: ${locations}
				});
				
				$("#playerPosition").change(function(){
					var id = "${teaminfo.id}";
					// 用统计,的数量+1来表示选择的数量
					var str = $("#playerPosition").val();
					var c = ","; // 要计算的字符
					var regex = new RegExp(c, 'g'); // 使用g表示整个字符串都要匹配
					var result = str.match(regex);
					var count = !result ? 0 : result.length;
					count = count + 1;
					if (count > 3) {
						$.toptip('最多只能选择3个踢球位置', 'warning');
						return;
					} 
					$.post("${pageContext.request.contextPath}/team/changePlayerPosition",{"playerPosition":$("#playerPosition").val(),"teaminfo.id":id},function(data){
						var json = JSON.parse(data);
						if(!json.data){
							$.toast("修改失败",'cancel');
						}
					});
				});
			</script>
			<!-- 
			<a class="weui-cell weui-cell_access" href="javascript:void(0)">
				<div class="weui-cell__bd">
					<p>投票调查</p>
				</div>
				<div class="weui-cell__ft">
				</div>
			</a>
			 -->
			<a class="weui-cell weui-cell_access" href="${pageContext.request.contextPath}/wxuser/showMyCard?teamId=${teaminfo.id}&userId=${currentUserId}">
				<div class="weui-cell__bd">
					<p>我的资料卡</p>
				</div>
				<div class="weui-cell__ft">
				</div>
			</a>
		</div>
		<div class="weui-msg__opr-area">
			<p class="weui-btn-area">
				<s:if test="#position.positionName == \"队长\"">
					<input type="button" id="dissolveTeam" class="weui-btn weui-btn_primary" value="解散该球队" />
				</s:if>
				<s:else>
					<input type="button" id="quitTeam" class="weui-btn weui-btn_primary" value="退出该球队" />
				</s:else>
			</p>
		</div>
<script type="text/javascript">
	//退出球队
	$("#quitTeam").click(function() {
		$.confirm("确认退出该球队？", function() {
		  	//点击确认后的回调函数
		  	var teamId = ${teaminfo.id};
		  	//跳转不增加历史记录的页面
			fnUrlReplace("quitTeam?teaminfo.id=" + teamId);
		 	}, function() {
		  	//点击取消后的回调函数
	  	});
	});
	//解散球队
	$("#dissolveTeam").click(function() {
		$.confirm("确认解散该球队？", function() {
		  	//点击确认后的回调函数
		  	var teamId = ${teaminfo.id};
		  	//跳转不增加历史记录的页面
			fnUrlReplace("teaminfoDissolve?teaminfo.id=" + teamId);
		 	}, function() {
		  	//点击取消后的回调函数
	  	});
	});
	
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
		var teamId = '${teaminfo.id}';
		var protocol = window.location.protocol;//协议
		var host = window.location.host;//域名
		var icon = $("#icon img")[0].src; //分享图标
		var teamName = '${teaminfo.name}';
		var currentUserId = '${currentUserId}';
	    // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
	    wx.onMenuShareTimeline({
	        title: teamName+'诚邀您加入球队,一起畅游足球世界！', // 分享标题
	        link:protocol+"//"+host+"/rongqiu/team/shareUrl?uid="+currentUserId+"&teaminfo.id="+teamId,//分享链接,该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
	        imgUrl: icon+'' // 分享图标
	    });
	    // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
	    wx.onMenuShareAppMessage({
	        title: teamName+'诚邀您加入球队,一起畅游足球世界！', // 分享标题
	        link:protocol+"//"+host+"/rongqiu/team/shareUrl?uid="+currentUserId+"&teaminfo.id="+teamId,//分享链接,该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
	        imgUrl: icon+''// 分享图标
	    });
	});
	wx.error(function (res) {
	    //alert("error");
	});
</script>
</body>
</html>