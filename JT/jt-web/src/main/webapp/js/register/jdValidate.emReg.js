$.extend(validateFunction,
		{
			regValidate : function() {
				if ($("#mobileCodeDiv").attr("class") == 'item') {
					$("#regName").jdValidate(validatePrompt.regName,
							validateFunction.regName, true);
					$("#pwd").jdValidate(validatePrompt.pwd,
							validateFunction.pwd, true);
					$("#pwdRepeat").jdValidate(validatePrompt.pwdRepeat,
							validateFunction.pwdRepeat, true);
					$("#mobileCode").jdValidate(validatePrompt.mobileCode,
							validateFunction.mobileCode, true);
					return validateFunction.FORM_submit([ "#regName", "#pwd",
							"#pwdRepeat", "#mobileCode" ]);
				} else {
					$("#regName").jdValidate(validatePrompt.regName,
							validateFunction.regName, true);
					$("#pwd").jdValidate(validatePrompt.pwd,
							validateFunction.pwd, true);
					$("#pwdRepeat").jdValidate(validatePrompt.pwdRepeat,
							validateFunction.pwdRepeat, true);
					return validateFunction.FORM_submit([ "#regName", "#pwd",
							"#pwdRepeat" ]);
				}
			}
		});
var isSubmit = false;
$("#pwd").bind("keyup", function() {
	validateFunction.pwdstrength();
}).jdValidate(validatePrompt.pwd, validateFunction.pwd)
$("#pwdRepeat")
		.jdValidate(validatePrompt.pwdRepeat, validateFunction.pwdRepeat);
$("#regName").jdValidate(validatePrompt.regName, validateFunction.regName);
//$("#mobileCode").jdValidate(validatePrompt.mobileCode,
//		validateFunction.mobileCode);
//$("#phone").jdValidate(validatePrompt.phone,
//		validateFunction.phone);
//$("#mail").jdValidate(validatePrompt.mail,
//		validateFunction.mail);

function checkReadMe() {
	if ($("#readme").attr("checked") == true) {
		$("#protocol_error").removeClass().addClass("error hide");
		return true;
	} else {
		$("#protocol_error").removeClass().addClass("error");
		return false;
	}
}

function agreeonProtocol() {
	if ($("#readme").attr("checked") == true) {
		$("#protocol_error").removeClass().addClass("error hide");
		return true;
	}
}

function protocolReg() {
	$("#closeBox").click();
	//reg();
}
//主注册流程
function reg() {
	if (isSubmit) {
		return;
	}
	var mobileCodeFlag = false;
	var agreeProtocol = checkReadMe();
	var regNameok = validateRegName();
	var passed = false;
	var mobile = $("#phone").val();
	if(closeMobileReg == 1)
	{
		if (mobile == "") {
			$('#phone').addClass('highlight2');
			$("#phone_error").html("请输入手机号码");
			$("#phone_error").removeClass().addClass("error");
			$("#phone_error").show();
		}
	    passed = validateFunction.regValidate() && regNameok && mobileFlags && agreeProtocol;
	}else
	{
		if (mobile == "") {
			$('#phone').addClass('highlight2');
			$("#phone_error").html("请输入手机号码");
			$("#phone_error").removeClass().addClass("error");
			$("#phone_error").show();
		}
		var mobileCode = $("#mobileCode").val();
		if (mobileCode == "") {
			$('#mobileCode').addClass('highlight2');
			$('#mobileCode_error').removeClass().addClass('error').html('请输入短信验证码');
			$('#mobileCode_error').show();
		} else {
			mobileCodeFlag = true;
		}
		var state = $("#state").val();
		if(state == "unbind")
		{
			mobileFlags=true;
		}
		isSubmit = true;
	    passed = validateFunction.regValidate() && regNameok && agreeProtocol
				&& mobileCodeFlag && mobileFlags;
	}
	
	if (passed) {
		$("#registsubmit").attr({
			"disabled" : "disabled"
		}).removeClass().addClass("btn-img btn-regist wait-btn");
		var _password = $("#personRegForm [name=password]").val();
		var _username = $("#personRegForm [name=username]").val();
		var _phone = $("#personRegForm [name=phone]").val();
		$.ajax({
			type : "POST",
			url : "/user/doRegister",
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			data : {password:_password,username:_username,phone:_phone},	// 请求参数
			dataType : 'json',	//返回值类型
			success : function(result) {
				if(result.status == "200"){
					// 注册成功，去登录页
					showMessage('注册成功，请登录！');
					verc();
					$("#registsubmit").removeAttr("disabled").removeClass()
							.addClass("btn-img btn-regist");
					isSubmit = false;
					return;
				}else{
					showMessage('注册失败,请联系管理员!');
					//alert('注册失败，请重新注册！   ' + result.data );
				}
//				if (result) {
//					var obj = eval(result);
//					if (obj.info) {
//						showMessage(obj.info);
////					    alert(obj.info);
//						verc();
//						$("#registsubmit").removeAttr("disabled").removeClass()
//								.addClass("btn-img btn-regist");
//						isSubmit = false;
//						return;
//					}
//					if (obj.noAuth) {
//						verc();
//						window.location = obj.noAuth;
//						return;
//					}
//					if (obj.success == true) {
//						window.location = obj.dispatchUrl;
//					}
//				}
			}
		});
	} else {
		$("#registsubmit").removeAttr("disabled").removeClass().addClass(
				"btn-img btn-regist");
		isSubmit = false;
	}
}
//popup注册
function popupReg() {
	var mobileCodeFlag = false;
	 var agreeProtocol = checkReadMe();
		var mobileCode = $("#mobileCode").val();
		if (mobileCode == "") {
			$("#mobileCode").attr({
				"class" : "text highlight2"
			});
			$('#mobileCode_error').addClass('error').html('请输入短信验证码');
		} else {
			mobileCodeFlag = true;
		}
	    var passed = validateRegName() && validateFunction.regValidate() && agreeProtocol && mobileCodeFlag && mobileFlags;;
	    if (passed) {
	        $("#popupRegButton").attr({ "disabled": "disabled" }).removeClass().addClass("btn-img btn-regist wait-btn");
	        $.ajax({
	            type: "POST",
	            url: "../register/regService?r=" + Math.random(),
	            contentType: "application/x-www-form-urlencoded; charset=utf-8",
	            data: $("#popupPersonRegForm").serialize(),
	            success: function (result) {
	                if (result) {
	                    var obj = eval(result);
	                    if (obj.info) {
	                    	showMessage(obj.info);
	                        verc();
	                        $("#popupRegButton").removeAttr("disabled").removeClass().addClass("btn-img btn-regist");
	                        return;
	                    }
	                    if (obj.noAuth) {
	                        verc();
	                        window.parent.location = obj.noAuth;
	                        return;
	                    }
	                    if (obj.success == true) {
	                        window.parent.jdModelCallCenter.init(true);
	                        return;
	                    }
	                }
	            }
	        });
	    } else {
	        $("#popupRegButton").removeAttr("disabled").removeClass().addClass("btn-img btn-regist");
	    }
	
}

function popupContinueReg() {
	$("#protocolContent").removeClass().addClass("regist-bor hide");
	$("#popupPersonRegForm").show();

	popupReg();
}

function showProtocol() {
	$("#popupPersonRegForm").hide();
	$("#protocolContent").removeClass().addClass("regist-bor");

}
function showMessage(alertMsg)
{
	$.jdThickBox({
		  type: "text",/*也可以是text,html,image,ajax,json*/
	         width: 360,
	         height: 100,
	         source: '<div class="thickbox-tip">'
	        	 		+'<div class="icon-box">'
	        	 		+'<span class="warn-icon m-icon"></span>'
	        	 		+ '<div class="item-fore ">'
	        	 		+'<h2 class="ftx-04 " id="alertMsg">'+alertMsg+'</h2>'
	        	 		+' </div>'
	        	 		+'</div>'
	        	 		+'</div>',
	         title: "温馨提示",
	         _close_val: "×",
	         _con: "opinioncon",
	         _titleOn: true
	});
}