// JavaScript Document

$(document).ready(function(){
	$(".toggle-results","#search-items").toggle(
		function() {			
			$(this).next(".video-results-container").show();
			$(this).text("Hide Video List").css({'background-position' : '112px bottom'});
			return false;
		},
		function() {
			$(this).next(".video-results-container").hide();
			$(this).text("Show Video List").css({'background-position' : '115px top'});
			return false;
		}
	);	
});