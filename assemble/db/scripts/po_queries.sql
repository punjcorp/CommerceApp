select * from pi_pos_industry.supplier;
select * from pi_pos_industry.account_head;
select * from pi_pos_industry.account_journal;
select * from pi_pos_industry.account_journal_tender;
select * from pi_pos_industry.account_journal_receipts;


select * from pi_pos_industry.po_return;
select * from pi_pos_industry.po_return_items ;
select * from pi_pos_industry.po_return_items where order_return_id=38;
select * from pi_pos_industry.po_return_items_tax where order_return_item_id in (select order_return_item_id from pi_pos_industry.po_return_items where order_return_id=38);
select * from pi_pos_industry.reason_codes;


select * from pi_pos_industry.purchase_order;
select * from pi_pos_industry.purchase_order_items where order_id=1;
select * from pi_pos_industry.purchase_order_items_tax;


select * from pi_pos_industry.item_stock where item_id=10000100002 and location_id=3;
select * from pi_pos_industry.item_stock_journal;


select * from pi_pos_industry.account_journal;

select * from pi_pos_industry.ledger_journal;


select * from pi_pos_industry.account_head;
select * from pi_pos_industry.account_journal;

select aj.* from pi_pos_industry. account_journal aj, pi_pos_industry.account_head ah where aj.account_id=ah.account_id and ah.entity_type = 'SUPPLIER' and aj.ledger_generated = 'N' order by created_date desc; 

select * from pi_pos_industry.v_receipt_li_item;

