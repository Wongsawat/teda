
package org.w3._2000._09.xmldsig_.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3._2000._09.xmldsig_.CanonicalizationMethodType;
import org.w3._2000._09.xmldsig_.ReferenceType;
import org.w3._2000._09.xmldsig_.SignatureMethodType;
import org.w3._2000._09.xmldsig_.SignedInfoType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignedInfoType", propOrder = {
    "canonicalizationMethod",
    "signatureMethod",
    "reference"
})
public class SignedInfoTypeImpl
    implements Serializable, SignedInfoType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "CanonicalizationMethod", required = true, type = CanonicalizationMethodTypeImpl.class)
    protected CanonicalizationMethodTypeImpl canonicalizationMethod;
    @XmlElement(name = "SignatureMethod", required = true, type = SignatureMethodTypeImpl.class)
    protected SignatureMethodTypeImpl signatureMethod;
    @XmlElement(name = "Reference", required = true, type = ReferenceTypeImpl.class)
    protected List<ReferenceType> reference;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    public CanonicalizationMethodType getCanonicalizationMethod() {
        return canonicalizationMethod;
    }

    public void setCanonicalizationMethod(CanonicalizationMethodType value) {
        this.canonicalizationMethod = ((CanonicalizationMethodTypeImpl) value);
    }

    public SignatureMethodType getSignatureMethod() {
        return signatureMethod;
    }

    public void setSignatureMethod(SignatureMethodType value) {
        this.signatureMethod = ((SignatureMethodTypeImpl) value);
    }

    public List<ReferenceType> getReference() {
        if (reference == null) {
            reference = new ArrayList<ReferenceType>();
        }
        return this.reference;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

}
