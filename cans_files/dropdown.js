/*sfHover = function() {
	var sfEls = document.getElementById("nav_primary").getElementsByTagName("LI");
	for (var i=0; i<sfEls.length; i++) {
		sfEls[i].onmouseover=function() {
			this.className+=" sfhover";
		}
		sfEls[i].onmouseout=function() {
			this.className=this.className.replace(new RegExp(" sfhover\\b"), "");
		}
	}
}
if (window.attachEvent) window.attachEvent("onload", sfHover);*/

$(document).ready(
	function()
		{
			$("ul#nav_primary li").hover(
				function () 
					{
						$(this).addClass("sfhover");
					},
				 function () 
				 	{
						$(this).removeClass("sfhover");
					}
			);
		}
	);