function list(tagName, propName){
	var arr = document.getElementsByTagName(tagName);
	for(var i = 0; i < arr.length; i++)
		if(arr[i][propName])
			console.log(arr[i][propName]);
}
list("a", "href");
list("img", "src");
list("audio", "src");
list("video", "src");
list("script", "src");