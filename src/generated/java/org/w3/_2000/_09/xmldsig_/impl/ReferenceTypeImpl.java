
package org.w3._2000._09.xmldsig_.impl;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3._2000._09.xmldsig_.DigestMethodType;
import org.w3._2000._09.xmldsig_.ReferenceType;
import org.w3._2000._09.xmldsig_.TransformsType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferenceType", propOrder = {
    "transforms",
    "digestMethod",
    "digestValue"
})
public class ReferenceTypeImpl
    implements Serializable, ReferenceType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Transforms", type = TransformsTypeImpl.class)
    protected TransformsTypeImpl transforms;
    @XmlElement(name = "DigestMethod", required = true, type = DigestMethodTypeImpl.class)
    protected DigestMethodTypeImpl digestMethod;
    @XmlElement(name = "DigestValue", required = true)
    protected byte[] digestValue;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
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

    public DigestMethodType getDigestMethod() {
        return digestMethod;
    }

    public void setDigestMethod(DigestMethodType value) {
        this.digestMethod = ((DigestMethodTypeImpl) value);
    }

    public byte[] getDigestValue() {
        return digestValue;
    }

    public void setDigestValue(byte[] value) {
        this.digestValue = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
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
