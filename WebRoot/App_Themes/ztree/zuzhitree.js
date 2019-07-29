		var setting = {
			async: {
				enable: true,
				url:"getNodesOnAsync.action",
				autoParam:["id"],
				type : "post" 
			},
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				drag: {
					isCopy: false,
					isMove: false
				},
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: showRenameBtn
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onAsyncSuccess: zTreeOnAsyncSuccess,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				onRemove: onRemove,
				onRename: onRename
			}
		};
		function beforeEditName(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			addtodiv(treeNode,1);
			return false;
		}
		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			if(treeNode==null){
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				var treeNode = zTree.getNodes()[0];
				zTree.reAsyncChildNodes(treeNode, "refresh");
			}
		};
		function showRemoveBtn(treeId, treeNode) {
			if (treeNode.pid==0){
				return false;
			}else{
				var node = treeNode.getParentNode();
				if(node==null){
					return false;
				}
			}
			
			return true;
		}
		function showRenameBtn(treeId, treeNode) {
			if (treeNode.pid==0){
				return false;
			}
			return true;
		}
		function beforeRemove(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			if(treeNode.isParent){
				alert("当前节点为非空结点，不允许删除");
				return false;
			}
	 		var judge=confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
			if(judge==true){
				var posturl;
				posturl="delOrganize.action";
				$.post(posturl,{"id":treeNode.id},function(data){
					if(data=="success"){
						 zTree.removeNode(treeNode);
						return true;
					}else{
						alert("删除不成功，请重试");
							return false;
					}
				}); 
			}else{
				return false;
			}
			
			return false;
			
			
		}
		function onRemove(e, treeId, treeNode) {//可以选择是在页面添加日志操作
			//showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		}
		function onRename(e, treeId, treeNode, isCancel) {//可以选择是在页面添加日志操作
			//showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
		}
		
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='添加' onfocus='this.blur();'></span>";
			if(treeNode.type==1 || treeNode.type==3 || treeNode.type==5 || treeNode.type==0){
				sObj.after(addStr);
			}
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.selectNode(treeNode);
				$("#nodeid").val(treeNode.id);
				addtodiv(treeNode,0);
				return false;
			});
		};
		function removeHoverDom(treeId, treeNode) {
			 $("#addBtn_"+treeNode.tId).unbind().remove();
		};
		function addtodiv(treeNode,judge){//judge判断是新增还是编辑0：新增，1：编辑
			var url;
			var companyId = 0;//总公司id
			var pNode = treeNode;
			if (pNode.type!=0){//前提该节点不是系统平台
				for (var i=1; i>0; i++){
					if (pNode.type != 1){
						pNode = pNode.getParentNode();
					}else{
						companyId = pNode.id;
						break;
					}
				}
			}
			if(judge==1){//编辑的话在node写入id值
				//公司类别0:根，1：总公司，2：1级部门，3：1级单位，4：2级部门，5:2级单位，6:3级部门
				url = "showOrganize.action?id="+treeNode.id;
				window.open(url, "edit_iframe");
			}else if(treeNode.type == 0 || treeNode.type == 5){
				url = "showOrganize.action?pid="+treeNode.id+"&companyId="+companyId;
				window.open(url, "edit_iframe");
			}else {
				url = "showOrganize.action?pid="+treeNode.id+"&companyId="+companyId;
				layer.open({
				  content: '请选择添加单位或者部门'
				  ,btn: ['单位','部门']
				  ,yes: function(index, layero){
					  layer.closeAll();
					  url += "&type=0";
					  window.open(url, "edit_iframe");
				  },btn2: function(index, layero){
					  url += "&type=1";
					  window.open(url, "edit_iframe");
				  },cancel: function(){ 
				    //右上角关闭回调
				  }
				});
			}
			
		};
		function addhover(){
			var url;
			var nodeid= $("#nodeid").val();
			var leixing =$("#leixing").val();
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			url = "showOrganize.action?pid="+nodeid;
			window.open(url, "edit_iframe");
		}
		function myrefresh(nodeid){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var selectedNode=zTree.getNodeByParam("id", nodeid, null);
			if(selectedNode == null){
				zTree.reAsyncChildNodes(selectedNode, "refresh");
			} else {
				if(selectedNode.isParent==false){
					selectedNode.isParent=true;
					zTree.reAsyncChildNodes(selectedNode, "refresh");
				} else {
					zTree.reAsyncChildNodes(selectedNode, "refresh");
				}
			}
		}
		
		function setleixing(leixing){
			$("#leixing").val(leixing);
		}
		function afterClose(){
			addhover();
			//alert('after close');
		}
		function beforeClose(){
			//alert('before close');
			return true;
		}
		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting);
               
        });