<%@ page contentType="text/html;charset=utf-8" language="java"%>
<style type="text/css">
.closeAds {
	width: 100px;
	height: 14px;
	border: 0px;
}

.ads {
	width: 100px;
	height: 300px;
	border: 0px;
}

.div_ads1 {
	position: absolute;
	z-index: 0;
	width: 100px;
	height: 200px;
	margin-top: 105px;
	left: 6px;
}

.div_ads2 {
	position: absolute;
	z-index: 0;
	width: 100px;
	height: 200px;
	margin-top: 105px;
	right: 6px;
}
</style>

<script type="text/javascript">
	lastScrollY = 0;
	function heartBeat() {
		diffY = document.body.scrollTop;
		percent = .3 * (diffY - lastScrollY);
		if (percent > 0)
			percent = Math.ceil(percent);
		else
			percent = Math.floor(percent);
		document.all.lovexin1.style.pixelTop += percent;
		document.all.lovexin2.style.pixelTop += percent;
		lastScrollY = lastScrollY + percent;
	}
	function hide() {
		lovexin1.style.visibility = "hidden";
		lovexin2.style.visibility = "hidden";
	}
	window.setInterval("heartBeat()", 1);
</script>

<DIV id=lovexin1 class="div_ads1">
	<img src='${contextPath}/templates/default/images/close.gif'
		class="closeAds" onClick='javascript:window.hide()' vspace='3'>
	<a href=''> <img
		src='${contextPath}/templates/default/images/ad_100x300.jpg'
		class="ads" />
	</a>
</DIV>

<DIV id=lovexin2 class="div_ads2">
	<img src='${contextPath}/templates/default/images/close.gif'
		class="closeAds" onClick='javascript:window.hide()' vspace='3'>
	<a href=''> <img
		src='${contextPath}/templates/default/images/ad_100x300.jpg'
		class="ads" />
	</a>
</DIV>