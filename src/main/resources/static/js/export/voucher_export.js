/**
 * This JS file has all the sources needed for voucher export
 * 
 */
var txnDataTable;
var startDateTime;
var endDateTime;
var token = $("meta[name='_csrf']").attr("content");
var alertUtil = new Utils();
var uniqueTxnNo = '';

$(function() {

	$(window).keydown(function(event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			return false;
		}
	});

	startDateTime = $('#startDateTime').flatpickr({
		dateFormat : 'd-M-y',
		maxDate : "today"
	});

	endDateTime = $('#endDateTime').flatpickr({
		dateFormat : 'd-M-y',
		maxDate : "today"
	});

	txnDataTable = $('#txnDataTable').DataTable({
		responsive : true,
		colReorder : true,
		select : {
			style : 'os',
			selector : 'tr',
			style : 'multi'
		},
		buttons : [ 'copyHtml5', 'excelHtml5', 'csvHtml5', 'pdfHtml5', {
			text : 'Export All',
			action : function(e, dt, node, config) {
				var txnType=$('#export_txn_type').val();
				var startDate=$('#export_start_date').val();
				var endDate=$('#export_end_date').val();
				var url='/admin/export_vouchers?txnType='+txnType+'&startDateTime='+startDate+'&endDateTime='+endDate;
				var win = window.open(url, '_blank');
				win.focus();
			}
		} ],
		columns : [ {
			"name" : "uniqueTxnNo"
		}, {
			"name" : "createdDate"
		}, {
			"name" : "txnType"
		}, {
			"name" : "customerName"
		}, {
			"name" : "createdBy"
		}, {
			"name" : "totalTax"
		}, {
			"name" : "totalAmount"
		}, {
			"name" : "actions"
		} ]
	});

	txnDataTable.buttons().container().appendTo($('#tableBtns'));

});
