
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
    "rt",
    "txval",
    "iamt",
    "csamt"
})
public class ItmDet_ {

    @JsonProperty("rt")
    private Integer rt;
    @JsonProperty("txval")
    private Double txval;
    @JsonProperty("iamt")
    private Double iamt;
    @JsonProperty("csamt")
    private Double csamt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("rt")
    public Integer getRt() {
        return rt;
    }

    @JsonProperty("rt")
    public void setRt(Integer rt) {
        this.rt = rt;
    }

    public ItmDet_ withRt(Integer rt) {
        this.rt = rt;
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

    public ItmDet_ withTxval(Double txval) {
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

    public ItmDet_ withIamt(Double iamt) {
        this.iamt = iamt;
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

    public ItmDet_ withCsamt(Double csamt) {
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

    public ItmDet_ withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("rt", rt).append("txval", txval).append("iamt", iamt).append("csamt", csamt).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(csamt).append(rt).append(additionalProperties).append(iamt).append(txval).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ItmDet_) == false) {
            return false;
        }
        ItmDet_ rhs = ((ItmDet_) other);
        return new EqualsBuilder().append(csamt, rhs.csamt).append(rt, rhs.rt).append(additionalProperties, rhs.additionalProperties).append(iamt, rhs.iamt).append(txval, rhs.txval).isEquals();
    }

}
