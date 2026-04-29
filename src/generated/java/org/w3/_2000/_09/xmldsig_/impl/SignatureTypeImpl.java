
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
import org.w3._2000._09.xmldsig_.KeyInfoType;
import org.w3._2000._09.xmldsig_.ObjectType;
import org.w3._2000._09.xmldsig_.SignatureType;
import org.w3._2000._09.xmldsig_.SignatureValueType;
import org.w3._2000._09.xmldsig_.SignedInfoType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SignatureType", propOrder = {
    "signedInfo",
    "signatureValue",
    "keyInfo",
    "object"
})
public class SignatureTypeImpl
    implements Serializable, SignatureType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "SignedInfo", required = true, type = SignedInfoTypeImpl.class)
    protected SignedInfoTypeImpl signedInfo;
    @XmlElement(name = "SignatureValue", required = true, type = SignatureValueTypeImpl.class)
    protected SignatureValueTypeImpl signatureValue;
    @XmlElement(name = "KeyInfo", type = KeyInfoTypeImpl.class)
    protected KeyInfoTypeImpl keyInfo;
    @XmlElement(name = "Object", type = ObjectTypeImpl.class)
    protected List<ObjectType> object;
    @XmlAttribute(name = "Id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    public SignedInfoType getSignedInfo() {
        return signedInfo;
    }

    public void setSignedInfo(SignedInfoType value) {
        this.signedInfo = ((SignedInfoTypeImpl) value);
    }

    public SignatureValueType getSignatureValue() {
        return signatureValue;
    }

    public void setSignatureValue(SignatureValueType value) {
        this.signatureValue = ((SignatureValueTypeImpl) value);
    }

    public KeyInfoType getKeyInfo() {
        return keyInfo;
    }

    public void setKeyInfo(KeyInfoType value) {
        this.keyInfo = ((KeyInfoTypeImpl) value);
    }

    public List<ObjectType> getObject() {
        if (object == null) {
            object = new ArrayList<ObjectType>();
        }
        return this.object;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

}
