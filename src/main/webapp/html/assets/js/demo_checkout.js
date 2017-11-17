/**
 *
 */
var step = 1;
var countdown = 60;
var myTimer;
var sent=0;
var executeUrl="";
var bank_card = new Object();
var payer_agreement=new Object();
var payer_info = new Object();
var payer = new Object();
$(document).ready(function(){

	// parameters for scan qrcode
	var amount1 = new Object();
	amount1.total = "0.03";
	amount1.currency = "CNY";

	var sub_order1 = new Object();
	sub_order1.amount = amount1;
	sub_order1.mer_sub_reference_id = getRandomNum(16);
	sub_order1.order_summary = "rest demo test";
	sub_order1.trans_code = "01121990";
	sub_order1.is_customs = "TRUE";
	sub_order1.sub_trans_type = "1";
	var item1 = new Object();
	item1.mer_item_id = "071815243911";
	item1.type = "FOOD";
	item1.name = "Beef";
	item1.description = "beef";
	item1.quantity = "2";

	var amount2 = new Object();
	amount2.total = "0.02";
	amount2.currency = "CNY";
	item1.amount = amount2;
	var item2 = new Object();
	item2.mer_item_id = "071815243912";
	item2.type = "ELECTRONIC";
	item2.name = "Mac Book Pro";
	item2.description = "Apple Mac Book";
	item2.quantity = "3";
	var amount3 = new Object();
	amount3.total = "0.01";
	amount3.currency = "CNY";
	item2.amount = amount3;
	var items = new Array();
	items[0] = item1;
	items[1] = item2;
	sub_order1.items=items;

	var sub_order2 = new Object();
	var amount4 = new Object();
	amount4.total = "0.01";
	amount4.currency = "CNY";
	sub_order2.amount = amount4;
	sub_order2.mer_sub_reference_id = getRandomNum(16);
	sub_order2.trans_code = "03223010";
	sub_order2.sub_trans_type = "3";

	var order = new Object();
	order.sub_mer_id = "umf_sub_mer_001";
	order.mer_reference_id = getRandomNum(16);
	var date = new Date();
	order.mer_date = date.yyyymmdd();
	var amount = new Object();
	amount.total = "0.04";
	amount.currency = "CNY";
	order.amount = amount;
	order.expire_time="360";
	var sub_orders = new Array();
	sub_orders[0] = sub_order1;
	sub_orders[1] = sub_order2;
	order.sub_orders = sub_orders;
	//order.user_ip = "10.1.1.1";

	var risk_info=new Object();
	risk_info.trans_type="02";
	risk_info.goods_type="1";
	risk_info.business_type="Y";
	risk_info.real_name="0";

	$("#alerts").hide();
	$("#step1").click();
	if(isWeixinBrowser()){
		$("#wechat-scan-code").addClass("hidden");
		$("#ali-pay").addClass("hidden");
	}else{
		$("#wechat-in-app").addClass("hidden");
	}
	$("#step1").click();
	$("#CNYprice").removeClass("hidden")
	$("#USDprice").addClass("hidden");
	$("#comfirmPay").addClass("hidden");

	$("#button-delivery-method").bind("click", function () {
        $("#step2").click();
        gotoTopOfElement("step1");
        $("#radio_paytype_usd").attr("checked","checked");
    });
	$("#button-currency-method").bind("click", function () {
        $("#step3").click();
        gotoTopOfElement("step2");
    	});

    // $("#button-payment-next").bind("click", function () {
    //     $("#step4").click();
    //     gotoTopOfElement("step3");
    // });

    $("#wechat-inApp-web-based").attr("disabled", "true");

    $('#input-card-no').on('keyup mouseout input',function(){
        var $this = $(this),
        v = $this.val();
        /\S{5}/.test(v) && $this.val(v.replace(/\s/g,'').replace(/(.{4})/g, "$1 "));

    });

    // confirm payment
	$("#confirm_card_payment").click(function(){
		$("#alerts").hide();
		$("body").addClass("loading");
		var payment =  new Object();
		payer.payer_info.bank_card.verify_code = $('#input-verify-code').val();;
		payment.payer = payer;
		var pageData =  new Object();
		pageData.payment = payment;
		pageData.url = executeUrl;
		$.ajax("/restdemo/payments/executePayment",{
			method:"POST",
			contentType :"application/json; charset=utf-8",
			data:JSON.stringify(pageData),
			dataType:"json",
			headers:{},
			success:function(data, statusCode){
				if(data.meta.ret_code === '0000'){
					$("#confirmation_card").removeClass("hidden");
					//window.location = getRootPath() + "/html/payment_success.html?order_id=" + data.payment.order.mer_reference_id;
					window.location = getRootPath() + "/html/payment_success.html?order_id=" +"for test only";
				}else{
					$("#msg").text(data.meta.ret_msg);
					$("#alerts").show();
				}
			},
			error:function(err){
				console.log(err);
				$("#msg").text(data.meta.ret_msg);
				$("#alerts").show();
			},
			complete: function(){
				$("body").removeClass("loading");
			}
		});
	});
	$("#button-payment-next").click(function(){
		$('#payinfo_wx').addClass("hidden");
		$("#alerts").hide();
		var pay_type = $('#pay_type_radio input:radio:checked').val();
		if(pay_type == "UNIONPAY_CARD"){
			if(step==1){
				var targetVal = removeAllSpace($("#input-card-no").val())
				var cardInfo = getCardInfo(targetVal);
				if(cardInfo.error_msg){
					$("#unionpay_card_info .form-group").addClass('has-error');
					$("#unionpay_card_info .form-group span").html("Please input a valid bank card number.");
					$("#input-card-no").focus();
					$("#input-card-no").select();
					return;
				}else{
					$("#unionpay_card_info .form-group").removeClass('has-error');
					$("#unionpay_card_info .form-group span").html("You may use this bank card number for demo.6217000010045997833     6217580100004222451");

				}
				$("#unionpay_card_info").addClass("hidden");
				$("#unionpay_step2").removeClass("hidden");
				$("#button-payment-next").prop("disabled", true);
				$("#button-payment-pre").removeClass("hidden");
				$("#gate_id").attr("value", cardInfo.bankCode);
				if(cardInfo.cardType == "CC"){
					$("#cvv2").removeClass("hidden");
					$("#expiration_data").removeClass("hidden");
					$("#input-card-type").attr("value", "CREDIT_CARD");
				}else{
					$("#cvv2").addClass("hidden");
					$("#expiration_data").addClass("hidden");
					$("#input-card-type").attr("value", "DEBIT_CARD");
				}
				console.log(cardInfo);
				setBankInfo(cardInfo);
				step++;
			}else{
				$('#payinfo_wx').addClass("hidden");
				$("#paybycard_conformation").removeClass("hidden");
				$("#step4").click();
				gotoTopOfElement("step3");
			}
		}else if(pay_type == "UMFPAY"){
				order.order_summary = "test for UMFPAY";
				$("body").addClass("loading");

				payer.payment_method = "NOT_APPLICABLE";
				payer.payer_info=payer_info;
				if(isMobile()){
					payer.interface_type = "SERVER_TO_H5WEB";
				}else{
					payer.interface_type = "SERVER_TO_WEB";
				}
				payer.business_type = "B2C";
				// parameters for create a payment
				var payment = new Object();
				payment.payer = payer;
				payment.order = order;
				payment.notify_url = "https://dev.restdemo.umftech.com/restdemo/demo/notifyResultRest";
				payment.ret_url = getRootPath() + "/html/payment_success.html";
				payment.risk_info=risk_info;
				$.ajax("/restdemo/payments/createPayment",{
					method:"POST",
					contentType :"application/json; charset=utf-8",
					data:JSON.stringify(payment),
					dataType:"json",
					headers:{},
					success:function(data, statusCode){
						if(data.meta.ret_code==="0000"){
							window.location.href= data.links[3].href;
						}else{
							$("#msg").text(data.meta.ret_msg);
							$("#alerts").show();
						}
					},
					error:function(err){
						console.log(err);
						$("#msg").text(data.meta.ret_msg);
						$("#alerts").show();
					},
					complete: function(){
						$("body").removeClass("loading");
					}
				});
				
		}else if(pay_type == "UMFPAY_URL"){
			order.order_summary = "test for UMFPAY";
			$("body").addClass("loading");

			payer.payment_method = "NOT_APPLICABLE";
			payer.payer_info=payer_info;
			if(isMobile()){
				payer.interface_type = "SERVER_TO_H5WEB";
			}else{
				payer.interface_type = "SERVER_TO_WEB";
			}
			payer.business_type = "B2C";
			// parameters for create a payment
			var payment = new Object();
			payment.payer = payer;
			payment.order = order;
			payment.notify_url = "https://dev.restdemo.umftech.com/restdemo/demo/notifyResultRest";
			payment.ret_url = getRootPath() + "/html/payment_success.html";
			payment.risk_info=risk_info;
			$.ajax("/restdemo/payments/createPayment",{
				method:"POST",
				contentType :"application/json; charset=utf-8",
				data:JSON.stringify(payment),
				dataType:"json",
				headers:{},
				success:function(data, statusCode){
					if(data.meta.ret_code==="0000"){
						$("#umpay_url_link").attr('href', data.links[2].href);
						$("#umpay_url_link").text(data.links[2].href);
						$("#umpay_url_info").removeClass("hidden");
					}else{
						$("#msg").text(data.meta.ret_msg);
						$("#alerts").show();
					}
				},
				error:function(err){
					console.log(err);
					$("#msg").text(data.meta.ret_msg);
					$("#alerts").show();
				},
				complete: function(){
					$("body").removeClass("loading");
				}
			});
		}else if(pay_type == "WECHAT_WEB"){
			$("body").addClass("loading");
			var pageDate = new Object();
			pageDate.notify_url = "http://umpay.huiplus.com.cn/restdemo/html/officialAccount.html";
			pageDate.mer_reference_id = getRandomNum(16);

			$.ajax("/restdemo/demo/getOpenID",{
				method:"POST",
				contentType :"application/json; charset=utf-8",
				data:JSON.stringify(pageDate),
				dataType:"json",
				headers:{},
				success:function(data, statusCode){
					if(data.meta.ret_code=='0000'){
						window.location.href= data.links[0].href;
					}else{
						$("#msg").text(data.meta.ret_msg);
						$("#alerts").show();
					}
				},
				error:function(err){
					console.log(err);
					$("#msg").text(data.meta.ret_msg);
					$("#alerts").show();
				},
				complete: function(){
					$("body").removeClass("loading");
				}
			});
		}else{
			order.order_summary = "test for scan qrcode";
			$("body").addClass("loading");
			var qr_code_scan = new Object();
			//qr_code_scan.citizen_id_type="IDENTITY_CARD";
			qr_code_scan.citizen_id_type="1";
			qr_code_scan.citizen_id_number=$("#input-id-no").val();
			payer_info.name=$("#name_app").val();
			payer_info.phone=$("#input-iphone-no").val();
			payer_info.qr_code_scan=qr_code_scan;

			payer.payment_method = pay_type;
			payer.payer_info=payer_info;
			payer.interface_type = "SERVER_TO_SERVER";
			payer.business_type = "B2C";
			// parameters for create a payment
			var payment = new Object();
			payment.payer = payer;
			payment.order = order;
			payment.notify_url = "https://dev.restdemo.umftech.com/restdemo/demo/notifyResultRest";
			payment.risk_info=risk_info;
			$.ajax("/restdemo/payments/createPayment",{
				method:"POST",
				contentType :"application/json; charset=utf-8",
				data:JSON.stringify(payment),
				dataType:"json",
				headers:{},
				success:function(data, statusCode){
					if(data.meta.ret_code==="0000"){
						$('#qrcode').empty();
						$('#qrcode').qrcode(data.payment.payer.payer_info.qr_code_scan.qr_code_url);
						$("#app_scan_info").addClass("hidden");
						$('#payinfo_wx').removeClass("hidden");
						if(pay_type == 'WECHAT_SCAN'){
							$("#tipMsg").text("Please use WeChat to scan the QR code below to finish the payment.");
						}else{
							$("#tipMsg").text("Please use Alipay to scan the QR code below to finish the payment.");
						}
						$("#step4").click();
						gotoTopOfElement("step3");
						myTimer = setInterval(function() {
							getPaymentStatus(data.payment.id); }
						, 1000);
					}else{
						$("#msg").text(data.meta.ret_msg);
						$("#alerts").show();
					}
				},
				error:function(err){
					console.log(err);
					$("#msg").text(data.meta.ret_msg);
					$("#alerts").show();
				},
				complete: function(){
					$("body").removeClass("loading");
				}
			});
			
		}
	});

	$("#get_verify_code").click(function(){
		$("#alerts").hide();
		$("#get_verify_code").prop("disabled", true);
		sent = 1;

		//var bank_card = new Object();
		bank_card.phone = $("#input-phone-number").val();
		bank_card.number = removeAllSpace($("#card_no_step2").val());
		bank_card.valid_date = $("#input-expiration-date").val();
		bank_card.cvv2 = $("#input-card-cvv2").val();
		//bank_card.card_holder = $("#card_holder").val();
		bank_card.citizen_id_type = "IDENTITY_CARD";
		bank_card.citizen_id_number = $("#input-id").val();
		bank_card.external_customer_id="";
		bank_card.payer_name=$("#card_holder").val();
		//var payer_info = new Object();
		payer_info.bank_card = bank_card;

		payer_agreement.usr_pay_agreement_id="";
		payer_agreement.usr_busi_agreement_id="";

		payer_info.payer_agreement=payer_agreement;

		payer.payer_info = payer_info;
		payer.payment_method = $("#input-card-type").val();
		payer.bank_code = $("#gate_id").val();
		payer.interface_type = "SERVER_TO_SERVER";
		payer.business_type = "B2C";

		// parameters for create a payment
		var pageData = new Object();
		pageData.payer = payer;
		order.order_summary = "test for scan bankcard";
		pageData.order = order;
		pageData.notify_url = "https://dev.restdemo.umftech.com/restdemo/demo/notifyResultRest";
		pageData.risk_info=risk_info;

		$.ajax("/restdemo/payments/createPayment",{
			method:"POST",
			contentType :"application/json; charset=utf-8",
			data:JSON.stringify(pageData),
			dataType:"json",
			headers:{},
			success:function(data, statusCode){
				console.log("Returned string of verifySMS:"+JSON.stringify(data));
				if("0000" === data.meta.ret_code){
					//TODO
					executeUrl=data.links[1].href;
					$("#input-verify-code").prop('disabled', false);
					$("#input-verify-code").focus();
					$("#get_verify_code").prop("disabled", true);
					$("#button-payment-next").prop("disabled", false);
					settime(this);
				}else{
					$("#get_verify_code").prop("disabled", false);
					$("#msg").text(data.meta.ret_msg);
					$("#alerts").show();
				}
			},
			error:function(err){
				console.log(err);
				$("#msg").text(data.meta.ret_msg);
				$("#alerts").show();
				$("#get_verify_code").prop("disabled", false);
			}
		});

	});

	$("#button-payment-pre").click(function(){
		$("#unionpay_card_info").removeClass("hidden");
		$("#unionpay_step2").addClass("hidden");
		$("#button-payment-next").attr("value", "Next");
		$("#button-payment-pre").addClass("hidden");
		$("#button-payment-next").prop("disabled", false);
		step--;
	});

	$("input[type=radio][name=payment_type]").change(function(e) {
		var targetVal = $(e.target).val();
		$("#unionpay_card_info").addClass("hidden");
		$("#app_scan_info").addClass("hidden");
		$("#umpay_url_info").addClass("hidden");
		$("#unionpay_step2").addClass("hidden");
		$("#button-payment-next").prop("disabled", false);
		if (targetVal == "UNIONPAY_CARD") {
			if(step==1){
				$("#unionpay_card_info").removeClass("hidden");
				$("#button-payment-next").attr("value", "Next");
				$("#umpay_url_info").addClass("hidden");
			}else{
				$("#unionpay_step2").removeClass("hidden");
				$("#button-payment-pre").removeClass("hidden");
				if(sent==0){
					$("#button-payment-next").prop("disabled", true);
				}
			}
        }else if(targetVal == "UMFPAY"){
        	$("#app_scan_info").addClass("hidden");
			$("#button-payment-pre").addClass("hidden");
			$("#umpay_url_info").addClass("hidden");
        }else if(targetVal == "UMFPAY_URL"){
        	$("#app_scan_info").addClass("hidden");
			$("#button-payment-pre").addClass("hidden");
			$("#umpay_url_info").addClass("hidden");
        }
        else {
			$("#app_scan_info").removeClass("hidden");
			$("#button-payment-pre").addClass("hidden");
			$("#button-payment-next").attr("value", "Next");
        }
	});

    $("input[type=radio][name=price]").change(function(e) {
		var targetVal = $(e.target).val();
		$("#CNYprice").addClass("hidden");
		$("#USDprice").addClass("hidden");
        if (targetVal == "CNY") {
			$("#CNYprice").removeClass("hidden");
        }else {
			$("#USDprice").removeClass("hidden");
        }
    });
});

function settime(val) {
    if (countdown == 0) {
    	$("#get_verify_code").prop("disabled", false);
        $("#get_verify_code").attr("value", "Send code");
    	countdown = 60;
    } else {
        $("#get_verify_code").prop("disabled", true);
        $("#get_verify_code").attr("value", "resend(" + countdown + ")");
        countdown--;
        setTimeout(function() {
        	settime(val)
        },1000)
    }
}

function getCardInfo(cardNum){
	var cardInfo = getBankBin(cardNum);
	if(cardInfo.error_msg){
		console.log(cardInfo.error_msg);
	}else{
		return cardInfo;
	}
	return cardInfo;
}

function setBankInfo(cardInfo){
	if(cardInfo.error_msg){
		$("#err_msg").text(cardInfo.error_msg);
	}else{
		$("#bank_name").text(cardInfo.bankName);
		$("#bank_code").text(cardInfo.bankCode);
		$("#bank_card_type").text(cardInfo.cardType);
		$("#card_type_name").text(cardInfo.cardTypeName);30
		$("#card_type_step2").text(cardInfo.bankName+cardInfo.cardTypeName);
		$("#card_no_step2").attr("value", $("#input-card-no").val());
	}
}
function getPaymentStatus(paymentId){
	console.log("getPaymentStatus");
	var pageData = new Object();
//	pageData["url"] = "https://fx.soopay.net/cberest/v1/payments/payment/PAY_GM3TANZSGEYTKMBVGM4DIMJUGUZDAMJXGA3TEMOU";
	pageData.payment_id = paymentId;
	$.ajax("/restdemo/demo/checkPaymentStatus", {
    	method:"POST",
    	contentType :"application/json",
    	data:JSON.stringify(pageData),
    	dataType:"json",
    	headers:{},
    	success:function(data, statusCode){
			if(data.meta.ret_code === '0000'){
				//console.log(data);
				if(data.payment.state === 'TRADE_SUCCESS'){
					//stopTimer  myTimer.stop();
					window.clearInterval(myTimer);
					//redirect to success url.
					window.location = getRootPath() + "/html/payment_success.html?order_id=" + data.payment.order.mer_reference_id;
					//window.location = "www.google.com";
				}
			}else{
					$("#msg").text(data.meta.ret_msg);
					$("#alerts").show();
			}
    	},
    	error:function(err){
    		console.log(err);
    		//myTimer.stop();
    		window.clearInterval(myTimer);
    	}
    });
}

function gotoTopOfElement(objId){
    var _targetTop = $('#'+objId).offset().top;
    jQuery("html,body").animate({scrollTop:_targetTop},300);
}

function getRootPath()
{
   var pathName = window.location.pathname.substring(1);
   var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
   return window.location.protocol + '//' + window.location.host + '/'+ webName;
}

Date.prototype.yyyymmdd = function() {
	  var mm = this.getMonth() + 1; // getMonth() is zero-based
	  var dd = this.getDate();

	  return [this.getFullYear(),
	          (mm>9 ? '' : '0') + mm,
	          (dd>9 ? '' : '0') + dd
	         ].join('');
};

//Show alert
function alertMessage(message) {

    $("#msg").text(message);
    var timeOut;
    $("#alerts").slideDown();
    //Is autoclosing alert
    var delay = $(this).attr('data-delay');
    if(delay != undefined)
    {
        delay = parseInt(delay);
        clearTimeout(timeOut);
        timeOut = window.setTimeout(function() {
                alert.slideUp();
            }, delay);
    }
}
//Close alert
$('.page-alert .close').click(function(e) {
    e.preventDefault();
    $(this).closest('.page-alerts').slideUp();
});
function removeAllSpace(str) {
	return str.replace(/\s+/g, "");
}

function isWeixinBrowser(){
	  var ua = navigator.userAgent.toLowerCase();
	  return (/micromessenger/.test(ua)) ? true : false ;
}
function isMobile(){
	var ua = navigator.userAgent.toLowerCase();
	return (/(android|iphone|ipad|ipod|ios|micromessenger)/.test(ua)) ? true : false ;
}
function getRandomNum(maxlength){
	a = Math.random().toString(36);
    b = a.length;
    return (a.length > 20) ? a.substr(4, maxlength) : a.substr(4);
}
function copyToClipboard(elementId) {
	var aux = document.createElement("input");
	aux.setAttribute("value", document.getElementById(elementId).innerHTML);
	document.body.appendChild(aux);
	aux.select();
	document.execCommand("copy");
	document.body.removeChild(aux);
}