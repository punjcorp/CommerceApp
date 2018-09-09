select * from pi_pos_industry.customer;
select * from pi_pos_industry.customer_address;
select * from pi_pos_industry.address_master;

select * from pi_pos_industry.account_head;

select * from pi_pos_industry.attribute_master;

select * from pi_pos_industry.item where item_level=2;

select * from pi_pos_industry.tax_location_mapping;

select * from pi_pos_industry.tax_location;

select state_name_short, state_name from pi_pos_industry.gst_state_codes;

update pi_pos_industry.gst_state_codes set state_code=trim(CHAR(9) FROM trim(state_code)), state_name=trim(CHAR(9) FROM trim(state_name)), state_name_short=trim(CHAR(9) FROM trim(state_name_short)) where id<100;



select * from pi_pos_industry.expense_txn_vouchers;

select * from pi_pos_industry.txn_master;
select * from pi_pos_industry.txn_line_item_master;
select * from pi_pos_industry.txn_li_item;
select * from pi_pos_industry.txn_li_tax;
select * from pi_pos_industry.txn_li_tender;

select * from pi_pos_industry.txn_customer;

select * from pi_pos_industry.txn_receipt;
select * from pi_pos_indv_receipt_li_item;

select * from pi_pos_industry.item_options;


select * from pi_pos_industry.sale_txn_invoices;





select * from pi_pos_industry.attribute_master;

select * from pi_pos_industry.item_price;
select * from pi_pos_industry.txn_customer;
select * from pi_pos_industry.customer;
select * from pi_pos_industry.sale_txn_invoice;


SELECT 
    txn_inv.invoice_no,
    txn_ma.created_date,
    txn_ma.txn_type,
    cust.name,
    txn_ma.created_by,
    txn_ma.tax_total,
    txn_ma.total
FROM
    pi_pos_industry.txn_master txn_ma,
    pi_pos_industry.txn_customer txn_cust,
    pi_pos_industry.customer cust,
    pi_pos_industry.sale_txn_invoices txn_inv
WHERE
    txn_ma.location_id = txn_inv.location_id
        AND txn_ma.business_date = txn_inv.business_date
        AND txn_ma.register = txn_inv.register
        AND txn_ma.txn_no = txn_inv.txn_no
        AND txn_ma.location_id = txn_cust.location_id
        AND txn_ma.business_date = txn_cust.business_date
        AND txn_ma.register = txn_cust.register
        AND txn_ma.txn_no = txn_cust.txn_no
        AND txn_cust.customer_id = cust.customer_id
        AND txn_cust.customer_type = 'CUSTOMER';