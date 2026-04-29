
package org.w3._2000._09.xmldsig_.impl;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.RSAKeyValueType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RSAKeyValueType", propOrder = {
    "modulus",
    "exponent"
})
public class RSAKeyValueTypeImpl
    implements Serializable, RSAKeyValueType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "Modulus", required = true)
    protected byte[] modulus;
    @XmlElement(name = "Exponent", required = true)
    protected byte[] exponent;

    public byte[] getModulus() {
        return modulus;
    }

    public void setModulus(byte[] value) {
        this.modulus = value;
    }

    public byte[] getExponent() {
        return exponent;
    }

    public void setExponent(byte[] value) {
        this.exponent = value;
    }

}
