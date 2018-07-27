package com.punj.app.ecommerce.services.dtos.dailydeeds;

import com.punj.app.ecommerce.domains.finance.DailyTotals;
import com.punj.app.ecommerce.domains.transaction.ids.TransactionId;

public class DailyDeedDTO {

    private DailyTotals dailyTotals;
    private TransactionId txnId;
    private String uniqueTxnNo;

    public DailyTotals getDailyTotals() {
        return dailyTotals;
    }

    public void setDailyTotals(DailyTotals dailyTotals) {
        this.dailyTotals = dailyTotals;
    }

    public TransactionId getTxnId() {
        return txnId;
    }

    public void setTxnId(TransactionId txnId) {
        this.txnId = txnId;
    }

    public String getUniqueTxnNo() {
        return uniqueTxnNo;
    }

    public void setUniqueTxnNo(String uniqueTxnNo) {
        this.uniqueTxnNo = uniqueTxnNo;
    }
}
