$(document).ready(function() {
	
	/*$(".navigation_tab li a").click(function() {
		if($(this).attr("class") != "active")
			{
				$(".navigation_tab li a").each(function(i)
					{
				   		$(this).removeClass();
				 	});		
				$(this).addClass("active");				
			}
		$(".navigation_tab li a").each(function(j)
		{
			if($(this).attr("class") != "active")
				{
					$(this).addClass("prior");	
				}	
			else
				{
					return false;	
				}
		});
	});*/
	
	$(".navigation_tab li a").each(function(j)
		{
			if($(this).attr("class") != "active")
				{
					$(this).addClass("prior");	
				}	
			else
				{
					return false;	
				}
		});
	
	
});