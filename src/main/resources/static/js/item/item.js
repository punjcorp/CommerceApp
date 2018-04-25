/**
 * This file has all the javascript code needed for Sales Screen
 * Auto Complete for item lookup functionality
 * 
 */

/**
 * All the global variables for sale screen will be defined here
 */

/**
 * This function will ensure the item auto complete functionality is executed when at least 3 letters has been typed in the item category
 * 
 * @returns
 */
/**
 * The expense related global variables
 */

var token = $("meta[name='_csrf']").attr("content");
var searchBean = new SearchBean();

$(function() {

	$("#hierarchy\\.name").autocomplete({
		minLength : 3,
		source : function(request, response) {
			searchBean.searchText =  $("#hierarchy\\.name").val();
			var formdata = JSON.stringify(searchBean);

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/admin/search_hierarchy',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(hierarchies) {
					response($.map(hierarchies, function(hierarchy) {
						return {
							name : hierarchy.name,
							code : hierarchy.code,
							hierarchyId : hierarchy.hierarchyId,
							desc : hierarchy.description,
							createdBy : hierarchy.createdBy	
						}
					}));

				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});

		},

		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				$('#hierarchy\\.hierarchyId').val(ui.item.hierarchyId);
				$('#hierarchy\\.name').val(ui.item.name);
			}
		}
	});

});

/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {
		return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
			'class' : 'row'
		}).append($('<div/>', {
			'class' : 'col-12'
		}).append($('<span/>', {
			html : "<b>" + item.name+ "</b><br/>" + item.desc
		})))).appendTo(ul);
};
