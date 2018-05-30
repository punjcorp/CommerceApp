/**
 * This JS file has all the sources needed for item lookup
 * 
 */
$(function() {
	/*  Submit form using Ajax */
	$('#searchBtn').click(function(e) {

		//Prevent default submission of form
		e.preventDefault();

		resubmitLookup();
	});

	$(".item-row-action").click(function() {
		searchItemDetails(this);
	});
});

function searchItemDetails(cntl) {
	var itemId = $(cntl).data("action");
	var itemDetailsURL = "/item_details_lookup";
	itemDetailsURL += "?itemId=" + itemId;
	window.location = itemDetailsURL;
}

function resubmitLookup(pageNo) {
	if (arguments.length > 0) {
		$('#page').val(pageNo);
		var formdata = $('form[id=searchForm]').serialize();
		$.post({
			url : '/item_lookup',
			data : formdata,
			success : function(res) {
				processResponse(res);
			}
		});
	} else {
		$.post({
			url : '/item_lookup',
			data : $('form[id=searchForm]').serialize(),
			success : function(res) {
				processResponse(res);
			}
		});
	}
}

function processResponse(res) {
	//Set response
	var result = res.items;
	var pager = res.pager;
	var output = createPagination(pager.displayPages, pager);
	output += '<table class="table table-striped table-hover table-bordered"><thead class="thead-light"><tr><th>S#</th><th width="40%">Item Details</th><th>Created By</th><th>Created Date</th></tr></thead><tbody>';
	for ( var i in result) {
		var finalItemPic = '';
		if (result[i].imageType && result[i].imageType.length > 0)
			finalItemPic = 'data:' + result[i].imageType + ';base64,' + result[i].baseEncodedImage;
		else
			finalItemPic = '/images/item_image_default_200.png';

		var sno = (+i) + 1;

		output += '<tr class="item-row-action" data-action="' + result[i].itemId + '" onclick="searchItemDetails(this)"><td> ' + sno + ' </td>';
		output += '<td width="40%">';
		output += '<div class="row"> <div class="col preview-thumbnail-cart">';
		output += '<img src="' + finalItemPic + '"/>';
		output += '</div> <div class="col">';
		output += '<b>' + result[i].itemId + ' </b><br/>';
		output += '<b>' + result[i].name + ' </b><br/>';
		output += '<em>' + result[i].longDesc + ' </em><br/>';
		output += '</div> </div>';
		output += '<td>' + result[i].createdBy + '</td>';
		output += '<td>' + result[i].createdDate + '</td>';
		output += '</td></tr>';
	}
	output += "</tbody></table>";

	output += createPagination(pager.displayPages, pager);

	$('#response').html(output);

}

function createPagination(displayPages, pager) {
	var output = "";
	output += '<nav aria-label="Pagination" > <ul class="pagination pagination-sm justify-content-end">';
	if(pager.firstPage==true)
		output += '<li class="page-item disabled"><a class="page-link" href="#" tabindex="-1" onclick="resubmitLookup(1);"><span th:text="#{commerce.screen.common.pagination.first}">First</span></a></li>';
	else
		output += '<li class="page-item"><a class="page-link" href="#" tabindex="-1" onclick="resubmitLookup(1);"><span th:text="#{commerce.screen.common.pagination.first}">First</span></a></li>';
	for ( var p in displayPages) {
		if(pager.currentPageNo==displayPages[p]){
			output += '<li class="page-item active"><a class="page-link" href="#" onclick="resubmitLookup(' + displayPages[p] + ');">' + displayPages[p] + '</a></li>';
		}else{
			output += '<li class="page-item"><a class="page-link" href="#" onclick="resubmitLookup(' + displayPages[p] + ');">' + displayPages[p] + '</a></li>';
		}
		
	}
	if(pager.lastPage==true)
		output += '<li class="page-item disabled"><a class="page-link" href="#" onclick="resubmitLookup(' + pager.lastPageNo
			+ ');"><span th:text="#{commerce.screen.common.pagination.last}">Last</span></a></li>';
	else
		output += '<li class="page-item"><a class="page-link" href="#" onclick="resubmitLookup(' + pager.lastPageNo
		+ ');"><span th:text="#{commerce.screen.common.pagination.last}">Last</span></a></li>';
	
	output += '</ul></nav>';
	return output;
}