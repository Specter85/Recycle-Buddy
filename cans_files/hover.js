$(document).ready(
	function() {
		$("#nav_wasteasaresource").hover(
			function() {
				$(this).children("a").css({color: "#41711F", backgroundPosition: "top", height: "39px"});					
			},
			function() {
				$(this).children("a").css({color: "#FFF", backgroundPosition: "bottom", height: "23px"});
			}
		);
		$("#nav_ourcommitment").hover(
			function() {
				$(this).children("a").css({color: "#41711F", backgroundPosition: "top", height: "39px"});				
			},
			function() {
				$(this).children("a").css({color: "#FFF", backgroundPosition: "bottom", height: "23px"});
			}
		);
		$("#nav_resources").hover(
			function() {
				$(this).children("a").css({color: "#41711F", backgroundPosition: "top", height: "39px"});			
			},
			function() {
				$(this).children("a").css({color: "#FFF", backgroundPosition: "bottom", height: "23px"});
			}
		);
		$("#nav_classroomtools").hover(
			function() {
				$(this).children("a").css({color: "#41711F", backgroundPosition: "top", height: "52px"});			
			},
			function() {
				$(this).children("a").css({color: "#FFF", backgroundPosition: "bottom", height: "23px"});
			}
		);
		$("#nav_wasteasaresource_recycling").hover(
			function() {					
				var bottom = $.browser.msie ? ($.browser.version.substr(0,1) == "6" ? "-14px" : "-7px") : "-10px";				
				var value = $(this).parent().width();
				$(this).children("ul").css({'left' : value-4});
				$(this).children("ul").children("div.footer").css({'bottom' : bottom });
				$(this).children("a").css({'background-color' : '#41711F' });					
			},
			function() {
				$(this).children("a").css({'background-color' :  '#f0b510' });
			}
		);
		
		$("#nav_resources_news").hover(
			function() {					
				var bottom = $.browser.msie ? ($.browser.version.substr(0,1) == "6" ? "-12px" : "-7px") : "-10px";				
				var value = $(this).parent().width();
				$(this).children("ul").css({'left' : value-4});
				$(this).children("ul").children("div.footer").css({'bottom' : bottom });
				$(this).children("a").css({'background-color' : '#41711F' });					
			},
			function() {
				$(this).children("a").css({'background-color' :  '#f0b510' });
			}
		);
		
		
		/*$("#nav_ourcommitment_ul > li.last").hover(
			function() {
				$("#nav_ourcommitment_ul").css('background-image','url(images/v5_nav/our_commitment_subnav_bottom_corner_hover.png)');
			},
			function() {
				$("#nav_ourcommitment_ul").css('background-image','url(images/v5_nav/our_commitment_subnav_bottom_corner.png)');
			}
		);
		$("#nav_wasteasaresource_ul > li.last").hover(
			function() {
				$("#nav_wasteasaresource_ul").css('background-image','url(images/v5_nav/waste_as_a_resource_subnav_bottom_corner_hover.png)');
			},
			function() {
				$("#nav_wasteasaresource_ul").css('background-image','url(images/v5_nav/waste_as_a_resource_subnav_bottom_corner.png)');
			}
		);*/
	}
);

