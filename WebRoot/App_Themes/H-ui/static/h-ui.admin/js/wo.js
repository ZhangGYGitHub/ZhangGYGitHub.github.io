$(document).ready(function() {
	var search = $('<i class="Hui-iconfont">&#xe665;</i>');
	var look = $('<i class="Hui-iconfont">&#xe616;</i>');
	var stop = $('<i class="Hui-iconfont">&#xe60b;</i>');
	var start = $('<i class="Hui-iconfont">&#xe601;</i>');
	var strike = $('<i class="Hui-iconfont">&#xe6e2;</i>');
	var add = $('<i class="Hui-iconfont">&#xe600;</i>');
	var publish = $('<i class="Hui-iconfont">&#xe632;</i>');
	var compile = $('<i class="Hui-iconfont">&#xe6df;</i>');
	

	$("button.search").prepend(search);
	$("a.search").prepend(search);
	$("a.look").prepend(look);
	$("a.stop").prepend(stop);
	$("a.start").prepend(stop);
	$("a.strike").prepend(strike);
	$("a.add").prepend(add);
	$("a.compile").prepend(compile);	
	$("button.submit").prepend(publish);
	
});