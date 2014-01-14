function startRequest(method,URL,isAsync,content,act){
	if(act){
		if(xmlHttp==null)
			createXMLHttpRequest();
		//alert("after create"+xmlHttp.readyState);
		try{
			xmlHttp.open(method,URL,isAsync);
		}
		catch(e){
			alert(e);
		}
		//alert("after open "+xmlHttp.readyState);
		xmlHttp.onreadystatechange=isNameExist;
		try{
			xmlHttp.send(content);
		}
		catch(e){
			alert(e);
		}
		//alert("after send "+xmlHttp.readyState);
		//alert("after");
		//alert(xmlHttp.status);
		//alert("startRequest"); 
	}
}

function createXMLHttpRequest() {
	if(window.ActiveXObject){
		xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	else if(window.XMLHttpRequest){
		xmlHttp = new XMLHttpRequest();
	}
}

function isNameExist(){
	//alert("isValueExist");
	//alert(xmlHttp.readyState);
	
	if(xmlHttp.readyState==4){
		if(xmlHttp.status==200){
			var exist=xmlHttp.responseText;
			var img=document.getElementById("username_img_Id");
			//alert(exist);
			if(exist=="true"){
				img.setAttribute("src","templates/default/images/error_bang.gif");
			}
			else{
				//alert(false);
				img.setAttribute("src","templates/default/images/done_valid.gif");
			}
		}
	}
}

function showProcessIcon(act){
	if(act){
		var img=document.getElementById("username_img_Id");
		//alert(img);
		if(img==null){
			img=document.createElement("img");
			img.setAttribute("id","username_img_Id");
			img.setAttribute("src","templates/default/images/processing.gif");
			var username=document.getElementById("username");
			username.parentNode.appendChild(img);
		}
		else{
			img.setAttribute("src","templates/default/images/processing.gif");
		}
	}
}