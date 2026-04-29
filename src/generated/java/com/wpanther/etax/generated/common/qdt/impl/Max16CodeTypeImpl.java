
package com.wpanther.etax.generated.common.qdt.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.qdt.Max16CodeType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlValue;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Max16CodeType", propOrder = {
    "value"
})
public class Max16CodeTypeImpl
    implements Serializable, Max16CodeType
{

    private final static long serialVersionUID = 1L;
    @XmlValue
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String value;
    @XmlAttribute(name = "listID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String listID;
    @XmlAttribute(name = "listAgencyID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String listAgencyID;
    @XmlAttribute(name = "listAgencyName")
    protected String listAgencyName;
    @XmlAttribute(name = "listName")
    protected String listName;
    @XmlAttribute(name = "listVersionID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String listVersionID;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "languageID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String languageID;
    @XmlAttribute(name = "listURI")
    @XmlSchemaType(name = "anyURI")
    protected String listURI;
    @XmlAttribute(name = "listSchemeURI")
    @XmlSchemaType(name = "anyURI")
    protected String listSchemeURI;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getListID() {
        return listID;
    }

    public void setListID(String value) {
        this.listID = value;
    }

    public String getListAgencyID() {
        return listAgencyID;
    }

    public void setListAgencyID(String value) {
        this.listAgencyID = value;
    }

    public String getListAgencyName() {
        return listAgencyName;
    }

    public void setListAgencyName(String value) {
        this.listAgencyName = value;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String value) {
        this.listName = value;
    }

    public String getListVersionID() {
        return listVersionID;
    }

    public void setListVersionID(String value) {
        this.listVersionID = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String value) {
        this.languageID = value;
    }

    public String getListURI() {
        return listURI;
    }

    public void setListURI(String value) {
        this.listURI = value;
    }

    public String getListSchemeURI() {
        return listSchemeURI;
    }

    public void setListSchemeURI(String value) {
        this.listSchemeURI = value;
    }

}
