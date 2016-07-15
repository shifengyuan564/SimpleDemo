var arr = document.getElementsByTagName("a");
for(var i = 0; i < arr.length; i++)
	if(arr[i]["href"])
		console.log(arr[i]["href"]);