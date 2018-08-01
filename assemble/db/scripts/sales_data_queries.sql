
-- basket size, basket amount , txn count and revenue
SELECT 
            `txn_basket`.`location_id` AS `location_id`,
                `txn_basket`.`business_date` AS `business_date`,
                ROUND((SUM(`txn_basket`.`txn_item_count`) / COUNT(`txn_basket`.`txn_no`)), 2) AS `basket_size`,
                ROUND((SUM(`txn_basket`.`basket_amounts`) / COUNT(`txn_basket`.`txn_no`)), 2) AS `basket_amount`,
                COUNT(`txn_basket`.`txn_no`) AS `txn_count`,
                ROUND(SUM(`txn_basket`.`gross_amounts`), 2) AS `revenue`
        FROM
            (SELECT 
            `txn_itm`.`location_id` AS `location_id`,
                `txn_itm`.`business_date` AS `business_date`,
                `txn_itm`.`register` AS `register`,
                `txn_itm`.`txn_no` AS `txn_no`,
                SUM(`txn_itm`.`gross_qty`) AS `txn_item_count`,
                SUM(`txn_itm`.`net_amount`) AS `basket_amounts`,
                SUM(`txn_itm`.`gross_amount`) AS `gross_amounts`
        FROM
            (`commercedb`.`txn_master` `txn`
        JOIN `commercedb`.`txn_li_item` `txn_itm`)
        WHERE
            ((`txn`.`txn_type` IN ('SALE' , 'RETURN'))
                AND (`txn`.`status` = 'COMPLETED')
                AND (`txn`.`business_date` = `txn_itm`.`business_date`)
                AND (`txn`.`register` = `txn_itm`.`register`)
                AND (`txn`.`location_id` = `txn_itm`.`location_id`)
                AND (`txn`.`txn_no` = `txn_itm`.`txn_no`))
        GROUP BY `txn_itm`.`location_id` , `txn_itm`.`business_date` , `txn_itm`.`register` , `txn_itm`.`txn_no`) `txn_basket`
        GROUP BY `txn_basket`.`location_id` , `txn_basket`.`business_date`;


-- customer count
SELECT 
            `txn`.`location_id` AS `location_id`,
                `txn`.`business_date` AS `business_date`,
                count(txn_cust.customer_id) AS `customer_count`
        FROM
            commercedb.txn_master `txn` LEFT JOIN commercedb.txn_customer txn_cust
            on (txn.location_id=txn_cust.location_id
            and txn.register=txn_cust.register
            and txn.business_date=txn_cust.business_date
            and txn.txn_no=txn_cust.txn_no)
            where `txn`.`txn_type` IN ('SALE' , 'RETURN')
                AND (`txn`.`status` = 'COMPLETED')
                
        GROUP BY `txn`.`location_id` , `txn`.`business_date`;
        
        
-- discount percent        
SELECT 
            `txn_discount_percent`.`location_id` AS `location_id`,
                `txn_discount_percent`.`business_date` AS `business_date`,
                SUM(`txn_discount_percent`.`total_discount`) AS `SUM(total_discount)`,
                SUM(`txn_discount_percent`.`total_price`) AS `SUM(total_price)`,
                ABS(ROUND(((SUM(`txn_discount_percent`.`total_discount`) / SUM(`txn_discount_percent`.`total_price`)) * 100), 2)) AS `discount_percent`
        FROM
            (SELECT 
            `txn_itm`.`location_id` AS `location_id`,
                `txn_itm`.`business_date` AS `business_date`,
                `txn_itm`.`register` AS `register`,
                `txn_itm`.`txn_no` AS `txn_no`,
                SUM(`txn_itm`.`discount_amount`) AS `total_discount`,
                SUM(`txn_itm`.`extended_amount`) AS `total_price`
        FROM
            (`commercedb`.`txn_master` `txn`
        JOIN `commercedb`.`txn_li_item` `txn_itm`)
        WHERE
            ((`txn`.`txn_type` IN ('SALE' , 'RETURN'))
                AND (`txn`.`status` = 'COMPLETED')
                AND (`txn`.`business_date` = `txn_itm`.`business_date`)
                AND (`txn`.`register` = `txn_itm`.`register`)
                AND (`txn`.`location_id` = `txn_itm`.`location_id`)
                AND (`txn`.`txn_no` = `txn_itm`.`txn_no`)
                AND ((IFNULL(`txn_itm`.`discount_percentage`, -(1)) = -(1))
                OR (`txn_itm`.`discount_percentage` IS NOT NULL)))
        GROUP BY `txn_itm`.`location_id` , `txn_itm`.`business_date` , `txn_itm`.`register` , `txn_itm`.`txn_no`) `txn_discount_percent`
        GROUP BY `txn_discount_percent`.`location_id` , `txn_discount_percent`.`business_date`;
        
        
        
-- discount amount
SELECT 
            `txn_discount_percent`.`location_id` AS `location_id`,
                `txn_discount_percent`.`business_date` AS `business_date`,
                ROUND(SUM(`txn_discount_percent`.`total_discount`), 2) AS `discount_amount`
        FROM
            (SELECT 
            `txn_itm`.`location_id` AS `location_id`,
                `txn_itm`.`business_date` AS `business_date`,
                `txn_itm`.`register` AS `register`,
                `txn_itm`.`txn_no` AS `txn_no`,
                SUM(`txn_itm`.`discount_amount`) AS `total_discount`
        FROM
            (`commercedb`.`txn_master` `txn`
        JOIN `commercedb`.`txn_li_item` `txn_itm`)
        WHERE
            (`txn`.`txn_type` IN ('SALE' , 'RETURN'))
                AND (`txn`.`status` = 'COMPLETED')
                AND (`txn`.`business_date` = `txn_itm`.`business_date`)
                AND (`txn`.`register` = `txn_itm`.`register`)
                AND (`txn`.`location_id` = `txn_itm`.`location_id`)
                AND (`txn`.`txn_no` = `txn_itm`.`txn_no`)              
                AND (ISNULL(`txn_itm`.`discount_percentage`) OR `txn_itm`.`discount_percentage`=0)
                AND ((`txn_itm`.`discount_amount` IS NOT NULL)
                OR (IFNULL(`txn_itm`.`discount_amount`, -(1)) = -(1)))
        GROUP BY `txn_itm`.`location_id` , `txn_itm`.`business_date` , `txn_itm`.`register` , `txn_itm`.`txn_no`) `txn_discount_percent`
        GROUP BY `txn_discount_percent`.`location_id` , `txn_discount_percent`.`business_date`;
;