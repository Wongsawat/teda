
package com.wpanther.etax.generated.receipt.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.receipt.ram.LineTradeAgreementType;
import com.wpanther.etax.generated.receipt.ram.TradePriceType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineTradeAgreementType", propOrder = {
    "grossPriceProductTradePrice"
})
public class LineTradeAgreementTypeImpl
    implements Serializable, LineTradeAgreementType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "GrossPriceProductTradePrice", type = TradePriceTypeImpl.class)
    protected TradePriceTypeImpl grossPriceProductTradePrice;

    public TradePriceType getGrossPriceProductTradePrice() {
        return grossPriceProductTradePrice;
    }

    public void setGrossPriceProductTradePrice(TradePriceType value) {
        this.grossPriceProductTradePrice = ((TradePriceTypeImpl) value);
    }

}
