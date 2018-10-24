
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
    "from",
    "to",
    "totnum",
    "cancel",
    "net_issue"
})
public class Doc {

    @JsonProperty("num")
    private Integer num;
    @JsonProperty("from")
    private String from;
    @JsonProperty("to")
    private String to;
    @JsonProperty("totnum")
    private Integer totnum;
    @JsonProperty("cancel")
    private Integer cancel;
    @JsonProperty("net_issue")
    private Integer netIssue;
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

    public Doc withNum(Integer num) {
        this.num = num;
        return this;
    }

    @JsonProperty("from")
    public String getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(String from) {
        this.from = from;
    }

    public Doc withFrom(String from) {
        this.from = from;
        return this;
    }

    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
    }

    public Doc withTo(String to) {
        this.to = to;
        return this;
    }

    @JsonProperty("totnum")
    public Integer getTotnum() {
        return totnum;
    }

    @JsonProperty("totnum")
    public void setTotnum(Integer totnum) {
        this.totnum = totnum;
    }

    public Doc withTotnum(Integer totnum) {
        this.totnum = totnum;
        return this;
    }

    @JsonProperty("cancel")
    public Integer getCancel() {
        return cancel;
    }

    @JsonProperty("cancel")
    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }

    public Doc withCancel(Integer cancel) {
        this.cancel = cancel;
        return this;
    }

    @JsonProperty("net_issue")
    public Integer getNetIssue() {
        return netIssue;
    }

    @JsonProperty("net_issue")
    public void setNetIssue(Integer netIssue) {
        this.netIssue = netIssue;
    }

    public Doc withNetIssue(Integer netIssue) {
        this.netIssue = netIssue;
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

    public Doc withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("num", num).append("from", from).append("to", to).append("totnum", totnum).append("cancel", cancel).append("netIssue", netIssue).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cancel).append(num).append(totnum).append(from).append(to).append(additionalProperties).append(netIssue).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Doc) == false) {
            return false;
        }
        Doc rhs = ((Doc) other);
        return new EqualsBuilder().append(cancel, rhs.cancel).append(num, rhs.num).append(totnum, rhs.totnum).append(from, rhs.from).append(to, rhs.to).append(additionalProperties, rhs.additionalProperties).append(netIssue, rhs.netIssue).isEquals();
    }

}
