/**
 * These are the global variables for customer screen
 */
var token = $("meta[name='_csrf']").attr("content");
var selectedIds=new Array();
var alertUtil = new Utils();
var customer_deletion_url; 
var deleteAction;


$(function() {

	customersTable = $('#customersTable').DataTable({
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
			"data" : "customerId"			
		}, {
			"data" : "customerIndex"			
		}, {
			"data" : "name"
		}, {
			"data" : "email"
		}, {
			"data" : "phone1"
		}, {
			"data" : "phone2"
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
                	customersTable.rows().select();
                }
            },
            {
                text: 'Select none',
                action: function () {
                	customersTable.rows().deselect();
                }
            }
        ]
	});

	customersTable.buttons().container().appendTo($('#customerTableBtns'));
	
	
	customersTable
     .on( 'select', function ( e, dt, type, indexes ) {
         var rowData = customersTable.rows( indexes ).data().toArray();
         if(rowData.length>0){
        	recordSelection(rowData); 
         }
         
     } )
     .on( 'deselect', function ( e, dt, type, indexes ) {
    	 var rowData = customersTable.rows( indexes ).data().toArray();
         if(rowData.length>0){
        	 recordDeSelection(rowData); 
         }
         
     } );

});

function recordSelection(rowData){
	$.each(rowData, function(index, row){
		selectedIds.push(row.customerId+"_"+row.customerIndex);
	});
	$('#customerIds').val(selectedIds);
	$('#btnDelete').removeAttr("disabled");
}



function recordDeSelection(rowData){
	$.each(rowData, function(index, row){
		var index = selectedIds.indexOf(row.customerId+"_"+row.customerIndex);
		if (index > -1) {
			selectedIds.splice(index, 1);
		}
	});
	
	$('#customerIds').val(selectedIds);
	if(selectedIds.length<1){
		$("#btnDelete").attr("disabled", "disabled");
	}
	
}

function confirmDeleteMultiple(deletionUrl){
	customer_deletion_url=deletionUrl;
	deleteAction='M';
	
	btnLabels = [ i18next.t('alert_btn_approve'), i18next.t('alert_btn_cancel') ];
	alertUtil.renderAlert('CONFIRM', i18next.t('error_confirmation_alert_header'), i18next.t('error_confirm_customer_delete'), btnLabels);
}

function confirmDeletion(deletionUrl){
	customer_deletion_url=deletionUrl;
	deleteAction='S';
	
	btnLabels = [ i18next.t('alert_btn_approve'), i18next.t('alert_btn_cancel') ];
	alertUtil.renderAlert('CONFIRM', i18next.t('error_confirmation_alert_header'), i18next.t('error_confirm_customer_delete'), btnLabels);
}

function alertAction(cntl) {
	if (cntl.id.indexOf('Cancel') > 0) {
		$('#alertModal').modal('hide');
	} else {
		processDeletion();
	}
}

function processDeletion(){
	if(typeof(deleteAction)!=='undefined' && deleteAction!=undefined && deleteAction=='S')
		window.location.href=customer_deletion_url;
	else if(typeof(deleteAction)!=='undefined' && deleteAction!=undefined && deleteAction=='M')
		submitForm(customer_deletion_url);
}

function searchResults(action) {
	
	$("#searchForm").attr("action", action);
	$('#searchForm').submit();
}

function submitForm(action) {

	$("#bulkForm").attr("action", action);
	$('#bulkForm').submit();
}

