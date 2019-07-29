<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="pg" uri="/WEB-INF/page.tld"%>
<!DOCTYPE html>
<html>
	<head>
		<title>编辑联赛</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/jquery-weui.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/common/css/demos.css">
		<script src="${pageContext.request.contextPath}/common/js/jquery-2.1.4.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/fastclick.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-weui.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jquery-form.js"></script>
		<script src="${pageContext.request.contextPath}/common/js/jweixin-1.0.0.js"></script> <!-- 微信分享所需要引的js -->
		<style>
			.v-line{
				position:inline-block;
				height:2em;
				width:2px;
				background:#ddd;
				margin:0 10px;
			}
			.result{
				height:9em;
				border:1px solid red;
			}
			.weui-tabbar{
				width:96%;
				margin:0.5em 2%;
			}
			.close-popup{
				width:100%;
			}
			.placeholder{
				width:100%;
				height:3.5em;
			}
			.league-match{
				margin: 1em;
			}
			.league-turn{
				color:#1C86EE;
			}
			.add-turn{
				width:96%;
				margin:0.5em 2%;
				
			}
			.match-btn{
				background:#1C86EE;
				color:white !important;
				-webkit-tap-highlight-color:rgba(6, 133, 252, 0.575);
			}
			.btn-group{
				padding-bottom:0;
			}
			.del-turn{
				float:right;
				position:absolute;
				right:1em;
				top:-2.5em;
			}
			.team-head{
				border-radius:50%;
				height:60px;
				width:60px;
			}
			.no-result{
				margin-top:3em;
				color:#A9A9A9;
				text-align:center;
			}
			.team-add-btn{
				margin-top:11px;
				border-radius:50%;
				height:40px !important;
				width:40px !important;
			}


        .fixed {
            width: 100%;
            float: left;
            position: fixed;
          }

        .search-btn {
            line-height: 1.8em;
            margin-right: 2.5em;
         }

        #score-full, #star-full {
            z-index: 5;
        }

        .tabbar{
            z-index: 3;
        }
        
        .msg-text {
        	font-size: 1.2em;
        }
        
        .search-close {
        	width: 96%;
        	margin: 0 2%;
        	position:fixed;
        	bottom: 0;
        }
		#src img{width: 5.1em;;height:5.1em;border:1px solid #ddd;margin:0.5em 0em 0.2em 1em;float:left}
		</style>
	</head>
	<body style="padding:0 0.5em">
		<!-- +++++++++++++++++++++++基本信息++++++++++++++++++++++++++++++++++++ -->
		<div class="weui-panel weui-panel_access">
			<div align="center" class="titleclick">
				<h2 class="weui-panel__hd"><font color="#09BB07" size="3px">基本信息&nbsp;&nbsp;&nbsp;&nbsp;</font>
				<img class="img-bottom" value="1" src="${pageContext.request.contextPath}/common/img/right.png" style="width:15px;hight:15px;margin-bottom: -2px">
				</h2>
			</div>
			<div class="weui-panel__bd" hidden="hidden">
				<form action="leagueSave" id="baseForm" method="post">
					<s:hidden name="league.id" />
					<s:hidden name="league.teamNum"/>
					<s:hidden name="league.attachmentNewName"/>
					<s:hidden name="league.attachmentOldName"/>
					<s:hidden name="league.createTime"/>
					<s:hidden name="league.creatorId"/>
					<s:hidden name="league.delState"/>
		
					<div class="weui-cells weui-cells_form">
						<div class="weui-cell">
							<div class="weui-cell__hd"><label class="weui-label">联赛名称</label></div>
							<div class="weui-cell__bd">
								<input id="LeagueName" name="league.name" value="${league.name}" class="weui-input my-valide" type="text" reg1="^.{2,30}$" desc="联赛名称2-30个任意字符，不能为空" placeholder="请输入名称" tip="已有同名联赛">
							</div>
						</div>				
						<div class="weui-cell weui-cell_switch">
							<div class="weui-cell__hd"><label class="weui-label">积分方式</label></div>
						    <div class="weui-cell__bd">一般(3/1/0)</div>
						    <div class="weui-cell__ft">
						      <input id="scoreTypeValue" type="text" name="league.scoreType" hidden="hidden" value="${league.scoreType}"/>
						    </div>
		  				</div>
						<div id="score_cal" hidden="hidden" style="margin:0 0.5em">
							<div class="weui-cell">
								<div class="weui-cell__hd"><label class="weui-label">胜</label></div>
								<div class="weui-cell__bd">
									<input id="win" class="weui-input" name="league.winScore" value="${league.winScore}" type="number" placeholder="请输入胜分" reg1="^(\d{1,3})|(-\d{1,3})$" desc="请输入合适的分数">
								</div>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd"><label class="weui-label">平</label></div>
								<div class="weui-cell__bd">
									<input id="draw" class="weui-input" name="league.drawScore" value="${league.drawScore}" type="number" placeholder="请输入平分" reg1="^(\d{1,3})|(-\d{1,3})$" desc="请输入合适的分数">
								</div>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd"><label class="weui-label">负</label></div>
								<div class="weui-cell__bd">
									<input id="lose" class="weui-input" name="league.loseScore" value="${league.loseScore}" type="number" placeholder="请输入负分" reg1="^(\d{1,3})|(-\d{1,3})$" desc="请输入合适的分数">
								</div>
							</div>
						</div>
						<script type="text/javascript">
							//积分类型选择
							$(".weui-cell_switch").click(function(){
								var type = $(".weui-cell_switch .weui-cell__bd");
								var score = $("#scoreTypeValue");
								if(score.val() == 0){
									type.text("自定义");
									$("#score_cal").show();
									$(this).find("#scoreTypeValue").val(1);
								}else{
									type.text("一般(3/1/0)");
									$("#win").val(3);
									$("#draw").val(1);
									$("#lose").val(0);
									$("#score_cal").hide();
									$(this).find("#scoreTypeValue").val(0);
								}
							});		
							if($("#scoreTypeValue").val() == 1){
								$(".weui-cell_switch .weui-cell__bd").text("自定义");
								$("#score_cal").show();
							}
						</script>
						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">上传附件</label>
							</div>
							<div class="weui-cell__bd">
								<input name="upload" id="file" value="" type="file">
							</div>
						</div>
						<s:if test="league.attachmentOldName != null">
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">已有附件：</label>
								</div>
								<div id="hasFile" class="weui-cell__bd" file="${league.attachmentOldName}">
									${league.attachmentOldName}
								</div>
							</div>
						</s:if>
						<div class="weui-cell">
						  <div class="weui-cell__bd">
						  	<span id="intro" hidden="hidden">${league.introduction}</span>
						    <textarea class="weui-textarea" name="league.introduction" id="introduce" value="" placeholder="联赛简介" rows="3"></textarea>
						    <div class="weui-textarea-counter"><span id="iNum">0</span>/50</div>
						  </div>
						</div>
						<script type="text/javascript">
							$("#introduce").val($("#intro").text());
							$("#iNum").text($("#intro").text().length);
							$("#introduce").keyup(function(){
								var iNum = $("#introduce").val().length;
								if(iNum <= 50){
									$("#iNum").text(iNum);
								} else{
									$("#introduce").val($("#introduce").val().substring(0, 50));
								}
							});
						</script>
						<div class="weui-msg__opr-area">
						<p class="weui-btn-area">
							<input type="button"  id="baseConfirm" class="weui-btn weui-btn_primary" value="提交" />
						</p>
					</div>
					</div>
				</form>
				<div class="weui-loadmore">
				<script type="text/javascript">
					var isOk = true;
					//联赛同名校验
					$(".my-valide").blur(function(data){
						var input = $(this);
						if($.trim(this.value) == "") return;
						$.post("ajaxValide" + this.id + "?name=" + $.trim(this.value), function(data){
						   	 if(JSON.parse(data).data != null){
						   	 	isOk = false;
						   	 	$.toptip(input.attr("tip"), "warning");
						   	 	input.focus();
						   	 }else{
						   	 	isOk = true;
						   	 }	  
						});
					});		
					$("#baseConfirm").click(function(){
						//************数据校验****************	//
						$("#baseForm").find("[reg1],[desc]").each(function(){
							var a=$(this);
							var reg = new RegExp(a.attr("reg1"));
							var objValue = a.val();
							if(!reg.test(objValue)){
									var tip = a.attr("desc").toString();
								  		$.toptip(tip, 'warning');
								  		a.focus();//获取焦点
								  		isOk = false;
										return;
							}
						});
						//post保存
					  	if(isOk){
					  		var options ={
					        	success:function(data){
					        		$.hideLoading();
					        		var json = $.parseJSON(data);
					        		if(json.data){
					        			$.toast("保存成功");
					        			//上传后界面文件处理
					        			var obj = document.getElementById('file');
				        				if(obj.value == ""){
				        					$("#hasFile").text($(this).attr("file"));
				        					$("#hasFile").attr("file", $(this).text())
				        				}else{
				        					var file = obj.value.split("\\");
				        					$("#hasFile").text(file[file.length-1]);
											obj.outerHTML = obj.outerHTML; 
						        			//$("#file").attr("value"," ");
				        				}
				        				//设为true 可以重复保存
				        				isOk = true;
									}else{
										$.toast("保存失败","cancel"); 
									}
								}
					        }
					        $("#baseForm").ajaxSubmit(options);
					        if($("#file").val() != "")
					        	$.showLoading("文件上传中");
					        else
					        	$.showLoading("保存中");
					  	}
					});
				</script>     
			</div>
		</div>
		<!-- +++++++++++++++++++++++赛程安排++++++++++++++++++++++++++++++++++++ -->
		<div id="team_full" class="weui-popup__container">
			<div class="weui-popup__overlay"></div>
			<div class="weui-popup__modal">
				<div class="placeholder"></div>
					<div class="popup-content" id="searchResult">
						<div id="noResult" class="no-result" hidden="hidden"><h3>无搜索结果</h3></div>
					</div>
				<div class="placeholder"></div>
			</div>
			<div class="weui-tab">
				<div class="weui-search-bar weui-navbar" id="searchBar">
					<form class="weui-search-bar__form">
						<div class="weui-search-bar__box">
							<i class="weui-icon-search"></i>
							<input type="search" class="weui-search-bar__input" id="searchInput" placeholder="搜索" required="">
							<a href="javascript:" class="weui-icon-clear" id="searchClear"></a>
						</div>
						<label class="weui-search-bar__label" id="searchText">
							<i class="weui-icon-search"></i>
							<span>搜索</span>
						</label>
					</form>
					<a href="javascript:" class="weui-search-bar__cancel-btn" id="searchCancel">取消</a>
				</div>
				<div class="placeholder"></div>
				<div class="weui-tabbar">
					<a href="javascript:;" class="weui-btn weui-btn_primary close-popup">关闭</a>
				</div>		
			</div>
			<script type="text/javascript">
				var oldword;
				$("#searchInput").bind("input propertychange",function(){
					var name = $.trim(this.value);
					if($.trim(this.value).length != 0 && oldword != name){
						oldword = name;
						$("#searchResult").find(".weui-media-box").remove();
						//0tid 1tname 2head 3队长
						$.post("ajaxLoadTeam?name=" + name, function(data){
						  //请求到数据
						  $("#noResult").hide();
						  var json = JSON.parse(data);
						  var list = json.data;
						  if(list != null && list.length >0 ){
							  for(var i = 0;i<list.length;i++){
								 if(list[i][2] == null || list[i][2] == ''){
									 list[i][2] = "common/img/default.png";
								 } else {
									 list[i][2] = "image/"+list[i][2];
								 }
							 	 $("#searchResult").append('<div class="weui-media-box weui-media-box_appmsg">'
												 	 			+'<div class="weui-media-box__hd" style="padding:10px auto">'
												 	 				+'<img class="team-head" src="${pageContext.request.contextPath}/'+list[i][2]+'">'
												 	 			+'</div>'
											 	 				+'<div onclick="" class="weui-media-box__bd">'
													 	 			+'<h4 class="weui-media-box__title">'+list[i][1]+'</h4>'
													 	 			+'<h4 class="weui-media-box__title">队长:'+list[i][3]+'</h4>'
												 	 			+'</div>'
												 	 			+'<div class="weui-media-box__hd" tid="'+list[i][0]+'" teamname="'+list[i][1]+'" style="margin:10px auto" onclick="addTeamName(this)">'
												 	 				+'<img class="weui-media-box__thumb team-add-btn" src="${pageContext.request.contextPath}/common/img/resizeApi.png" />'
												 	 			+'</div>'
											 	 			+'</div>');
							  }
						}else{
							$("#noResult").show();
						}				
					});
					}
					
				});
			</script>
		</div>
		<div class="weui-panel weui-panel_access"  >
			<div align="center">
				<h2 id="matchEdit" class="weui-panel__hd" onclick="switchSelect(this)"><font color="#09BB07" size="3px">赛程安排&nbsp;&nbsp;&nbsp;&nbsp;</font>
				<img class="img-bottom img-match" value="2" src="${pageContext.request.contextPath}/common/img/right.png" style="width:15px;height:15px;margin-bottom: -2px" >
				</h2>
			</div>
			<div id="leagueMatch" class="weui-panel__bd league-match_bd">
				<div class="weui-panel weui-panel_access league-match">
					<input id="turnNum" type="text" value="${turns.size()}" hidden="hidden"/>
					<s:if test="turns != null && turns.size() > 0">
	                	<s:iterator value="turns" status="status" var="list">
	                		<div class="weui-panel weui-panel_access fastclick" turn="${turn}">
								<div align="center" class="titleclick isTurn" onclick="ajaxLoadSite(this)">
									<h2 class="weui-panel__hd"><font color="#1C86EE" size="3px">第${turn}轮&nbsp;&nbsp;&nbsp;&nbsp;</font>
										<img class="img-bottom" val="1" src="${pageContext.request.contextPath}/common/img/right.png" style="width:15px;hight:15px;margin-bottom: -2px; border:none" >
									</h2>
									<s:if test="#status.index+1 > 1">
										<div style="position:relative" onclick="delTurn(this)">
											<div class="del-turn">
												<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini" >删除</a>
											</div>
										</div>
									</s:if>
								</div>
								<div class="weui-panel__bd league-turn_bd" turn="${turn}"  hidden="hidden" >			
								</div>
	                		</div>
	                	</s:iterator>
               		</s:if>
               		<s:else>
               		<div class="weui-panel weui-panel_access fastclick" turn="1">
               			<div align="center" type="turn">
							<h2 class="weui-panel__hd"  onclick="switchSelect(this)" id="oneSite">
								<font color="#1C86EE" size="3px">第1轮&nbsp;&nbsp;&nbsp;&nbsp;</font>
								<img class="img-bottom img-turn" value="2" src="${pageContext.request.contextPath}/common/img/right.png" style="width:15px;hight:15px;margin-bottom: -2px">
							</h2>
							<div style="position:relative" onclick="delTurn(this)" hidden="hidden">
								<div class="del-turn">
									<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini" >删除</a>
								</div>
							</div>
						</div>
						<div class="weui-panel__bd league-turn_bd " turn="1" >
							<div class="site" site="1" turn="1" lmid="0">
								<div class="league-site"  >第1场</div>
								<div class="weui-cell">
									<div class="weui-cell__bd">
										<input readonly class="weui-input need-form my-time" type="text" placeholder="开始时间" onclick="starTime(this)">
									</div>
									<div class="v-line"></div>
									<div class="weui-cell__bd">
										<input readonly class="weui-input need-form my-time" type="text" placeholder="结束时间" onclick="endTime(this)">
									</div>
								</div>
								<div class="weui-cell">
									<div class="weui-cell__bd">
										<input readonly class="weui-input need-form open-popup" tid="0" data-target="#team_full" type="text"  placeholder="球队一" onclick="remember(this)">
									</div>
									<div class="v-line"></div>
									<div class="weui-cell__bd">
										<input readonly class="weui-input need-form open-popup" tid="0" data-target="#team_full" type="text"  placeholder="球队二" onclick="remember(this)">
									</div>
								</div>
								<div class="weui-cell btn-group">
									<div class="weui-cell__bd">
										<input class="weui-input"  type="number" readonly="true"/>
									</div>
									<div class="weui-cell__hd" hidden="hidden">
										<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini open-popup" onclick="delSite(this)">删除</a>
									</div>
									<div class="weui-cell__hd">
										<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini open-popup add-site match-btn" onclick="addSite(this)">新增</a>
									</div>
								</div> 
							</div>
						</div>
					</div>
				</s:else>
				<div class="weui-cell__hd">
					<a id="add_turn" href="javascript:;" class="weui-btn weui-btn_default open-popup add-turn match-btn" onclick="addTurn(this)">添加一轮</a>
				</div>
				<div class="weui-cell__hd">
					<a id="" href="javascript:;" class="weui-btn weui-btn_primary open-popup add-turn" onclick="saveLeagueMatch()">提交</a>
				</div>
			</div>
		</div>
		<script type="text/javascript">	
			var delSiteList = [];
			var delTurnList = [];
			//保存LeagueMatch 信息
			function saveLeagueMatch(){
				$.showLoading("保存中，请稍后");
				var ajax = "";
				var matchs = document.getElementsByClassName("site");
				for(var i = 0; i < matchs.length; i++){
					var e = $(matchs[i]);
					var str = e.attr("site") +","+ e.attr("turn") + "," + e.attr("lmid") + ",";
					console.log(str)
					e.find(".need-form").each(function(i, n){
						if(i < 2){
							str += $(n).val() + ",";
						}else if(i == 2){
							str += $(n).attr("tid") + ",";
						}else{
							str += $(n).attr("tid");
						}
					});
					console.log(str)
					if(i < matchs.length-1){
						ajax += str + ";";
					}else{
						ajax += str;
					}
				}
				$.post("ajaxSaveMatch?", {ajax:ajax, delSiteList:delSiteList.toString(), delTurnList:delTurnList.toString()}, function(data){
					var json = $.parseJSON(data);
				    var list = json.data;
				    if(list != null){
				    	$.hideLoading();
				    	$.toast("操作成功");			    	
				    }else{
				    	$.toast("保存失败", "cancel");
				    }
				})
			}
			//timePicker
			function starTime(time){
				var my = $(time);
				my.datetimePicker({
					title: '开始时间',
					min: "1970-01-01 00:00",
					max: "2050-12-31 23:59",
					onChange: function(picker, values, displayValues) {
						var endTimePicker = my.parent().next().next().children("input:first-child");
						// 结束时间默认开始时间后的100分钟
						var start = my.val();
						//兼容苹果手机
						var arr = start.split(/[- : \/]/);
						var date = new Date(arr[0], arr[1]-1, arr[2], arr[3], arr[4], "00");
						// 获取当前分钟
						var min=date.getMinutes();
						// 设置当前时间+100分钟：把当前分钟数+100后的值重新设置为date对象的分钟数
						date.setMinutes(min+100);
						// 格式化时间处理
						var endTime = date.toLocaleString('chinese',{hour12:false});
						// 按日期和时间分割  2017/01/01 00:00:00
						var timeStrs = endTime.split(" ");
						var year = date.getFullYear();
						//判断是否是数字
						if(isNaN(year)) return;
						
						var month = date.getMonth()+1; 
						var day = date.getDate(); 
						// 拼接结束时间
						var currentDate = year + "-";
						if (month >= 10 ) { 
							currentDate += month + "-"; 
						} else { 
							currentDate += "0" + month + "-"; 
						} 
						if (day >= 10 ){ 
							currentDate += day ; 
						} else { 
							currentDate += "0" + day ; 
						}
						// 格式  2017-01-01 00:00:00
						currentDate += " "+timeStrs[1];
						// 截取成需要的结束时间
						var end = currentDate.substring(0,currentDate.length - 3);
						//塞回结束时间
						endTimePicker.val(end);
					},
					onClose: function(){
					}
				});
			}
			function endTime(time){
				$(time).datetimePicker({
				title: '结束时间',
				min: "1970-01-01 00:00",
				max: "2050-12-31 23:59",
				onChange: function(picker, values, displayValues) {
				}
			});
			}
			
			//记住点击添加球队的框位置
			var teaminput;
			function remember(input){
				teaminput = input;
			}
			//搜索球队时添加
			function addTeamName(name){
				$(teaminput).attr("tid", $(name).attr("tid"));
				$(teaminput).val($(name).attr("teamname"));
				$.closePopup()
			}
			//添加一场
			function addSite(btn){
				$(btn).parent().prev().show();
				$(btn).parent().hide();
				var parent = $(btn).parent().parent().parent();
				var turn = parent.parent().attr("turn");
				var site = parent.attr("site");
				site++;
				parent.parent().append('<div class="site" site="'+site+'" turn="'+turn+'" lmid="0">'+
											'<div class="league-site">第'+site+'场</div>'+
											'<div class="weui-cell">'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input need-form my-time" type="text" placeholder="开始时间" onclick="starTime(this)">'+
												'</div>'+
												'<div class="v-line"></div>'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input end-time need-form my-time" type="text" placeholder="结束时间" onclick="endTime(this)">'+
												'</div>'+
											'</div>'+
											'<div class="weui-cell ">'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input open-popup need-form" tid="0" data-target="#team_full" type="text"  placeholder="球队一" onclick="remember(this)">'+
												'</div>'+
												'<div class="v-line"></div>'+
												'<div class="weui-cell__bd open-popup">'+
													'<input readonly class="weui-input open-popup need-form" tid="0" data-target="#team_full" type="text"  placeholder="球队二" onclick="remember(this)">'+
												'</div>'+
											'</div>'+
											'<div class="weui-cell btn-group">'+
												'<div class="weui-cell__bd">'+
													'<input class="weui-input"  type="number" readonly="true"/>'+
												'</div>'+
												'<div class="weui-cell__hd">'+
													'<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini open-popup" onclick="delSite(this)">删除</a>'+
												'</div>'+
												'<div class="weui-cell__hd">'+
													'<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini open-popup match-btn" onclick="addSite(this)">新增</a>'+
												'</div>'+
											'</div> '+
										'</div'); 
				//下拉框预加载
				parent.next().find(".my-time").click();
					
			}
			//删除一场比赛
			function delSite(btn){
				$.confirm("您确定要删除该场比赛吗，场次将重新排列", "确认删除?", function() {
					//置入删除列表
					delSiteList.push($(btn).parent().parent().parent().attr("lmid"));
					var parent = $(btn).parent().parent().parent().parent();
					$(btn).parent().parent().parent().remove();
					//设置场次
					parent.find(".site").each(function(i){
						$(this).find(".league-site").text("第" + (i+1) + "场");
						$(this).attr("site", (i+1));
					});
					//显示最后一个的add
					var last = parent.find(".site:last");
					var add = last.find(".btn-group .weui-cell__hd:last");
					add.show();
					//隐藏最后一个的del
					if(last.attr("site") == 1){
						add.prev().hide();
					}
				}, function() {
				//取消操作
				});
			}
			//添加一轮比赛
			function addTurn(btn){
				var turn = $(btn).parent().prev().attr("turn");
				turn++;
				$(btn).parent().before('<div class="weui-panel weui-panel_access fastclick" turn="'+turn+'">'+
												'<div align="center" type="turn" onclick="ajaxLoadSite(this)">'+
													'<h2 class="weui-panel__hd" onclick="switchSelect(this)"><font color="#1C86EE" size="3px">第'+turn+'轮&nbsp;&nbsp;&nbsp;&nbsp;</font>'+
													'<img class="img-bottom img-turn" value="2" src="${pageContext.request.contextPath}/common/img/bottom.png" style="width:15px;hight:15px;margin-bottom: -2px">'+
													'</h2>'+
													'<div style="position:relative" onclick="delTurn(this)">'+
														'<div class="del-turn">'+
															'<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini" >删除</a>'+
														'</div>'+
													'</div>'+
												'</div>'+
												'<div class="weui-panel__bd league-turn_bd" turn="1">'+
													'<div class="site" site="1" turn="'+turn+'" lmid="0">'+
														'<div class="league-site">第1场</div>'+
														'<div class="weui-cell">'+
															'<div class="weui-cell__bd">'+
																'<input readonly class="weui-input need-form my-time"  type="text" placeholder="开始时间" onclick="starTime(this)">'+
															'</div>'+
															'<div class="v-line"></div>'+
															'<div class="weui-cell__bd">'+
																'<input readonly class="weui-input end-time need-form my-time" type="text" placeholder="结束时间" onclick="endTime(this)">'+
															'</div>'+
														'</div>'+
														'<div class="weui-cell">'+
															'<div class="weui-cell__bd">'+
																'<input readonly class="weui-input open-popup need-form" tid="0" data-target="#team_full" type="text"  placeholder="球队一" onclick="remember(this)">'+
															'</div>'+
															'<div class="v-line"></div>'+
															'<div class="weui-cell__bd open-popup">'+
																'<input readonly class="weui-input open-popup need-form" tid="0" data-target="#team_full" type="text"  placeholder="球队二" onclick="remember(this)">'+
															'</div>'+
														'</div>'+
														'<div class="weui-cell btn-group">'+
															'<div class="weui-cell__bd">'+
																'<input class="weui-input"  type="number" readonly="true"/>'+
															'</div>'+
															'<div class="weui-cell__hd" hidden="hidden">'+
																'<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini open-popup" onclick="delSite(this)">删除</a>'+
															'</div>'+
															'<div class="weui-cell__hd">'+
																'<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini open-popup add-site match-btn" onclick="addSite(this)">新增</a>'+
															'</div>'+
														'</div>' +
													'</div>'+
												'</div>');
				//下拉框预加载
				$(btn).parent().prev().find(".my-time").click();
			}
			//删除一轮比赛
			function delTurn(btn){
				$.confirm("您确定要删除该轮比赛吗，轮次将重新排列", "确认删除?", function() {
					var parent = $(btn).parent().parent().parent();
					//置入删除列表
					delTurnList.push($(btn).parent().parent().attr("turn"));
					$(btn).parent().parent().remove();
					parent.find(".fastclick").each(function(i){
						var turn = i + 1;
						$(this).attr("turn", turn);
						$(this).find("div:first h2 font").text("第"+turn+"轮");
						$(this).find(".league-turn_bd").attr("turn", turn);
						$(this).find(".league-turn_bd .site").attr("turn", turn);	
					});
					var last = parent.find(".league-match:last");
					if(last.attr("turn") == 1){
						last.find("div:first div").hide();
					}
				}, function() {
				//取消操作
				});
			}
			//ajax加载场次
			var turnLoad = [];
			for(var i = 0; i < parseInt($("#turnNum").val()); i++)
				turnLoad[i] = true;
			function ajaxLoadSite(btn){
				var turn = $(btn).parent().attr("turn");
				var site = $(btn).next();
				if(turnLoad[turn-1]){
				   $.post("ajaxLoadSite?turn=" + turn,function(data){
				   	   turnLoad[turn-1] = false;
					   var json = JSON.parse(data);
					   var list = json.data;
					   if(list != null && list.length > 0 ){
						  for(var i = 0; i < list.length; i++){
						  	//sql查询 0oneWin 1twoWin 2site 3startTime 4endTime 5oneName 6twoName 7onePic 8twoPic 9id1 10id2 11lmid 12finish
						  	//tname为空时
						  	if(list[i][5] == null) list[i][5] = "";
						  	if(list[i][6] == null) list[i][6] = "";
						  	//时间为空时
						  	if(list[i][3] == null) list[i][3] = "";
						  	if(list[i][4] == null) list[i][4] = "";
						  	//tid为空时设为0
						  	if(list[i][9] == null) list[i][9] = 0;
						  	if(list[i][10] == null) list[i][10] = 0;
						  	//只有一个时只有add
						  	if(list.length == 1){
						  		site.append('<div class="site" site="'+list[i][2]+'" turn="'+turn+'" lmid="'+list[i][11]+'">'+
											'<div class="league-site">第'+list[i][2]+'场</div>'+
											'<div class="weui-cell">'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input need-form my-time" value="'+list[i][3]+'" type="text" placeholder="开始时间" onclick="starTime(this)">'+
												'</div>'+
												'<div class="v-line"></div>'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input end-time need-form my-time" value="'+list[i][4]+'"type="text" placeholder="结束时间" onclick="endTime(this)">'+
												'</div>'+
											'</div>'+
											'<div class="weui-cell ">'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input open-popup need-form" tid="'+list[i][9]+'" value="'+list[i][5]+'" data-target="#team_full" type="text"  placeholder="球队一" onclick="remember(this)">'+
												'</div>'+
												'<div class="v-line"></div>'+
												'<div class="weui-cell__bd open-popup">'+
													'<input readonly class="weui-input open-popup need-form" tid="'+list[i][10]+'" value="'+list[i][6]+'" data-target="#team_full" type="text"  placeholder="球队二" onclick="remember(this)">'+
												'</div>'+
											'</div>'+
											'<div class="weui-cell btn-group">'+
												'<div class="weui-cell__bd">'+
													'<input class="weui-input"  type="number" readonly="true"/>'+
												'</div>'+
												'<div class="weui-cell__hd" hidden="hidden">'+
													'<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini open-popup" onclick="delSite(this)">删除</a>'+
												'</div>'+
												'<div class="weui-cell__hd">'+
													'<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini open-popup match-btn" onclick="addSite(this)">新增</a>'+
												'</div>'+
											'</div> '+
										'</div>');
						  		}else{//除最后一个都显示删除
							  		if(i < list.length-1){
							  			site.append('<div class="site" site="'+list[i][2]+'" turn="'+turn+'" lmid="'+list[i][11]+'">'+
											'<div class="league-site">第'+list[i][2]+'场</div>'+
											'<div class="weui-cell">'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input need-form my-time" value="'+list[i][3]+'" type="text" placeholder="开始时间" onclick="starTime(this)">'+
												'</div>'+
												'<div class="v-line"></div>'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input end-time need-form my-time" value="'+list[i][4]+'"type="text" placeholder="结束时间" onclick="endTime(this)">'+
												'</div>'+
											'</div>'+
											'<div class="weui-cell ">'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input open-popup need-form" tid="'+list[i][9]+'" value="'+list[i][5]+'" data-target="#team_full" type="text"  placeholder="球队一" onclick="remember(this)">'+
												'</div>'+
												'<div class="v-line"></div>'+
												'<div class="weui-cell__bd open-popup">'+
													'<input readonly class="weui-input open-popup need-form" tid="'+list[i][10]+'" value="'+list[i][6]+'" data-target="#team_full" type="text"  placeholder="球队二" onclick="remember(this)">'+
												'</div>'+
											'</div>'+
											'<div class="weui-cell btn-group">'+
												'<div class="weui-cell__bd">'+
													'<input class="weui-input"  type="number" readonly="true">'+
												'</div>'+
												'<div class="weui-cell__hd">'+
													'<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini open-popup" onclick="delSite(this)">删除</a>'+
												'</div>'+
												'<div class="weui-cell__hd" hidden="hidden">'+
													'<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini open-popup match-btn" onclick="addSite(this)">新增</a>'+
												'</div>'+
											'</div> '+
										'</div>');
							  		}else{
							  			//最后一个删除和新增
							  			site.append('<div class="site" site="'+list[i][2]+'" turn="'+turn+'" lmid="'+list[i][11]+'">'+
											'<div class="league-site">第'+list[i][2]+'场</div>'+
											'<div class="weui-cell">'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input need-form my-time" value="'+list[i][3]+'" type="text" placeholder="开始时间" onclick="starTime(this)">'+
												'</div>'+
												'<div class="v-line"></div>'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input end-time need-form my-time" value="'+list[i][4]+'"type="text" placeholder="结束时间" onclick="endTime(this)">'+
												'</div>'+
											'</div>'+
											'<div class="weui-cell ">'+
												'<div class="weui-cell__bd">'+
													'<input readonly class="weui-input open-popup need-form" tid="'+list[i][9]+'" value="'+list[i][5]+'" data-target="#team_full" type="text"  placeholder="球队一" onclick="remember(this)">'+
												'</div>'+
												'<div class="v-line"></div>'+
												'<div class="weui-cell__bd open-popup">'+
													'<input readonly class="weui-input open-popup need-form" tid="'+list[i][10]+'" value="'+list[i][6]+'" data-target="#team_full" type="text"  placeholder="球队二" onclick="remember(this)">'+
												'</div>'+
											'</div>'+
											'<div class="weui-cell btn-group">'+
												'<div class="weui-cell__bd">'+
													'<input class="weui-input"  type="number" readonly="true">'+
												'</div>'+
												'<div class="weui-cell__hd">'+
													'<a href="javascript:;" class="weui-btn weui-btn_warn weui-btn_mini open-popup" onclick="delSite(this)">删除</a>'+
												'</div>'+
												'<div class="weui-cell__hd">'+
													'<a href="javascript:;" class="weui-btn weui-btn_default weui-btn_mini open-popup match-btn" onclick="addSite(this)">新增</a>'+
												'</div>'+
											'</div> '+
										'</div>');
							  		}
						  		}
							}
						}
						site.show();
						var img = $(btn).find("img");
						img.attr("value", 2);
						img.attr("src", "${pageContext.request.contextPath}/common/img/bottom.png");
						
						//当全部加载完成后 预加载下拉框
						for(var j = 0; j < turnLoad.length; j++){
							if(turnLoad[j] == true) return;
						}
						
						$("#leagueMatch").find(".my-time").click();
					});
				}	
			}
		</script>
		<!-- ++++++++++++++++++++++++++++++++++++++++++裁判组++++++++++++++++++++++++++++++++++++++++ -->
		<div class="weui-panel weui-panel_access">
			<div align="center" class="titleclick">
				<h2 class="weui-panel__hd"><font color="#09BB07" size="3px">裁&nbsp;判&nbsp;组&nbsp;&nbsp;&nbsp;&nbsp;</font>
				<img class="img-bottom" value="1" src="${pageContext.request.contextPath}/common/img/right.png" style="width:15px;hight:15px;margin-bottom: -2px">
				</h2>
			</div>
		<div class="weui-panel__bd">
				 <div class="weui-cells weui-cells_form">	
        		<a id="share" href="javascript:;" class="weui-grid js_grid open-popup" style="width:100%;border:1px solid #ddd" data-target="#full">
					<div class="weui-grid__icon">
						<img src="${pageContext.request.contextPath}/common/img/yaoqing.png"  alt="">
					</div>
					<p class="weui-grid__label">
						邀请
					</p>
				</a>
		     </div>
		    <div class="weui-cells weui-cells_form">
				<a class="weui-cell weui-cell_access" href="${pageContext.request.contextPath}/apply/applyLeagueList?lid=${league.id}">
					<div class="weui-cell__bd">
						<p>查看申请&nbsp;
						<s:if test="#waitLeagueHandleList != null && #waitLeagueHandleList.size() > 0">
							<span class="weui-badge" style="position: absolute;"><s:property value="#waitLeagueHandleList.size()"/></span>
						</s:if>
						</p>
					</div>
					<div class="weui-cell__ft">
					</div>
				</a>
		  </div>
		   <div class="weui-cells weui-cells_form">
				<div class="weui-cell weui-cell_access" >
					<div class="weui-cell__bd" style="inline-block:true;float:left;border-bottom:1px solid #ddd" >
						<p>已加入的裁判&nbsp;</p>
					</div>
				</div>
				<div  id="src" >
						<s:iterator  value="#agreeList" status="status" var="list" >
						<s:if test="#list[2] == null || #list[2] =='' ">  
							<div  style="float:left;position:relative;height:7.3em;">
								<a  href="${pageContext.request.contextPath}/league/showReferee?uid=${list[0]}">
			 						<img src="${pageContext.request.contextPath}/common/img/default.png">
			 					</a>
			 				 <button uid="${list[0]}" onclick="refereeDel(this)" style="background:#1C86EE;color:white;border-radius:5px;height:1.8em;position:absolute;width:83%;top:7.3em;right:1px;text-align:center;">删除</button>
							</div>
			 			</s:if>  
			  			<s:else>  
			  				<div  style="float:left;position:relative;height:7.3em;">
			  					<a  href="${pageContext.request.contextPath}/league/showReferee?uid=${list[0]}">
			  					<s:if test="#list[2].indexOf('http') != -1">
			    					<img src="${list[2]}">
			    				</s:if>
			    				<s:else>
			    					<img src="${pageContext.request.contextPath}/image/${list[2]}">
			    				</s:else>
			    				</a>
			    			     <button uid="${list[0]}" onclick="refereeDel(this)" style="background:#1C86EE;color:white;border-radius:5px;height:1.8em;position:absolute;width:83%;margin-top:7.3em;right:1px;text-align:center;">删除</button>
							</div>
			    		</s:else>
			    		</s:iterator>
			    </div>
		  </div>
		  
		 	 <div id="full" class='weui-popup__container' >
			      <div class="weui-popup__overlay"></div>
			      <div class="weui-popup__modal"  style="background-color: #4D4D4D">
			      	<div>
			      		<img id="shareImg" src="${pageContext.request.contextPath}/common/img/share.jpg" width="100%">
			      	</div>
			      </div>
			</div>
		  </div>
		</div>
	 </div>
 </div>

		<script type="text/javascript">
			$(".weui-panel__bd").hide();
		    $("#doing_league").show();
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
		})
		
		//删除裁判按钮
			function refereeDel(btn) {
		        $.confirm("您确定要删除该裁判吗?", "确认删除?", function() {
		          $.toast("裁判已经删除!");
		          var uid = $(btn).attr("uid");
		          $.post("refereeDel?uid="+ uid,function(data){
		          		$(btn).parent().remove();
		          });
		          
		        }, function() {
		          //取消操作
		        });
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
		var leagueId = '${league.id}'
		var protocol = window.location.protocol;//协议
		var host = window.location.host;//域名
		//var icon = $("#icon img")[0].src; //分享图标
		var leagueName = '${league.name}';
		var currentUserId = '${currentUserId}';
	    // 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
	    wx.onMenuShareTimeline({
	        title: '诚邀您加入联赛——' +leagueName+ ',一起畅游足球世界！', // 分享标题
	        link:protocol+"//"+host+"/rongqiu/league/shareUrl?uid="+currentUserId+"&league.id="+leagueId//分享链接,该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
	        //imgUrl: icon+'' // 分享图标
	    });
	    // 获取“分享给朋友”按钮点击状态及自定义分享内容接口
	    wx.onMenuShareAppMessage({
	        title: '诚邀您加入联赛——'+leagueName+ ',一起畅游足球世界！', // 分享标题
	        link:protocol+"//"+host+"/rongqiu/league/shareUrl?uid="+currentUserId+"&league.id="+leagueId//分享链接,该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
	        //imgUrl: icon+''// 分享图标
	    });
	});
	wx.error(function (res) {
	    //alert("error");
	});
	</script>
		<div class="weui-panel weui-panel_access league-del" style="margin:2em 2%">
			<button class="weui-btn weui-btn_warn">删除联赛</button>
		</div>
		<script>
			//比赛下拉框
       		function switchSelect(div){      
				var img;
				if($(div).parent().attr("type") == "turn")
					img = $(div).find("img");
				else
					img = $(div).find(".img-match");
				//下拉框
				if(img.attr("value") == 2){
					img.attr("src", "${pageContext.request.contextPath}/common/img/right.png");
					if($(div).parent().attr("type") == "turn")
						$(div).parent().parent().find(".league-turn_bd").hide();
					else
       					$(div).parent().parent().find(".league-match_bd").hide();
					img.attr("value", 1);
				}else{
					img.attr("src", "${pageContext.request.contextPath}/common/img/bottom.png");
					if($(div).parent().attr("type") == "turn")
						$(div).parent().parent().find(".league-turn_bd").show();
					else
       					$(div).parent().parent().find(".league-match_bd").show();
       				img.attr("value", 2);
				}
			}
		</script>
	
		<script>
			//最大的下拉框
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
			});
			//删除联赛按钮
			$(".league-del").click(function() {
		        $.confirm("您确定要删除该联赛吗?", "确认删除?", function() {
		          $.toast("联赛已经删除!");
		          fnUrlReplace("${pageContext.request.contextPath}/league/leagueDel");
		        }, function() {
		          //取消操作
		        });
	      	});
      	
      	//用于进入时的加载以及便于保存
      	$(".isTurn").click();
      	$("#matchEdit").click();
      	$("#oneSite").click();
		</script>
	
	</body>
</html>