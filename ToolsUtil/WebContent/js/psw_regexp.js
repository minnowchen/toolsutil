/*===================================================================================================*/

/*即時驗證方法*/
function NaNvalid(tag) {
	//遇到數字驗證後會顯示錯誤訊息，點下去會把錯誤訊息取消
	$("."+tag).each(function(){
		$(this).blur(function (){
			var v = $(this).val();
			
			if(isNaN(v)){
				$(this).next("span").text("請輸入數字");
			}
		});
		
		$(this).focus(function(){
			$(this).next("span").text("");
		});
	});
}


function PSWvalid(tag) {

	var errmsg = checkPSW(tag);
	if(errmsg.length != 0){
		$("#"+tag).next("span").text(errmsg);
	}
		
	$("#"+tag).focus(function(){
		$(this).next("span").text("");
	});
}

/*===================================================================================================*/

/*表單submit時驗證方法*/
function checkform(){
	
	var isValid = true;
	
	//密碼驗證
	var errmsg = checkPSW("pswValidation");
	if(errmsg.length != 0){
		$("#pswValidation").next("span").text(errmsg);
		isValid = false;
	}
	//數字驗證
	isNaN = checkNaN("myValidation");
	if(isNaN == false){
		isValid = false;
	}
	
	//是否送出表單
	if(isValid){
		return confirm('確認送出資料？')
	}else{
		return false;
	}
}



/*===================================================================================================*/

/* 密碼驗證 */
function checkPSW(tag){
	/*取值*/
	var value = $("#"+tag).val();
	
	/*設定正則運算法*/
//	var regx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{6,}$/g; //正則表示法 不加引號，g全域比對
	var regx = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*()])[a-zA-Z0-9!@#$%^&*()]{6,}$/g; //正則表示法 不加引號，g全域比對
	
	/*驗證*/	
	var err="";
	var isValid = true;
	
	if(regx.test(value)==false){
	
//		if( (/^(?=.*[a-z]).+$/g).test(value) == false){  //  /^ (?=.*[a-z]) .+ $/g
//			err = "必須有一個小寫英文";
//		}
//		if( (/^(?=.*[A-Z]).+$/g).test(value) == false){  //  /^ (?=.*[A-Z]) .+ $/g
//			if(err.length>0){
//				err = err + ",大寫英文";
//			}else{
//				err = "必須有一個大寫英文";
//			}
//		}
// =================================================================================================		
		if( (/^(?=.*[a-zA-Z]).+$/g).test(value) == false){  //  /^ (?=.*[a-zA-Z]) .+ $/g
		err = "必須有一個英文"; //大小寫不拘
		}		
				
		if( (/^(?=.*[0-9]).+$/g).test(value) == false){  //  /^(?=.*[0-9]).+$/g
			if(err.length>0){
				err = err + ",0~9的數字";
			}else{
				err = "必須有一個0~9的數字";
			}
		}	
		
		if( (/^(?=.*[!@#$%^&*()]).+$/g).test(value) == false){  //  /^(?=.*[!@#$%^&*()]).+$/g
			if(err.length!=0){
				err = err + ",特殊符號";
			}else{
				err = "必須有一個特殊符號";
			}
		}
		
		if( (/^.{6,}$/g).test(value) == false ){  //  /^.{6,}$/g
			if(err.length>0){
				err = err + ",不得少於六個字元";
			}else{
				err = "不得少於六個字元";
			}
		}	
	}
	
	return err;
}


/* 數字驗證 */
function checkNaN(tag){
	var isValid = true;
	$("."+tag).each(function(){
		if(isNaN($(this).val())){
			$(this).next("span").text("請輸入數字");
			isValid = false;
		}
	});
	
	return isValid;
}

