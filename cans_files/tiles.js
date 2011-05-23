$(document).ready(function() {
	
	var count = 0;
	var active = 0;
	var value = 0;
	var move = 0;	
	
	//Determines which tile is active
	$(".recycle_menu li a").each(function(i)
		{
			count++;
			if($(this).attr("class") == "active")
				{
					active = count;
				}
		});
	
	//Sets start value of UL so that the active tile is showing
	if(active > 3)
		{
			value = (active - 3) * (-134);
			$(".recycle_menu").css({'left' : value });	
		}
	
	//Determines if the left/right arrow needs to be shown
	if(count <= 3)
		{
			$("#right_control").css({'display' : 'none'});	
			$("#left_control").css({'display' : 'none'});	
		}
		
	move = value;
	
	//Sets the move for the right control
	$("#right_control").click(function() {
		if(move > (-134 * (count - 3)))
			{
				move -= 134;
				$(".recycle_menu").animate({left: move},200);		
			}
	});
	
	//Sets the move for the left control
	$("#left_control").click(function() {
		if(move < 0)
			{
				move += 134;
				$(".recycle_menu").animate({left: move},200);
			}
	});
});