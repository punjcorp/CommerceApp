select * from pi_pos_industry.txn_master where location_id=2;

delete from pi_pos_industry.txn_master where location_id=2;
delete from pi_pos_industry.txn_line_item_master where location_id=2;
delete from pi_pos_industry.txn_li_item  where location_id=2;
delete from pi_pos_industry.txn_li_tax where location_id=2;
delete from pi_pos_industry.txn_li_tender  where location_id=2;
delete from pi_pos_industry.txn_no_sale where location_id=2;
delete from pi_pos_industry.txn_no_sale_tender  where location_id=2;
delete from pi_pos_industry.txn_customer where location_id=2;
delete from pi_pos_industry.tender_movement where location_id=2;

delete from pi_pos_industry.txn_no_sale_tender where location_id=2;
delete from pi_pos_industry.txn_receipt where location_id=2;

delete from pi_pos_industry.txn_tender_denomination where location_id=2;
delete from pi_pos_industry.txn_tender_count where location_id=2;

select * from pi_pos_industry.daily_totals where location_id=2;

commit;


select * from pi_pos_industry.txn_master where location_id=7997 order by created_date desc;
select * from pi_pos_industry.txn_line_item_master where location_id=7997 order by created_date desc;
select * from pi_pos_industry.txn_li_item  where location_id=7997;
select * from pi_pos_industry.txn_li_tax where location_id=7997;
select * from pi_pos_industry.txn_li_tender  where location_id=7997;
