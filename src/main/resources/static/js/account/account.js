/**
 * This file contains all the JS code needed for the account management
 * 
 */
var SearchBean = function() {
	this.searchText;
	this.pager;
}

$.extend(SearchBean.prototype, {

});

var searchBean = new SearchBean();
var token = $("meta[name='_csrf']").attr("content");

$(function() {

	$('#username').change(function() {
		$('#userSearchBusy').removeClass('d-none');
		searchBean.searchText = $('#username').val();
		var formdata = JSON.stringify(searchBean);

		if (searchBean.searchText != '') {

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/admin/search_account',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(user) {
					$('#userSearchBusy').addClass('d-none');
					if (user== true) {
						$('#isUserExisting').val(user);
						$('#userSearchErrorMsg').removeClass('valid-feedback');
						$('#userSearchErrorMsg').addClass('invalid-feedback');
						$('#userSearchErrorMsg').html('<h6>The username is already in use!</h6>');
						$('#userSearchErrorMsg').show();
					} else {
						$('#isUserExisting').val(user);
						$('#userSearchErrorMsg').removeClass('invalid-feedback');
						$('#userSearchErrorMsg').addClass('valid-feedback');
						$('#userSearchErrorMsg').html('<h6>The username is available.</h6>');
						$('#userSearchErrorMsg').show();
					}
				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});

		} else {
			$('#isUserExisting').val('false');
			$('#userSearchBusy').addClass('d-none');
			$('#userSearchErrorMsg').hide();
		}
	});

	//The event for file upload starts
	//*
	$('#imageData').change(function() {

		if (typeof (FileReader) != "undefined") {

			if (this.files.length > 1) {
				alert("Please select max 1 photo to upload.");
				this.preventDefault();
			} else {

				var image_holder = $('#previewPhoto');
				image_holder.empty();

				var reader = new FileReader();
				reader.onload = function(e) {
					$("<img />", {
						"src" : e.target.result,
						"class" : "img-fluid img-thumbnail"
					}).appendTo(image_holder);

					$('#btnDeletePhoto').removeClass('d-none');
					$('#imageData').addClass('d-none');
				}
				image_holder.show();
				reader.readAsDataURL($(this)[0].files[0]);
			}

		} else {
			alert("This browser does not support FileReader.");
		}
	});
	//*

	//The event for file upload ends

	$('#btnDeletePhoto').click(function() {
		var image_holder = $('#previewPhoto');
		image_holder.empty();
		$("<img />", {
			"src" : '/images/no_photo.jpg',
			"class" : "img-fluid img-thumbnail"
		}).appendTo(image_holder);
		image_holder.show();

		$('#imageData').val('');
		$('#btnDeletePhoto').addClass('d-none');
		$('#imageData').removeClass('d-none');

	});

});