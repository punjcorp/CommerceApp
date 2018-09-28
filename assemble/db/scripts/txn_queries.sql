select * from commercedb.txn_master where location_id=2;

delete from commercedb.txn_master where location_id=2;
delete from commercedb.txn_line_item_master where location_id=2;
delete from commercedb.txn_li_item  where location_id=2;
delete from commercedb.txn_li_tax where location_id=2;
delete from commercedb.txn_li_tender  where location_id=2;
delete from commercedb.txn_no_sale where location_id=2;
delete from commercedb.txn_no_sale_tender  where location_id=2;
delete from commercedb.txn_customer where location_id=2;
delete from commercedb.tender_movement where location_id=2;

delete from commercedb.txn_no_sale_tender where location_id=2;
delete from commercedb.txn_receipt where location_id=2;

delete from commercedb.txn_tender_denomination where location_id=2;
delete from commercedb.txn_tender_count where location_id=2;

select * from commercedb.daily_totals where location_id=2;

commit;


select * from pi_pos_industry.txn_master where location_id=7997 order by created_date desc;
select * from pi_pos_industry.txn_line_item_master where location_id=7997 order by created_date desc;
select * from pi_pos_industry.txn_li_item  where location_id=7997;
select * from pi_pos_industry.txn_li_tax where location_id=7997;
select * from pi_pos_industry.txn_li_tender  where location_id=7997;
