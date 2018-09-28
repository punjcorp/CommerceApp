/**
 * This JS file has all the sources needed for item lookup
 * 
 */
var token = $("meta[name='_csrf']").attr("content");
var invDataTable;
var SearchBean= function() {
	this.searchText;
	this.pager;
}

$.extend(SearchBean.prototype, {

	
});

$(function() {
	
	invDataTable=$('#invDataTable').DataTable( {
        responsive : true,
        colReorder: true,
        buttons: [
            'copyHtml5',
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ],
		columns: [ { "name": "sNo" },
			{ "name": "itemId" },
			{ "name": "locationId" },
		    { "name": "totalQty" },
		    { "name": "nonSellableQty" },
		    { "name": "reservedQty" },
		    { "name": "sohQty" } ]
    } );
	
	invDataTable.buttons().container().appendTo( $('#tableBtns') );
	

	$('[id^=searchText]').autocomplete({
		minLength : 3,
		source : function(request, response) {
			$('#itemSearchBusy').removeClass('d-none');
			var searchBean = new SearchBean();
			searchBean.searchText=$('#searchText').val();
			var formdata = JSON.stringify(searchBean);

			// AJAX call here and refresh the Expense Screen after the save
			$.ajax({
				url : '/order_item_lookup',
				type : 'POST',
				cache : false,
				data : formdata,
				contentType : "application/json; charset=utf-8",
				dataType : "json",
				success : function(data) {
					$('#itemSearchBusy').addClass('d-none');
					if(!data.length){
						$('#searchText').val('');
		            	$('#itemId').val('');						
						$('#itemErrorMsg').addClass('invalid-feedback');
						$('#itemErrorMsg').html('Please enter a valid Item!');
						$('#itemErrorMsg').show();
					}else{
						$('#itemErrorMsg').hide();
						response($.map(data, function(item) {
							var dataVal = "<small>" + item.itemId + "-" + item.name + "</small>";
							var descVal = item.name;
							
							var finalItemPic='';
							if(item.imageType && item.imageType.length>0)
								finalItemPic='data:'+item.imageType+';base64,'+item.baseEncodedImage;
							else
								finalItemPic='/images/item_image_default_200.png';
							
							return {
								label : dataVal,
								value : item.itemId,
								desc : descVal,
								longDesc : item.longDesc,
								pic : '/images/default_image.png',
								skuImage: finalItemPic
							}
						}));
					}
				},
				beforeSend : function(xhr) {
					xhr.setRequestHeader('X-CSRF-TOKEN', token)
				}
			});

		},
		change: function (event, ui) {
			if (ui.item == null || ui.item == undefined) {
            	$('#searchText').val('');	
            	$('#itemId').val('');
				$('#itemErrorMsg').addClass('invalid-feedback');
				$('#itemErrorMsg').html('Please select an valid item!!');
				$('#itemErrorMsg').show();
            } else {
            	$('#itemErrorMsg').hide();
            }
        },
		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				updateItemDetails(event, ui);
			}
		}
	});
	
	
});





/* This section will allow the item listing to be in a specific format */
$["ui"]["autocomplete"].prototype["_renderItem"] = function(ul, item) {
		
		return $("<li></li>").data("item.autocomplete", item).html($('<div/>', {
			'class' : 'row'
		}).append($('<div/>', {
			'class' : 'col-4'
		}).append($('<div/>', {
			'class' : 'preview-thumbnail-cart'
		}).append($('<img/>', {
			src : item.pic,
			class: 'img-thumbnail'
		})))).append($('<div/>', {
			'class' : 'col-6'
		}).append($('<span/>', {
			html : "<b>" + item.value + "</b><br/>" + item.desc
		})))).appendTo(ul);		

};



function updateItemDetails(event, ui){
	var itemId = ui.item.value;
	$('#itemId').val(itemId);
	$('#searchText').val(ui.item.desc);
}