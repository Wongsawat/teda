
package com.wpanther.etax.generated.receipt.ram.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.wpanther.etax.generated.common.qdt.Max16CodeType;
import com.wpanther.etax.generated.common.qdt.Max256TextType;
import com.wpanther.etax.generated.common.qdt.impl.Max16CodeTypeImpl;
import com.wpanther.etax.generated.common.qdt.impl.Max256TextTypeImpl;
import com.wpanther.etax.generated.receipt.ram.DesignatedProductClassificationType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DesignatedProductClassificationType", propOrder = {
    "classCode",
    "className"
})
public class DesignatedProductClassificationTypeImpl
    implements Serializable, DesignatedProductClassificationType
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ClassCode", type = Max16CodeTypeImpl.class)
    protected Max16CodeTypeImpl classCode;
    @XmlElement(name = "ClassName", type = Max256TextTypeImpl.class)
    protected List<Max256TextType> className;

    public Max16CodeType getClassCode() {
        return classCode;
    }

    public void setClassCode(Max16CodeType value) {
        this.classCode = ((Max16CodeTypeImpl) value);
    }

    public List<Max256TextType> getClassName() {
        if (className == null) {
            className = new ArrayList<Max256TextType>();
        }
        return this.className;
    }

}
