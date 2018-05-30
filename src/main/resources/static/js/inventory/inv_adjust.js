/**
 * This JS file has all the sources needed for inventory adjustment pages
 * 
 */
// Global variable section
var itemIndex;

var SearchBean= function() {
	this.searchText;
	this.pager;
}

$.extend(SearchBean.prototype, {

	
});

var searchBean=new SearchBean();
var token = $("meta[name='_csrf']").attr("content");

/**
 * This function will be called on page load and has the following functionalities 1. When the choose item button from item lookup control is clicked,
 * the selected item is displayed in the respected line item on the screen 2. On change of location field, the call to show correct inventory will be
 * made 3. On change of reason code, all the reason code for line items will be changed 4. When the item lookup icon is clicked at line item, the item
 * lookup pop up is displayed.
 * 
 * @returns
 */
$(function() {

	
	
	$('#locationId').on('change', function(e) {
		var selIndex = 0;
		$("[id^=invAdjustItems]").each(function() {
			if (this.id.indexOf("reasonCodeId") >= 0) {
				updatechangeIndex(selIndex);
				selIndex++;
			}
		});

	});

	$('#reasonCodeId').on('change', function(e) {
		var optionSelected = $(this).val();
		var selIndex = 0;
		$("[id^=invAdjustItems]").each(function() {
			if (this.id.indexOf("reasonCodeId") >= 0) {
				$(this).val(optionSelected);
				updatechangeIndex(selIndex);
				selIndex++;
			}
		});

	});
	
	
	$('[id^=invAdjustItems][id$=\\.itemDesc]').autocomplete({
		minLength : 3,
		source : function(request, response) {
			var index= this.element[0].id.replace(/[^0-9]/gi, '');
			var cntlId= this.element[0].id;
			var itemIdCntlId= cntlId.replace('itemDesc', 'itemId');
			$('#itemSearchBusy'+index).removeClass('d-none');
			var searchText = this.element[0].value;
			searchBean.searchText = searchText;
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
					$('#itemSearchBusy'+index).addClass('d-none');
					if(!data.length){
						
		            	$('#invAdjustItems'+index+'\\.itemDesc').val('');		
		            	$('#invAdjustItems'+index+'\\.itemId').val('');						
						
						$('#itemSearchErrorMsg'+index).addClass('invalid-feedback');
						$('#itemSearchErrorMsg'+index).html('Please enter a valid Item!');
						$('#itemSearchErrorMsg'+index).show();
					}else{
						$('#itemSearchErrorMsg'+index).hide();
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
							pic : finalItemPic
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
			var index= this.id.replace(/[^0-9]/gi, '');
            if (ui.item == null || ui.item == undefined) {
            	$('#invAdjustItems'+index+'\\.itemDesc').val('');		
            	$('#invAdjustItems'+index+'\\.itemId').val('');
				$('#itemSearchErrorMsg'+index).addClass('invalid-feedback');
				$('#itemSearchErrorMsg'+index).html('Please choose a valid Item from the list!');
				$('#itemSearchErrorMsg'+index).show();
            } else {
            	$('#itemSearchErrorMsg'+index).hide();
            }
        },
		select : function(event, ui) {
			event.preventDefault();
			if (ui.item) {
				
				var index = event.target.id.replace(/[^0-9]/gi, '');
				
				var itemId = ui.item.value;
				var itemDesc = ui.item.desc;
				$('#invAdjustItems'+index+'\\.itemId').val(itemId);
				$('#invAdjustItems'+index+'\\.itemDesc').val(itemDesc);
				
				updatechangeIndex(index);
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





/**
 * This method is used to update the global index variable for line item control calls
 * 
 * @param indexValue
 * @returns
 */
function setIndex(indexValue) {
	itemIndex = indexValue;
}

/**
 * This method is used to update all the items related to a stock adjustment
 * @returns
 */
function showEditScreen(){
	
	if(stock_invAdjustId!='' && stock_invAdjustId!='undefined' && stock_invAdjustId>0 && stock_status!='' && stock_status!='undefined' && stock_status=='C'){
		var counter=0;
		while(counter<totalRecords){
			updatechangeIndex(counter);
			counter++;
		}
	}
}

/**
 * This method is used to update the change index based on the selected line items from the screen
 * 
 * @param changeIndexVal
 * @returns
 */
function updatechangeIndex(changeIndexVal) {
	getInventoryDetails(changeIndexVal);
}

/**
 * This method will check for item, location and reason code to retrieve the item inventory details from the database
 * 
 * @param changedIndex
 * @returns
 */
function getInventoryDetails(changedIndex) {
	var itemIdVal = $('#invAdjustItems' + changedIndex + '\\.itemId').val();
	var locationIdVal = $('#locationId').val();
	var reasonIdVal = $('#invAdjustItems' + changedIndex + '\\.reasonCodeId')
			.val();

	if (itemIdVal === '' || locationIdVal === '' || reasonIdVal === '') {

		$('#invAdjustItems' + changedIndex + '\\.fromAvailable').val('');
		$('#invAdjustItems' + changedIndex + '\\.span\\.fromAvailable').text('');	
		$('#invAdjustItems' + changedIndex + '\\.toAvailable').val('');
		$('#invAdjustItems' + changedIndex + '\\.span\\.toAvailable').text('');
		
		
	} else {
		var token = $("meta[name='_csrf']").attr("content");
		var formdata = {
			'itemId' : itemIdVal,
			'reasonCodeId' : reasonIdVal,
			'locationId' : locationIdVal,
			'_csrf' : token
		};

		$.post({
			url : '/get_item_inv',
			data : formdata,
			success : function(res) {
				processInventoryDetails(res, changedIndex);
			}
		});

	}
}

/**
 * This method is used to update the inventory details in the respective line item fields based on the response retrieved from database
 * 
 * @param res
 * @param changedIndex
 * @returns
 */
function processInventoryDetails(res, changedIndex) {
	var result = res;
	$('#invAdjustItems' + changedIndex + '\\.fromAvailable').val(
			result.fromBucketQty);
	$('#invAdjustItems' + changedIndex + '\\.span\\.fromAvailable').text(
			result.fromBucketQty);	
	$('#invAdjustItems' + changedIndex + '\\.toAvailable').val(
			result.toBucketQty);
	$('#invAdjustItems' + changedIndex + '\\.span\\.toAvailable').text(
			result.toBucketQty);
}

/**
 * This method is called when the delete line item button is clicked
 * 
 * @param item
 * @returns
 */
function deleteItem(item) {

	$("#addForm").attr("action", item);

	$('#addForm').submit();
}

