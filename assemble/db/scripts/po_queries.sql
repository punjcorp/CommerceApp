select * from commercedb.supplier;
select * from commercedb.account_head;
select * from commercedb.account_journal;
select * from commercedb.account_journal_tender;
select * from commercedb.account_journal_receipts;


select * from commercedb.po_return;
select * from commercedb.po_return_items ;
select * from commercedb.po_return_items where order_return_id=38;
select * from commercedb.po_return_items_tax where order_return_item_id in (select order_return_item_id from commercedb.po_return_items where order_return_id=38);
select * from commercedb.reason_codes;


select * from commercedb.purchase_order;
select * from commercedb.purchase_order_items where order_id=1;
select * from commercedb.purchase_order_items_tax;


select * from commercedb.item_stock where item_id=10000100002 and location_id=3;
select * from commercedb.item_stock_journal;


select * from commercedb.account_journal;

select * from commercedb.ledger_journal;


select * from commercedb.account_head;
select * from commercedb.account_journal;

select aj.* from commercedb. account_journal aj, commercedb.account_head ah where aj.account_id=ah.account_id and ah.entity_type = 'SUPPLIER' and aj.ledger_generated = 'N' order by created_date desc; 

select * from pi_pos_industry.v_receipt_li_item;

