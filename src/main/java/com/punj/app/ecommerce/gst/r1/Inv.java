
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
    "pos",
    "rchrg",
    "itms",
    "inv_typ"
})
public class Inv {

    @JsonProperty("inum")
    private String inum;
    @JsonProperty("idt")
    private String idt;
    @JsonProperty("val")
    private Double val;
    @JsonProperty("pos")
    private String pos;
    @JsonProperty("rchrg")
    private String rchrg;
    @JsonProperty("itms")
    private List<Itm> itms = new ArrayList<Itm>();
    @JsonProperty("inv_typ")
    private String invTyp;
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

    public Inv withInum(String inum) {
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

    public Inv withIdt(String idt) {
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

    public Inv withVal(Double val) {
        this.val = val;
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

    public Inv withPos(String pos) {
        this.pos = pos;
        return this;
    }

    @JsonProperty("rchrg")
    public String getRchrg() {
        return rchrg;
    }

    @JsonProperty("rchrg")
    public void setRchrg(String rchrg) {
        this.rchrg = rchrg;
    }

    public Inv withRchrg(String rchrg) {
        this.rchrg = rchrg;
        return this;
    }

    @JsonProperty("itms")
    public List<Itm> getItms() {
        return itms;
    }

    @JsonProperty("itms")
    public void setItms(List<Itm> itms) {
        this.itms = itms;
    }

    public Inv withItms(List<Itm> itms) {
        this.itms = itms;
        return this;
    }

    @JsonProperty("inv_typ")
    public String getInvTyp() {
        return invTyp;
    }

    @JsonProperty("inv_typ")
    public void setInvTyp(String invTyp) {
        this.invTyp = invTyp;
    }

    public Inv withInvTyp(String invTyp) {
        this.invTyp = invTyp;
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

    public Inv withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("inum", inum).append("idt", idt).append("val", val).append("pos", pos).append("rchrg", rchrg).append("itms", itms).append("invTyp", invTyp).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(val).append(itms).append(pos).append(idt).append(rchrg).append(invTyp).append(additionalProperties).append(inum).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Inv) == false) {
            return false;
        }
        Inv rhs = ((Inv) other);
        return new EqualsBuilder().append(val, rhs.val).append(itms, rhs.itms).append(pos, rhs.pos).append(idt, rhs.idt).append(rchrg, rhs.rchrg).append(invTyp, rhs.invTyp).append(additionalProperties, rhs.additionalProperties).append(inum, rhs.inum).isEquals();
    }

}
