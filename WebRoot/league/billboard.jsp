<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<%@ taglib prefix="se" uri="/WEB-INF/security.tld"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>榜单</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/my-biliboard.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/common/fonts/font-awesome.min.css">
	<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
	<script src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
</head>
<body>
    <div class="weui-tab">
        <div class="weui-tab__bd">
            <!-- ++++++++++++++++++++++++++++++++++++++ 赛程 ++++++++++++++++++++++++++++++++++++++ -->
            <div id="match_" class="weui-tab__bd-item weui-tab__bd-item--active">
               <div class="weui-cell title fixed">
                  <div class="weui-cell__hd">
                      <span class="fa fa-book"></span>
                  </div>
                  <div class="weui-cell__bd">
                      <p>赛程安排</p>
                  </div>
                  <s:if test="league.attachmentNewName == null || league.attachmentNewName == ''">
                  </s:if>
                  <s:else>
                 	 <a style="display:block;float:left;margin:0 2em;color:white" href="${pageContext.request.contextPath}/league/leagueFile?id=${league.id}">
                     	<span class="fa fa-download"></span>
                     </a> 
                  </s:else>                
               </div>
               <div class="placeholder"></div>
               <s:if test="turns != null && turns.size() > 0">
                	<s:iterator value="turns" status="status" var="list">
                		<div class="weui-panel weui-panel_access fastclick" turn="${turn}">
							<div align="center" class="titleclick">
								<h2 class="weui-panel__hd"><font color="#09BB07" size="3px">第<s:property value="#status.index+1"/>轮&nbsp;&nbsp;&nbsp;&nbsp;</font>
									<img class="img-bottom" val="2" src="${pageContext.request.contextPath}/common/img/right.png" style="width:15px;hight:15px;margin-bottom: -2px; border:none" >
								</h2>
							</div>
							<div class="weui-panel__bd" hidden="hidden" >
							</div>
                		</div>
                	</s:iterator>
               </s:if>
               <div class="placeholder"></div>
			</div>  
           	<script>
           		var turn;
				var onwork = false //防止快速点击
           		$(".titleclick").click(function(){      
  					var img = $(this).parent().find("div h2 .img-bottom");
  					turn = $(this).parent().attr("turn");
  					//下拉框
  					if(img.attr("val") == 2){
  						img.attr("src", "${pageContext.request.contextPath}/common/img/right.png");
           				$(this).parent().find(".weui-panel__bd").hide();
           				img.attr("val", 1);
  					}else{
  						img.attr("src", "${pageContext.request.contextPath}/common/img/bottom.png");
           				$(this).parent().find(".weui-panel__bd").show();
           				img.attr("val", 2);
  					}
  					//post获取比赛
  					if($(this).parent().find(".weui-panel__bd").children().length == 0){
  						$.showLoading("加载中");
  						if(onwork) return;//防止快速点击
  						onwork = true;//防止快速点击
  						$.post("ajaxGetMatch?turn=" + turn , function(data) {
  						  //sql查询 0oneWin 1twoWin 2site 3startTime 4endTime 5oneName 6twoName 7onePic 8twoPic 9t1id 10t2id 11lmid
   						   var json = JSON.parse(data);
						   var list = json.data;
						   if(list != null && list.length > 0 ){
						   	  var num = list.length/2;
							  for(var i = 0; i < num; i++){
							  	var oneSrc = "${pageContext.request.contextPath}/image/" + list[i][7];
							  	var twoSrc = "${pageContext.request.contextPath}/image/" + list[i][8];
							  	if(list[i][7] == "") oneSrc = "${pageContext.request.contextPath}/common/img/default.png";
							  	if(list[i][8] == "") twoSrc = "${pageContext.request.contextPath}/common/img/default.png";
							  	if(list[i][0] == null) list[i][0] = 0;
							  	if(list[i][1] == null) list[i][1] = 0; 
							  	if(list[i][5] == null) list[i][5] = "未填写";
							  	if(list[i][6] == null) list[i][6] = "未填写"; 
							  	$(".weui-panel_access[turn="+turn+"] .weui-panel__bd").append(
							  		'<a href="${pageContext.request.contextPath}/feedback/leagueFeedBackList?lmid='+list[i][11]+'" style="text-decoration:none;color:black">'+
										'<div class="match-cell weui-cell_access" >'+
										    '<div class="match-left default-border">'+
												'<img class="match-img match-top_img" src="'+ oneSrc +'"/>'+
												'<img class="match-img match-bottom_img" src="'+ twoSrc +'"/>'+						
											'</div>'+
											'<div class="match-right">'+
												'<div><span class="match-team_name">'+ list[i][5] +'</span><span class="vs">VS</span><span class="match-team_name">'+ list[i][6] +'</span></div>'+
												'<div><span class="default-text">比分:</span><span class="default-score">'+ list[i][0] +'</span>-<span class="default-score">'+ list[i][1] +'</span><span class="default-text">场次:<span><span class="default-score">'+ list[i][2] +'</span></div>'+
											'</div>'+
											'<div class="float-clear"/>'+
										'</div>'+
										'<div class="default-line"></div>'+
									'</a>'
								);
							  }
							  $(".weui-panel_access[turn="+turn+"] div h2 .img-bottom").attr("src", "${pageContext.request.contextPath}/common/img/bottom.png");
							  $(".weui-panel_access[turn="+turn+"] .weui-panel__bd").show();
							}
							onwork = false;//防止快速点击
							$.hideLoading();
						});
						
  					}
           		})
           	</script>
        	
            <!-- +++++++++++++++++++++++++++++++++++ 积分榜 +++++++++++++++++++++++++++++++++++++++ -->
            <div id="score_" class="weui-tab__bd-item">
                <div class="weui-cell title fixed">
                    <div class="weui-cell__hd">
                        <span class="fa fa-trophy"></span>
                    </div>
                    <div class="weui-cell__bd">
                        <p>积分排名</p>
                    </div>
                </div>
                <div class="placeholder"></div>
                <!-- sql查询  0tname 1tpic 2win 3lose 4draw 5inNum 6outNum 7gd 8score -->
                <s:if test="scoreList != null && scoreList.size() > 0">
                	<s:iterator value="scoreList" status="status" var="list">    
               			<div class="rank-cell">               		
               				<div class="rank-left rank-cell">
               					<s:if test="#status.index+1 <= 3" >
               						<img class="rank-img" src="${pageContext.request.contextPath}/common/img/award<s:property value="#status.index+1"/>.png"/>
               					</s:if>
          						<s:else>
          							<img class="rank-num" src="${pageContext.request.contextPath}/common/img/award4.png"/>
          							<div class="rank-num"><s:property value="#status.index+1"/></div>
          						</s:else>
          						<s:if test="#list[1] != '' ">
	          						<img class="rank-head" src="${pageContext.request.contextPath}/image/${list[1]}"/>
	          					</s:if>
	          					<s:else>
	          						<img class="rank-head default-border" src="${pageContext.request.contextPath}/common/img/default.png"/>
	          					</s:else>
               				</div>
               				<div class="rank-right">
               					<div class="rank-name">${list[0]}</div>
               					<div class="default-space"><span class="default-text">总积分</span>:<span class="default-score">${list[8]}</span></div>
               					<div class="default-space">
               						<span class="default-text">胜</span>/
               						<span class="default-text">负</span>/
               						<span class="default-text">平</span>:
               						<span class="default-score">${list[2]}</span>/
               						<span class="default-score">${list[3]}</span>/
               						<span class="default-score">${list[4]}</span>
               					</div>
               					<div class="default-space">
               						<span class="default-text">进球</span>/
               						<span class="default-text">失球</span>/
               						<span class="default-text">净胜球</span>:
               					</div>
               					<div class="default-space">
            						<span class="default-score">${list[5]}</span>/
            						<span class="default-score">${list[6]}</span>/
              						<span class="default-score">${list[7]}</span>
               					</div>
               				</div>
               				<div class="float-clear"></div>
               				<div class="default-line"></div>
               			</div>
                	</s:iterator>
                </s:if>
                <div class="placeholder"></div>
            </div>
            <!-- +++++++++++++++++++++++++++++++++++++++ 射手榜 ++++++++++++++++++++++++++++++++++++++++++++ -->
            <div id="star_" class="weui-tab__bd-item">
                <div class="weui-cell title fixed">
                    <div class="weui-cell__hd">
                        <span class="fa fa-user"></span>
                    </div>
                    <div class="weui-cell__bd">
                        <p>射手排名</p>
                    </div>
                </div>
                <div class="placeholder"></div>
                <!-- sql查询  0id 1name 2num 3pic 4tname -->
                <s:if test="shootList != null && shootList.size() > 0">
                	<s:iterator value="shootList" status="status" var="list">
						<div class="rank-cell">
							<a href="${pageContext.request.contextPath}/league/showSoccer?soccer=${list[0]}" style="text-decoration:none;color:black">
		               			<div class="rank-left rank-cell">
		               				<s:if test="#status.index+1 <= 3">
		               					<img class="rank-img" src="${pageContext.request.contextPath}/common/img/award<s:property value="#status.index+1"/>.png"/>
		               				</s:if>
		          					<s:else>
		          						<img class="rank-num" src="${pageContext.request.contextPath}/common/img/award4.png"/>
		          						<div class="rank-num"><s:property value="#status.index+1"/></div>
		          					</s:else>
		          					<s:if test="#list[3] != '' ">
		          						<s:if test="#list[3].indexOf('http') != -1 ">
		          							<img class="rank-head" src="${list[3]}"/>
		          						</s:if>
		          						<s:else>
		          							<img class="rank-head" src="${pageContext.request.contextPath}/image/${list[3]}"/>
		          						</s:else>	          						
		          					</s:if>
		          					<s:else>
		          						<img class="rank-head default-border" src="${pageContext.request.contextPath}/common/img/default.png"/>
		          					</s:else>		
		               			</div>
	               				<div class="rank-right">
	               					<div class="rank-name">${list[1]}</div> 
	              					<div>
	              						<div class="default-text" style="width:3em;float:left;height:1em">球队：</div>
	              						<div class="default-text" style="overflow:hidden;">${list[4]}</div>
	              					</div>
	               					<div class="default-space">
	               						<span class="default-text">进球：<span class="default-score">${list[2]}</span></span>
	               					</div>
		               			</div>
	               				<div class="float-clear"></div>
	               				<div class="default-line"></div>
	               			</a>
	               		</div>
                	</s:iterator>
                </s:if>       
                <div class="placeholder"></div>
            </div>
        </div>
        <div class="weui-tabbar fixed tabbar">
            <a class="weui-navbar__item weui-bar__item--on" href="#match_">
                <span class="fa fa-book"></span>赛程
            </a>
            <a class="weui-navbar__item" href="#score_">
                <span class="fa fa-trophy"></span>积分榜
            </a>
            <a class="weui-navbar__item" href="#star_">
                <span class="fa fa-user"></span>射手榜
            </a>
        </div>
    </div>
</body>

</html>