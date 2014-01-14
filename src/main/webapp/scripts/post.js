
function activateTab(name, currentLi)
{
	$("#tabs10 > ul > li").each(function() {
		var targetName = $(this).attr("target");
		var target = $("#" + targetName);

		if (target.length && name != targetName) {
			target.hide();
			$(this).removeClass("current");
		}
	});


	$("#" + name).show();
	$(currentLi).parent().addClass("current");
}
