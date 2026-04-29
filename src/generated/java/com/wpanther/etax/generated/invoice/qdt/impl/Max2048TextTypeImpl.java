
package com.wpanther.etax.generated.invoice.qdt.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.invoice.qdt.Max2048TextType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Max2048TextType", propOrder = {
    "value"
})
public class Max2048TextTypeImpl
    implements Serializable, Max2048TextType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String value;
    @XmlAttribute(name = "languageID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String languageID;
    @XmlAttribute(name = "languageLocaleID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String languageLocaleID;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String value) {
        this.languageID = value;
    }

    public String getLanguageLocaleID() {
        return languageLocaleID;
    }

    public void setLanguageLocaleID(String value) {
        this.languageLocaleID = value;
    }

}
