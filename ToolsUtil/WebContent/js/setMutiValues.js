//放入選項 - JAVABean轉Json版
function setOption_Json(tag, optionJson){  //ex:  tag="#itvQAForm #industry"
	if(optionJson==""){
		return;
	}
	
	var json = JSON.parse(optionJson);

	$(tag).empty();
	$(tag).append(	$('<option></option>')
						.val("")
						.text("請選擇交通方式") );

	for(var i=0; i<json.length; i++){
		$(tag).append(
						$('<option></option>')
							.val(json[i].value)
							.text(json[i].name) );
	}
}

//放入選項 - 純文字值用逗點隔開版
function setOption_Values(tag, optionValues){ //ex:  tag="#itvQAForm #industry"
	
	if(optionValues.length != 0){
		var ary;
		if(optionValues.indexOf(",") != -1){
			ary = optionValues.split(",");
		}else {
			ary = [optionValues];
		}
		
		$(tag).empty();
		$(tag).append(
						$('<option></option>')
							.val("")
							.text("請選擇") );

		for(var i=0; i<ary.length; i++){
			$(tag).append(
							$('<option></option>')
								.val(ary[i])
								.text(ary[i]) );
		}
	}
	
}


//放入選中的值
function setMutilSelected(tag, value){ //ex:  tag="#itvQAForm #industry"
	if(value.length != 0){
		var ary;
		if(value.indexOf(",") != -1){
			ary = value.split(",");
		}else {
			ary = [value];
		}
		for(var i=0;i<ary.length;i++){
			$(tag).find("option[value='"+ary[i]+"']").prop("selected",true);
		}
	}
}

function setSelect2Value(tag, value){  //ex: tag="#itvQAForm #industry"
	if(value.length != 0){
		var ary;
		if(value.indexOf(",") != -1){
			ary = value.split(",");
		}else {
			ary = [value];
		}
		$(tag).val(ary).trigger('change'); //要加上trigger('change')，不然span不會放入選中的資料顯示出來
	  //$(tag).val(ary);                   //適用複選，但是少了trigger
	  //$(tag).select2("val",ary);         //只能用於單選，複選會只選到第一個（4.1會失效）
	}
}

function setCheckboxValue(tag, value){  //ex: tag="#itvQAForm input[name='industry']"
	if(value.length == 0){
		return;
	}
	
	var ary;
	if(value.indexOf(",") != -1){
		ary = value.split(",");
	}else {
		ary = [value]; //使用inArry但是value不是Array所以不能比對
	}
	$(tag).each(function(){
		var v= $(this).val();
		if($.inArray(v,ary)!=-1){
			$(this).prop("checked",true);
		}else{
			$(this).prop("checked",false);
		}
	});
}


function setCheckboxToShowHide(tag,value){  //ex:  tag="input[name='industry']"
	if(value.length!=0){
		var ary ;
		if(value.indexOf(",") != -1){
			var ary = value.split(",");
		}else {
			var ary = [value];
		}
		
	    $(tag).each(function(){
			    var v= $(this).val();
			    if($.inArray(v,ary)!=-1){
			    	$(this).prop("checked",true);
		     		$(this).show();
		     		$(this).next("label").show();
			    	if(v=="c01"){ $("#interviewTwo").show(); }
		     	}
			    else{
		     		$(this).hide();
		     		$(this).next("label").hide();
		     	}
		    });
	}
}