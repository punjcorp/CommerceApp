
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
    "doc_num",
    "doc_typ",
    "docs"
})
public class DocDet {

    @JsonProperty("doc_num")
    private Integer docNum;
    @JsonProperty("doc_typ")
    private String docTyp;
    @JsonProperty("docs")
    private List<Doc> docs = new ArrayList<Doc>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("doc_num")
    public Integer getDocNum() {
        return docNum;
    }

    @JsonProperty("doc_num")
    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    public DocDet withDocNum(Integer docNum) {
        this.docNum = docNum;
        return this;
    }

    @JsonProperty("doc_typ")
    public String getDocTyp() {
        return docTyp;
    }

    @JsonProperty("doc_typ")
    public void setDocTyp(String docTyp) {
        this.docTyp = docTyp;
    }

    public DocDet withDocTyp(String docTyp) {
        this.docTyp = docTyp;
        return this;
    }

    @JsonProperty("docs")
    public List<Doc> getDocs() {
        return docs;
    }

    @JsonProperty("docs")
    public void setDocs(List<Doc> docs) {
        this.docs = docs;
    }

    public DocDet withDocs(List<Doc> docs) {
        this.docs = docs;
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

    public DocDet withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("docNum", docNum).append("docTyp", docTyp).append("docs", docs).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(docNum).append(additionalProperties).append(docTyp).append(docs).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DocDet) == false) {
            return false;
        }
        DocDet rhs = ((DocDet) other);
        return new EqualsBuilder().append(docNum, rhs.docNum).append(additionalProperties, rhs.additionalProperties).append(docTyp, rhs.docTyp).append(docs, rhs.docs).isEquals();
    }

}
