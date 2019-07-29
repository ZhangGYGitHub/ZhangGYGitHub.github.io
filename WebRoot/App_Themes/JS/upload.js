function convertImgToBase64(url, callback, outputFormat) {
	var imgArray = new Array();
	var img = new Image;
	img.crossOrigin = 'Anonymous';
	img.onload = function() {
		for ( var i = 0; i < 3; i++) {
			var canvas = document.createElement('CANVAS');
			var ctx = canvas.getContext('2d');
			var width = img.width;
			var height = img.height;
			var rate = (width < height ? width / height : height / width)
					/ ((i + 1) * 2);
			canvas.width = width * rate;
			canvas.height = height * rate;
			ctx.drawImage(img, 0, 0, width, height, 0, 0, width * rate, height
					* rate);
			var dataURL = canvas.toDataURL(outputFormat || 'image/png');
			canvas = null;
			imgArray[i] = dataURL.split(",")[1];
		}
		callback.call(this, imgArray);
	};
	img.src = url;
}

function getObjectURL(file) {
	var url = null;
	if (window.createObjectURL != undefined) { // basic
		url = window.createObjectURL(file);
	} else if (window.URL != undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file);
	} else if (window.webkitURL != undefined) { // web_kit or chrome
		url = window.webkitURL.createObjectURL(file);
	}
	return url;
}
// 前端只需要给input file绑定这个change事件即可（上面两个方法不用管）huodong-msg为input class
function handleFiles(files, id) {
	var imageUrl = getObjectURL(files[0]);
	convertImgToBase64(imageUrl, function(base64Img) {
		upload(base64Img, id);
	});
	event.preventDefault();
}

function upload(array, id) {
	var fileName = document.getElementById('fileElem').value;
	$.ajax({
		type : 'POST',
		url : 'imgajax',
		dataType : 'json',
		data : {
			imgBase64_max : array[0],
			imgBase64_mid : array[1],
			imgBase64_min : array[2],
			fileRealName : fileName
		},
		success : function(data, status) {
			$("#d" + id).remove();
			var strAdd = "<div id='d" + id
					+ "' style='float:left;'><input id='sys" + id
					+ "' type='hidden' value='" + data.fileSysName
					+ "'/><input id='real" + id
					+ "' type='hidden' value='" + data.fileRealName
					+ "'/><img src='/uploadfiles/mid_"
					+ data.fileSysName
					+ "' width='100px' height='100px'/>";
			strAdd += "<input type='button' value='删除' onclick='javascript:delImg("
					+ id + ")'/></div>";
			$("#img").append(strAdd);// 在ID为imgAdd的标签后添加html语句
		},
		error : function(data, status) {
			 alert("您上传的图片过大,请上传2M以下的图片");
			//alert(status);
		}
	});
}

// 删除图片
function delImg(id) {
	$("#d" + id).remove();
}