
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
    "itm_det"
})
public class Itm_ {

    @JsonProperty("num")
    private Integer num;
    @JsonProperty("itm_det")
    private ItmDet_ itmDet;
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

    public Itm_ withNum(Integer num) {
        this.num = num;
        return this;
    }

    @JsonProperty("itm_det")
    public ItmDet_ getItmDet() {
        return itmDet;
    }

    @JsonProperty("itm_det")
    public void setItmDet(ItmDet_ itmDet) {
        this.itmDet = itmDet;
    }

    public Itm_ withItmDet(ItmDet_ itmDet) {
        this.itmDet = itmDet;
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

    public Itm_ withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("num", num).append("itmDet", itmDet).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(itmDet).append(additionalProperties).append(num).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Itm_) == false) {
            return false;
        }
        Itm_ rhs = ((Itm_) other);
        return new EqualsBuilder().append(itmDet, rhs.itmDet).append(additionalProperties, rhs.additionalProperties).append(num, rhs.num).isEquals();
    }

}
