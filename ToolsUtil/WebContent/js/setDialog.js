/* ================================================================================================ */
/* dialog：
 * 1.如果<div>內已有內容，要一載入頁面就先setDialog（才能隱藏div），等要使用再打開 ；
 * 2.若是使用他頁jsp，可以等response放入後setDialog並自動打開*/
/* ================================================================================================ */
function setDialog(id, width, isModal, header, isAutoOpen){
	$(id).dialog({
		autoOpen : isAutoOpen,
		show : { effect : "blind", duration : 300},
		hide : { effect : "explode", duration : 300},
		width : width,
		modal : isModal, //鎖定頁面功能，預設關閉(false)
		//dialogClass : "dlg-no-close", //隱藏右上角的close button X
		closeOnEscape : true, //按"Esc"關閉，預設開啟(true)。
		draggable : true,  //滑鼠按住標題列拖曳，預設開啟(true)
		resizable : false, //設定dialog box縮放功能(滑鼠按住視窗右下角拖曳)，預設關閉(false)。
		title : header, //標題列的標題，預設null
		//beforeClose : function(){ //關閉前做的事情
		//	$(id).html("");
		//},
	});
}

function setDialog2(id, width, isModal, header, isAutoOpen, isEmptyDiv){
	$(id).dialog({
		autoOpen : isAutoOpen,
		show : { effect : "blind", duration : 300},
		hide : { effect : "explode", duration : 300},
		width : width,
		modal : isModal, //鎖定頁面功能，預設關閉(false)
		//dialogClass : "dlg-no-close", //隱藏右上角的close button X
		closeOnEscape : true, //按"Esc"關閉，預設開啟(true)。
		draggable : true,  //滑鼠按住標題列拖曳，預設開啟(true)
		resizable : false, //設定dialog box縮放功能(滑鼠按住視窗右下角拖曳)，預設關閉(false)。
		title : header, //標題列的標題，預設null
	});
	
	if(isEmptyDiv){
		$(id).dialog({
			beforeClose : function(){ //關閉前做的事情
				$(id).html("");
			},
		});
	}
}

//自動打開對話視窗並出 雙button
function open_dialog(id, width, isModal, header, isAutoOpen){
	$(id).dialog({
		autoOpen : isAutoOpen,
		show : { effect : "blind", duration : 300},
		hide : { effect : "explode", duration : 300},
		width : width,
		modal : isModal,                //鎖定頁面功能，僅能點選dialog box的元件，預設關閉(false)
		draggable : true,               //滑鼠按住標題列拖曳，預設開啟(true)
		title : header,                 //標題列的標題，預設null
		//dialogClass : "dlg-no-close", //隱藏右上角的close button X
		//closeOnEscape : true,         //按"Esc"關閉，預設開啟(true)。
		//draggable : false,            //設定dialog box拖曳功能(滑鼠按住標題列拖曳)，預設開啟(true)。
		//resizable : false,            //設定dialog box縮放功能(滑鼠按住視窗右下角拖曳)，預設關閉(false)。
		//close: function() {}
		buttons: [	
					{ 	text : "是",
						click: function() {
							$("input[name='isEditContact']").val("Y");
							$("#projRForm").submit();	
							$( this ).dialog( "close" );
						}
					},
					{ 	text : "否",
						click: function() {
							$("#projRForm").submit();
							$( this ).dialog( "close" );
						}
					}
				 ]
		
	});
}

function openDialog(id, url){
	$.ajax({
		url : url,
		cache: false,
		type: "POST",
		error : function(xhr) {
			$(id).html("資料讀取失敗，請稍後重試！");
			$(id).dialog("open");
		},
		success : function(response) {
			$(id).html(response);
			$(id).dialog("open");
		},
	});
}

function open_Dialog(id){
	$(id).dialog("open");
}
