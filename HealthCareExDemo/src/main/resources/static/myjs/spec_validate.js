$(document).ready(function () {
			//1.hide error section
			$("#specCodeError").hide();
			$("#specNameError").hide();
			$("#specNoteError").hide();

			//2.define error variable
			var specCodeError = false;
			var specNameError = false;
			var specNoteError = false;

			//3. validate function
			function validate_specCode() {
				var val = $("#specCode").val();
				var exp = /^[A-Z]{4,10}$/;
				if (val == '') {
					$("#specCodeError").show();
					$("#specCodeError").html("Please Enter <b>specCode</b>");
					$("#specCodeError").css("color", "red");
					specCodeError = false;
				} else if (!exp.test(val)) {
					$("#specCodeError").show();
					$("#specCodeError").html("*<b>specCode</b> must be 4-12");
					$("#specCodeError").css("color", "red");
					specCodeError = false;
				} else {
					var id = 0; //for register
					if($("#id").val()!=undefined){ //edit page
						specCodeError = true;
						id = $("#id").val();
					}
					$.ajex({
						url : 'checkCode',
						data: {"code": val,"id":id},
						success:function(respTxt){
							if(respTxt!=''){
								$("#specCodeError").show();
					            $("#specCodeError").html("*<b>specCode</b> must be 4-12");
					            $("#specCodeError").css("color", "red");
					              specCodeError = false;
							} else{
								$("#specCodeError").hide();
					             specCodeError = true;
							}
						}
					});
				}
				return specCodeError;
			}

			function validate_specName() {
				var val = $("#specName").val();
				var exp = /^[A-Za-z0-9\s\. ]{4,60}$/;
				if (val == "") {
					$("#specNameError").show();
					$("#specNameError").html("Please Enter <b>specCode</b>");
					$("#specNameError").css("color", "red");
					specNameError = false;
				} else if (!exp.test(val)) {
					$("#specNameError").show();
					$("#specNameError").html("*<b>specName</b> must be 4-12");
					$("#specNameError").css("color", "red");
					specNameError = false;
				} else {
					$("#specNameError").hide();
					specNameError = true;
				}
				return specNameError;
			}

			function validate_specNote() {
				var val = $("#specNote").val();
				var exp = /^[A-Za-z0-9\s\. ]{10,150}$/;
				if (val == "") {
					$("#specNoteError").show();
					$("#specNoteError").html("Please Enter <b>specNote</b>");
					$("#specNoteError").css("color", "red");
					specNoteError = false;
				} else if (!exp.test(val)) {
					$("#specNoteError").show();
					$("#specNoteError").html("*<b>specNote</b> must be 4-12");
					$("#specNoteError").css("color", "red");
					specNoteError = false;
				} else {
					$("#specNoteError").hide();
					specNoteError = true;
				}
				return specNoteError;
			}
			//4. action-event
			$("#specCode").keyup(function () {
				$(this).val($(this).val().toUpperCase());
				validate_specCode();
			});

			$("#specName").keyup(function () {
				$(this).val($(this).val().toUpperCase());
				validate_specName();
			});

			$("#specNote").keyup(function () {
				$(this).val($(this).val().toUpperCase());
				validate_specNote();
			});
			//5. on submit
			$("#specForm").submit(function () {
				validate_specCode();
				validate_specName();
				validate_specNote();

				if (specCodeError && specNameError && specNoteError) return true;
				else return false;
			});
		});