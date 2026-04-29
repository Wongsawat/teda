
package com.wpanther.etax.generated.cancellationnote.ram.impl;

import java.io.Serializable;
import com.wpanther.etax.generated.cancellationnote.ram.DocumentContextParameterType;
import com.wpanther.etax.generated.cancellationnote.ram.ExchangedDocumentContextType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExchangedDocumentContextType", propOrder = {
    "guidelineSpecifiedDocumentContextParameter"
})
public class ExchangedDocumentContextTypeImpl
    implements Serializable, ExchangedDocumentContextType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "GuidelineSpecifiedDocumentContextParameter", required = true, type = DocumentContextParameterTypeImpl.class)
    protected DocumentContextParameterTypeImpl guidelineSpecifiedDocumentContextParameter;

    public DocumentContextParameterType getGuidelineSpecifiedDocumentContextParameter() {
        return guidelineSpecifiedDocumentContextParameter;
    }

    public void setGuidelineSpecifiedDocumentContextParameter(DocumentContextParameterType value) {
        this.guidelineSpecifiedDocumentContextParameter = ((DocumentContextParameterTypeImpl) value);
    }

}
