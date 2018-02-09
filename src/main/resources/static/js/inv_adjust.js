/**
 * This JS file has all the sources needed for inventory adjustment pages
 * 
 */
// Global variable section
var itemIndex;

/**
 * This function will be called on page load and has the following functionalities
 * 1. When the choose item button from item lookup control is clicked, the selected
 * 		item is displayed in the respected line item on the screen
 * 2. On change of location field, the call to show correct inventory will be made
 * 3. On change of reason code, all the reason code for line items will be changed 
 * 4. When the item lookup icon is clicked at line item, the item lookup pop up is 
 * 		displayed.
 * @returns
 */
$(function() {
	$('#itemChooseBtn').click(function(e) {
		$('#itemModal').modal('toggle');
		var rdItem = $('input[name="rditemId"]');
		var checkValue = rdItem.filter(':checked').val();
		$('#invAdjustItems' + itemIndex + '\\.itemId').val(checkValue);
		updatechangeIndex(itemIndex);

	});

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
	
	$("#itembtn").click(function() {

		$('#itemModal').modal('toggle');

	});
});

/**
 * This method is used to update the global index variable for line item control calls
 * @param indexValue
 * @returns
 */
function setIndex(indexValue) {
	itemIndex = indexValue;
}

/**
 * This method is used to update the change index based on the selected line items from the screen
 * @param changeIndexVal
 * @returns
 */
function updatechangeIndex(changeIndexVal) {
	getInventoryDetails(changeIndexVal);
}

/**
 * This method will check for item, location and reason code to retrieve
 * the item inventory details from the database
 * @param changedIndex
 * @returns
 */
function getInventoryDetails(changedIndex) {
	var itemIdVal = $('#invAdjustItems' + changedIndex + '\\.itemId').val();
	var locationIdVal = $('#locationId').val();
	var reasonIdVal = $('#invAdjustItems' + changedIndex + '\\.reasonCodeId')
			.val();

	if (itemIdVal === '' || locationIdVal === '' || reasonIdVal === '') {
		console.log('no changes');
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
 * This method is used to update the inventory details in the respective line item fields
 * based on the response retrieved from database
 * @param res
 * @param changedIndex
 * @returns
 */
function processInventoryDetails(res, changedIndex) {
	var result = res;
	$('#invAdjustItems' + changedIndex + '\\.fromAvailable').val(
			result.fromBucketQty);
	$('#invAdjustItems' + changedIndex + '\\.toAvailable').val(
			result.toBucketQty);
}

/**
 * This method is called when the delete line item button is clicked 
 * @param item
 * @returns
 */
function deleteItem(item) {

	$("#addForm").attr("action", item);

	$('#addForm').submit();
}

