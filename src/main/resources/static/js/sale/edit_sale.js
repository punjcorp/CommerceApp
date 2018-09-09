
function parseRetrievedTxnHeader(txn_saleTxnDTO, openLocGSTNo, billingLocGSTNo){
	var txnHeader=new TransactionHeader();
	
	txnHeader.txnNo=txn_saleTxnDTO.transactionHeader.txnNo;
	txnHeader.locationId=txn_saleTxnDTO.transactionHeader.locationId;
	txnHeader.registerId=txn_saleTxnDTO.transactionHeader.registerId;
	txnHeader.businessDate=txn_saleTxnDTO.transactionHeader.businessDate;
	txnHeader.username=txn_saleTxnDTO.transactionHeader.createdBy;
	txnHeader.createdBy=txn_saleTxnDTO.transactionHeader.createdBy;
	txnHeader.uniqueTxnNo=txn_saleTxnDTO.transactionHeader.uniqueTxnNo;

	txnHeader.startTime=txn_saleTxnDTO.transactionHeader.startTime;
	txnHeader.endTime=txn_saleTxnDTO.transactionHeader.endTime;

	txnHeader.subTotalAmt = txn_saleTxnDTO.transactionHeader.subTotalAmt;
	txnHeader.totalDiscountAmt = txn_saleTxnDTO.transactionHeader.totalDiscountAmt;
	txnHeader.totalSGSTTaxAmt = txn_saleTxnDTO.transactionHeader.totalSGSTTaxAmt;
	txnHeader.totalCGSTTaxAmt = txn_saleTxnDTO.transactionHeader.totalCGSTTaxAmt;
	txnHeader.totalIGSTTaxAmt = txn_saleTxnDTO.transactionHeader.totalIGSTTaxAmt;
	txnHeader.totalTaxAmt = txn_saleTxnDTO.transactionHeader.totalTaxAmt;
	txnHeader.totalDueAmt = txn_saleTxnDTO.transactionHeader.totalDueAmt;
	txnHeader.totalRoundedDueAmt = txn_saleTxnDTO.transactionHeader.roundedTotalAmt;
	
	
	var openLocGST=openLocGSTNo;
	var billingLocGST=billingLocGSTNo;
	var openLocState='';
	var billingLocState='';
	
	if(typeof(openLocGST)!=='undefined' && openLocGST!=undefined && openLocGST!=''){
		openLocState=openLocGST.substr(0,2);
	}
	if(typeof(billingLocGST)!=='undefined' && billingLocGST!=undefined && billingLocGST!=''){
		billingLocState=billingLocGST.substr(0,2);
	}
	
	if(openLocState!='' && billingLocState!=''){
		if(openLocState==billingLocState){
			isGSTFlag='S';
		}else{
			isGSTFlag='I';
		}
	}else if(openLocState!='' && billingLocState==''){
		isGSTFlag='S';
	}else if(openLocState=='' && billingLocState!=''){
		isGSTFlag='N';
	}
	txnAction.txnHeader.applicableTax=isGSTFlag;
	
	
	
	
	txnHeader.applicableTax=txn_saleTxnDTO.transactionHeader.applicableTax;
	
	
	
	txnAction.txnHeader = txnHeader;

}

function parseRetrievedTxnLineItems(txn_saleTxnDTO){
	
	var saleLineItemUtil=new SaleLineItem();
	
	var saleLineItemList=new Array();
	var tenderLineItem;
	$.each(txn_saleTxnDTO.txnSaleLineItems, function(index, saleLineItem){
		var parsedSaleLineItem = saleLineItemUtil.parseRetrievedSaleLineItem(saleLineItem);
		saleLineItemList.push(parsedSaleLineItem);
	});
	

	txnAction.saleItemList=saleLineItemList;

}

function parseRetrievedTxnTenderLineItems(txn_saleTxnDTO){
	
	var tenderLineItemList=new Array();
	var tenderLineItem;
	$.each(txn_saleTxnDTO.txnTenderLineItems, function(index, txnTenderLineItem){
		tenderLineItem=new TenderLineItem();
		tenderLineItem.tenderId = txnTenderLineItem.tenderId;
		tenderLineItem.tenderIndex = index;
		tenderLineItem.typeCode = txnTenderLineItem.typeCode;
		tenderLineItem.name = txnTenderLineItem.name;
		tenderLineItem.amount = txnTenderLineItem.amount;
		tenderLineItem.seqNo=txnTenderLineItem.seqNo;
		tenderLineItem.changeFlag = txnTenderLineItem.changeFlag;
		tenderLineItemList.push(tenderLineItem);
	});
	
	txnAction.tenderLineItems = tenderLineItemList;
}


function parseRetrievedTxnCustomer(txn_saleTxnDTO){
	var customer=new Customer();
	
	customer.customerId=txn_saleTxnDTO.customer.customerId;
	customer.name=txn_saleTxnDTO.customer.name;
	customer.phone=txn_saleTxnDTO.customer.phone;
	customer.email=txn_saleTxnDTO.customer.email;
	customer.gstNo=txn_saleTxnDTO.customer.gstNo;
	customer.panNo=txn_saleTxnDTO.customer.panNo;
	customer.customerType=txn_saleTxnDTO.customer.customerType;
	customer.billingAddressId=txn_saleTxnDTO.customer.billingAddressId;
	customer.shippingAddressId=txn_saleTxnDTO.customer.shippingAddressId;
	
	var primaryAddress=txn_saleTxnDTO.customer.primaryAddress.address1 +', ';
	
	if(typeof(txn_saleTxnDTO.customer.primaryAddress.address2)!=='undefined' && txn_saleTxnDTO.customer.primaryAddress.address2!=undefined && txn_saleTxnDTO.customer.primaryAddress.address2!='')
		primaryAddress+='<br>'+txn_saleTxnDTO.customer.primaryAddress.address2 +', ';
	
	primaryAddress+='<br>'+txn_saleTxnDTO.customer.primaryAddress.city +', '+txn_saleTxnDTO.customer.primaryAddress.state+', ';
	primaryAddress+='<br>'+txn_saleTxnDTO.customer.primaryAddress.country +' - '+txn_saleTxnDTO.customer.primaryAddress.pincode+'.';
	
	
	var shippingAddress=txn_saleTxnDTO.customer.shippingAddress.address1 +', ';
	
	if(typeof(txn_saleTxnDTO.customer.shippingAddress.address2)!=='undefined' && txn_saleTxnDTO.customer.shippingAddress.address2!=undefined && txn_saleTxnDTO.customer.shippingAddress.address2!='')
		shippingAddress+='<br>'+txn_saleTxnDTO.customer.shippingAddress.address2 +', ';
	
	shippingAddress+='<br>'+txn_saleTxnDTO.customer.shippingAddress.city +', '+txn_saleTxnDTO.customer.shippingAddress.state+', ';
	shippingAddress+='<br>'+txn_saleTxnDTO.customer.shippingAddress.country +' - '+txn_saleTxnDTO.customer.shippingAddress.pincode+'.';
	

	showRetrievedTxnCustomerDetails(customer, primaryAddress, shippingAddress);
	
	$('#collapseOne').removeClass('show');
	$('#collapseTwo').addClass('show');
	
}


function parseRetrievedTxnShipment(txn_saleTxnDTO){
	
	var orderRequest=new OrderRequest();
	if(typeof(txn_saleTxnDTO.orderRequest)!=="undefined" && txn_saleTxnDTO.orderRequest!=undefined){
		orderRequest.customerOrderNo =txn_saleTxnDTO.orderRequest.customerOrderNo;
		if(typeof(txn_saleTxnDTO.orderRequest.orderDate)!=="undefined" && txn_saleTxnDTO.orderRequest.orderDate!=undefined){
			var ordDate=new Date(txn_saleTxnDTO.orderRequest.orderDate.year, txn_saleTxnDTO.orderRequest.orderDate.monthValue-1, txn_saleTxnDTO.orderRequest.orderDate.dayOfMonth);
			orderRequest.orderDate =moment(ordDate).format('DD-MMM-YY');
		}else{
			orderRequest.orderDate ="";
		}
	}
		
	var shipment=new Shipment();
	if(typeof(txn_saleTxnDTO.shipment)!=="undefined" && txn_saleTxnDTO.shipment!=undefined){
		shipment.shippingCompany=txn_saleTxnDTO.shipment.shippingCompany;
		shipment.gpPrNo=txn_saleTxnDTO.shipment.gpPrNo;
		if(typeof(txn_saleTxnDTO.shipment.gpPrDate)!=="undefined" && txn_saleTxnDTO.shipment.gpPrDate!=undefined){
			var gpPrDt=new Date(txn_saleTxnDTO.shipment.gpPrDate.year, txn_saleTxnDTO.shipment.gpPrDate.monthValue-1, txn_saleTxnDTO.shipment.gpPrDate.dayOfMonth);
			shipment.gpPrDate=moment(gpPrDt).format('DD-MMM-YY');
		}else{
			shipment.gpPrDate="";
		}
		
		shipment.vehicleNo=txn_saleTxnDTO.shipment.vehicleNo;
	}

		
	txnAction.shipment =shipment;
	txnAction.orderRequest=orderRequest;

}