
package org.w3._2000._09.xmldsig_.impl;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.RetrievalMethodType;
import org.w3._2000._09.xmldsig_.TransformsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrievalMethodType", propOrder = {
    "transforms"
})
public class RetrievalMethodTypeImpl
    implements Serializable, RetrievalMethodType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Transforms", type = TransformsTypeImpl.class)
    protected TransformsTypeImpl transforms;
    @XmlAttribute(name = "URI")
    @XmlSchemaType(name = "anyURI")
    protected String uri;
    @XmlAttribute(name = "Type")
    @XmlSchemaType(name = "anyURI")
    protected String type;

    public TransformsType getTransforms() {
        return transforms;
    }

    public void setTransforms(TransformsType value) {
        this.transforms = ((TransformsTypeImpl) value);
    }

    public String getURI() {
        return uri;
    }

    public void setURI(String value) {
        this.uri = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

}
