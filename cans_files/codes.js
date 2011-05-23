// JavaScript Document

$(document).ready(	
	function() {
		$('#pete').parent().hover(
			function() {
				$(this).children('.img_rollover').css({'display' : 'block' , 'top' : '-385px' , 'left' : '-31px' , 'width' : '371px' , 'height' : '400px' });
				$(this).children('#pete').css({'background-position' : 'top'});
			},
			function() {				
				$(this).children('.img_rollover').css({'display' : 'none'});	
				$(this).children('#pete').css({'background-position' : 'bottom'});
			}
		);		
	
		$('#hdpe').parent().hover(
			function() {
				$(this).children('.img_rollover').css({'display' : 'block' , 'top' : '-385px' , 'left' : '-97px' , 'width' : '371px' , 'height' : '400px' });
				$(this).children('#hdpe').css({'background-position' : 'top'});
			},
			function() {				
				$(this).children('.img_rollover').css({'display' : 'none'});	
				$(this).children('#hdpe').css({'background-position' : 'bottom'});
			}
		);		
	
		$('#v').parent().hover(
			function() {
				$(this).children('.img_rollover').css({'display' : 'block' , 'top' : '-385px' , 'left' : '-158px' , 'width' : '371px' , 'height' : '400px' });
				$(this).children('#v').css({'background-position' : 'top'});
			},
			function() {				
				$(this).children('.img_rollover').css({'display' : 'none'});	
				$(this).children('#v').css({'background-position' : 'bottom'});
			}
		);		
		
		$('#ldpe').parent().hover(
			function() {
				$(this).children('.img_rollover').css({'display' : 'block' , 'top' : '-385px' , 'left' : '-159px' , 'width' : '371px' , 'height' : '400px' });
				$(this).children('#ldpe').css({'background-position' : 'top'});
			},
			function() {				
				$(this).children('.img_rollover').css({'display' : 'none'});	
				$(this).children('#ldpe').css({'background-position' : 'bottom'});
			}
		);		
	
		$('#ps').parent().hover(
			function() {
				$(this).children('.img_rollover').css({'display' : 'block' , 'top' : '-385px' , 'left' : '-226px' , 'width' : '371px' , 'height' : '400px' });
				$(this).children('#ps').css({'background-position' : 'top'});
			},
			function() {				
				$(this).children('.img_rollover').css({'display' : 'none'});	
				$(this).children('#ps').css({'background-position' : 'bottom'});
			}
		);		
	
		$('#pp').parent().hover(
			function() {
				$(this).children('.img_rollover').css({'display' : 'block' , 'top' : '-385px' , 'left' : '-163px' , 'width' : '371px' , 'height' : '400px' });
				$(this).children('#pp').css({'background-position' : 'top'});
			},
			function() {				
				$(this).children('.img_rollover').css({'display' : 'none'});	
				$(this).children('#pp').css({'background-position' : 'bottom'});
			}
		);		
	
		$('#other').parent().hover(
			function() {
				$(this).children('.img_rollover').css({'display' : 'block' , 'top' : '-385px' , 'left' : '-288px' , 'width' : '371px' , 'height' : '400px' });
				$(this).children('#other').css({'background-position' : 'top'});
			},
			function() {				
				$(this).children('.img_rollover').css({'display' : 'none'});	
				$(this).children('#other').css({'background-position' : 'bottom'});
			}
		);		
	}
);	
