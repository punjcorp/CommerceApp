-- Customer Count output for a business day
select location_id, business_date, count(*) as customer_count  from pi_pos_industry.txn_customer where location_id=7997  and business_date= '2018-06-08 00:00:00';

-- basket size, basket amount for a business day
select location_id, business_date, round(sum(txn_item_count)/count(txn_no),2) as basket_size, round(sum(basket_amounts)/count(txn_no),2) as basket_amount, count(txn_no) as txn_count, round(sum(gross_amounts),2) as revenue  from (SELECT 
    txn_itm.location_id, txn_itm.business_date, txn_itm.register, txn_itm.txn_no, sum(gross_qty) as txn_item_count, sum(net_amount) as basket_amounts, sum(gross_amount) as gross_amounts
FROM
    pi_pos_industry.txn_master txn,
    pi_pos_industry.txn_li_item txn_itm
WHERE
	txn.txn_type in ('SALE', 'RETURN') and status in('COMPLETED') and
    txn.business_date = txn_itm.business_date
        AND txn.register = txn_itm.register
        AND txn.location_id = txn_itm.location_id
        and txn.txn_no=txn_itm.txn_no and txn.location_id=7997  and txn.business_date= '2018-06-08 00:00:00' group by txn_itm.location_id, txn_itm.business_date, txn_itm.register, txn_itm.txn_no) txn_basket
        group by location_id, business_date;

-- Discount Percent of total transaction amount for the day
select location_id, business_date, sum(total_discount), sum(total_price), abs(round((sum(total_discount)/sum(total_price))*100,2)) as discount_percent from (SELECT 
    txn_itm.location_id,
    txn_itm.business_date,
    txn_itm.register,
    txn_itm.txn_no,
    SUM(discount_amount) as total_discount,
    SUM(extended_amount) as total_price
FROM
    pi_pos_industry.txn_master txn,
    pi_pos_industry.txn_li_item txn_itm
WHERE
    txn.txn_type IN ('SALE' , 'RETURN')
        AND status IN ('COMPLETED')
        AND txn.business_date = txn_itm.business_date
        AND txn.register = txn_itm.register
        AND txn.location_id = txn_itm.location_id
        AND txn.txn_no = txn_itm.txn_no
        AND txn_itm.discount_percentage IS NOT NULL
        AND txn.location_id = 7997
        AND txn.business_date = '2018-06-08 00:00:00'
GROUP BY txn_itm.location_id , txn_itm.business_date , txn_itm.register , txn_itm.txn_no) txn_discount_percent
        group by location_id, business_date;


-- Discount Amount of total transaction amount for the day
select location_id, business_date, round(sum(total_discount),2) as discount_amount from (SELECT 
    txn_itm.location_id,
    txn_itm.business_date,
    txn_itm.register,
    txn_itm.txn_no,
    SUM(discount_amount) as total_discount
FROM
    pi_pos_industry.txn_master txn,
    pi_pos_industry.txn_li_item txn_itm
WHERE
    txn.txn_type IN ('SALE' , 'RETURN')
        AND status IN ('COMPLETED')
        AND txn.business_date = txn_itm.business_date
        AND txn.register = txn_itm.register
        AND txn.location_id = txn_itm.location_id
        AND txn.txn_no = txn_itm.txn_no
        AND txn_itm.discount_percentage IS NULL and txn_itm.discount_amount is not null 
        AND txn.location_id = 7997
        AND txn.business_date = '2018-06-08 00:00:00'
GROUP BY txn_itm.location_id , txn_itm.business_date , txn_itm.register , txn_itm.txn_no) txn_discount_percent
        group by location_id, business_date;
        
        
-- OPEN_STORE
-- OPEN_REGISTER
-- NOSALE
-- SALE
-- RETURN        
-- select distinct txn_Type from pi_pos_industry.txn_master;

select distinct status from pi_pos_industry.txn_master;
select * from pi_pos_industry.txn_li_item;

