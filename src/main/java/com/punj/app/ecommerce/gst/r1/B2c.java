
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
    "sply_ty",
    "pos",
    "typ",
    "txval",
    "iamt",
    "csamt",
    "camt",
    "samt"
})
public class B2c {

    @JsonProperty("rt")
    private Integer rt;
    @JsonProperty("sply_ty")
    private String splyTy;
    @JsonProperty("pos")
    private String pos;
    @JsonProperty("typ")
    private String typ;
    @JsonProperty("txval")
    private Double txval;
    @JsonProperty("iamt")
    private Double iamt;
    @JsonProperty("csamt")
    private Double csamt;
    @JsonProperty("camt")
    private Double camt;
    @JsonProperty("samt")
    private Double samt;
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

    public B2c withRt(Integer rt) {
        this.rt = rt;
        return this;
    }

    @JsonProperty("sply_ty")
    public String getSplyTy() {
        return splyTy;
    }

    @JsonProperty("sply_ty")
    public void setSplyTy(String splyTy) {
        this.splyTy = splyTy;
    }

    public B2c withSplyTy(String splyTy) {
        this.splyTy = splyTy;
        return this;
    }

    @JsonProperty("pos")
    public String getPos() {
        return pos;
    }

    @JsonProperty("pos")
    public void setPos(String pos) {
        this.pos = pos;
    }

    public B2c withPos(String pos) {
        this.pos = pos;
        return this;
    }

    @JsonProperty("typ")
    public String getTyp() {
        return typ;
    }

    @JsonProperty("typ")
    public void setTyp(String typ) {
        this.typ = typ;
    }

    public B2c withTyp(String typ) {
        this.typ = typ;
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

    public B2c withTxval(Double txval) {
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

    public B2c withIamt(Double iamt) {
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

    public B2c withCsamt(Double csamt) {
        this.csamt = csamt;
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

    public B2c withCamt(Double camt) {
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

    public B2c withSamt(Double samt) {
        this.samt = samt;
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

    public B2c withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("rt", rt).append("splyTy", splyTy).append("pos", pos).append("typ", typ).append("txval", txval).append("iamt", iamt).append("csamt", csamt).append("camt", camt).append("samt", samt).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(csamt).append(samt).append(rt).append(splyTy).append(pos).append(txval).append(typ).append(camt).append(additionalProperties).append(iamt).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof B2c) == false) {
            return false;
        }
        B2c rhs = ((B2c) other);
        return new EqualsBuilder().append(csamt, rhs.csamt).append(samt, rhs.samt).append(rt, rhs.rt).append(splyTy, rhs.splyTy).append(pos, rhs.pos).append(txval, rhs.txval).append(typ, rhs.typ).append(camt, rhs.camt).append(additionalProperties, rhs.additionalProperties).append(iamt, rhs.iamt).isEquals();
    }

}
