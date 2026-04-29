
package com.wpanther.etax.generated.invoice.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.common.udt.TextType;
import com.wpanther.etax.generated.common.udt.impl.TextTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.CountryIDType;
import com.wpanther.etax.generated.invoice.qdt.Max16CodeType;
import com.wpanther.etax.generated.invoice.qdt.Max16TextType;
import com.wpanther.etax.generated.invoice.qdt.Max256TextType;
import com.wpanther.etax.generated.invoice.qdt.Max35IDType;
import com.wpanther.etax.generated.invoice.qdt.Max70TextType;
import com.wpanther.etax.generated.invoice.qdt.impl.CountryIDTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.Max16CodeTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.Max16TextTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.Max256TextTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.Max35IDTypeImpl;
import com.wpanther.etax.generated.invoice.qdt.impl.Max70TextTypeImpl;
import com.wpanther.etax.generated.invoice.ram.TradeAddressType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeAddressType", propOrder = {
    "postcodeCode",
    "buildingName",
    "lineOne",
    "lineTwo",
    "lineThree",
    "lineFour",
    "lineFive",
    "streetName",
    "cityName",
    "citySubDivisionName",
    "countryID",
    "countrySubDivisionID",
    "buildingNumber"
})
public class TradeAddressTypeImpl
    implements Serializable, TradeAddressType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "PostcodeCode", type = Max16CodeTypeImpl.class)
    protected Max16CodeTypeImpl postcodeCode;
    @XmlElement(name = "BuildingName", type = Max70TextTypeImpl.class)
    protected Max70TextTypeImpl buildingName;
    @XmlElement(name = "LineOne", type = Max256TextTypeImpl.class)
    protected Max256TextTypeImpl lineOne;
    @XmlElement(name = "LineTwo", type = Max256TextTypeImpl.class)
    protected Max256TextTypeImpl lineTwo;
    @XmlElement(name = "LineThree", type = Max70TextTypeImpl.class)
    protected Max70TextTypeImpl lineThree;
    @XmlElement(name = "LineFour", type = Max70TextTypeImpl.class)
    protected Max70TextTypeImpl lineFour;
    @XmlElement(name = "LineFive", type = Max70TextTypeImpl.class)
    protected Max70TextTypeImpl lineFive;
    @XmlElement(name = "StreetName", type = Max70TextTypeImpl.class)
    protected Max70TextTypeImpl streetName;
    @XmlElement(name = "CityName", type = TextTypeImpl.class)
    protected TextTypeImpl cityName;
    @XmlElement(name = "CitySubDivisionName", type = TextTypeImpl.class)
    protected TextTypeImpl citySubDivisionName;
    @XmlElement(name = "CountryID", type = CountryIDTypeImpl.class)
    protected CountryIDTypeImpl countryID;
    @XmlElement(name = "CountrySubDivisionID", type = Max35IDTypeImpl.class)
    protected Max35IDTypeImpl countrySubDivisionID;
    @XmlElement(name = "BuildingNumber", type = Max16TextTypeImpl.class)
    protected Max16TextTypeImpl buildingNumber;

    public Max16CodeType getPostcodeCode() {
        return postcodeCode;
    }

    public void setPostcodeCode(Max16CodeType value) {
        this.postcodeCode = ((Max16CodeTypeImpl) value);
    }

    public Max70TextType getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(Max70TextType value) {
        this.buildingName = ((Max70TextTypeImpl) value);
    }

    public Max256TextType getLineOne() {
        return lineOne;
    }

    public void setLineOne(Max256TextType value) {
        this.lineOne = ((Max256TextTypeImpl) value);
    }

    public Max256TextType getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(Max256TextType value) {
        this.lineTwo = ((Max256TextTypeImpl) value);
    }

    public Max70TextType getLineThree() {
        return lineThree;
    }

    public void setLineThree(Max70TextType value) {
        this.lineThree = ((Max70TextTypeImpl) value);
    }

    public Max70TextType getLineFour() {
        return lineFour;
    }

    public void setLineFour(Max70TextType value) {
        this.lineFour = ((Max70TextTypeImpl) value);
    }

    public Max70TextType getLineFive() {
        return lineFive;
    }

    public void setLineFive(Max70TextType value) {
        this.lineFive = ((Max70TextTypeImpl) value);
    }

    public Max70TextType getStreetName() {
        return streetName;
    }

    public void setStreetName(Max70TextType value) {
        this.streetName = ((Max70TextTypeImpl) value);
    }

    public TextType getCityName() {
        return cityName;
    }

    public void setCityName(TextType value) {
        this.cityName = ((TextTypeImpl) value);
    }

    public TextType getCitySubDivisionName() {
        return citySubDivisionName;
    }

    public void setCitySubDivisionName(TextType value) {
        this.citySubDivisionName = ((TextTypeImpl) value);
    }

    public CountryIDType getCountryID() {
        return countryID;
    }

    public void setCountryID(CountryIDType value) {
        this.countryID = ((CountryIDTypeImpl) value);
    }

    public Max35IDType getCountrySubDivisionID() {
        return countrySubDivisionID;
    }

    public void setCountrySubDivisionID(Max35IDType value) {
        this.countrySubDivisionID = ((Max35IDTypeImpl) value);
    }

    public Max16TextType getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(Max16TextType value) {
        this.buildingNumber = ((Max16TextTypeImpl) value);
    }

}
