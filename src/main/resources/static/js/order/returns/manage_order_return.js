

var returnDTO = new OrderReturn();
var selectedIds=new Array();

$(function() {
	
	orderReturnsTable = $('#orderReturnsTable').DataTable({
		dom: 'Bfrtip',
		"paging":   false,
        "info":     false,
        "searching":     false,
		columnDefs : [ {
			orderable : false,
			className : 'select-checkbox',
			targets : 0
		},{
			"targets" : [1],
			"visible": false,
            "orderable": false
		},{
			"targets" : [2],
			"visible": false,
            "orderable": false
		}
		],
		select : {
			style : 'os',
			selector : 'tr',
			style : 'multi'
		},
		order : [ [ 1, 'asc' ] ],
		"columns" : [ {
			"data" : "id"
		}, {
			"data" : "returnId"			
		}, {
			"data" : "returnIndex"			
		}, {
			"data" : "orderReturnId"
		}, {
			"data" : "comments"
		}, {
			"data" : "supplier"
		}, {
			"data" : "location"
		},{
			"data" : "status"
		}, {
			"data" : "createdDate"
		}, {
			"data" : "createdBy"
		},{
			"data" : "actions"
		}],
		buttons: [
            {
                text: 'Select all',
                action: function () {
                	selectedIds=[];
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
	
	
	orderReturnsTable
     .on( 'select', function ( e, dt, type, indexes ) {
         var rowData = orderReturnsTable.rows( indexes ).data().toArray();
         if(rowData.length>0){
        	recordSelection(rowData); 
         }
         
     } )
     .on( 'deselect', function ( e, dt, type, indexes ) {
    	 var rowData = orderReturnsTable.rows( indexes ).data().toArray();
         if(rowData.length>0){
        	 recordDeSelection(rowData); 
         }
         
     } );
	
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

function submitForm(action) {

	$("#bulkForm").attr("action", action);
	$('#bulkForm').submit();
}

function recordSelection(rowData){
	$.each(rowData, function(index, row){
		selectedIds.push(row.returnId+"_"+row.returnIndex);
	});
	$('#orderReturnIds').val(selectedIds);
	$('#btnApprove').removeAttr("disabled");
	$('#btnDelete').removeAttr("disabled");
}



function recordDeSelection(rowData){
	$.each(rowData, function(index, row){
		var index = selectedIds.indexOf(row.returnId+"_"+row.returnIndex);
		if (index > -1) {
			selectedIds.splice(index, 1);
		}
	});
	
	$('#orderReturnIds').val(selectedIds);
	if(selectedIds.length<1){
		$("#btnApprove").attr("disabled", "disabled");
		$("#btnDelete").attr("disabled", "disabled");
	}
	
}

