
package com.punj.app.ecommerce.gst.r1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "inum",
    "idt",
    "val",
    "itms"
})
public class Inv_ {

    @JsonProperty("inum")
    private String inum;
    @JsonProperty("idt")
    private String idt;
    @JsonProperty("val")
    private Double val;
    @JsonProperty("itms")
    private List<Itm_> itms = new ArrayList<Itm_>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("inum")
    public String getInum() {
        return inum;
    }

    @JsonProperty("inum")
    public void setInum(String inum) {
        this.inum = inum;
    }

    public Inv_ withInum(String inum) {
        this.inum = inum;
        return this;
    }

    @JsonProperty("idt")
    public String getIdt() {
        return idt;
    }

    @JsonProperty("idt")
    public void setIdt(String idt) {
        this.idt = idt;
    }

    public Inv_ withIdt(String idt) {
        this.idt = idt;
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

    public Inv_ withVal(Double val) {
        this.val = val;
        return this;
    }

    @JsonProperty("itms")
    public List<Itm_> getItms() {
        return itms;
    }

    @JsonProperty("itms")
    public void setItms(List<Itm_> itms) {
        this.itms = itms;
    }

    public Inv_ withItms(List<Itm_> itms) {
        this.itms = itms;
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

    public Inv_ withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("inum", inum).append("idt", idt).append("val", val).append("itms", itms).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(idt).append(val).append(itms).append(additionalProperties).append(inum).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Inv_) == false) {
            return false;
        }
        Inv_ rhs = ((Inv_) other);
        return new EqualsBuilder().append(idt, rhs.idt).append(val, rhs.val).append(itms, rhs.itms).append(additionalProperties, rhs.additionalProperties).append(inum, rhs.inum).isEquals();
    }

}
