package com.punj.app.ecommerce.models.dailydeeds;

import com.punj.app.ecommerce.models.common.LocationBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClosingReportBean {

    private LocationBean locationBean;

    private Integer locationId;
    private Integer registerId;
    private LocalDateTime businessDate;

    private LocalDateTime closedDate;
    private String closedBy;
    private String printedBy;

    private Integer totalTxnCount;
    private Integer totalSalesCount;
    private Integer totalReturnCount;
    private Integer totalNoSalesCount;

    private BigDecimal totalTxnAmount;
    private BigDecimal totalSalesamount;
    private BigDecimal totalReturnsamount;
    private BigDecimal totalNoSalesAmount;
    private BigDecimal totalExpensesAmount;
    private BigDecimal totalPaymentAmount;

    private BigDecimal startOfDayAmount;
    private BigDecimal endOfDayAmount;
    private BigDecimal profitAmount;


    public LocationBean getLocationBean() {
        return locationBean;
    }

    public void setLocationBean(LocationBean locationBean) {
        this.locationBean = locationBean;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public LocalDateTime getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(LocalDateTime businessDate) {
        this.businessDate = businessDate;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public String getPrintedBy() {
        return printedBy;
    }

    public void setPrintedBy(String printedBy) {
        this.printedBy = printedBy;
    }

    public Integer getTotalTxnCount() {
        return totalTxnCount;
    }

    public void setTotalTxnCount(Integer totalTxnCount) {
        this.totalTxnCount = totalTxnCount;
    }

    public Integer getTotalSalesCount() {
        return totalSalesCount;
    }

    public void setTotalSalesCount(Integer totalSalesCount) {
        this.totalSalesCount = totalSalesCount;
    }

    public Integer getTotalReturnCount() {
        return totalReturnCount;
    }

    public void setTotalReturnCount(Integer totalReturnCount) {
        this.totalReturnCount = totalReturnCount;
    }

    public Integer getTotalNoSalesCount() {
        return totalNoSalesCount;
    }

    public void setTotalNoSalesCount(Integer totalNoSalesCount) {
        this.totalNoSalesCount = totalNoSalesCount;
    }

    public BigDecimal getTotalTxnAmount() {
        return totalTxnAmount;
    }

    public void setTotalTxnAmount(BigDecimal totalTxnAmount) {
        this.totalTxnAmount = totalTxnAmount;
    }

    public BigDecimal getTotalSalesamount() {
        return totalSalesamount;
    }

    public void setTotalSalesamount(BigDecimal totalSalesamount) {
        this.totalSalesamount = totalSalesamount;
    }

    public BigDecimal getTotalReturnsamount() {
        return totalReturnsamount;
    }

    public void setTotalReturnsamount(BigDecimal totalReturnsamount) {
        this.totalReturnsamount = totalReturnsamount;
    }

    public BigDecimal getTotalNoSalesAmount() {
        return totalNoSalesAmount;
    }

    public void setTotalNoSalesAmount(BigDecimal totalNoSalesAmount) {
        this.totalNoSalesAmount = totalNoSalesAmount;
    }

    public BigDecimal getTotalExpensesAmount() {
        return totalExpensesAmount;
    }

    public void setTotalExpensesAmount(BigDecimal totalExpensesAmount) {
        this.totalExpensesAmount = totalExpensesAmount;
    }

    public BigDecimal getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(BigDecimal totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public BigDecimal getStartOfDayAmount() {
        return startOfDayAmount;
    }

    public void setStartOfDayAmount(BigDecimal startOfDayAmount) {
        this.startOfDayAmount = startOfDayAmount;
    }

    public BigDecimal getEndOfDayAmount() {
        return endOfDayAmount;
    }

    public void setEndOfDayAmount(BigDecimal endOfDayAmount) {
        this.endOfDayAmount = endOfDayAmount;
    }

    public BigDecimal getProfitAmount() {
        return profitAmount;
    }

    public void setProfitAmount(BigDecimal profitAmount) {
        this.profitAmount = profitAmount;
    }
}
