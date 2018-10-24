
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
    "pos",
    "inv"
})
public class B2cl {

    @JsonProperty("pos")
    private String pos;
    @JsonProperty("inv")
    private List<Inv_> inv = new ArrayList<Inv_>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("pos")
    public String getPos() {
        return pos;
    }

    @JsonProperty("pos")
    public void setPos(String pos) {
        this.pos = pos;
    }

    public B2cl withPos(String pos) {
        this.pos = pos;
        return this;
    }

    @JsonProperty("inv")
    public List<Inv_> getInv() {
        return inv;
    }

    @JsonProperty("inv")
    public void setInv(List<Inv_> inv) {
        this.inv = inv;
    }

    public B2cl withInv(List<Inv_> inv) {
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

    public B2cl withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("pos", pos).append("inv", inv).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(inv).append(additionalProperties).append(pos).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof B2cl) == false) {
            return false;
        }
        B2cl rhs = ((B2cl) other);
        return new EqualsBuilder().append(inv, rhs.inv).append(additionalProperties, rhs.additionalProperties).append(pos, rhs.pos).isEquals();
    }

}
