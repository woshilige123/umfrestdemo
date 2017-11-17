$(document).ready(function() {

	$("#btn1").click(function() {
		var pageData = new Object();
		pageData = $("#plainString").val();
		;
		$.ajax("/restdemo/tools/sign", {
			method : "POST",
			contentType : "application/json; charset=utf-8",
			data : JSON.stringify(pageData),
			dataType : "json",
			headers : {},
			success : function(data, statusCode) {
				$("#signString").text(data);
			},
			error : function(err) {
				console.log(err);
			},
			complete : function() {
				$("body").removeClass("loading");
			}
		});
	});

});