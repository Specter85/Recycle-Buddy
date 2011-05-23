
 $(document).ready(function(){
	$("input").focus(function () {
	  var value = $(this).val();
      var text = "";
      $(this).val(text);
	  $(this).blur(function() {
	  	if($(this).val() == '')
	  	  $(this).val(value);
	  });
    });
	
	$("#code").click(function() { 
	 	if (document.selection)
		{
		//IE
		//var div = document.body.createTextRange();
		
		//div.moveToElementText(document.getElementById("code"));
		//div.select();
		$(this).focus();
		$(this).select();
		CopiedTxt = document.selection.createRange();

   		CopiedTxt.execCommand("Copy");
		}
		else
		{
		//Firefox
		
		//var div = document.createRange();
		
		//div.setStartBefore(document.getElementById("code"));
		//div.setEndAfter(document.getElementById("code")) ;
		
		//window.getSelection().addRange(div);
		$(this).focus();
		$(this).select();
		
		}
		
		});
	
	$("#get_widget_cta").click(function() {	
		//$("#step1").css('display','none');
		//$("#step2").css('display','block');	
		$("#step1").hide();
		$("#step2").show();	
	});
});
