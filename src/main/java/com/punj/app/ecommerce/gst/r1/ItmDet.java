
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
    "txval",
    "rt",
    "camt",
    "samt",
    "csamt",
    "iamt"
})
public class ItmDet {

    @JsonProperty("txval")
    private Double txval;
    @JsonProperty("rt")
    private Integer rt;
    @JsonProperty("camt")
    private Double camt;
    @JsonProperty("samt")
    private Double samt;
    @JsonProperty("csamt")
    private Double csamt;
    @JsonProperty("iamt")
    private Double iamt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("txval")
    public Double getTxval() {
        return txval;
    }

    @JsonProperty("txval")
    public void setTxval(Double txval) {
        this.txval = txval;
    }

    public ItmDet withTxval(Double txval) {
        this.txval = txval;
        return this;
    }

    @JsonProperty("rt")
    public Integer getRt() {
        return rt;
    }

    @JsonProperty("rt")
    public void setRt(Integer rt) {
        this.rt = rt;
    }

    public ItmDet withRt(Integer rt) {
        this.rt = rt;
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

    public ItmDet withCamt(Double camt) {
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

    public ItmDet withSamt(Double samt) {
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

    public ItmDet withCsamt(Double csamt) {
        this.csamt = csamt;
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

    public ItmDet withIamt(Double iamt) {
        this.iamt = iamt;
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

    public ItmDet withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("txval", txval).append("rt", rt).append("camt", camt).append("samt", samt).append("csamt", csamt).append("iamt", iamt).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(samt).append(csamt).append(rt).append(txval).append(camt).append(additionalProperties).append(iamt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ItmDet) == false) {
            return false;
        }
        ItmDet rhs = ((ItmDet) other);
        return new EqualsBuilder().append(samt, rhs.samt).append(csamt, rhs.csamt).append(rt, rhs.rt).append(txval, rhs.txval).append(camt, rhs.camt).append(additionalProperties, rhs.additionalProperties).append(iamt, rhs.iamt).isEquals();
    }

}
