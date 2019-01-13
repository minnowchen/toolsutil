/* ================================================================================================ */
/* ajax */
/* ================================================================================================ */
function ajaxByForm(servletURL, formData, doThing){
	$.ajax({
		url : servletURL,
		data: formData,
		method: "POST",
		cache: false, //上傳文件不需暫存 （預設是true）
	    processData: false, //data值是FormData對象，不需要對數據做處理
	    contentType: false, //由<form>表單構成的FormData，已經聲明屬性enctype="multipart/form-data"， 原預設"application/x-www-form-urlencoded"
		success : function(response) {
			if(doThing == "list"){
				setNTBTDatasTable(response)
			}
		}
	});
}


function ajaxByData(servletURL, data, doThing){
	$.ajax({
		url : servletURL ,
		data : data,
		method : 'POST',
		cache : false, //IE不要cache （預設是true）
		async : true, //是否採用非同步（預設是true）
		contentType : "application/x-www-form-urlencoded", //(預設)
		success : function(response) {
			
			if(doThing == "error_Itv" || doThing == "error_fia"){
				if(response == ""){
					return;
				}
				ajaxByDialog(context+"/includes/ajaxRequest_itvOneError.jsp", "#error-dialog", doThing, response)
				
			}
		}
	});
}








function ajaxByDialog(servletURL, id, doThing, jsonStr){
	$.ajax({
		url : servletURL,
		method: "POST",
		cache: false,
		error : function(xhr) {
			$(id).html("資料讀取失敗，請稍後重試！");
			$(id).dialog("open");
		},
		success : function(response) {
			$(id).html(response);
			$(id).dialog("open");
			
			if(doThing == "error_Itv" || doThing == "error_fia"){
				setItvDialog(doThing, jsonStr);
			}
		},
	});



function ajaxByDialog(servletURL, id, doThing, jsonStr){
	$.ajax({
		url : servletURL,
		method: "POST",
		cache: false,
		error : function(xhr) {
			$(id).html("資料讀取失敗，請稍後重試！");
			$(id).dialog("open");
		},
		success : function(response) {
			$(id).html(response);
			$(id).dialog("open");
			
			if(doThing == "error_Itv" || doThing == "error_fia"){
				setItvDialog(doThing, jsonStr);
			}
		},
	});
}
