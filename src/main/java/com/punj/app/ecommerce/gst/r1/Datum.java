
package com.punj.app.ecommerce.gst.r1;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "num",
    "hsn_sc",
    "desc",
    "uqc",
    "qty",
    "val",
    "txval",
    "iamt",
    "camt",
    "samt",
    "csamt"
})
public class Datum {

    @JsonProperty("num")
    private Integer num;
    @JsonProperty("hsn_sc")
    private String hsnSc;
    @JsonProperty("desc")
    private String desc;
    @JsonProperty("uqc")
    private String uqc;
    @JsonProperty("qty")
    private Integer qty;
    @JsonProperty("val")
    private Double val;
    @JsonProperty("txval")
    private Double txval;
    @JsonProperty("iamt")
    private Double iamt;
    @JsonProperty("camt")
    private Double camt;
    @JsonProperty("samt")
    private Double samt;
    @JsonProperty("csamt")
    private Double csamt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("num")
    public Integer getNum() {
        return num;
    }

    @JsonProperty("num")
    public void setNum(Integer num) {
        this.num = num;
    }

    public Datum withNum(Integer num) {
        this.num = num;
        return this;
    }

    @JsonProperty("hsn_sc")
    public String getHsnSc() {
        return hsnSc;
    }

    @JsonProperty("hsn_sc")
    public void setHsnSc(String hsnSc) {
        this.hsnSc = hsnSc;
    }

    public Datum withHsnSc(String hsnSc) {
        this.hsnSc = hsnSc;
        return this;
    }

    @JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

    @JsonProperty("desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Datum withDesc(String desc) {
        this.desc = desc;
        return this;
    }

    @JsonProperty("uqc")
    public String getUqc() {
        return uqc;
    }

    @JsonProperty("uqc")
    public void setUqc(String uqc) {
        this.uqc = uqc;
    }

    public Datum withUqc(String uqc) {
        this.uqc = uqc;
        return this;
    }

    @JsonProperty("qty")
    public Integer getQty() {
        return qty;
    }

    @JsonProperty("qty")
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Datum withQty(Integer qty) {
        this.qty = qty;
        return this;
    }

    @JsonProperty("val")
    public Double getVal() {
        return val;
    }

    @JsonProperty("val")
    public void setVal(Double val) {
        this.val = val;
    }

    public Datum withVal(Double val) {
        this.val = val;
        return this;
    }

    @JsonProperty("txval")
    public Double getTxval() {
        return txval;
    }

    @JsonProperty("txval")
    public void setTxval(Double txval) {
        this.txval = txval;
    }

    public Datum withTxval(Double txval) {
        this.txval = txval;
        return this;
    }

    @JsonProperty("iamt")
    public Double getIamt() {
        return iamt;
    }

    @JsonProperty("iamt")
    public void setIamt(Double iamt) {
        this.iamt = iamt;
    }

    public Datum withIamt(Double iamt) {
        this.iamt = iamt;
        return this;
    }

    @JsonProperty("camt")
    public Double getCamt() {
        return camt;
    }

    @JsonProperty("camt")
    public void setCamt(Double camt) {
        this.camt = camt;
    }

    public Datum withCamt(Double camt) {
        this.camt = camt;
        return this;
    }

    @JsonProperty("samt")
    public Double getSamt() {
        return samt;
    }

    @JsonProperty("samt")
    public void setSamt(Double samt) {
        this.samt = samt;
    }

    public Datum withSamt(Double samt) {
        this.samt = samt;
        return this;
    }

    @JsonProperty("csamt")
    public Double getCsamt() {
        return csamt;
    }

    @JsonProperty("csamt")
    public void setCsamt(Double csamt) {
        this.csamt = csamt;
    }

    public Datum withCsamt(Double csamt) {
        this.csamt = csamt;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Datum withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("num", num).append("hsnSc", hsnSc).append("desc", desc).append("uqc", uqc).append("qty", qty).append("val", val).append("txval", txval).append("iamt", iamt).append("camt", camt).append("samt", samt).append("csamt", csamt).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(val).append(samt).append(uqc).append(num).append(txval).append(camt).append(iamt).append(csamt).append(qty).append(additionalProperties).append(hsnSc).append(desc).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Datum) == false) {
            return false;
        }
        Datum rhs = ((Datum) other);
        return new EqualsBuilder().append(val, rhs.val).append(samt, rhs.samt).append(uqc, rhs.uqc).append(num, rhs.num).append(txval, rhs.txval).append(camt, rhs.camt).append(iamt, rhs.iamt).append(csamt, rhs.csamt).append(qty, rhs.qty).append(additionalProperties, rhs.additionalProperties).append(hsnSc, rhs.hsnSc).append(desc, rhs.desc).isEquals();
    }

}
