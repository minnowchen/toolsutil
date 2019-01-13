/*  ===========================================
 *  共用submit
 *  id ：判定是從哪一個網頁進來，發動Ajax前要處理一些步驟
 */
function submitContent(form, id){
	if(confirm("確定要送出資料？")==false){
		return;
	}
	var nextFuntion = "";
	var contentType = "application/x-www-form-urlencoded";

	//使用於每一個個別狀況的ID
	if(id == ""){

	}
	else if (id == "") {
		
	}
	
	ajax_form(form, pagesURL, nextFuntion, contentType);
}


/*  ===========================================
 *  共用ajax
 * 	form      :網頁上的form，要輸入到資料庫的
 * 	servletURL：要使用的Servlet路徑
 * 	method    ：成功後，要找哪一個method處理response
 */
function ajax_form(form, servletURL, method, contentType){
	$.ajax({
		url : servletURL ,
		data : $(form).serialize(),
		method : 'POST',
		cache : false, //IE不要cache （預設是true）
		async : true, //是否採用非同步（預設是true）
		contentType : contentType,
		success : function(response) {
			ajax_nextmethod(doThing, response); //傳給下一個人使用
		},
	});
}


/*  ===============================================
 *  共用ajax ：初始取出資料
 *  data   ：是每個不同類型的資料取出
 *  doThing：根據不同的doThing去不同的method處理回傳的資料
 */
function ajax_data(servletURL, data){
	var doThing = data.doThing;
	$.ajax({
		url : servletURL ,
		data : data,
		method : 'POST',
		cache : false, //IE不要cache （預設是true）
		async : true, //是否採用非同步（預設是true）
		contentType : "application/x-www-form-urlencoded", //傳給Servlet的格式
	  //dataType : "", //若使用text回傳字串，若使用json就可以少一個JSON.parse轉譯 
						 //[default: Intelligent Guess (xml, json, script, or html)]
		success : function(response) {
			ajax_nextmethod(doThing, response); //傳給下一個人使用
		},
	});
}



/* 共用Dialog
 * 先用setDialog設定顯示模式，再用openDialog傳進資料打開畫面
 */
function setDialog(id, width, isModal){
	$(id).dialog({
		autoOpen : false,
		show : { effect : "blind", duration : 300},
		hide : { effect : "explode", duration : 300},
		width : width,
		beforeClose : function(){
			$(id).html("");
		},
		modal : isModal
	});
}

function openDialog(id, data, url){
	
	$.ajax({
		url : url, //contextPath + "/console/websiteLinks/edit-webNameOne.jsp"
		data: data,
		cache: false,
		type: "POST",
		error : function(xhr) {
			$("#"+id).html("資料讀取失敗，請稍後重試！");
			$("#"+id).dialog("open");
		},
		success : function(response) {
			$("#"+id).html(response);
			$("#"+id).dialog("open");
			
			dialog_nextmethod(id, data); //傳給下一個人使用
			/*
			if(id == "user-dialog"){
				setPSW_Dialog(data);
			}  
			/*
		},
	});
}