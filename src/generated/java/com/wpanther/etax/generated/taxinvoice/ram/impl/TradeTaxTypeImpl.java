
package com.wpanther.etax.generated.taxinvoice.ram.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.common.qdt.AmountType;
import com.wpanther.etax.generated.common.qdt.TaxTypeCodeType;
import com.wpanther.etax.generated.common.qdt.impl.AmountTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.TaxTypeCodeTypeImpl;
import com.wpanther.etax.generated.taxinvoice.ram.TradeTaxType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeTaxType", propOrder = {
    "typeCode",
    "calculatedRate",
    "basisAmount",
    "calculatedAmount"
})
public class TradeTaxTypeImpl
    implements Serializable, TradeTaxType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "TypeCode", required = true, type = TaxTypeCodeTypeImpl.class)
    protected TaxTypeCodeTypeImpl typeCode;
    @XmlElement(name = "CalculatedRate", required = true)
    protected BigDecimal calculatedRate;
    @XmlElement(name = "BasisAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> basisAmount;
    @XmlElement(name = "CalculatedAmount", required = true, type = AmountTypeImpl.class)
    protected List<AmountType> calculatedAmount;

    public TaxTypeCodeType getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(TaxTypeCodeType value) {
        this.typeCode = ((TaxTypeCodeTypeImpl) value);
    }

    public BigDecimal getCalculatedRate() {
        return calculatedRate;
    }

    public void setCalculatedRate(BigDecimal value) {
        this.calculatedRate = value;
    }

    public List<AmountType> getBasisAmount() {
        if (basisAmount == null) {
            basisAmount = new ArrayList<AmountType>();
        }
        return this.basisAmount;
    }

    public List<AmountType> getCalculatedAmount() {
        if (calculatedAmount == null) {
            calculatedAmount = new ArrayList<AmountType>();
        }
        return this.calculatedAmount;
    }

}
