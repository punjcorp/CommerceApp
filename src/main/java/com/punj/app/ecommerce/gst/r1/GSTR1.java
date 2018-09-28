
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
    "gstin",
    "fp",
    "gt",
    "cur_gt",
    "b2b",
    "b2cl",
    "b2cs",
    "hsn",
    "doc_issue"
})
public class GSTR1 {

    @JsonProperty("gstin")
    private String gstin;
    @JsonProperty("fp")
    private String fp;
    @JsonProperty("gt")
    private Double gt;
    @JsonProperty("cur_gt")
    private Double curGt;
    @JsonProperty("b2b")
    private List<B2b> b2b = new ArrayList<B2b>();
    @JsonProperty("b2cl")
    private List<B2cl> b2cl = new ArrayList<B2cl>();
    @JsonProperty("b2cs")
    private List<B2c> b2cs = new ArrayList<B2c>();
    @JsonProperty("hsn")
    private Hsn hsn;
    @JsonProperty("doc_issue")
    private DocIssue docIssue;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("gstin")
    public String getGstin() {
        return gstin;
    }

    @JsonProperty("gstin")
    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public GSTR1 withGstin(String gstin) {
        this.gstin = gstin;
        return this;
    }

    @JsonProperty("fp")
    public String getFp() {
        return fp;
    }

    @JsonProperty("fp")
    public void setFp(String fp) {
        this.fp = fp;
    }

    public GSTR1 withFp(String fp) {
        this.fp = fp;
        return this;
    }

    @JsonProperty("gt")
    public Double getGt() {
        return gt;
    }

    @JsonProperty("gt")
    public void setGt(Double gt) {
        this.gt = gt;
    }

    public GSTR1 withGt(Double gt) {
        this.gt = gt;
        return this;
    }

    @JsonProperty("cur_gt")
    public Double getCurGt() {
        return curGt;
    }

    @JsonProperty("cur_gt")
    public void setCurGt(Double curGt) {
        this.curGt = curGt;
    }

    public GSTR1 withCurGt(Double curGt) {
        this.curGt = curGt;
        return this;
    }

    @JsonProperty("b2b")
    public List<B2b> getB2b() {
        return b2b;
    }

    @JsonProperty("b2b")
    public void setB2b(List<B2b> b2b) {
        this.b2b = b2b;
    }

    public GSTR1 withB2b(List<B2b> b2b) {
        this.b2b = b2b;
        return this;
    }

    @JsonProperty("b2cl")
    public List<B2cl> getB2cl() {
        return b2cl;
    }

    @JsonProperty("b2cl")
    public void setB2cl(List<B2cl> b2cl) {
        this.b2cl = b2cl;
    }

    public GSTR1 withB2cl(List<B2cl> b2cl) {
        this.b2cl = b2cl;
        return this;
    }

    @JsonProperty("b2cs")
    public List<B2c> getB2cs() {
        return b2cs;
    }

    @JsonProperty("b2cs")
    public void setB2cs(List<B2c> b2cs) {
        this.b2cs = b2cs;
    }

    public GSTR1 withB2cs(List<B2c> b2cs) {
        this.b2cs = b2cs;
        return this;
    }

    @JsonProperty("hsn")
    public Hsn getHsn() {
        return hsn;
    }

    @JsonProperty("hsn")
    public void setHsn(Hsn hsn) {
        this.hsn = hsn;
    }

    public GSTR1 withHsn(Hsn hsn) {
        this.hsn = hsn;
        return this;
    }

    @JsonProperty("doc_issue")
    public DocIssue getDocIssue() {
        return docIssue;
    }

    @JsonProperty("doc_issue")
    public void setDocIssue(DocIssue docIssue) {
        this.docIssue = docIssue;
    }

    public GSTR1 withDocIssue(DocIssue docIssue) {
        this.docIssue = docIssue;
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

    public GSTR1 withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("gstin", gstin).append("fp", fp).append("gt", gt).append("curGt", curGt).append("b2b", b2b).append("b2cl", b2cl).append("b2cs", b2cs).append("hsn", hsn).append("docIssue", docIssue).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(hsn).append(docIssue).append(b2b).append(curGt).append(b2cl).append(fp).append(additionalProperties).append(gstin).append(gt).append(b2cs).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GSTR1) == false) {
            return false;
        }
        GSTR1 rhs = ((GSTR1) other);
        return new EqualsBuilder().append(hsn, rhs.hsn).append(docIssue, rhs.docIssue).append(b2b, rhs.b2b).append(curGt, rhs.curGt).append(b2cl, rhs.b2cl).append(fp, rhs.fp).append(additionalProperties, rhs.additionalProperties).append(gstin, rhs.gstin).append(gt, rhs.gt).append(b2cs, rhs.b2cs).isEquals();
    }

}
