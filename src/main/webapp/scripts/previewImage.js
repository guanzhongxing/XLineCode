function previewImage(){	
	var path=document.getElementById("path");
	var pattern=/\.jpg$|\.jpeg$|\.gif$|\.png$|\.bmp$/i;
	var imageDiv=document.getElementById("imageDiv");
	var notImageFileDiv=document.getElementById("notImgFileDiv");
	
	if(pattern.test(path.value)){
		var img=document.getElementById("uploadedImg");
		var description=document.getElementById("description");
		description.lastChild.nodeValue="Path:"+path.value;
		img.setAttribute("src","file://localhost/"+path.value);
		
//		alert(img.src);
		imageDiv.style.display="inline";
		notImageFileDiv.style.display="none";
	}
	else{
		imageDiv.style.display="none";
		notImageFileDiv.style.display="inline";
	}
}