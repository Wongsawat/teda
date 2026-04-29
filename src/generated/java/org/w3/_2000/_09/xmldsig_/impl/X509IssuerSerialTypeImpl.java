
package org.w3._2000._09.xmldsig_.impl;

import java.io.Serializable;
import java.math.BigInteger;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.X509IssuerSerialType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "X509IssuerSerialType", propOrder = {
    "x509IssuerName",
    "x509SerialNumber"
})
public class X509IssuerSerialTypeImpl
    implements Serializable, X509IssuerSerialType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "X509IssuerName", required = true)
    protected String x509IssuerName;
    @XmlElement(name = "X509SerialNumber", required = true)
    protected BigInteger x509SerialNumber;

    public String getX509IssuerName() {
        return x509IssuerName;
    }

    public void setX509IssuerName(String value) {
        this.x509IssuerName = value;
    }

    public BigInteger getX509SerialNumber() {
        return x509SerialNumber;
    }

    public void setX509SerialNumber(BigInteger value) {
        this.x509SerialNumber = value;
    }

}
