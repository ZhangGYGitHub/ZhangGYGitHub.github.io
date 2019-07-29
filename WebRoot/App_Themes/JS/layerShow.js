//打开窗口

function openLayer(title, url) {
		var index = layer.open({
			type : 2,
			title : title,
			content : url
		});
		layer.full(index);
	}

//关闭自己
function closeSelf(){
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index);
}