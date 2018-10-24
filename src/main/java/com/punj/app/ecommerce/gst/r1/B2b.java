
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
    "ctin",
    "inv"
})
public class B2b {

    @JsonProperty("ctin")
    private String ctin;
    @JsonProperty("inv")
    private List<Inv> inv = new ArrayList<Inv>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ctin")
    public String getCtin() {
        return ctin;
    }

    @JsonProperty("ctin")
    public void setCtin(String ctin) {
        this.ctin = ctin;
    }

    public B2b withCtin(String ctin) {
        this.ctin = ctin;
        return this;
    }

    @JsonProperty("inv")
    public List<Inv> getInv() {
        return inv;
    }

    @JsonProperty("inv")
    public void setInv(List<Inv> inv) {
        this.inv = inv;
    }

    public B2b withInv(List<Inv> inv) {
        this.inv = inv;
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

    public B2b withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("ctin", ctin).append("inv", inv).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(ctin).append(inv).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof B2b) == false) {
            return false;
        }
        B2b rhs = ((B2b) other);
        return new EqualsBuilder().append(ctin, rhs.ctin).append(inv, rhs.inv).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
