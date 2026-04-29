
package com.wpanther.etax.generated.taxinvoice.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.common.qdt.Max256TextType;
import com.wpanther.etax.generated.common.qdt.Max35IDType;
import com.wpanther.etax.generated.common.qdt.Max70IDType;
import com.wpanther.etax.generated.common.qdt.impl.Max256TextTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max70IDTypeImpl;
import com.wpanther.etax.generated.taxinvoice.ram.NoteType;
import com.wpanther.etax.generated.taxinvoice.ram.ProductClassificationType;
import com.wpanther.etax.generated.taxinvoice.ram.TradeCountryType;
import com.wpanther.etax.generated.taxinvoice.ram.TradeProductInstanceType;
import com.wpanther.etax.generated.taxinvoice.ram.TradeProductType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeProductType", propOrder = {
    "id",
    "globalID",
    "name",
    "description",
    "individualTradeProductInstance",
    "designatedProductClassification",
    "originTradeCountry",
    "informationNote"
})
public class TradeProductTypeImpl
    implements Serializable, TradeProductType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", type = Max35IDTypeImpl.class)
    protected Max35IDTypeImpl id;
    @XmlElement(name = "GlobalID", type = Max70IDTypeImpl.class)
    protected Max70IDTypeImpl globalID;
    @XmlElement(name = "Name", required = true, type = Max256TextTypeImpl.class)
    protected List<Max256TextType> name;
    @XmlElement(name = "Description", type = Max256TextTypeImpl.class)
    protected List<Max256TextType> description;
    @XmlElement(name = "IndividualTradeProductInstance", type = TradeProductInstanceTypeImpl.class)
    protected List<TradeProductInstanceType> individualTradeProductInstance;
    @XmlElement(name = "DesignatedProductClassification", type = ProductClassificationTypeImpl.class)
    protected ProductClassificationTypeImpl designatedProductClassification;
    @XmlElement(name = "OriginTradeCountry", type = TradeCountryTypeImpl.class)
    protected TradeCountryTypeImpl originTradeCountry;
    @XmlElement(name = "InformationNote", type = NoteTypeImpl.class)
    protected List<NoteType> informationNote;

    public Max35IDType getID() {
        return id;
    }

    public void setID(Max35IDType value) {
        this.id = ((Max35IDTypeImpl) value);
    }

    public Max70IDType getGlobalID() {
        return globalID;
    }

    public void setGlobalID(Max70IDType value) {
        this.globalID = ((Max70IDTypeImpl) value);
    }

    public List<Max256TextType> getName() {
        if (name == null) {
            name = new ArrayList<Max256TextType>();
        }
        return this.name;
    }

    public List<Max256TextType> getDescription() {
        if (description == null) {
            description = new ArrayList<Max256TextType>();
        }
        return this.description;
    }

    public List<TradeProductInstanceType> getIndividualTradeProductInstance() {
        if (individualTradeProductInstance == null) {
            individualTradeProductInstance = new ArrayList<TradeProductInstanceType>();
        }
        return this.individualTradeProductInstance;
    }

    public ProductClassificationType getDesignatedProductClassification() {
        return designatedProductClassification;
    }

    public void setDesignatedProductClassification(ProductClassificationType value) {
        this.designatedProductClassification = ((ProductClassificationTypeImpl) value);
    }

    public TradeCountryType getOriginTradeCountry() {
        return originTradeCountry;
    }

    public void setOriginTradeCountry(TradeCountryType value) {
        this.originTradeCountry = ((TradeCountryTypeImpl) value);
    }

    public List<NoteType> getInformationNote() {
        if (informationNote == null) {
            informationNote = new ArrayList<NoteType>();
        }
        return this.informationNote;
    }

}
