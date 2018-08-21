

var returnDTO = new OrderReturn();

$(function() {
	
	orderReturnsTable = $('#orderReturnsTable').DataTable({
		columnDefs : [ {
			orderable : false,
			className : 'select-checkbox',
			targets : 0
		} ],
		select : {
			style : 'os',
			selector : 'tr',
			style : 'multi'
		},
		order : [ [ 1, 'asc' ] ],
		"columns" : [ {
			"data" : "id"
		}, {
			"data" : "orderReturnId"
		}, {
			"data" : "comments"
		}, {
			"data" : "orderId"
		},{
			"data" : "status"
		}, {
			"data" : "createdDate"
		}, {
			"data" : "createdBy"
		},{
			"data" : "actions"
		}],
		dom: 'Bfrtip',
		"paging":   false,
        "info":     false,
        "searching":     false,
		buttons: [
            {
                text: 'Select all',
                action: function () {
                	orderReturnsTable.rows().select();
                }
            },
            {
                text: 'Select none',
                action: function () {
                	orderReturnsTable.rows().deselect();
                }
            }
        ]
	});

	orderReturnsTable.buttons().container().appendTo($('#returnTableBtns'));
	
});
	


function printOrderReturnReportById(orderReturnId) {

	returnDTO.orderReturnId = orderReturnId;

	var token = $("meta[name='_csrf']").attr("content");
	var formdata = JSON.stringify(returnDTO);
	// AJAX call here and refresh the sell item page with receipt printing
	$.ajax({
		url : print_rcpt_url,
		type : 'POST',
		cache : false,
		data : formdata,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		success : function(data) {
			$('#manageMsgs').html('The selected order return was printed successfully!');
			$('#manageMsgs').removeClass('d-none');
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-CSRF-TOKEN', token)
		}
	});

}



function searchResults(action) {
	
	$("#searchForm").attr("action", action);

	$('#searchForm').submit();
}